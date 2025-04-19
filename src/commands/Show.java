package commands;

import basic.*;

import java.util.ArrayDeque;

public class Show extends Command{
    public Show(){
        super("show");
    }

    @Override
    public String getHelp(){
        return "Выводит все элементы коллекции";
    }

    public void execute(ArrayDeque<Person> arDeq) throws NullException{
      System.out.println(arDeq);
    }
}
