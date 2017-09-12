package kmitl.lab04.chiibi.simplemydot.Model;

public class Dot {

    private int cordX;
    private int cordY;
    private int radius;
    private int color;

    public Dot(int cordX, int cordY, int radius, int color) {
        this.cordX = cordX;
        this.cordY = cordY;
        this.radius = radius;
        this.color = color;
    }

    public int getCordX() {
        return cordX;
    }

    public void setCordX(int cordX) {
        this.cordX = cordX;
    }

    public int getCordY() {
        return cordY;
    }

    public void setCordY(int cordY) {
        this.cordY = cordY;
    }

    public int getRadius() {
        return radius;
    }

    public void setRadius(int radius) {
        this.radius = radius;
    }

    public int getColor() {
        return color;
    }

    public void setColor(int dotColor) {
        this.color = dotColor;
    }
}