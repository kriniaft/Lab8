package commands;
import commands.base.*;

import java.util.HashMap;

public class Info extends Command {
    private Info(){
        super("info");
    }

    @Override
    public void execute(Environment env) {

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
