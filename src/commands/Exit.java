package commands;
import commands.base.*;

import java.io.InputStream;
import java.io.PrintStream;
import java.util.HashMap;

public class Exit extends Command {
    private Exit(){
        super("exit");
    }

    @Override
    public void execute(Environment env, InputStream sIn, PrintStream sOut) {
        sOut.println("Завершение программы...");
        System.exit(0);

    }



    @Override
    public String getHelp(){
        return "завершить программу (без сохранения в файл)";
    }

    public static void register(HashMap<String, Command> stringCommandHashMap) {
        Exit exit = new Exit();
        stringCommandHashMap.put(exit.getName(), exit);
    }
}
