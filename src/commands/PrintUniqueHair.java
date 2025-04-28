package commands;
import commands.base.*;

public class PrintUniqueHair extends Command {
    private PrintUniqueHair(){
        super("print_unique_hair_color");
    }

    public String getHelp(){
        return "Выводит уникальные цвета волос";
    }

}
