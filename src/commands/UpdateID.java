package commands;
import basic.*;
import commands.base.*;
import java.util.HashMap;
import java.util.Iterator;

public class UpdateID extends Command {
    private UpdateID() {
        super("update id {element}");
    }

    @Override
    public void execute(Environment env) throws NullException {
        Iterator<Person> iterator = env.profiles.iterator();
        FieldsWork fw = new FieldsWork();
        long id = fw.id();
        while (iterator.hasNext()) {
            Person person = iterator.next();
            if (person.getId() == id) {
                float height = fw.height();
                person.setHeight(height);
                System.out.println("Рост успешно изменен");
            }else {
                System.out.println("Не удалось изменить рост");
            }
        }
        System.out.println("Процесс удаления по росту выполнен");
    }


    @Override
    public String getHelp(){
        return "Обновить значение элемента коллекции по id";
    }

    public static void register(HashMap<String, Command> stringCommandHashMap) {
        UpdateID updateID = new UpdateID();
        stringCommandHashMap.put(updateID.getName(), updateID);

    }
}
