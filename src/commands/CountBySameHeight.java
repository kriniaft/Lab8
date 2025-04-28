package commands;
import commands.base.*;
public class CountBySameHeight extends Command{
    private CountBySameHeight(){
        super("count_by_height");
    }

    @Override
    public String getHelp(){
        return "Выводит количество людей, рост которых равен заданному";
    }

}
