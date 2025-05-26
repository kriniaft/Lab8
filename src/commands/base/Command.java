package commands.base;

import basic.Person;
import commands.NullException;

import java.io.InputStream;
import java.io.PrintStream;
import java.util.ArrayDeque;

public abstract class Command {
    private final String name;
    private boolean isArgs;

    protected Command(String name, boolean args) {

        this.name = name;
        isArgs = args;
    }

    public String getName() {
        return name;
    }

    public abstract void execute(Environment env, InputStream sIn, PrintStream sOut, String[] commandsArgs) throws NullException;
    public abstract String getHelp();
}
