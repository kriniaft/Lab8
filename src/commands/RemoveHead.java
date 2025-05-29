package commands;

import java.io.InputStream;
import java.io.PrintStream;
import java.util.HashMap;
import commands.base.Command;
import commands.base.Environment;
import database.DatabaseConnector;

public class RemoveHead extends Command {
    private RemoveHead(){
        super("remove_head");
    }

    @Override
    public String getHelp(){
        return "Выводит первый элемент коллекции и удаляет его";
    }

    @Override
    public void execute (Environment env, InputStream sIn, PrintStream sOut, String[] commandsArgs, DatabaseConnector db){
        if (env.getProfiles() == null || env.getProfiles().isEmpty()) {
            sOut.println("Коллекция пуста или не инициализирована");
            return;
        }

        env.profiles.poll();
        sOut.println("Процесс удаления успешно выполнен");
    }

    public static void register(HashMap<String, Command> stringCommandHashMap) {
        RemoveHead removeHead = new RemoveHead();
        stringCommandHashMap.put(removeHead.getName(), removeHead);


    }



}
