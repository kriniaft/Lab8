package commands;
import commands.base.*;

import java.util.HashMap;
import java.util.Iterator;

public class RemoveByPassport extends Command{
    private RemoveByPassport(){
        super("remove_any_by_passport_i_d");
    }

    public void execute(Environment env) throws NullException {
        FieldsWork fw = new FieldsWork();
        String pID = fw.passport();

        if (env.getProfiles() == null || env.getProfiles().isEmpty()) {
            System.out.println("Коллекция пуста или не инициализирована");
            return;
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
            System.out.println("Человек с паспортным ID '" + pID + "' успешно удален.");
        } else {
            System.out.println("Человек с паспортным ID '" + pID + "' не найден в коллекции.");
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
