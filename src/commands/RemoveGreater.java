package commands;

public class RemoveGreater extends Command{
    private RemoveGreater(){
        super("remove_greater {element}");
    }
    @Override
    public String getHelp(){
        return "удаляет из коллекции все элементы, превышающие заданный";
    }
}
