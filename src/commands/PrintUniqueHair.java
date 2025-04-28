package commands;
import commands.base.*;

import java.util.HashMap;

public class PrintUniqueHair extends Command {
    private PrintUniqueHair(){
        super("print_unique_hair_color");
    }

    @Override
    public String getHelp(){
        return "Выводит уникальные цвета волос";
    }


    public void execute(Environment env){

    }

    public static void register(HashMap<String, Command> stringCommandHashMap) {
        PrintUniqueHair puh = new PrintUniqueHair();
        stringCommandHashMap.put(puh.getName(), puh);
    }

}
