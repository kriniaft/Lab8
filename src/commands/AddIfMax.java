package commands;

import basic.Person;
import commands.base.*;

import java.io.InputStream;
import java.io.PrintStream;
import java.util.HashMap;

public class AddIfMax extends Command {
   private AddIfMax(){
        super("add_if_max");
   }

      @Override
      public String getHelp(){
        return "Добавляет новый элемент, если его значение больше всех остальных в коллекции";
      }

      @Override
      public void execute(Environment env, InputStream sIn, PrintStream sOut) throws NullException {
        FieldsWork fw = new FieldsWork();
        float h = fw.height(sIn, sOut);
        boolean isMax = env.profiles.stream().allMatch(p -> h > p.getHeight());
          if(isMax){
            Person person = new Person(fw.name(sIn, sOut), fw.coordinates(sIn, sOut), fw.height(sIn, sOut), fw.passport(sIn, sOut), fw.color(sIn, sOut), fw.country(sIn, sOut), fw.location(sIn, sOut));
            env.profiles.add(person);
            sOut.println("Новый человек успешно добавлен");
          }else{
            sOut.println("О нет, кажется уже есть люди с бОльшим ростом");
          }
      }

      public static void register(HashMap<String, Command> stringCommandHashMap) {
        AddIfMax addIfMax = new AddIfMax();
        stringCommandHashMap.put(addIfMax.getName(), addIfMax);
      }
}
