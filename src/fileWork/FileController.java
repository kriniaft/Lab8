package fileWork;
import javax.xml.bind.*;
import javax.xml.bind.JAXBException;
import java.io.*;
import java.util.*;
import basic.*;
import commands.NullException;


public class FileController {

    public static List<Person> readFile(String filePath) {
        List<Person> result = new ArrayList<>();
        try (BufferedInputStream bis = new BufferedInputStream(new FileInputStream(filePath))) {
            JAXBContext context = JAXBContext.newInstance(PersonList.class);
            Unmarshaller unmarshaller = context.createUnmarshaller();
            PersonList wrapper = (PersonList) unmarshaller.unmarshal(bis);

            List<Person> rawPersons = wrapper.getPersons();
            if (rawPersons == null) {
                System.out.println(" Файл пуст или не содержит валидных элементов.");
                return result;
            }

            Set<Color> validHairColors = EnumSet.of(Color.YELLOW, Color.GREEN, Color.RED, Color.BLUE, Color.WHITE);
            Set<Country> validNationalities = EnumSet.of(Country.CHINA, Country.USA, Country.GERMANY, Country.SPAIN);

            for (Person p : rawPersons) {
                try {
                    if (p.getName() == null || p.getCoordinates() == null || p.getCreationDate() == null ||
                            p.getPassportID() == null || p.getHairColor() == null || p.getNationality() == null ||
                            p.getLocation() == null) {
                        throw new NullPointerException("У одного из элементов обнаружены значения null");
                    }

                    if (p.getHeight() == null || p.getHeight() <= 0 || p.getHeight() >= 4) {
                        throw new IllegalArgumentException("Рост должен быть > 0 и < 4");
                    }

                    if (!validHairColors.contains(p.getHairColor())) {
                        throw new IllegalArgumentException("Недопустимый цвет волос");
                    }

                    if (!validNationalities.contains(p.getNationality())) {
                        throw new IllegalArgumentException("Недопустимая национальность");
                    }

                    result.add(p);
                } catch (Exception e) {
                    System.out.println("Пропущен некорректный элемент: " + e.getMessage());
                }
            }
        } catch (UnmarshalException e) {
            Throwable linked = e.getLinkedException();
            if (linked != null) {
                System.out.println("Ошибка чтения XML: " + linked.getMessage());
            } else {
                System.out.println("Ошибка чтения XML: " + e.getMessage());
            }

        } catch (FileNotFoundException e) {
            System.out.println("Файл не найден: " + filePath);

        } catch (Exception e) {
            System.out.println("Произошла ошибка при чтении файла: " + e.getMessage());
        }

        return result;
    }


    public void saveToFile(String filePath, Collection<Person> people) throws IOException {
        try (OutputStreamWriter writer = new OutputStreamWriter(new FileOutputStream(filePath))) {
            JAXBContext context = JAXBContext.newInstance(PersonList.class);
            Marshaller marshaller = context.createMarshaller();
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);

            PersonList wrapper = new PersonList();
            wrapper.setPersons(new ArrayList<>(people));
            marshaller.marshal(wrapper, writer);
        } catch (FileNotFoundException e) {
            System.out.println("Файл не найден");
        } catch (SecurityException e) {
            System.out.println("Ошибка прав доступа");
        } catch (IOException e) {
            System.out.println("Не удалось сохранить данные");
        } catch (JAXBException e) {
            System.out.println("Проблема в файле формата xml");
        }

    }
}
