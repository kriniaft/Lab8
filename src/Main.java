import java.util.ArrayDeque;
import java.util.HashMap;
import java.util.Scanner;

import commands.*;
import basic.*;
import javax.naming.InvalidNameException;

public class Main{
    public static void main(String[] args) throws NullException, InvalidNameException {
        CollectionController coll = new CollectionController();
        Add add = new Add();
        add.execute(coll.collection());
        System.out.println(coll.collection().peekLast());
        System.out.println("Введите название команды или help, чтобы узнать больше о командах (для выхода используйте 'exit'");

        Scanner in = new Scanner(System.in);

        HashMap<String, Command> map = new HashMap<>();
        Add.register(map);
        Show.register(map);

        while (in.hasNextLine()) {
            String line = in.nextLine();

        }
    }
}
