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


    }
}
