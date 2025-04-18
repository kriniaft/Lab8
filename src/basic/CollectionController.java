package basic;
import java.util.ArrayDeque;
public class CollectionController {
    ArrayDeque<Person> profiles = new ArrayDeque<>();
    public ArrayDeque<Person> collection(){
        return profiles;
    }
}
