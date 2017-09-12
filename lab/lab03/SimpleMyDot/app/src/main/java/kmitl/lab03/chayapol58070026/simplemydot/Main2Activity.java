package kmitl.lab03.chayapol58070026.simplemydot;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.io.Serializable;

import kmitl.lab03.chayapol58070026.simplemydot.Model.Profile;

public class Main2Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        Button btnOpenActivity = (Button) findViewById(R.id.btnBack);
        btnOpenActivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        int x = getIntent().getIntExtra("X", 0);
        Profile profile = (Profile) getIntent().getParcelableExtra("profileObj");

        TextView txtView = (TextView) findViewById(R.id.tvValueX);
        txtView.setText(Integer.toString(x));

        TextView txtViewName = (TextView) findViewById(R.id.tvName);
        TextView txtViewAge = (TextView) findViewById(R.id.tvAge);
        TextView txtViewWeight = (TextView) findViewById(R.id.tvWeight);

        txtViewName.setText(profile.getName());
        txtViewAge.setText(Integer.toString(profile.getAge()));
        txtViewWeight.setText(Double.toString(profile.getWeight()));
    }
}
