package commands;
 import basic.*;
 import java.util.Scanner;
 import java.util.ArrayDeque;


public class Add extends Command {
   public Add() {
       super("add");
   }

   @Override
   public void execute(ArrayDeque<Person> arDeq) throws NullException {
       FieldsWork fw = new FieldsWork();
       Person person = new Person(fw.name(), fw.coordinates(), fw.height(),
               fw.passport(), fw.color(), fw.country(), fw.location());
       arDeq.offerLast(person);
   }

   @Override
    public String getHelp() {
       return "Добавляет человека в коллекцию";
   }
}
