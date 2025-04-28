import java.util.HashMap;
import java.util.Scanner;

import commands.*;
import basic.*;
import commands.base.Command;
import commands.base.Environment;

import javax.naming.InvalidNameException;

public class Main{
    public static void main(String[] args) throws NullException, InvalidNameException {
        CollectionController coll = new CollectionController();
        System.out.println("Введите название команды или help, чтобы узнать больше о командах (для выхода используйте 'exit')");

        Scanner in = new Scanner(System.in);

        HashMap<String, Command> map = new HashMap<>();
        Add.register(map);
        AddIfMax.register(map);
        Clear.register(map);
        CountBySameHeight.register(map);
        ExecuteScript.register(map);
        Exit.register(map);
        Help.register(map);
        Info.register(map);
        PrintUniqueHair.register(map);
        RemoveByID.register(map);
        RemoveByPassport.register(map);
        RemoveGreater.register(map);
        Save.register(map);
        Show.register(map);
        UpdateID.register(map);



        Environment environment = new Environment(map, null);

        while (in.hasNextLine()) {
            String line = in.nextLine();
            if (map.keySet().contains(line)) {
                Command command = map.get(line);
                command.execute(environment);
            } else {
                System.err.println("Такой команды не существует, введите другую");
            }


        }
    }
}
