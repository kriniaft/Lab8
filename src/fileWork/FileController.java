package fileWork;
import javax.xml.bind.*;
import java.io.*;
import java.util.*;
import basic.*;


public class FileController {

    public static List<Person> readFile(String filePath) throws Exception {
        try (BufferedInputStream bis = new BufferedInputStream(new FileInputStream(filePath))) {
            JAXBContext context = JAXBContext.newInstance(PersonList.class);
            Unmarshaller unmarshaller = context.createUnmarshaller();
            PersonList wrapper = (PersonList) unmarshaller.unmarshal(bis);
            return wrapper.getPersons();
        }
    }

    public static void saveToFile(String filePath, Collection<Person> people) throws Exception {
        try (OutputStreamWriter writer = new OutputStreamWriter(new FileOutputStream(filePath))) {
            JAXBContext context = JAXBContext.newInstance(PersonList.class);
            Marshaller marshaller = context.createMarshaller();
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);

            PersonList wrapper = new PersonList();
            wrapper.setPersons(new ArrayList<>(people));
            marshaller.marshal(wrapper, writer);
        } catch (Exception e){
        System.out.println("Ошибка при сохранении файла");
        }
    }
}
