package commands;

import commands.base.Command;
import commands.base.Environment;
import java.io.InputStream;
import java.io.PrintStream;
import java.util.HashMap;

public class Help extends Command {
    private Help() {
        super("help");
    }

    public void execute(Environment env, InputStream sIn, PrintStream sOut, String[] commandsArgs) {
        HashMap<String, Command> stringCommandHashMap = env.getStringCommandHashmap();
        stringCommandHashMap.forEach((key, value) -> {
            sOut.println(key + ": " + value.getHelp());
        });
        System.out.println("Учтите! Пользователям БЕЗ авторизации доступны ТОЛЬКО команды:");
        HashMap<String, Command> comForEveryone = env.getStringCommandHashmap();
        comForEveryone.forEach((key, value) -> {
            sOut.println(key + ": " + value.getHelp());
        });
    }

    public static void register(HashMap<String, Command> stringCommandHashMap) {
        Help help = new Help();
        stringCommandHashMap.put(help.getName(), help);
    }

    public static void regForEveryone(HashMap<String, Command> mapForEveryone) {
        Help help = new Help();
        mapForEveryone.put(help.getName(), help);
    }


    public String getHelp(){
        return "Выводит список всех команд";
    }



}
