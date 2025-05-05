package basic;
import java.time.ZonedDateTime;
import java.util.Random;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

@XmlRootElement
public class Person {
    private final long id; //Поле не может быть null, Значение поля должно быть больше 0, Значение этого поля должно быть уникальным, Значение этого поля должно генерироваться автоматически
    private String name;
    private Coordinates coordinates;
    private final java.time.ZonedDateTime creationDate; //Поле не может быть null, Значение этого поля должно генерироваться автоматически
    private Float height;
    private String passportID;
    private Color hairColor;
    private Country nationality;
    private Location location;

    public Person() {
        this.id = new Random().nextLong(1000);
        this.creationDate = ZonedDateTime.now(); // временная зона может быть уточнена позже
    }

    public Person(String n, Coordinates c, Float h, String p, Color hc, Country nt, Location l){
        setName(n);
        setCoordinates(c);
        setHeight(h);
        setPassportID(p);
        setHairColor(hc);
        setNationality(nt);
        setLocation(l);
        creationDate = ZonedDateTime.now(nt.getZoneId());
        Random random = new Random();
        id = random.nextLong(1000);

    }

    @XmlElement
    public long getId(){
        return id;
    }


    public void setName(String n){
        if(n != null && !n.isEmpty()){
        name = n;} else { throw new IllegalArgumentException("Некорректное имя");}
    }



    @XmlElement
    public String getName(){
        return name;
    }

    public void setCoordinates(Coordinates c){
        if(c != null){
            coordinates = c;} else { throw new IllegalArgumentException("Некорректные координаты");}
    }

    @XmlElement
    public Coordinates getCoordinates(){
        return coordinates;
    }

    @XmlJavaTypeAdapter(ZonedDateTimeAdapter.class)
    public java.time.ZonedDateTime getCreationDate(){
        return creationDate;
    }

    public void setHeight(Float h){
        if( h != null && h>0){
            height = h;
        }else{throw new IllegalArgumentException("Вы ввели неверный рост");}
    }


    @XmlElement
    public float getHeight() {
        return height;
    }

    public void setPassportID(String p){
        if(p != null){
            passportID = p;} else { throw new IllegalArgumentException("Некорректные данные");}
    }

    @XmlElement
    public String getPassportID(){
        return passportID;
    }

    public void setHairColor(Color c){
        if(c != null){
            hairColor = c;}else{ throw new IllegalArgumentException("Некорректный цвет волос");}
    }

    @XmlElement
    public Color getHairColor(){
        return hairColor;
    }

    public void setNationality(Country c){
        if(c != null){
            nationality = c;} else { throw new IllegalArgumentException("Некорректная страна");}
    }

    @XmlElement
    public Country getNationality(){
        return nationality;
    }

    public void setLocation(Location l){
        if(l != null){
            location = l;} else { throw new IllegalArgumentException("Некорректные координаты");}
    }

    @XmlElement
    public Location getLocation(){
        return location;
    }

    public String toString(){
        return "ID " + id + ", " + "name: " + name + ", " + "Coordinates: " + coordinates + ", " +
                "Date: " +  creationDate + ", " + "Height: " + height + ", " + "Passport: " + passportID + ", " +
                "Hair color: " + hairColor + ", " + "Nationality: " + nationality + ", " + "Location: " + location + ".";
    }
}
