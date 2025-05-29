import java.io.InputStream;
import java.io.PrintStream;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.*;

import commands.base.*;
import database.DatabaseConnector;
import fileWork.FileController;


import commands.*;
import basic.*;
import commands.base.Command;
import commands.base.Environment;

import javax.naming.InvalidNameException;


public class Main{
    static Scanner sc = new Scanner(System.in);
    public static void main(String[] args) throws Exception {
        DatabaseConnector db = new DatabaseConnector();
        CommandController comcontr = new CommandController(db);

        HashMap<String, Command> nameMap = new HashMap<>();
        HashMap<String, Command> comForEveryone = new HashMap<>();

        MapFilling mf = new MapFilling();
        mf.mapFill(nameMap);
        mf.mapForEveryOneFill(comForEveryone);

        ArrayDeque<Person> person = new ArrayDeque<>();
        Environment environment = new Environment(nameMap, comForEveryone, person, args);

     //   List<Person> loaded = FileController.readFile(args[0]);
     //   environment.getProfiles().addAll(loaded);


        try {
            db.connect(); // подключение к базе
        } catch (Exception e) {
            System.out.println("Ошибка подключения к базе данных");
            return;
        }

        System.out.println("Добро пожаловать!");
        String username;

        while (true) {
            System.out.print("Введите команду (login/register/exit): ");
            String command = sc.nextLine().trim().toLowerCase();

            if (command.equals("exit")) {
                System.out.println("Выход...");
                return;
            }

            System.out.println("Логин: ");
            username = sc.nextLine().trim();
            db.setCurrentUser(username);
            System.out.println("Пароль: ");
            String password = sc.nextLine().trim();

            if (command.equals("register")) {
                db.registration(username, password);
            } else if (command.equals("login")) {
                if (db.login(username, password)) {
                    System.out.println("Успешный вход как " + username);
                    break;
                } else {
                    System.out.println("Неверный логин или пароль");
                }
            } else {
                System.out.println("Неизвестная команда");
            }
        }

        PrintStream out = System.out;
        InputStream inputStream = System.in;
        comcontr.command(environment, inputStream, out, username);
    }
}
