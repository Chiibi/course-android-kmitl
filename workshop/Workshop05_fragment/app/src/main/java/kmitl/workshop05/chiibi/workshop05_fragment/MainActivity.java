package kmitl.workshop05.chiibi.workshop05_fragment;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import kmitl.workshop05.chiibi.workshop05_fragment.Fragment.AFragment;
import kmitl.workshop05.chiibi.workshop05_fragment.Fragment.BFragment;
import kmitl.workshop05.chiibi.workshop05_fragment.Fragment.CFragment;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .add(R.id.FrameLayout1, new AFragment())
                .commit();

        Button btnA = (Button) findViewById(R.id.buttonA);
        btnA.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                FragmentManager fragmentManager = getSupportFragmentManager();
                fragmentManager.beginTransaction()
                        .replace(R.id.FrameLayout1, new AFragment())
                        .commit();
            }
        });

        Button btnB = (Button) findViewById(R.id.buttonB);
        btnB.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                FragmentManager fragmentManager = getSupportFragmentManager();
                fragmentManager.beginTransaction()
                        .replace(R.id.FrameLayout1, new BFragment())
                        .commit();
            }
        });

        Button btnC = (Button) findViewById(R.id.buttonC);
        btnC.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                FragmentManager fragmentManager = getSupportFragmentManager();
                fragmentManager.beginTransaction()
                        .replace(R.id.FrameLayout1, new CFragment())
                        .commit();
            }
        });
    }
}
