package commands;
import basic.*;
import commands.base.*;
import database.DatabaseConnector;

import java.io.InputStream;
import java.io.PrintStream;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Iterator;

public class UpdateID extends Command {
    private UpdateID() {
        super("update_id");
    }

    @Override
    public void execute(Environment env, InputStream sIn, PrintStream sOut, String[] commandsArgs, DatabaseConnector db) throws NullException {
        FieldsWork fw = new FieldsWork();
        long id;

        if (env.profiles == null || env.profiles.isEmpty()) {
            sOut.println("Коллекция пуста или не инициализирована");
            return;
        }

        if (commandsArgs.length > 0) {
            try {
                id = Long.parseLong(commandsArgs[0]);
                if (id <= 0) throw new NumberFormatException();
            } catch (NumberFormatException e) {
                sOut.println("Ошибка: неверный формат ID. Используйте положительное число");
                return;
            }
        } else {
            id = fw.id(sIn, sOut);
        }

        float newHeight = fw.height(sIn, sOut);
        if(db.updateByUd(newHeight, id)) {
            for (Person person : env.profiles) {
                if (person.getId() == id) {
                    person.setHeight(newHeight);
                    return;
                }
            }
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
