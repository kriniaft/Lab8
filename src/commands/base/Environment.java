package commands.base;

import basic.Person;

import java.time.LocalDateTime;
import java.util.ArrayDeque;
import java.util.HashMap;

public final class Environment {
    private final HashMap<String, Command> stringCommandHashmap;
    public ArrayDeque<Person> profiles = new ArrayDeque<>();
    private final LocalDateTime collectionInitializationDate;
    private final String arg;
    private boolean scriptMode = false;

    public Environment(HashMap<String, Command> stringCommandHashmap, ArrayDeque<Person> profiles, String[] arg) {
        this.stringCommandHashmap = stringCommandHashmap;
        this.profiles = profiles;
        this.collectionInitializationDate = LocalDateTime.now();
        this.arg = arg[0];
    }

    public HashMap<String, Command> getStringCommandHashmap() {
        return stringCommandHashmap;
    }


    public ArrayDeque<Person> getProfiles(){
        return profiles;
    }
    public LocalDateTime getCollectionInitializationDate() {
        return collectionInitializationDate;
    }

    public String getFileName(){
        return arg;
    }

    public boolean isScriptMode() {
        return scriptMode;
    }

    public void setScriptMode(boolean scriptMode) {
        this.scriptMode = scriptMode;
    }
}
