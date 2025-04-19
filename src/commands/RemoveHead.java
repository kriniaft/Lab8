package commands;

import basic.Person;
import commands.base.Command;
import commands.base.Environment;

import java.util.ArrayDeque;

public class RemoveHead extends Command {
    private RemoveHead(){
        super("remove_head");
    }

    @Override
    public String getHelp(){
        return "Выводит первый элемент коллекции и удаляет его";
    }

    @Override
    public void execute (ArrayDeque<Person> arDeq){
        arDeq.poll();
    }

}
