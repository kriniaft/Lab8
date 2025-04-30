package commands.base;

import basic.Person;
import commands.NullException;

import java.io.InputStream;
import java.io.PrintStream;
import java.util.ArrayDeque;

public abstract class Command {
    private final String name;

    protected Command(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public abstract void execute(Environment env, InputStream sIn, PrintStream sOut) throws NullException;
    public abstract String getHelp();
}
