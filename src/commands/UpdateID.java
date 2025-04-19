package commands;
import basic.*;
import java.util.ArrayDeque;

public class UpdateID extends Command{
    UpdateID() {
        super("update id {element}");
    }

        @Override


        @Override
        public String getHelp(){
            return "Обновить значение элемента коллекции, id которого вы ввели";
        }
    }
}
