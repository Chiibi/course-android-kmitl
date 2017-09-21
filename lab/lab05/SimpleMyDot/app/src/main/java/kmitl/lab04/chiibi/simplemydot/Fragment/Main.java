package kmitl.lab04.chiibi.simplemydot.Fragment;


import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import java.io.ByteArrayOutputStream;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import kmitl.lab04.chiibi.simplemydot.Model.Colors;
import kmitl.lab04.chiibi.simplemydot.Model.Dot;
import kmitl.lab04.chiibi.simplemydot.Model.DotsList;
import kmitl.lab04.chiibi.simplemydot.R;
import kmitl.lab04.chiibi.simplemydot.Utility.Convertor;
import kmitl.lab04.chiibi.simplemydot.View.DotView;

/**
 * A simple {@link Fragment} subclass.
 */
public class Main extends Fragment implements DotsList.onDotsListChangeListener, DotView.OnDotViewPressListener, View.OnClickListener{

    public interface onSelectDotListener{
        void onSelectDot(Dot dot);
    }

    private DotView dotView;
    private DotsList dotsList;
    private onSelectDotListener listener;

    public Main() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(savedInstanceState == null){
            dotsList = new DotsList();
        } else {
            dotsList = savedInstanceState.getParcelable("dotslist");
        }
        dotsList.setListener(this);
        listener = (onSelectDotListener) getActivity();
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.activity_main_menu, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.shareBtn:
                this.onShare();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_main, container, false);
        initView(rootView);
        setHasOptionsMenu(true);
        return rootView;
    }

    @Override
    public void onDotsListChanged(DotsList dotsList) {
        dotView.setDotsList(dotsList);
        dotView.invalidate();
    }

    @Override
    public void onDotViewPressed(int x, int y) {
        boolean searchDone = true;
        List<Dot> cloneList = dotsList.getAllDots();
        Collections.reverse(cloneList);
        for (Dot dot: cloneList) {
            if(dotsList.findDotGap(dot, x, y)){
                dotsList.remove(dot);
                searchDone = false;
                Collections.reverse(cloneList);
                break;
            }
        }
        if(searchDone){
            Collections.reverse(cloneList);
            dotsList.addDot(new Dot(x, y, 50, Colors.getColorByRandom()));
        }
    }

    @Override
    public void onDotViewLongPressed(int x, int y) {
        boolean searchDone = true;
        for (Dot dot: dotsList.getAllDots()) {
            if(dotsList.findDotGap(dot, x, y)){
                listener.onSelectDot(dot);
                searchDone = false;
                break;
            }
        }
        if(searchDone){
            dotsList.addDot(new Dot(x, y, 50, Colors.getColorByRandom()));
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.rndBtn:
                randomDot();
                break;
            case R.id.undoBtn:
                undo();
                break;
            case R.id.clearBtn:
                clear();
                break;
        }
    }

    private void initView(View rootView) {
        //<---Blind View--->
        dotView = rootView.findViewById(R.id.dotView);
        Button rndBtn = rootView.findViewById(R.id.rndBtn);
        Button undoBtn = rootView.findViewById(R.id.undoBtn);
        Button clearBtn = rootView.findViewById(R.id.clearBtn);
        //<---Set Attribute--->
        dotView.setOnDotViewPressListener(this);
        rndBtn.setOnClickListener(this);
        undoBtn.setOnClickListener(this);
        clearBtn.setOnClickListener(this);
        dotView.setDotsList(dotsList);
        dotView.invalidate();

    }

    public void randomDot(){
        Random rand = new Random();
        int CordX = rand.nextInt(dotView.getWidth());
        int CordY = rand.nextInt(dotView.getHeight());
        Dot dot = new Dot(CordX, CordY, 50, Colors.getColorByRandom());
        dotsList.addDot(dot);
    }

    public void clear(){
        dotsList.clear();
    }

    public void undo(){
        dotsList.undo();
    }

    public void onShare(){
        if(requestWriteExternalStoragePermission()){
            Bitmap bitmap = Convertor.convertViewtoBitmap(dotView);
            Uri bitmapUri = getImageUri(getActivity().getApplicationContext(), bitmap);
            share(bitmapUri);
        }
    }

    public boolean requestWriteExternalStoragePermission(){
        if(ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 100);
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

    public void share(Uri uri){
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_SEND);
        intent.putExtra(intent.EXTRA_STREAM, uri);
        intent.setType("image/*");
        startActivity(Intent.createChooser(intent, "Share Screen"));
    }

    public static Main newInstance(){
        Bundle args = new Bundle();
        Main mainFragment = new Main();
        mainFragment.setArguments(args);
        return mainFragment;
    }
}
