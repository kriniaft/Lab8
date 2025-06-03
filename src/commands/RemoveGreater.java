package commands;
import basic.Person;
import commands.base.*;
import database.DatabaseConnector;

import java.io.InputStream;
import java.io.PrintStream;
import java.sql.SQLException;
import java.util.ArrayDeque;
import java.util.HashMap;
import java.util.Iterator;

public class RemoveGreater extends Command{
    RemoveGreater(){
        super("remove_greater");
    }
    @Override
    public String getHelp(){
        return "удаляет из коллекции все элементы, превышающие заданный";
    }

    public static void register(HashMap<String, Command> stringCommandHashMap) {
        RemoveGreater removeGreater = new RemoveGreater();
        stringCommandHashMap.put(removeGreater.getName(), removeGreater);
    }

    public void execute(Environment env, InputStream sIn, PrintStream sOut, String[] commandsArgs, DatabaseConnector db) throws SQLException {
        if (env.getProfiles() == null || env.getProfiles().isEmpty()) {
            sOut.println("Коллекция пуста или не инициализирована");
            return;
        }

        ArrayDeque<Person> persons = db.loadPersonsByUser(db.getUserNow());
        Iterator<Person> iterator = persons.iterator();
        FieldsWork fw = new FieldsWork();
        float maxHeight = fw.height(sIn, sOut);
        while (iterator.hasNext()) {
            Person person = iterator.next();
            if (person.getHeight() > maxHeight) {
               if(db.deletePerson(person)){
                iterator.remove();}
            }
        }
        sOut.println("Процесс удаления по росту выполнен");
    }
}
