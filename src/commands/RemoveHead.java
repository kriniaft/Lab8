package commands;

import basic.Person;

import java.util.ArrayDeque;

public class RemoveHead extends Command{
    RemoveHead(){
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
