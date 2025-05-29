package commands;

import basic.Color;
import commands.base.Command;
import commands.base.Environment;

import java.io.InputStream;
import java.io.PrintStream;
import java.util.HashMap;

public class PrintUniqueHair extends Command {
    public PrintUniqueHair() {
        super("print_unique_hair_color");
    }

    @Override
    public String getHelp() {
        return "Выводит уникальные цвета волос";
    }

    public static void register(HashMap<String, Command> stringCommandHashMap) {
        PrintUniqueHair puh = new PrintUniqueHair();
        stringCommandHashMap.put(puh.getName(), puh);
    }

    public static void regForEveryone(HashMap<String, Command> mapForEveryone) {
        PrintUniqueHair hair = new PrintUniqueHair();
        mapForEveryone.put(hair.getName(), hair);
    }

    @Override
    public void execute(Environment env, InputStream sIn, PrintStream sOut, String[] commandsArgs) {
        if (env.getProfiles() == null || env.getProfiles().isEmpty()) {
            sOut.println("Коллекция пуста или не инициализирована");
            return;
        }

        long countGreen = env.getProfiles().stream().filter(p -> p.getHairColor() == Color.GREEN).count();
        long countBlue = env.getProfiles().stream().filter(p -> p.getHairColor() == Color.BLUE).count();
        long countRed = env.getProfiles().stream().filter(p -> p.getHairColor() == Color.RED).count();
        long countWhite = env.getProfiles().stream().filter(p -> p.getHairColor() == Color.WHITE).count();
        long countYellow = env.getProfiles().stream().filter(p -> p.getHairColor() == Color.YELLOW).count();
        sOut.println("В исходной коллекции встречаются люди с таким цветом волос, как:");
        if(countGreen > 0){ sOut.println("Зелёный");}
        if(countBlue > 0){ sOut.println("Синий");}
        if(countRed > 0){ sOut.println("Красный");}
        if(countWhite > 0){ sOut.println("Белый");}
        if(countYellow > 0){ sOut.println("Желтый");}
    }

}
