package commands;
 import basic.*;
 import commands.base.Command;
 import commands.base.Environment;

 import java.io.InputStream;
 import java.io.PrintStream;
 import java.util.HashMap;


public class Add extends Command {
   private Add() {
       super("add");
   }

   @Override
   public void execute(Environment env, InputStream sIn, PrintStream sOut) throws NullException {
       FieldsWork fw = new FieldsWork();
       Person person = new Person(fw.name(sIn, sOut), fw.coordinates(sIn, sOut), fw.height(sIn, sOut),
               fw.passport(sIn, sOut), fw.color(sIn, sOut), fw.country(sIn, sOut), fw.location(sIn, sOut));
       env.profiles.offerLast(person);
       sOut.println("Новый человек успешно добавлен");
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
