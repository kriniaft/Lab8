package commands;
import basic.*;
import commands.base.Command;

import java.util.ArrayDeque;
import java.util.HashMap;

public class UpdateID extends Command {
    private UpdateID() {
        super("update id {element}");
    }

    @Override
    public void execute(ArrayDeque<Person> arDeq) throws NullException {
        arDeq.poll();
    }


    @Override
    public String getHelp(){
        return "Обновить значение элемента коллекции, id которого вы ввели";
    }

    public static void register(HashMap<String, Command> stringCommandHashMap) {
        UpdateID updateID = new UpdateID();
        stringCommandHashMap.put(updateID.getName(), updateID);

    }
}
}