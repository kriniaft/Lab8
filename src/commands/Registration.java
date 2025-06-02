package commands;
import commands.base.Command;
import commands.base.Environment;
import database.DatabaseConnector;

import java.io.InputStream;
import java.io.PrintStream;
import java.util.HashMap;
import java.util.Scanner;

public class Registration extends Command{
    private Registration(){
        super("registration");
    }
    public void execute(Environment env, InputStream sIn, PrintStream sOut, String[] commandsArgs, DatabaseConnector db) throws NullException {
        try{
            Scanner sc = new Scanner(System.in);
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
                db.registration(login, password);
            }
        } catch (NullException exc) {
            System.out.println(exc.getMessage());
        }
    }


    public String getHelp(){
        return "Регистрирует нового пользователя";
    }

    public static void register(HashMap<String, Command> stringCommandHashMap) {
        Registration reg = new Registration();
        stringCommandHashMap.put(reg.getName(), reg);
    }
    public static void regForEveryone(HashMap<String, Command> mapForEveryone) {
        Registration registration = new Registration();
        mapForEveryone.put(registration.getName(), registration);
    }
}

