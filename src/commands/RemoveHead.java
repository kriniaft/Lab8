package commands;

import java.io.InputStream;
import java.io.PrintStream;
import java.sql.SQLException;
import java.util.ArrayDeque;
import java.util.HashMap;

import basic.Person;
import commands.base.Command;
import commands.base.Environment;
import database.DatabaseConnector;

public class RemoveHead extends Command {
    private RemoveHead(){
        super("remove_head");
    }

    @Override
    public String getHelp(){
        return "Удаляет первый элемент коллекции";
    }

    @Override
    public void execute (Environment env, InputStream sIn, PrintStream sOut, String[] commandsArgs, DatabaseConnector db8) throws SQLException {
        if (env.getProfiles() == null || env.getProfiles().isEmpty()) {
            sOut.println("Коллекция пуста или не инициализирована");
            return;
        }

        ArrayDeque<Person> person = db8.loadPersonsByUser(db8.getUserNow());
        System.out.println("Первый элемент - " + person.getFirst());
        if(db8.deletePerson(person.getFirst())){
        person.poll();
        sOut.println("Процесс удаления успешно выполнен");
        }else {
            System.out.println("Не удалось удалить первый элемент(");
        }
    }

    public static void register(HashMap<String, Command> stringCommandHashMap) {
        RemoveHead removeHead = new RemoveHead();
        stringCommandHashMap.put(removeHead.getName(), removeHead);


    }



}
