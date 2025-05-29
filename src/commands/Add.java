package commands;
 import basic.*;
 import commands.base.Command;
 import commands.base.Environment;
 import java.io.InputStream;
 import java.io.PrintStream;
 import java.sql.SQLException;
 import java.util.HashMap;


public class Add extends Command {
   private Add() {
       super("add");
   }

   @Override
   public void execute(Environment env, InputStream sIn, PrintStream sOut, String[] commandsArgs) throws NullException, SQLException {
       if (env.isScriptMode()) {
           try {
               if (commandsArgs.length < 9) {
                   sOut.println("Недостаточно аргументов для команды add в скрипте.");
                   return;
               }
               String name = commandsArgs[0];
               float x = Float.parseFloat(commandsArgs[1]);
               float y = Float.parseFloat(commandsArgs[2]);
               float height = Float.parseFloat(commandsArgs[3]);
               String passportID = commandsArgs[4];
               Color color = Color.valueOf(commandsArgs[5].toUpperCase());
               Country country = Country.valueOf(commandsArgs[6].toUpperCase());
               Float locX = Float.parseFloat(commandsArgs[7]);
               float locY = Float.parseFloat(commandsArgs[8]);
               float locZ = Float.parseFloat(commandsArgs[8]);

               Coordinates coordinates = new Coordinates(x, y);
               Location location = new Location(locX, locY, locZ);

               Person person = new Person(dBC.minId(), name, coordinates, height, passportID, color, country, location);

               env.profiles.offerLast(person);
               sOut.println("Человек добавлен из скрипта.");
           } catch (Exception e) {
               sOut.println("Ошибка при добавлении из скрипта: " + e.getMessage());
           }
       } else {
           FieldsWork fw = new FieldsWork();
           Person person = new Person(dBC.minId(), fw.name(sIn, sOut), fw.coordinates(sIn, sOut), fw.height(sIn, sOut),
                   fw.passport(sIn, sOut), fw.color(sIn, sOut), fw.country(sIn, sOut), fw.location(sIn, sOut));
           // сохр в таблицу
           env.profiles.offerLast(person);
           sOut.println("Новый человек успешно добавлен");
       }
   }

   @Override
    public String getHelp() {
       return "Добавляет человека в коллекцию";
   }

   public static void register(HashMap<String, Command> stringCommandHashMap) {
       Add add = new Add();
       stringCommandHashMap.put(add.getName(), add);
   }

}
