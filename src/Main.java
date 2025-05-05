import java.io.InputStream;
import java.io.PrintStream;
import java.util.ArrayDeque;
import java.util.HashMap;
import java.util.List;

import fileWork.FileController;


import commands.*;
import basic.*;
import commands.base.Command;
import commands.base.Environment;

import javax.naming.InvalidNameException;

public class Main{
    public static void main(String[] args) throws NullException, InvalidNameException {
        System.out.println("Введите название команды или help, чтобы узнать больше о командах (для выхода используйте 'exit')");
        CommandController comcontr = new CommandController();

        HashMap<String, Command> map = new HashMap<>();
        ArrayDeque<Person> person = new ArrayDeque<>();
        Environment environment = new Environment(map, person);
        List<Person> loaded = FileController.readFile(args[0]);
        System.out.println("Загружено объектов: " + loaded.size());
        for (Person p : loaded) {
            System.out.println(p); // или p.getName(), если toString не настроен
        }

        environment.getProfiles().addAll(loaded);
        CommandController comcontr = new CommandController();


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
        PrintStream out = System.out;
        InputStream inputStream = System.in;
        comcontr.command(environment, inputStream, out);
        //java nio 8 file
    }
}
