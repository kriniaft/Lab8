package commands;
import commands.base.*;

public class RemoveByPassport extends Command{
    private RemoveByPassport(){
        super("remove_any_by_passport_i_d");
    }

    public String getHelp(){
        return "удаляет из коллекции элемент с таким же номером паспорта";
    }
}
