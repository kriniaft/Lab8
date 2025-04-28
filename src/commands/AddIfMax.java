package commands;

import commands.base.*;

public class AddIfMax extends Command {
   private AddIfMax(){
        super("add_if_max {element}");
    }

    @Override
    public String getHelp(){
        return "Добавляет новый элемент, если его значение больше всех остальных в коллекции";
    }
}
