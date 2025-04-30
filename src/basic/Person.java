package basic;
import java.util.Random;
public class Person {
    private final long id; //Поле не может быть null, Значение поля должно быть больше 0, Значение этого поля должно быть уникальным, Значение этого поля должно генерироваться автоматически
    private String name;
    private Coordinates coordinates;
    private java.time.ZonedDateTime creationDate; //Поле не может быть null, Значение этого поля должно генерироваться автоматически
    private Float height;
    private String passportID;
    private Color hairColor;
    private Country nationality;
    private Location location;


    public Person(String n, Coordinates c, Float h, String p, Color hc, Country nt, Location l){
        setName(n);
        setCoordinates(c);
        setHeight(h);
        setPassportID(p);
        setHairColor(hc);
        setNationality(nt);
        setLocation(l);
        Random random = new Random();
        id = random.nextLong(1000);
    }


    public long getId(){
        return id;
    }


    public void setName(String n){
        if(n != null && !n.isEmpty()){
        name = n;} else { throw new IllegalArgumentException("Некорректное имя");}
    }

    public String getName(){
        return name;
    }

    public void setCoordinates(Coordinates c){
        if(c != null){
            coordinates = c;} else { throw new IllegalArgumentException("Некорректные координаты");}
    }
    public Coordinates getCoordinates(){
        return coordinates;
    }

    public java.time.ZonedDateTime getCreationDate(){
        return creationDate;
    }

    public void setHeight(Float h){
        if( h != null && h>0){
            height = h;
        }else{throw new IllegalArgumentException("Вы ввели неверный рост");}
    }


    public float getHeight() {
        return height;
    }

    public void setPassportID(String p){
        if(p != null){
            passportID = p;} else { throw new IllegalArgumentException("Некорректные данные");}
    }

    public String getPassportID(){
        return passportID;
    }

    public void setHairColor(Color c){
        if(c != null){
            hairColor = c;}else{ throw new IllegalArgumentException("Некорректный цвет волос");}
    }
    public Color getHairColor(){
        return hairColor;
    }

    public void setNationality(Country c){
        if(c != null){
            nationality = c;} else { throw new IllegalArgumentException("Некорректная страна");}
    }
    public Country getNationality(){
        return nationality;
    }

    public void setLocation(Location l){
        if(l != null){
            location = l;} else { throw new IllegalArgumentException("Некорректные координаты");}
    }

    public Location getLocation(){
        return location;
    }

    public String toString(){
        return "ID " + id + ", " + "name: " + name + ", " + "Coordinates: " + coordinates + ", " +
                "Date: " +  creationDate + ", " + "Height: " + height + ", " + "Passport: " + passportID + ", " +
                "Hair color: " + hairColor + ", " + "Nationality: " + nationality + ", " + "Location: " + location + ".";
    }
}
