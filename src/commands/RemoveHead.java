package commands;

import basic.Person;

import java.io.InputStream;
import java.io.PrintStream;
import java.util.ArrayDeque;
import java.util.HashMap;
import commands.base.Command;
import commands.base.Environment;
import java.util.HashMap;

public class RemoveHead extends Command {
    private RemoveHead(){
        super("remove_head");
    }

    @Override
    public String getHelp(){
        return "Выводит первый элемент коллекции и удаляет его";
    }

    @Override
    public void execute (Environment env, InputStream sIn, PrintStream sOut){
        env.profiles.poll();
        sOut.println("Процесс удаления успешно выполнен");
    }

    public static void register(HashMap<String, Command> stringCommandHashMap) {
        RemoveHead removeHead = new RemoveHead();
        stringCommandHashMap.put(removeHead.getName(), removeHead);


    }



}
