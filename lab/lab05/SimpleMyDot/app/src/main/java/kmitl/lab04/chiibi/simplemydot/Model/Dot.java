package kmitl.lab04.chiibi.simplemydot.Model;

import android.os.Parcel;
import android.os.Parcelable;

public class Dot implements Parcelable, Cloneable{

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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.cordX);
        dest.writeInt(this.cordY);
        dest.writeInt(this.radius);
        dest.writeInt(this.color);
    }

    protected Dot(Parcel in) {
        this.cordX = in.readInt();
        this.cordY = in.readInt();
        this.radius = in.readInt();
        this.color = in.readInt();
    }

    public static final Parcelable.Creator<Dot> CREATOR = new Parcelable.Creator<Dot>() {
        @Override
        public Dot createFromParcel(Parcel source) {
            return new Dot(source);
        }

        @Override
        public Dot[] newArray(int size) {
            return new Dot[size];
        }
    };
}