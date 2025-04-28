package commands;
import commands.base.*;

import java.util.HashMap;

public class RemoveByPassport extends Command{
    private RemoveByPassport(){
        super("remove_any_by_passport_i_d");
    }

    public String getHelp(){
        return "удаляет из коллекции элемент с таким же номером паспорта";
    }

    public static void register(HashMap<String, Command> stringCommandHashMap) {
        RemoveByPassport rbp = new RemoveByPassport();
        stringCommandHashMap.put(rbp.getName(), rbp);
    }
}
