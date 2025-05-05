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
            String input = sc.nextLine();
            String[] s = input.split(" ");
            String name = s[0];
            String[] commandsArgs = new String[s.length - 1];
            System.arraycopy(s, 1, commandsArgs, 0, commandsArgs.length);
            try {
                if (name.isEmpty()) {
                    throw new NullException("Команда не может быть пустой.");
                }
                HashMap<String, Command> commands = env.getStringCommandHashmap();
                Command command = commands.get(name);
                if (command == null) {
                    throw new NullException("Команда '" + name + "' не найдена.");
                }
                command.execute(env, sIn, sOut, commandsArgs);
            }catch(NullException e){
                System.out.println("Команда  не найдена");
            }
        }
    }
}
