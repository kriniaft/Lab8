package commands.base;

import basic.Person;
import commands.NullException;
import database.DatabaseConnector;

import java.io.InputStream;
import java.io.PrintStream;
import java.sql.SQLException;
import java.util.ArrayDeque;

public abstract class Command {
    private final String name;
    private boolean isArgs;

    public DatabaseConnector dBC = new DatabaseConnector();

    protected Command(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public abstract void execute(Environment env, InputStream sIn, PrintStream sOut, String[] commandsArgs) throws NullException, SQLException;
    public abstract String getHelp();
}
