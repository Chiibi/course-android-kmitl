package kmitl.lab04.chiibi.simplemydot;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import kmitl.lab04.chiibi.simplemydot.Model.Colors;
import kmitl.lab04.chiibi.simplemydot.Model.Dot;
import kmitl.lab04.chiibi.simplemydot.Model.DotsList;
import kmitl.lab04.chiibi.simplemydot.Utility.Convertor;
import kmitl.lab04.chiibi.simplemydot.View.DotView;

public class MainActivity extends AppCompatActivity implements DotsList.onDotsListChangeListener, DotView.OnDotViewPressListener{
    private DotView dotView;
    private DotsList dotsList;
    private Colors colors;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        dotView = (DotView) findViewById(R.id.dotView);
        dotView.setOnDotViewPressListener(this);
        dotsList = new DotsList();
        dotsList.setListener(this);
        colors = new Colors();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.shareBtn :
                this.onShare();
                return super.onOptionsItemSelected(item);
            default : return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflator = getMenuInflater();
        inflator.inflate(R.menu.activity_main_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public void onDotsListChanged(DotsList dotsList) {
        dotView.setDotsList(dotsList);
        dotView.invalidate();
    }

    @Override
    public void onDotViewPressed(int x, int y) {
        boolean end = true;
        List<Dot> cloneList = dotsList.getAllDots();
        Collections.reverse(cloneList);
        for (Dot dot: cloneList) {
            if(dotsList.findDotGap(dot, x, y)){
                dotsList.remove(dot);
                end = false;
                Collections.reverse(cloneList);
                break;
            }
        }
        if(end){
            Collections.reverse(cloneList);
            dotsList.addDot(new Dot(x, y, 50, colors.getcolorByRandom()));
        }
    }

    public void onShare(){
        if(requestWriteExternalStoragePermission()){
            Bitmap bitmap = Convertor.convertViewtoBitmap(dotView);
            Uri bitmapUri = getImageUri(this.getApplicationContext(), bitmap);
            share(bitmapUri);
        }
    }

    public boolean requestWriteExternalStoragePermission(){
        if(ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 100);
            return false;
        }else{
            return true;
        }
    }

    public Uri getImageUri(Context context, Bitmap bitmap){
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
        String path = MediaStore.Images.Media.insertImage(context.getContentResolver(), bitmap, "Untitled", null);
        return Uri.parse(path);
    }

    public void onRandomDot(View view){
        Random rand = new Random();
        int CordX = rand.nextInt(dotView.getWidth());
        int CordY = rand.nextInt(dotView.getHeight());
        Dot dot = new Dot(CordX, CordY, 50, colors.getcolorByRandom());
        dotsList.addDot(dot);
    }

    public void onClear(View view){
        dotsList.clear();
    }

    public void onUndo(View view){
        dotsList.undo();
    }



    public void share(Uri uri){
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_SEND);
        intent.putExtra(intent.EXTRA_STREAM, uri);
        intent.setType("image/*");
        startActivity(Intent.createChooser(intent, "Share Screen"));
    }
}
