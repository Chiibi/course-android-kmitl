package kmitl.lab04.chiibi.simplemydot.View;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import kmitl.lab04.chiibi.simplemydot.Model.Dot;
import kmitl.lab04.chiibi.simplemydot.Model.DotsList;

public class DotView extends View {
    public interface OnDotViewPressListener{
        void onDotViewPressed(int x, int y);
    }

    private Paint paint;
    private DotsList dotsList;
    private OnDotViewPressListener listener;

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
        if(this.dotsList != null){
            for (Dot dot: dotsList.getAllDots()) {
                paint.setColor(dot.getColor());
                canvas.drawCircle(dot.getCordX(), dot.getCordY(), dot.getRadius(), paint);
            }
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch(event.getAction()){
            case MotionEvent.ACTION_DOWN:
                this.listener.onDotViewPressed((int) event.getX(), (int) event.getY());
                return true;
        }
        return false;
    }

    public void setDotsList(DotsList dotsList) {
        this.dotsList = dotsList;
    }

    public void setOnDotViewPressListener(OnDotViewPressListener listener) {
        this.listener = listener;
    }
}

