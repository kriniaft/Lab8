package commands;
import commands.base.*;
import database.DatabaseConnector;

import java.io.InputStream;
import java.io.PrintStream;
import java.util.HashMap;


public class CountBySameHeight extends Command{
    private CountBySameHeight(){
        super("count_by_height");
    }


    @Override
    public void execute(Environment env, InputStream sIn, PrintStream sOut, String[] commandsArgs, DatabaseConnector db) {
        FieldsWork fw = new FieldsWork();
        float targetHeight;

        if (env.getProfiles() == null || env.getProfiles().isEmpty()) {
            sOut.println("Коллекция пуста или не инициализирована");
            return;
        }

        if (commandsArgs.length > 0) {
            try {
                targetHeight = Float.parseFloat(commandsArgs[0]);
                if (targetHeight <= 0) throw new NumberFormatException();
                if(targetHeight>=4){ throw new NumberFormatException();}
            } catch (NumberFormatException e) {
                sOut.println("Ошибка: рост должен быть положительным числом не больше 4");
                return;
            }
        }
        else {
            targetHeight = fw.height(sIn, sOut);
        }

        long count = env.getProfiles().stream()
                .filter(person -> person.getHeight() == targetHeight)
                .count();
        sOut.println("Количество людей с ростом " + targetHeight + ": " + count);

    }

    @Override
    public String getHelp(){
        return "Выводит количество людей, рост которых равен заданному";
    }

    public static void register(HashMap<String, Command> stringCommandHashMap) {
        CountBySameHeight countBySameHeight = new CountBySameHeight();
        stringCommandHashMap.put(countBySameHeight.getName(), countBySameHeight);
    }

    public static void regForEveryone(HashMap<String, Command> mapForEveryone) {
        CountBySameHeight count = new CountBySameHeight();
        mapForEveryone.put(count.getName(), count);
    }

}
