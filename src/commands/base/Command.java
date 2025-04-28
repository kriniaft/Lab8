package commands.base;

import basic.Person;
import commands.NullException;

import java.util.ArrayDeque;

public abstract class Command {
    private final String name;

    protected Command(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public abstract void execute(Environment env) throws NullException;
    public abstract String getHelp();
}
