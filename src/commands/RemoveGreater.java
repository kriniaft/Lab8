package commands;

import commands.base.Command;

import java.util.HashMap;

public class RemoveGreater extends Command {
    private RemoveGreater(){
        super("remove_greater {element}");
    }
    @Override
    public String getHelp(){
        return "удаляет из коллекции все элементы, превышающие заданный";
    }

    public static void register(HashMap<String, Command> stringCommandHashMap) {
        RemoveGreater removeGreater = new RemoveGreater();
        stringCommandHashMap.put(removeGreater.getName(), removeGreater);
}
