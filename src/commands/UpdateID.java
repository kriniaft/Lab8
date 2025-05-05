package commands;
import basic.*;
import commands.base.*;

import java.io.InputStream;
import java.io.PrintStream;
import java.util.HashMap;
import java.util.Iterator;

public class UpdateID extends Command {
    private UpdateID() {
        super("update_id");
    }

    @Override
    public void execute(Environment env, InputStream sIn, PrintStream sOut, String[] commandsArgs) throws NullException {
        FieldsWork fw = new FieldsWork();
        long id;

        if (env.profiles == null || env.profiles.isEmpty()) {
            sOut.println("Коллекция пуста или не инициализирована");
            return;
        }

        Iterator<Person> iterator = env.profiles.iterator();
        boolean found = false;

        if (commandsArgs.length > 0) {
            try {
                id = Long.parseLong(commandsArgs[0]);
                if (id <= 0) throw new NumberFormatException();
            } catch (NumberFormatException e) {
                sOut.println("Ошибка: неверный формат ID. Используйте положительное число");
                return;
            }
        }
        else {
            sOut.println("Введите ID человека, чей рост нужно изменить:");
            id = fw.id(sIn, sOut);
        }

        while (iterator.hasNext()) {
            Person person = iterator.next();
            if (person.getId() == id) {
                float height = fw.height(sIn, sOut);
                person.setHeight(height);
                sOut.println("Рост успешно изменен");
                found = true;
                break;
            }
        }

        if(!found) {
            sOut.println("Не удалось изменить рост по введенному id");
        }
    }


    @Override
    public String getHelp(){
        return "Обновить значение элемента коллекции по id";
    }

    public static void register(HashMap<String, Command> stringCommandHashMap) {
        UpdateID updateID = new UpdateID();
        stringCommandHashMap.put(updateID.getName(), updateID);

    }
}
