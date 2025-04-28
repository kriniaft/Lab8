package commands;
import commands.base.*;

import java.util.HashMap;

public class Exit extends Command {
    private Exit(){
        super("exit");
    }

    @Override
    public void execute(Environment env) {
        System.out.println("Завершение программы...");
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
