package commands;
import commands.base.*;
import database.DatabaseConnector;

import java.io.InputStream;
import java.io.PrintStream;
import java.util.HashMap;
import java.util.Iterator;

public class RemoveByPassport extends Command{
    private RemoveByPassport(){
        super("remove_any_by_passport_id");
    }

    public void execute(Environment env, InputStream sIn, PrintStream sOut, String[] commandsArgs, DatabaseConnector db) throws NullException {
        FieldsWork fw = new FieldsWork();
        String pID;

        if (env.getProfiles() == null || env.getProfiles().isEmpty()) {
            sOut.println("Коллекция пуста или не инициализирована");
            return;
        }

        if (commandsArgs.length > 0) {
            try {
                if (!commandsArgs[0].matches("\\d+")) {
                    throw new NumberFormatException();
                }
                pID = commandsArgs[0];
            } catch (NumberFormatException e) {
                sOut.println("Ошибка: серия и номер паспорта должны содержать только цифры");
                return;
            }
        }

        else {
            pID = fw.passport(sIn, sOut);
        }


        boolean removed = false;
        Iterator<basic.Person> iterator = env.getProfiles().iterator();
        while (iterator.hasNext()) {
            basic.Person person = iterator.next();
            if (person.getPassportID().equals(pID)) {
                iterator.remove();
                removed = true;
                break;
            }
        }

        if (removed) {
            sOut.println("Человек с паспортным ID '" + pID + "' успешно удален.");
        } else {
            sOut.println("Человек с паспортным ID '" + pID + "' не найден в коллекции.");
        }
    }

    public String getHelp(){
        return "удаляет из коллекции элемент с таким же номером паспорта";
    }

    public static void register(HashMap<String, Command> stringCommandHashMap) {
        RemoveByPassport rbp = new RemoveByPassport();
        stringCommandHashMap.put(rbp.getName(), rbp);
    }
}
