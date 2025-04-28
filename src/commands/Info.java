package commands;
import commands.base.*;

public class Info extends Command {
    private Info(){
        super("info");
    }

    public String getHelp(){
        return "Выводит информацию о коллекции";
    }
}
