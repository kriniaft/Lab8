package commands;

import basic.Color;
import commands.base.Command;
import commands.base.Environment;

public class PrintUniqueHair extends Command {
    public PrintUniqueHair() {
        super("print_unique_hair_color");
    }

    @Override
    public String getHelp() {
        return "Выводит уникальные цвета волос";
    }


    public void execute(Environment env) {
        long countGreen = env.getProfiles().stream().filter(p -> p.getHairColor() == Color.GREEN).count();
        long countBlue = env.getProfiles().stream().filter(p -> p.getHairColor() == Color.BLUE).count();
        long countRed = env.getProfiles().stream().filter(p -> p.getHairColor() == Color.RED).count();
        long countWhite = env.getProfiles().stream().filter(p -> p.getHairColor() == Color.WHITE).count();
        long countYellow = env.getProfiles().stream().filter(p -> p.getHairColor() == Color.YELLOW).count();
        System.out.println("В исходной коллекции встречаются люди с таким цветом волос, как:");
        if(countGreen > 0){ System.out.println("Зелёный");}
        if(countBlue > 0){ System.out.println("Синий");}
        if(countRed > 0){ System.out.println("Красный");}
        if(countWhite > 0){ System.out.println("Белый");}
        if(countYellow > 0){ System.out.println("Желтый");}


    }

}
