package commands;

import commands.base.Command;

public class Help extends Command {
    private Help() {
        super("help");
    }

    public String getHelp(){
        return "Выводит список всех команд";
    }
}
