package commands.base;

import basic.Person;

import java.time.LocalDateTime;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.HashMap;

public final class Environment {
    private final HashMap<String, Command> stringCommandHashmap;
    private final HashMap<String, Command> commandForEveryone;
    public ArrayDeque<Person> profiles = new ArrayDeque<>();
    private final LocalDateTime collectionInitializationDate;
    private boolean scriptMode = false;

    public Environment(HashMap<String, Command> stringCommandHashmap, HashMap<String, Command> commandForEveryone, ArrayDeque<Person> profiles) {
        this.stringCommandHashmap = stringCommandHashmap;
        this.commandForEveryone = commandForEveryone;
        this.profiles = profiles;
        this.collectionInitializationDate = LocalDateTime.now();
    }

    public HashMap<String, Command> getStringCommandHashmap() {
        return stringCommandHashmap;
    }

    public HashMap<String, Command> getCommandForEveryone() {
        return commandForEveryone;
    }

    public ArrayDeque<Person> getProfiles(){
        return profiles;
    }

    public void setProfiles(ArrayDeque<Person> profiles) {
        this.profiles = profiles;
    }

    public LocalDateTime getCollectionInitializationDate() {
        return collectionInitializationDate;
    }

    public boolean isScriptMode() {
        return scriptMode;
    }

    public void setScriptMode(boolean scriptMode) {
        this.scriptMode = scriptMode;
    }
}
