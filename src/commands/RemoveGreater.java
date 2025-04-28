package commands;
import commands.base.*;

public class RemoveGreater extends Command {
    private RemoveGreater(){
        super("remove_greater");
    }
    @Override
    public String getHelp(){
        return "удаляет из коллекции все элементы, превышающие заданный";
    }
}
