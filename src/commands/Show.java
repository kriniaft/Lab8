package commands;

import basic.*;
import commands.base.Command;
import commands.base.Environment;
import database.DatabaseConnector;

import java.io.InputStream;
import java.io.PrintStream;
import java.util.ArrayDeque;
import java.util.HashMap;

public class Show extends Command {
    private Show(){
        super("show");
    }

    @Override
    public String getHelp(){
        return "Выводит все элементы коллекции";
    }

    @Override
    public void execute(Environment env, InputStream sIn, PrintStream sOut, String[] commandsArgs, DatabaseConnector db) throws NullException{
        if (env.getProfiles() == null || env.getProfiles().isEmpty()) {
            sOut.println("Коллекция пуста или не инициализирована");
            return;
        }

        sOut.println(env.profiles);
    }

    public static void register(HashMap<String, Command> stringCommandHashMap) {
        Show show = new Show();
        stringCommandHashMap.put(show.getName(), show);
    }

    public static void regForEveryone(HashMap<String, Command> mapForEveryone) {
        Show show = new Show();
        mapForEveryone.put(show.getName(), show);
    }
}
