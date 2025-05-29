package commands;
import basic.Person;
import commands.base.*;

import java.io.InputStream;
import java.io.PrintStream;
import java.time.LocalDateTime;
import java.util.ArrayDeque;
import java.util.HashMap;

public class Info extends Command {
    private Info(){
        super("info");
    }

    @Override
    public void execute(Environment env, InputStream sIn, PrintStream sOut, String[] commandsArgs) {
        ArrayDeque<Person> profiles = env.getProfiles();
        LocalDateTime initializationDate = env.getCollectionInitializationDate();

        if (profiles == null) {
            sOut.println("  Статус: Коллекция не инициализирована.");
            return;
        }
        sOut.println("  Тип коллекции: " + profiles.getClass().getSimpleName());
        sOut.println(initializationDate.toString());
        sOut.println("  Количество элементов: " + profiles.size());



    }


    @Override
    public String getHelp(){
        return "Выводит информацию о коллекции";
    }

    public static void register(HashMap<String, Command> stringCommandHashMap) {
        Info info = new Info();
        stringCommandHashMap.put(info.getName(), info);
    }
    public static void regForEveryone(HashMap<String, Command> mapForEveryone) {
        Info info = new Info();
        mapForEveryone.put(info.getName(), info);
    }
}
