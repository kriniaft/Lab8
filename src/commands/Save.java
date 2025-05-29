package commands;

import commands.base.*;
import database.DatabaseConnector;
import fileWork.FileController;
import java.io.*;
import java.util.HashMap;

public class Save extends Command {
    Save(){
        super("save");
    }

    public void execute(Environment env, InputStream sIn, PrintStream sOut, String[] arg, DatabaseConnector db) {
        FileController fc = new FileController();
        try {
            fc.saveToFile(env.getFileName(), env.getProfiles());
            sOut.println("Файл успешно сохранен");
        } catch (IOException e) {
            sOut.println("Не удалось сохранить файл(");
        }
    }


    public String getHelp(){
        return "сохраняет коллекцию в файл";
    }

    public static void register(HashMap<String, Command> stringCommandHashMap) {
        Save save = new Save();
        stringCommandHashMap.put(save.getName(), save);
    }

}