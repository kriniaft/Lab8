package commands;
import commands.base.Command;
import commands.base.Environment;
import database.DatabaseConnector;

import java.io.InputStream;
import java.io.PrintStream;
import java.util.HashMap;
import java.util.Scanner;

public class Login extends Command {
    Login (){
        super("login");
    }
    public void execute(Environment env, InputStream sIn, PrintStream sOut, String[] commandsArgs, DatabaseConnector db) throws NullException {
        try{
            Scanner sc = new Scanner(System.in);
            System.out.println("ведите логин:");
            while (true) {
                System.out.println("ведите логин:");
                String login = sc.nextLine();
                if (login.isEmpty()) {
                    throw new NullException("Вы ничего не ввели");
                }
                System.out.println("ведите пароль:");
                String password = sc.nextLine();
                if(password.isEmpty()){
                    throw new NullException("Вы ничего не ввели");
                }
                if (db.login(login, password)){
                    db.setUserNow(login);
                }
            }
        } catch (NullException exc) {
            System.out.println(exc.getMessage());
        }
    }

    public String getHelp(){
        return "выполняет вход в аккаунт";
    }
    public static void register(HashMap<String, Command> stringCommandHashMap) {
        Login login = new Login();
        stringCommandHashMap.put(login.getName(), login);
    }
}
