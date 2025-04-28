package commands;
import commands.base.*;
public class ExecuteScript extends Command{
    private ExecuteScript(){
        super("execute_script");
    }

    @Override
    public String getHelp(){
        return "исполнит скрипт из файла";
    }
}
