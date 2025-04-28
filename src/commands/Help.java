package commands;

import commands.base.Command;
import commands.base.Environment;

import java.util.HashMap;

public class Help extends Command {
    private Help() {
        super("help");
    }

    public void execute(Environment env) {
        HashMap<String, Command> stringCommandHashMap = env.getStringCommandHashmap();
        stringCommandHashMap.forEach((key, value) -> {
            System.out.println(key + ": " + value.getHelp());
        });

    }


    public static void register(HashMap<String, Command> stringCommandHashMap) {
        Help help = new Help();
        stringCommandHashMap.put(help.getName(), help);
    }
}
