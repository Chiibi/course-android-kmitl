package kmitl.lab07.chiibi.mylazyinstagram;

import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.wang.avi.AVLoadingIndicatorView;

import kmitl.lab07.chiibi.mylazyinstagram.Model.UserProfile;
import kmitl.lab07.chiibi.mylazyinstagram.Utility.PostAdapter;
import kmitl.lab07.chiibi.mylazyinstagram.Utility.RetrofitAPI;
import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener{

    private UserProfile userProfile;
    private View overlay;
    private View rootView;
    private AVLoadingIndicatorView avi;
    private Button followBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        rootView = findViewById(R.id.root_main);
        overlay = findViewById(R.id.main_container);
        avi = findViewById(R.id.avi);
        followBtn = findViewById(R.id.followButton);

        setInitSpinner();
        getUserProfile("android");
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        getUserProfile((String) adapterView.getItemAtPosition(i));
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

    private void showProfile(UserProfile userProfile) {
        TextView textUser = findViewById(R.id.textUser);
        TextView textPost = findViewById(R.id.textPost);
        TextView textFollower = findViewById(R.id.textFollower);
        TextView textFollowing = findViewById(R.id.textFollowing);
        TextView textBio = findViewById(R.id.textBio);

        textUser.setText("@" + userProfile.getUser());
        textPost.setText(String.valueOf(userProfile.getPost()));
        textFollower.setText(String.valueOf(userProfile.getFollower()));
        textFollowing.setText(String.valueOf(userProfile.getFollowing()));
        textBio.setText(userProfile.getBio());

        ImageView imageProfile = findViewById(R.id.imageProfile);
        Glide.with(this).load(userProfile.getUrlProfile()).into(imageProfile);

        if(userProfile.isFollow()) {
            setBtnFollowed();
        } else {
            setBtnFollow();
        }
    }

    private void setBtnFollowed() {
        followBtn.setText("Followed");
        followBtn.setTextColor(Color.rgb(255,255,255));
        followBtn.getBackground().setColorFilter(ContextCompat.getColor(this, android.R.color.darker_gray), PorterDuff.Mode.MULTIPLY);
    }

    private void setBtnFollow() {
        followBtn.setText("Follow");
        followBtn.setTextColor(Color.rgb(255,255,255));
        followBtn.getBackground().setColorFilter(ContextCompat.getColor(this, android.R.color.holo_blue_dark), PorterDuff.Mode.MULTIPLY);
    }

    private void showImg(UserProfile userProfile, int item) {
        RecyclerView list = findViewById(R.id.list);
        list.setLayoutManager(new GridLayoutManager(this, item));
        PostAdapter adapter = new PostAdapter(this);
        adapter.setData(userProfile.getPosts());
        list.setAdapter(adapter);
    }

    private void setInitSpinner() {
        Spinner spinner = findViewById(R.id.spinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.acc_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);
    }

    private void getUserProfile(String itemId) {
        showActivityIndicator();
        OkHttpClient client = new OkHttpClient.Builder().build();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(RetrofitAPI.BASE)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        RetrofitAPI api = retrofit.create(RetrofitAPI.class);
        api.getProfile(itemId).enqueue(new retrofit2.Callback<UserProfile>() {
            @Override
            public void onResponse(Call<UserProfile> call, Response<UserProfile> response) {
                Log.d("Debug", "onResponse:" + response.body());
                userProfile = response.body();
                showProfile(userProfile);
                showImg(userProfile, 3);
                hideActivityIndicator();
            }

            @Override
            public void onFailure(Call<UserProfile> call, Throwable t) {
                Log.d("Debug", "onFailure");
                hideActivityIndicator();
            }
        });
    }

    public void onFollowBtnPressed(View view) {
        showActivityIndicator();

        OkHttpClient client = new OkHttpClient.Builder().build();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(RetrofitAPI.BASE)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        RetrofitAPI api = retrofit.create(RetrofitAPI.class);
        api.postProfile(userProfile.getUser(), !userProfile.isFollow()).enqueue(new retrofit2.Callback<UserProfile>() {
            @Override
            public void onResponse(Call<UserProfile> call, Response<UserProfile> response) {
                Log.d("Debug", "onResponse:" + response.body());
                getUserProfile(userProfile.getUser());
            }

            @Override
            public void onFailure(Call<UserProfile> call, Throwable t) {
                Log.d("Debug", "onFailure");
                hideActivityIndicator();
            }
        });

    }

    private void showActivityIndicator() {
        rootView.setClickable(false);
        overlay.bringToFront();
        overlay.setVisibility(View.VISIBLE);
    }

    private void hideActivityIndicator() {
        rootView.setClickable(true);
        overlay.bringToFront();
        overlay.setVisibility(View.INVISIBLE);
    }

    public void onGridButtonClick(View view) {
        showImg(userProfile, 3);
    }

    public void onListButtonClicked(View view) {
        showImg(userProfile, 1);
    }
}
