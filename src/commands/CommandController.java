package commands;
import commands.base.*;

import java.util.HashMap;
import java.util.Scanner;

public class CommandController {
    public void command(Environment env) throws NullException {
        System.out.print("Введите команду: ");
        while(true) {
            Scanner sc = new Scanner(System.in);

            String name = sc.nextLine();
            if (name.isEmpty()) {
                throw new IllegalArgumentException("Команда не может быть пустой.");
            }
            HashMap<String, Command> commands = env.getStringCommandHashmap();
            Command command = commands.get(name);
            command.execute(env);
        }
    }
}
