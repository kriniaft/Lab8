package commands.base;

import java.util.HashMap;

public final class Environment {
    private final HashMap<String, Command> stringCommandHashmap;

    public Environment(HashMap<String, Command> stringCommandHashmap) {
        this.stringCommandHashmap = stringCommandHashmap;
    }

    public HashMap<String, Command> getStringCommandHashmap() {
        return stringCommandHashmap;
    }
}
