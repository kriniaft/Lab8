package basic;
public class Person {
    private long id; //Поле не может быть null, Значение поля должно быть больше 0, Значение этого поля должно быть уникальным, Значение этого поля должно генерироваться автоматически
    private String name; //Поле не может быть null, Строка не может быть пустой
    private Coordinates coordinates; //Поле не может быть null
    private java.time.ZonedDateTime creationDate; //Поле не может быть null, Значение этого поля должно генерироваться автоматически
    private float height; //Поле не может быть null, Значение поля должно быть больше 0
    private String passportID; //Поле может быть null
    private Color hairColor; //Поле не может быть null
    private Country nationality; //Поле может быть null
    private Location location; //Поле не может быть null

    public Person(long i){
        id = i;
    }

    public Person(long i, String n){
        id = i;
        name = n;
    }

    public Person(long i, String n, Coordinates c){
        id = i;
        name = n;
        coordinates = c;
    }

    public Person(long i, String n, Coordinates c, float h){
        id = i;
        name = n;
        coordinates = c;
        height = h;
    }

    public Person(long i, String n, Coordinates c, float h, String p){
        id = i;
        name = n;
        coordinates = c;
        height = h;
        passportID = p;
    }

    public Person(long i, String n, Coordinates c, float h, String p, Color hc){
        id = i;
        name = n;
        coordinates = c;
        height = h;
        passportID = p;
        hairColor = hc;
    }

    public Person(long i, String n, Coordinates c, float h, String p, Color hc, Country nt){
        id = i;
        name = n;
        coordinates = c;
        height = h;
        passportID = p;
        hairColor = hc;
        nationality = nt;
    }

    public Person(long i, String n, Coordinates c, float h, String p, Color hc, Country nt, Location l){
        id = i;
        name = n;
        coordinates = c;
        height = h;
        passportID = p;
        hairColor = hc;
        nationality = nt;
        location = l;
    }


    public long getId(){
        return id;
    }

    public String getName(){
        return name;
    }

    public Coordinates getCoordinates(){
        return coordinates;
    }

    public java.time.ZonedDateTime getCreationDate(){
        return creationDate;
    }

    public float getHeight(){
        return height;
    }

    public String getPassportID(){
        return passportID;
    }

    public Color getHairColor(){
        return hairColor;
    }

    public Country getNationality(){
        return nationality;
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
