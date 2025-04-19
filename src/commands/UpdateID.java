package commands;
import commands.base.Command;

public class UpdateID extends Command {
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
