package commands;

import basic.*;
import commands.base.Command;
import commands.base.Environment;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintStream;
import java.util.ArrayDeque;
import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.*;

class AddIfMaxTest {

    /*public void execute(Environment env, InputStream sIn, PrintStream sOut, String[] commandsArgs) throws NullException {
        FieldsWork fw = new FieldsWork();
        float h;

          if (env.getProfiles() == null || env.getProfiles().isEmpty()) {
              sOut.println("Коллекция пуста или не инициализирована");
              return;
          }

          if (commandsArgs.length > 0) {
              try {
                  h = Float.parseFloat(commandsArgs[0]);
                  if (h <= 0) throw new NumberFormatException();
                  if(h>=4){ throw new NumberFormatException();}
              } catch (NumberFormatException e) {
                  sOut.println("Ошибка: рост должен быть положительным числом не больше 4");
                  return;
              }
          }
          else {
              h = fw.height(sIn, sOut);
          }

        boolean isMax = env.profiles.stream().allMatch(p -> h > p.getHeight());
          if(isMax){
            Person person = new Person(fw.name(sIn, sOut), fw.coordinates(sIn, sOut), fw.height(sIn, sOut), fw.passport(sIn, sOut), fw.color(sIn, sOut), fw.country(sIn, sOut), fw.location(sIn, sOut));
            env.profiles.add(person);
            sOut.println("Новый человек успешно добавлен");
          }else{
            sOut.println("О нет, кажется уже есть люди с бОльшим ростом");
          }
      }*/


    @Test
    public void test1() throws NullException {
        HashMap<String, Command> hm = new HashMap<>();
        ArrayDeque<Person> pers = new ArrayDeque<>();

        Coordinates c = new Coordinates(2, 6);
        Location l = new Location(2F, 6, 8F);
        Person pers1 = new Person("karina", c, 1.55F, "76767", Color.BLUE, Country.GERMANY, l);
        Person pers2 = new Person("Ivan", c, 1.78F, "5555", Color.GREEN, Country.SPAIN, l);
        pers.add(pers1);
        pers.add(pers2);
        String[] arg = {"1.9"};
        Environment env = new Environment(hm, pers, arg);
        AddIfMax cmd = new AddIfMax();

        InputStream inputStream = System.in;
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        PrintStream outputStream = new PrintStream(outContent);

        cmd.execute(env, inputStream, outputStream, arg);

        String output = outContent.toString();
        assertTrue(output.contains("О нет, кажется уже есть люди с бОльшим ростом"));
        assertEquals(1, env.getProfiles().size());

    }

}