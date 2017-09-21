package kmitl.lab04.chiibi.simplemydot;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;

import kmitl.lab04.chiibi.simplemydot.Fragment.EditorFragment;
import kmitl.lab04.chiibi.simplemydot.Fragment.Main;
import kmitl.lab04.chiibi.simplemydot.Model.Dot;

public class MainActivity extends AppCompatActivity implements Main.onSelectDotListener, EditorFragment.EditorFragmentListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if(savedInstanceState == null){
            createMainFragment();
        }
    }

    private void createMainFragment() {
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .add(R.id.fragmentContainer, Main.newInstance())
                .commit();
    }

    private void setFragment(Fragment fragment){
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.fragmentContainer, fragment)
                .addToBackStack(null)
                .commit();
    }


    @Override
    public void onSelectDot(Dot dot) {
        setFragment(EditorFragment.newInstance(dot));
    }

    @Override
    public void onEditApply() {
        getSupportFragmentManager().popBackStack();
    }
}
