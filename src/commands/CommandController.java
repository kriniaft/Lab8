package commands;
import commands.base.*;
import java.util.HashMap;
import java.util.Scanner;

public class CommandController {
    public void command(Environment env) throws NullException {
        System.out.print("Введите команду: ");
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
                command.execute(env);
            }catch(NullException e){
                System.out.println("Команда  не найдена");
            }
        }
    }
}
