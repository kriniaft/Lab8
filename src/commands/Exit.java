package commands;
import commands.base.*;
public class Exit extends Command {
    private Exit(){
        super("exit");
    }

    @Override
    public String getHelp(){
        return "завершить программу (без сохранения в файл)";
    }
}
