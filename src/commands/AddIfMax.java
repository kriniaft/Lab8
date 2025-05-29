package commands;

import basic.Person;
import commands.base.*;
import database.DatabaseConnector;

import java.io.InputStream;
import java.io.PrintStream;
import java.sql.SQLException;
import java.util.HashMap;

public class AddIfMax extends Command {
   public AddIfMax(){
        super("add_if_max");
   }

      @Override
      public String getHelp(){
        return "Добавляет новый элемент, если его значение больше всех остальных в коллекции";
      }

      @Override
      public void execute(Environment env, InputStream sIn, PrintStream sOut, String[] commandsArgs, DatabaseConnector db) throws NullException, SQLException {
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
            Person person = new Person(db.minId(),fw.name(sIn, sOut), fw.coordinates(sIn, sOut), fw.height(sIn, sOut), fw.passport(sIn, sOut), fw.color(sIn, sOut), fw.country(sIn, sOut), fw.location(sIn, sOut));
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
