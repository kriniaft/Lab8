package basic;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.util.Random;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

@XmlRootElement
public class Person {
    private long id; //Поле не может быть null, Значение поля должно быть больше 0, Значение этого поля должно быть уникальным, Значение этого поля должно генерироваться автоматически
    private String name;
    private Coordinates coordinates;
    private final java.time.ZonedDateTime creationDate; //Поле не может быть null, Значение этого поля должно генерироваться автоматически
    private Float height;
    private String passportID;
    private Color hairColor;
    private Country nationality;
    private Location location;
    private String Creator;


    public Person(long id, String n, Coordinates c, Float h, String p,
                  Color hc, Country nt, Location l, ZonedDateTime creationDate) {
        this.id = id;
        this.name = n;
        this.coordinates = c;
        this.height = h;
        this.passportID = p;
        this.hairColor = hc;
        this.nationality = nt;
        this.location = l;

        this.creationDate = ZonedDateTime.now(nationality.getZoneId());
    }

    public Person(long id, String n, Coordinates c, Float h, String p,
                  Color hc, Country nt, Location l) {
        this(id, n, c, h, p, hc, nt, l,
                ZonedDateTime.now(nt.getZoneId()));
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
    public Float getHeight() {
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


    public void setId(int anInt) {
        id = anInt;
    }



    public String toString(){
        return "ID " + id + ", " + "name: " + name + ", " + "Coordinates: " + coordinates + ", " +
                "Date: " +  creationDate + ", " + "Height: " + height + ", " + "Passport: " + passportID + ", " +
                "Hair color: " + hairColor + ", " + "Nationality: " + nationality + ", " + "Location: " + location + ".";
    }


}
