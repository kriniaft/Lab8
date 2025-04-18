package basic;

public class Location {
    private float x; //Поле не может быть null
    private float y;
    private float z; //Поле не может быть null

    public Location(float x, float y, float z){
        this.x = x;
        this.y = y;
        this.z = z;
    }

    @Override
    public String toString() {
        return "(" + x +", " + y + ", " + z + ")";
    }
}