import java.io.InputStream;
import java.io.PrintStream;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayDeque;
import java.util.HashMap;
import java.util.List;

import commands.base.MapFilling;
import database.DatabaseConnector;
import fileWork.FileController;


import commands.*;
import basic.*;
import commands.base.Command;
import commands.base.Environment;

import javax.naming.InvalidNameException;

public class Main{
    public static void main(String[] args) throws Exception {
                try (Connection conn = DatabaseConnector.connect()) {
                    if (conn != null) {
                        System.out.println(" Подключение к базе данных прошло успешно!");
                    } else {
                        System.out.println(" Подключение не удалось (null).");
                    }
                } catch (SQLException e) {
                    System.out.println(" Ошибка при подключении к базе данных:");
                    e.printStackTrace();
                    System.out.println("ЭТО МЫ ТОЧНО ПОТОМ УДАЛИМ");
                }


/*
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
