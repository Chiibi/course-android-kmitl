package kmitl.lab04.chiibi.simplemydot.Utility;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.view.View;

public class Convertor {

    public Convertor() {
    }

    public static Bitmap convertViewtoBitmap(View view){
        //view.measure(View.MeasureSpec.UNSPECIFIED, View.MeasureSpec.UNSPECIFIED);
        Bitmap bitmap = Bitmap.createBitmap(view.getMeasuredWidth(), view.getMeasuredHeight(),
                Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        view.layout(0, 0, view.getMeasuredWidth(), view.getMeasuredHeight());
        view.draw(canvas);
        return bitmap;
    }
}
