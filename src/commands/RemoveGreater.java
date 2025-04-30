package commands;
import basic.Person;
import commands.base.*;

import java.io.InputStream;
import java.io.PrintStream;
import java.util.HashMap;

import java.util.HashMap;
import java.util.Iterator;

public class RemoveGreater extends Command{
    private RemoveGreater(){
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

    public void execute(Environment env, InputStream sIn, PrintStream sOut){
        Iterator<Person> iterator = env.profiles.iterator();
        FieldsWork fw = new FieldsWork();
        float maxHeight = fw.height();
        while (iterator.hasNext()) {
            Person person = iterator.next();
            if (person.getHeight() > maxHeight) {
                iterator.remove();
            }
        }
        sOut.println("Процесс удаления по росту выполнен");
    }
}
