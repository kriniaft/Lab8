package commands;

import basic.*;
import commands.base.Command;
import commands.base.Environment;

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
    public void execute(Environment env, InputStream sIn, PrintStream sOut, String[] commandsArgs) throws NullException{
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
}
