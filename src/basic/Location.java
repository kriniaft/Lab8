package basic;

public class Location {
    private Float x; //Поле не может быть null
    private float y;
    private Float z; //Поле не может быть null

    public Location(Float x, float y, Float z){
        if(x != null ) {
            this.x = x;
        } else{
            throw new IllegalArgumentException("Некорректный ввод");}
        this.y = y;
        if(z != null) {
            this.z = z;
        } else{
            throw new IllegalArgumentException("Некорректный ввод");}
    }

    public void setX(Float x){
        this.x = x;
    }

    public Float getX(){
        return x;
    }

    public void setY(float y){
        this.y = y;
    }

    public float getY(){
        return y;
    }
    public void setZ(Float z){
        this.z = z;
    }

    public Float getZ(){
        return z;
    }

    @Override
    public String toString() {
        return "(" + x +", " + y + ", " + z + ")";
    }
}