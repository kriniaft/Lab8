package commands;
import basic.*;
import commands.base.*;
import database.DatabaseConnector;

import java.io.*;
import java.nio.file.Path;
import java.util.*;

public class ExecuteScript extends Command{
    ExecuteScript(){
        super("executeScript");
    }
    private static final HashSet<String> executingFiles = new HashSet<>();


    @Override
    public void execute(Environment env, InputStream sIn, PrintStream sOut, String[] args, DatabaseConnector db) {
        if (args.length == 0) {
            sOut.println("Укажите имя файла со скриптом.");
            return;
        }

        String fileName = args[0].trim();

        if (executingFiles.contains(fileName)) {
            sOut.println("Предотвращена рекурсивная загрузка скрипта: " + fileName);
            return;
        }

        File file = new File(fileName);
        if (!file.exists() || !file.canRead()) {
            sOut.println("Файл не существует или не доступен для чтения: " + fileName);
            return;
        }

        executingFiles.add(fileName);

        try (Scanner fileScanner = new Scanner(file)) {
            env.setScriptMode(true);
            while (fileScanner.hasNextLine()) {
                String commandLine = fileScanner.nextLine().trim();
                if (commandLine.isEmpty()) continue;

                String[] split = commandLine.split(" ");
                String commandName = split[0];
                String[] commandArgs = new String[split.length - 1];
                System.arraycopy(split, 1, commandArgs, 0, commandArgs.length);

                Command command = env.getStringCommandHashmap().get(commandName);
                if (command != null) {
                    command.execute(env, sIn, sOut, commandArgs, db);
                } else {
                    sOut.println("Неизвестная команда: " + commandName);
                }
            }
            env.setScriptMode(false);
        } catch (FileNotFoundException e) {
            sOut.println("Ошибка: файл не найден.");
        } catch (Exception e) {
            sOut.println("Ошибка при выполнении скрипта: " + e.getMessage());
        } finally {
            executingFiles.remove(fileName);
        }
    }

    public static void register(HashMap<String, Command> map) {
        map.put("executeScript", new ExecuteScript());
    }

    public String getHelp(){
    return "исполнит скрипт из файла";
    }
}