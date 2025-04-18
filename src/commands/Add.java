package commands;
 import basic.*;
 import java.util.Scanner;
 import java.util.ArrayDeque;


public class Add {
   public void add(ArrayDeque<Person> arDeq) throws NullException {
       FieldsWork fw = new FieldsWork();
       Person person = new Person(fw.id(), fw.name(), fw.coordinates(), fw.height(),
               fw.passport(), fw.color(), fw.country(), fw.location());
       arDeq.offerLast(person);
   }
}
