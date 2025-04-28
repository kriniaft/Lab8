package commands;
import basic.Person;
import commands.base.*;

import java.util.HashMap;
import java.util.Iterator;

public class RemoveByID extends Command {
    private RemoveByID() {
        super("remove_by_id");
    }

    public void execute(Environment env) throws NullException{
        FieldsWork fw = new FieldsWork();
        long nID = fw.id();


        if (env.getProfiles() == null || env.getProfiles().isEmpty()) {
            System.out.println("Коллекция пуста или не инициализирована.");
            return;
        }

        boolean removed = false;
        Iterator<Person> iterator = env.getProfiles().iterator();
        while (iterator.hasNext()) {
            basic.Person person = iterator.next();
            if (person.getId() == nID) {
                iterator.remove();
                removed = true;
                break;
            }
        }

        if (removed) {
            System.out.println("Человек с ID '" + nID + "' успешно удален.");
        } else {
            System.out.println("Человек с ID '" + nID + "' не найден в коллекции.");
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