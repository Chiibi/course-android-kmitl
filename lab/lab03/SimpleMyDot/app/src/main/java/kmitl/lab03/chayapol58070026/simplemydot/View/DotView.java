package kmitl.lab03.chayapol58070026.simplemydot.View;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.view.View;

import java.util.ArrayList;

import kmitl.lab03.chayapol58070026.simplemydot.Model.Dot;

public class DotView extends View {

    private Paint paint;
    private ArrayList<Dot> dotList;

    public DotView(Context context) {
        super(context);
        paint = new Paint();
    }

    public DotView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        paint = new Paint();
    }

    public DotView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        paint = new Paint();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if(dotList != null) {
            for (Dot dot : dotList) {
                paint.setColor(dot.getDotColor());
                canvas.drawCircle(dot.getCordX(), dot.getCordY(), dot.getRadius(), paint);
            }
        }
    }

    public boolean checkGap(Dot dot, int tCordX, int tCordY) {
        double distance;
        if (dot != null) {
            distance = Math.sqrt(Math.pow(dot.getCordX() - tCordX, 2) + Math.pow(dot.getCordY() - tCordY, 2));
            if (distance <= dot.getRadius()) {
                return true;
            }
        }
        return false;
    }

    public void removeDot(Dot dot) {
        dotList.remove(dot);
    }

    public ArrayList<Dot> getDotList() {
        return dotList;
    }

    public void setDotList(ArrayList<Dot> dotList) {
        this.dotList = dotList;
    }
}
