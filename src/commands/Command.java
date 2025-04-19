package commands;

import basic.Person;

import java.util.ArrayDeque;

public abstract class Command {
    private final String name;

    protected Command(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public abstract void execute(ArrayDeque<Person> arDeq) throws NullException;

    public abstract String getHelp();
}
