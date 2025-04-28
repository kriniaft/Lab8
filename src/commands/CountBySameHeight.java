package commands;
import commands.base.*;

import java.util.HashMap;
import java.util.List;

public class CountBySameHeight extends Command{
    private CountBySameHeight(){
        super("count_by_height");
    }


    @Override
    public String execute(Environment env, List<String> args) {
        if (args.isEmpty()) {
            return "Ошибка: необходимо указать рост для поиска.";
        }

        if (env.getProfiles() == null) {
            return "Ошибка: коллекция профилей не инициализирована.";
        }

        try {
            float targetHeight = Float.parseFloat(args.get(0));
            long count = env.getProfiles().stream()
                    .filter(person -> person.getHeight() == targetHeight)
                    .count();
            return "Количество людей с ростом " + targetHeight + ": " + count;
        } catch (NumberFormatException e) {
            return "Ошибка: некорректный формат роста. Пожалуйста, введите число.";
        }
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
