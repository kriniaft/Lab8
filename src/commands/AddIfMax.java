package commands;

import commands.base.*;

import java.util.HashMap;
import java.util.Scanner;

public class AddIfMax extends Command {
   private AddIfMax(){
        super("add_if_max {element}");
    }

    @Override
    public String getHelp(){
        return "Добавляет новый элемент, если его значение больше всех остальных в коллекции";
    }

    @Override
    public void execute(Environment env){
        boolean isMax = env.profiles.stream().allMatch(p -> newPerson.getHeight() > p.getHeight());
       env.profiles.forEach();

}

    public static void register(HashMap<String, Command> stringCommandHashMap) {
        AddIfMax addIfMax = new AddIfMax();
        stringCommandHashMap.put(addIfMax.getName(), addIfMax);
    }
}
