package commands;
import java.io.*;
        import java.util.ArrayDeque;

class Save {
    public void save(ArrayDeque<Person> arDeq, String fileName) {
        try (ObjectOutputStream outputStream = new ObjectOutputStream(new FileOutputStream(fileName))) {
            outputStream.writeObject(arDeq);
        } catch (Exception e) {
            System.out.println("Ошибка при сохранении");
        }
    }
}

