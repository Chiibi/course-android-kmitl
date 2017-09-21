package kmitl.lab04.chiibi.simplemydot.View;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;

import kmitl.lab04.chiibi.simplemydot.Model.Dot;
import kmitl.lab04.chiibi.simplemydot.Model.DotsList;

public class DotView extends View {
    public interface OnDotViewPressListener{
        void onDotViewPressed(int x, int y);
        void onDotViewLongPressed(int x, int y);
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

    final GestureDetector gestureDetector = new GestureDetector(getContext(), new GestureDetector.SimpleOnGestureListener(){
        @Override
        public boolean onSingleTapUp(MotionEvent e) {
            listener.onDotViewPressed((int) e.getX(),(int) e.getY());
            return super.onSingleTapUp(e);
        }

        @Override
        public boolean onDown(MotionEvent e) {
            return true;
        }

        @Override
        public void onLongPress(MotionEvent e) {
            listener.onDotViewLongPressed((int) e.getX(),(int) e.getY());
        }
    });

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
        return gestureDetector.onTouchEvent(event);
    }

    public void setDotsList(DotsList dotsList) {
        this.dotsList = dotsList;
    }

    public void setOnDotViewPressListener(OnDotViewPressListener listener) {
        this.listener = listener;
    }
}

