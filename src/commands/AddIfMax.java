package commands;

import commands.base.*;

import java.util.HashMap;

public class AddIfMax extends Command {
   private AddIfMax(){
        super("add_if_max {element}");
    }

    @Override
    public String getHelp(){
        return "Добавляет новый элемент, если его значение больше всех остальных в коллекции";
    }

    public static void register(HashMap<String, Command> stringCommandHashMap) {
        AddIfMax addIfMax = new AddIfMax();
        stringCommandHashMap.put(addIfMax.getName(), addIfMax);


}
