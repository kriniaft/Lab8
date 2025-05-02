package commands;
import basic.*;
import commands.base.*;

import java.io.InputStream;
import java.io.PrintStream;
import java.util.HashMap;
import java.util.Iterator;

public class UpdateID extends Command {
    private UpdateID() {
        super("update id");
    }

    @Override
    public void execute(Environment env, InputStream sIn, PrintStream sOut) throws NullException {
        Iterator<Person> iterator = env.profiles.iterator();
        FieldsWork fw = new FieldsWork();
        long id = fw.id(sIn, sOut);
        boolean found = false;

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
