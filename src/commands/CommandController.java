package commands;
import commands.base.*;

import java.io.InputStream;
import java.io.PrintStream;
import java.util.HashMap;
import java.util.Scanner;

public class CommandController {
    public void command(Environment env, InputStream sIn, PrintStream sOut) throws NullException {
        sOut.print("Введите команду: ");
        Scanner sc = new Scanner(System.in);
        while(true) {
            String name = sc.nextLine();
            try {
                if (name.isEmpty()) {
                    throw new NullException("Команда не может быть пустой.");
                }
                HashMap<String, Command> commands = env.getStringCommandHashmap();
                Command command = commands.get(name);
                if (command == null) {
                    throw new NullException("Команда '" + name + "' не найдена.");
                }
                command.execute(env, sIn, sOut);
            }catch(NullException e){
                System.out.println("Команда  не найдена");
            }
        }
    }
}
