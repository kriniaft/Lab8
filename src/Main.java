import java.util.ArrayDeque;
import java.util.HashMap;


import commands.*;
import basic.*;
import commands.base.Command;
import commands.base.Environment;

import javax.naming.InvalidNameException;

public class Main{
    public static void main(String[] args) throws NullException, InvalidNameException {
        CollectionController coll = new CollectionController();
        System.out.println("Введите название команды или help, чтобы узнать больше о командах (для выхода используйте 'exit')");
        CommandController comcontr = new CommandController();

        HashMap<String, Command> map = new HashMap<>();
        ArrayDeque<Person> person = new ArrayDeque<>();
        Environment environment = new Environment(map, person);

        Add.register(map);
        AddIfMax.register(map);
        Clear.register(map);
        CountBySameHeight.register(map);
        Exit.register(map);
        Help.register(map);
        Info.register(map);
        PrintUniqueHair.register(map);
        RemoveByID.register(map);
        RemoveByPassport.register(map);
        RemoveGreater.register(map);
        Show.register(map);
        UpdateID.register(map);
        comcontr.command(environment);
    }
}
