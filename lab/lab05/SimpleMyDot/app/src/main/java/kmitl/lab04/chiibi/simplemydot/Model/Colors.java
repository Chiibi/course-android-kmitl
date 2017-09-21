package kmitl.lab04.chiibi.simplemydot.Model;

import android.graphics.Color;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Colors {
    private static List<Integer> colorsList = new ArrayList<>();

    public static int getColorByRandom(){
        colorsList.add(Color.RED);
        colorsList.add(Color.GREEN);
        colorsList.add(Color.BLUE);
        colorsList.add(Color.CYAN);
        colorsList.add(Color.MAGENTA);
        colorsList.add(Color.YELLOW);
        colorsList.add(Color.GRAY);
        return colorsList.get(new Random().nextInt(colorsList.size()));
    }

}
