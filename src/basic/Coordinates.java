package basic;

public class Coordinates {
    private float x; //Максимальное значение поля: 946
    private float y;
    public Coordinates(float x, float y){
        this.x = x;
        this.y = y;
    }

    @Override
    public String toString() {
        return "(" + x +", " + y +  ")";
    }
}
