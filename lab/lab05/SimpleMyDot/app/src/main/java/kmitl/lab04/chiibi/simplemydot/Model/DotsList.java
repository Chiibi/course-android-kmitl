package kmitl.lab04.chiibi.simplemydot.Model;

import java.util.ArrayList;
import java.util.List;

import kmitl.lab04.chiibi.simplemydot.MainActivity;

public class DotsList {
    public interface onDotsListChangeListener{
        void onDotsListChanged(DotsList dotsList);
    }

    private onDotsListChangeListener listener;
    private List<Dot> allDots = new ArrayList<>();

    public void addDot(Dot dot){
        allDots.add(dot);
        this.listener.onDotsListChanged(this);
    }

    public void clear(){
        allDots.clear();
        this.listener.onDotsListChanged(this);
    }

    public void remove(Dot dot) {
        allDots.remove(dot);
        this.listener.onDotsListChanged(this);
    }


    public void undo() {
        if(allDots != null && allDots.size() > 0){
            allDots.remove(allDots.size()-1);
            this.listener.onDotsListChanged(this);
        }
    }

    public boolean findDotGap(Dot dot, int touchX, int touchY){
        double gap;
        if(dot != null){
            gap = Math.sqrt(Math.pow(dot.getCordX()-touchX, 2) + Math.pow(dot.getCordY()-touchY, 2));
            if(gap <= dot.getRadius()){
                return true;
            }
        }
        return false;
    }



    public List<Dot> getAllDots() {
        return allDots;
    }

    public void setListener(onDotsListChangeListener listener){
        this.listener = listener;
    }

}
