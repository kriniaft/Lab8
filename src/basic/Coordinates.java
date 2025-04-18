package basic;

public class Coordinates {
    private float x; //Максимальное значение поля: 946
    private float y;
    public Coordinates(float x, float y){
        setX(x);
        this.x = getX();
        this.y = y;
    }

    public void setX(float x){
        if(x < 946){
            this.x = x;
        } else throw new IllegalArgumentException("Координата должна быть меньше 946");
    }

    public float getX(){
        return x;
    }

    public void setY(float y){
      this.y = y;
    }

    public float getY(){
        return y;
    }


    @Override
    public String toString() {
        return "(" + x +", " + y +  ")";
    }
}
