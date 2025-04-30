package commands;
 import basic.*;
 import commands.base.Command;
 import commands.base.Environment;

 import java.util.HashMap;
 import java.util.ArrayDeque;


public class Add extends Command {
   private Add() {
       super("add");
   }

   @Override
   public void execute(Environment env) throws NullException {
       FieldsWork fw = new FieldsWork();
       Person person = new Person(fw.name(), fw.coordinates(), fw.height(),
               fw.passport(), fw.color(), fw.country(), fw.location());
       env.profiles.offerLast(person);
       System.out.println("Новый человек успешно добавлен");
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
