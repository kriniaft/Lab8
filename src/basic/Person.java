package basic;

import java.time.ZonedDateTime;

public class Person {
    private final long id; //Поле не может быть null, Значение поля должно быть больше 0, Значение этого поля должно быть уникальным, Значение этого поля должно генерироваться автоматически
    private String name;
    private Coordinates coordinates;
    private java.time.ZonedDateTime creationDate; //Поле не может быть null, Значение этого поля должно генерироваться автоматически
    private Float height; /
    private String passportID;
    private Color hairColor; //Поле не может быть null
    private Country nationality;
    private Location location; //Поле не может быть null

    public Person(long id, String name, Coordinates coordinates, ZonedDateTime creationDate, Float height, String passportID, Color hairColor, Country nationality, Location location ) throws IllegalArgumentException {
        this.id = id;
        this.setName(name);
        this.setCoordinates(coordinates);
        this.setHeight(height);
        this.creationDate = creationDate;
        this.passportID = passportID;
        this.setHairColor(hairColor);
        this.nationality = nationality;
        this.setLocation(location);
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) throws IllegalArgumentException {
        if(name != null && name.isBlank()) {
            this.name = name;
        } else {
            throw new IllegalArgumentException("name не может быть null, Строка не может быть пустой");
        }
    }

    public Coordinates getCoordinates() {
        return coordinates;
    }

    public void setCoordinates(Coordinates coordinates) throws IllegalArgumentException {
        if(coordinates != null) {
            this.coordinates = coordinates;
        } else {
            throw new IllegalArgumentException("coordinates не может быть null");
        }
    }

    public ZonedDateTime getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(ZonedDateTime creationDate) {
        this.creationDate = creationDate;
    }

    public float getHeight() {
        return height;
    }

    public void setHeight(Float height) {
        if(height != null && height > 0) {
            this.height = height;
        } else {
            throw new IllegalArgumentException("height не может быть null, Значение поля должно быть больше 0");
        }
    }

    public String getPassportID() {
        return passportID;
    }

    public void setPassportID(String passportID) {
        this.passportID = passportID;
    }

    public Color getHairColor() {
        return hairColor;
    }

    public void setHairColor(Color hairColor) {
        if (hairColor != null) {
            this.hairColor = hairColor;
        } else {
            throw new IllegalArgumentException("hairColor не может быть null");
        }
    }

    public Country getNationality() {
        return nationality;
    }

    public void setNationality(Country nationality) {
        this.nationality = nationality;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        if (location != null) {
            this.location = location;
        } else {
            throw new IllegalArgumentException("location не может быть null");
        }
    }

    public String toString(){
        return "ID " + id + ", " + "name: " + name + ", " + "Coordinates: " + coordinates + ", " +
                "Date: " +  creationDate + ", " + "Height: " + height + ", " + "Passport: " + passportID + ", " +
                "Hair color: " + hairColor + ", " + "Nationality: " + nationality + ", " + "Location: " + location + ".";
    }
}
