package kmitl.lab03.chayapol58070026.simplemydot;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Random;
import kmitl.lab03.chayapol58070026.simplemydot.Model.Dot;
import kmitl.lab03.chayapol58070026.simplemydot.View.DotView;

public class MainActivity extends AppCompatActivity{

    private ArrayList<Dot> dotList;
    private DotView dotView;
    private Random randObj;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        dotView = (DotView) findViewById(R.id.dotView);
        dotList = new ArrayList<>();
        randObj = new Random();
    }

    public void addDotOnBtnClick(View view) {
        int cordX = randObj.nextInt(this.dotView.getWidth());
        int cordY = randObj.nextInt(this.dotView.getHeight());
        int color = Color.rgb(randObj.nextInt(255), randObj.nextInt(255),randObj.nextInt(255));
        dotList.add(new Dot(cordX, cordY, 60, color));
        dotView.setDotList(dotList);
        dotView.invalidate();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if(event.getAction() == MotionEvent.ACTION_UP){
            int scrPosition[] = new int[2];
            dotView.getLocationOnScreen(scrPosition);
            int touchCordX = (int) (event.getX() - scrPosition[0]);
            int touchCordY = (int) (event.getY() - scrPosition[1]);
            for(Dot dot:dotList){
                boolean gapState = dotView.checkGap(dot, touchCordX, touchCordY);
                if(gapState){
                    dotView.removeDot(dot);
                    dotView.invalidate();
                    return super.onTouchEvent(event);
                }
            }
            addDotOnTouch(touchCordX, touchCordY);
        }
        return super.onTouchEvent(event);
    }

    public void addDotOnTouch(int cordX, int cordY){
        int color = Color.rgb(randObj.nextInt(255), randObj.nextInt(255),randObj.nextInt(255));
        dotList.add(new Dot(cordX, cordY, 60, color));
        dotView.setDotList(dotList);
        dotView.invalidate();
    }

    public void onClearBtnClick(View view){
        dotList.clear();
        dotView.setDotList(dotList);
        dotView.invalidate();
    }
}
