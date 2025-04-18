import java.util.ArrayDeque;
import commands.*;
import basic.*;
import javax.naming.InvalidNameException;

public class Main{
    public static void main(String[] args) throws NullException, InvalidNameException {
        CollectionController coll = new CollectionController();
        Add add = new Add();
        add.add(coll.collection());
        System.out.println(coll.collection().peekLast());
    }
}
