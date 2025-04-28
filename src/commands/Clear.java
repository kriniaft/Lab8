package commands;

import commands.base.Command;
import commands.base.Environment;

import java.util.HashMap;

public class Clear extends Command {
    private Clear(){
        super("clear");
    }



    @Override
    public String getHelp(){
        return "Очищает коллекцию (удаляет все элементы)";
    }

    @Override
    public void execute(Environment env){
        env.profiles.clear();
    }

    public static void register(HashMap<String, Command> stringCommandHashMap) {
        Clear clear = new Clear();
        stringCommandHashMap.put(clear.getName(), clear);
    }


}
