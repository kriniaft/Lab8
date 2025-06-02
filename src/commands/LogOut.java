package commands;

import commands.base.Command;
import commands.base.Environment;
import database.DatabaseConnector;

import java.io.InputStream;
import java.io.PrintStream;
import java.util.HashMap;

public class LogOut extends Command {
    LogOut(){
        super("logout");
    }
    public void execute(Environment env, InputStream sIn, PrintStream sOut, String[] commandsArgs, DatabaseConnector db){
        db.setUserNow(null);
        System.out.println("Вы вышли из аккаунта");
    }

    public String getHelp(){
        return "выполняет выход из аккаунта";
    }
    public static void register(HashMap<String, Command> stringCommandHashMap) {
        LogOut logout = new LogOut();
        stringCommandHashMap.put(logout.getName(), logout);
    }
}
