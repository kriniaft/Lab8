package commands;

import basic.Person;
import commands.base.*;
import java.util.HashMap;

public class AddIfMax extends Command {
   private AddIfMax(){
        super("add_if_max {element}");
   }

      @Override
      public String getHelp(){
        return "Добавляет новый элемент, если его значение больше всех остальных в коллекции";
      }

      @Override
      public void execute(Environment env) throws NullException {
        FieldsWork fw = new FieldsWork();
        float h = fw.height();
        boolean isMax = env.profiles.stream().allMatch(p -> h > p.getHeight());
          if(isMax){
            Person person = new Person(fw.name(), fw.coordinates(), fw.height(), fw.passport(), fw.color(), fw.country(), fw.location());
            env.profiles.add(person);
            System.out.println("Новый человек успешно добавлен");
          }else{
            System.out.println("О нет, кажется уже есть люди с бОльшим ростом");
          }
      }

      public static void register(HashMap<String, Command> stringCommandHashMap) {
        AddIfMax addIfMax = new AddIfMax();
        stringCommandHashMap.put(addIfMax.getName(), addIfMax);
      }
}
