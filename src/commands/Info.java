package commands;
import basic.Person;
import commands.base.*;

import java.time.LocalDateTime;
import java.util.ArrayDeque;
import java.util.HashMap;

public class Info extends Command {
    private Info(){
        super("info");
    }

    @Override
    public void execute(Environment env) {
        ArrayDeque<Person> profiles = env.getProfiles();
        LocalDateTime initializationDate = env.getCollectionInitializationDate();

        if (profiles == null) {
            System.out.println("  Статус: Коллекция не инициализирована.");
            return;
        }
        System.out.println("  Тип коллекции: " + profiles.getClass().getSimpleName());
        System.out.println(initializationDate.toString());
        System.out.println("  Количество элементов: " + profiles.size());



    }


    @Override
    public String getHelp(){
        return "Выводит информацию о коллекции";
    }

    public static void register(HashMap<String, Command> stringCommandHashMap) {
        Info info = new Info();
        stringCommandHashMap.put(info.getName(), info);
    }
}
