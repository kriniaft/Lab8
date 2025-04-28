package commands;
import commands.base.*;

import java.util.HashMap;

public class ExecuteScript extends Command{
    private ExecuteScript(){
        super("execute_script");
    }



    @Override
    public String getHelp(){
        return "исполнит скрипт из файла";
    }

    public static void register(HashMap<String, Command> stringCommandHashMap) {
        ExecuteScript executeScript = new ExecuteScript();
        stringCommandHashMap.put(executeScript.getName(), executeScript);
    }
}
