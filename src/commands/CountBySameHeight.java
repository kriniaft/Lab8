package commands;
import commands.base.*;

import java.util.HashMap;

public class CountBySameHeight extends Command{
    private CountBySameHeight(){
        super("count_by_height");
    }

    @Override
    public String getHelp(){
        return "Выводит количество людей, рост которых равен заданному";
    }

    public static void register(HashMap<String, Command> stringCommandHashMap) {
        CountBySameHeight countBySameHeight = new CountBySameHeight();
        stringCommandHashMap.put(countBySameHeight.getName(), countBySameHeight);
    }

}
