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


       /* try {
            db.connect(); // подключение к базе
        } catch (Exception e) {
            System.out.println("Ошибка подключения к базе данных");
            return;
        }*/

        System.out.println("Добро пожаловать!");
        PrintStream out = System.out;
        InputStream inputStream = System.in;
        comcontr.command(environment, inputStream, out, db);
    }
}
