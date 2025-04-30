package commands;
import commands.base.*;

import java.io.InputStream;
import java.io.PrintStream;
import java.util.HashMap;


public class CountBySameHeight extends Command{
    private CountBySameHeight(){
        super("count_by_height");
    }


    @Override
    public void execute(Environment env, InputStream sIn, PrintStream sOut) {
        FieldsWork fw = new FieldsWork();
        float targetHeight = fw.height(sIn, sOut);

        if (env.getProfiles() == null) {
            sOut.println("Ошибка: коллекция профилей не инициализирована.");
            return;
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

}
