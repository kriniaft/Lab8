package commands;
import commands.base.*;
public class RemoveByID extends Command{
    private RemoveByID(){
        super("remove_by_id");
    }

    public String getHelp(){
        return "удалить элемент из коллекции по его id";
    }
}
