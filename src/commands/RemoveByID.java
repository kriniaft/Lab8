package commands;
import commands.base.*;

import java.util.HashMap;

public class RemoveByID extends Command{
    private RemoveByID(){
        super("remove_by_id");
    }

    public String getHelp(){
        return "удалить элемент из коллекции по его id";
    }

    public static void register(HashMap<String, Command> stringCommandHashMap) {
        RemoveByID rbi = new RemoveByID();
        stringCommandHashMap.put(rbi.getName(), rbi);
    }
}
