package commands;

import basic.*;

import java.util.ArrayDeque;
import java.util.HashMap;

public class Show extends Command{
    public Show(){
        super("show");
    }

    @Override
    public String getHelp(){
        return "Выводит все элементы коллекции";
    }

    @Override
    public void execute(ArrayDeque<Person> arDeq) throws NullException{
      System.out.println(arDeq);
    }

    public static void register(HashMap<String, Command> stringCommandHashMap) {
        Show show = new Show();
        stringCommandHashMap.put(show.getName(), show);

    }
}
