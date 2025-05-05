package commands;
import commands.base.*;

import java.io.InputStream;
import java.io.PrintStream;
import java.util.HashMap;

public class ExecuteScript{

    public String getHelp(){
        return "исполнит скрипт из файла";
    }

    public static void register(HashMap<String, Command> stringCommandHashMap) {
        ExecuteScript executeScript = new ExecuteScript();
    }
}
