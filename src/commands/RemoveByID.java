package commands;
import basic.Person;
import commands.base.*;
import database.DatabaseConnector;

import java.io.InputStream;
import java.io.PrintStream;
import java.sql.SQLException;
import java.util.ArrayDeque;
import java.util.HashMap;
import java.util.Iterator;

public class RemoveByID extends Command {
    private RemoveByID() {
        super("remove_by_id");
    }

    public void execute(Environment env, InputStream sIn, PrintStream sOut, String[] commandsArgs, DatabaseConnector db) throws NullException, SQLException {
        FieldsWork fw = new FieldsWork();
        long nID;
        if (env.getProfiles() == null || env.getProfiles().isEmpty()) {
            System.out.println("Коллекция пуста или не инициализирована.");
            return;
        }

        if (commandsArgs.length > 0) {
            try {
                nID = Long.parseLong(commandsArgs[0]);
                if (nID <= 0) throw new NumberFormatException();
            } catch (NumberFormatException e) {
                sOut.println("Ошибка: неверный формат ID. Используйте положительное число");
                return;
            }
        }
        else {
            nID = fw.id(sIn, sOut);
        }



        boolean removed = false;
        ArrayDeque<Person> persons = db.loadPersonsByUser(db.getUserNow());
        Iterator<Person> iterator = persons.iterator();
        while (iterator.hasNext()) {
            basic.Person person = iterator.next();
            if (person.getId() == nID) {
                db.deletePerson(person);
                removed = true;
                break;
            }
        }

        if (removed) {
            sOut.println("Человек с ID '" + nID + "' успешно удален.");
            env.setProfiles(db.getPersons());
        } else {
            sOut.println("Человек с ID '" + nID + "' не найден в коллекции.");
        }
    }


    public static void register(HashMap<String, Command> stringCommandHashMap) {
        RemoveByID rbi = new RemoveByID();
        stringCommandHashMap.put(rbi.getName(), rbi);
    }


    public String getHelp() {
        return "удалить элемент из коллекции по его id";
    }

}