package kmitl.lab03.chayapol58070026.simplemydot.Model;

public class Dot {

    private int cordX;
    private int cordY;
    private int radius;
    private int dotColor;

    public Dot(int cordX, int cordY, int radius, int color) {
        this.cordX = cordX;
        this.cordY = cordY;
        this.radius = radius;
        this.dotColor = color;
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

    public int getDotColor() {
        return dotColor;
    }

    public void setDotColor(int dotColor) {
        this.dotColor = dotColor;
    }
}
