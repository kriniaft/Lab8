package commands;

import commands.base.Command;
import commands.base.Environment;
import database.DatabaseConnector;

import java.io.InputStream;
import java.io.PrintStream;
import java.util.HashMap;

public class Clear extends Command {
    private Clear(){
        super("clear");
    }



    @Override
    public String getHelp(){
        return "Очищает коллекцию (удаляет все элементы)";
    }

    @Override
    public void execute(Environment env, InputStream sIn, PrintStream sOut, String[] commandsArgs, DatabaseConnector db){
        if (env.getProfiles() == null || env.getProfiles().isEmpty()) {
            sOut.println("Коллекция пуста или не инициализирована");
            return;
        }

        env.profiles.clear();
        sOut.println("Коллекция очищена");
    }

    public static void register(HashMap<String, Command> stringCommandHashMap) {
        Clear clear = new Clear();
        stringCommandHashMap.put(clear.getName(), clear);
    }

    public static void regForEveryone(HashMap<String, Command> mapForEveryone) {
        Clear clear = new Clear();
        mapForEveryone.put(clear.getName(), clear);
    }


}
