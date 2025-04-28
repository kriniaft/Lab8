package commands;
import basic.Person;
import commands.base.Command;
import commands.base.Environment;

import java.io.*;
import java.util.ArrayDeque;
import java.util.HashMap;

class Save extends Command {
    private Save(){
        super("save");
    }

    @Override
    public void execute(ArrayDeque<Person> arDeq, Environment env, String fileName) {
        try (ObjectOutputStream outputStream = new ObjectOutputStream(new FileOutputStream(fileName))) {
            outputStream.writeObject(arDeq);
        } catch (Exception e) {
            System.out.println("Ошибка при сохранении");
        }
    }

    @Override
    public String getHelp(){
        return "сохраняет коллекцию в файл";
    }

    public static void register(HashMap<String, Command> stringCommandHashMap) {
        Save save = new Save();
        stringCommandHashMap.put(save.getName(), save);
    }

}