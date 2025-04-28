package commands.base;

import basic.Person;

import java.util.ArrayDeque;
import java.util.HashMap;

public final class Environment {
    private final HashMap<String, Command> stringCommandHashmap;
    public ArrayDeque<Person> profiles = new ArrayDeque<>();

    public Environment(HashMap<String, Command> stringCommandHashmap, ArrayDeque<Person> profiles) {
        this.stringCommandHashmap = stringCommandHashmap;
        this.profiles = profiles;
    }

    public HashMap<String, Command> getStringCommandHashmap() {
        return stringCommandHashmap;
    }


    public ArrayDeque<Person> getProfiles(){
        return profiles;
    }
}
