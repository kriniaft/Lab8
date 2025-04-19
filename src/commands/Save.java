package commands;
import basic.Person;

import java.io.*;
        import java.util.ArrayDeque;

class Save extends Command {
    Save(){
        super("save");
    }

    @Override
    public void execute(ArrayDeque<Person> arDeq, String fileName) {
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

}

