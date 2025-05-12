import java.io.InputStream;
import java.io.PrintStream;
import java.util.ArrayDeque;
import java.util.HashMap;
import java.util.List;

import commands.base.MapFilling;
import fileWork.FileController;


import commands.*;
import basic.*;
import commands.base.Command;
import commands.base.Environment;


public class Main{
    public static void main(String[] args) throws Exception {



        System.out.println("Введите название команды или help, чтобы узнать больше о командах (для выхода используйте 'exit')");
        CommandController comcontr = new CommandController();

        HashMap<String, Command> map = new HashMap<>();
        MapFilling mf = new MapFilling();
        mf.mapFill(map);
        ArrayDeque<Person> person = new ArrayDeque<>();
        Environment environment = new Environment(map, person, args);

        List<Person> loaded = FileController.readFile(args[0]);
        environment.getProfiles().addAll(loaded);


        PrintStream out = System.out;
        InputStream inputStream = System.in;
        comcontr.command(environment, inputStream, out);
    }
}
