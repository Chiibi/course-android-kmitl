package kmitl.lab04.chiibi.simplemydot.Fragment;


import android.content.DialogInterface;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.SeekBar;

import com.flask.colorpicker.ColorPickerView;
import com.flask.colorpicker.OnColorSelectedListener;
import com.flask.colorpicker.builder.ColorPickerClickListener;
import com.flask.colorpicker.builder.ColorPickerDialogBuilder;

import kmitl.lab04.chiibi.simplemydot.Model.Dot;
import kmitl.lab04.chiibi.simplemydot.R;
import kmitl.lab04.chiibi.simplemydot.View.DotPreview;


/**
 * A simple {@link Fragment} subclass.
 */
public class EditorFragment extends Fragment implements View.OnClickListener, SeekBar.OnSeekBarChangeListener{
    public interface EditorFragmentListener{
        void onEditApply();
    }

    private DotPreview dotPreview;
    private Button colorPicker;
    private Button applyBtn;
    private Button cancelBtn;
    private SeekBar radSeeker;

    private Dot dot;
    private Dot preDot;

    private EditorFragmentListener listener;

    public EditorFragment() {
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        dot = getArguments().getParcelable("dot");
        preDot = new Dot(dot.getCordX(), dot.getCordY(), dot.getRadius(), dot.getColor());
        listener = (EditorFragmentListener) getActivity();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_editor_fragment, container, false);
        initView(rootView);
        return rootView;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.colorPickerBtn:
                onClickColorPicker();
                break;
            case R.id.cancelBtn:
                onClickCancel();
                break;
            case R.id.applyBtn:
                onClickApply();
                break;
        }
    }

    @Override
    public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
        updatePreDotRadius(i+20);
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {

    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {

    }

    private void initView(View rootView) {
        dotPreview = rootView.findViewById(R.id.dotPreview);
        colorPicker = rootView.findViewById(R.id.colorPickerBtn);
        applyBtn = rootView.findViewById(R.id.applyBtn);
        cancelBtn = rootView.findViewById(R.id.cancelBtn);
        radSeeker = rootView.findViewById(R.id.radSeek);
        //---------------------------------------------------------
        dotPreview.setDot(preDot);
        colorPicker.setBackgroundColor(preDot.getColor());
        radSeeker.setProgress(preDot.getRadius());
        radSeeker.setOnSeekBarChangeListener(this);
        colorPicker.setOnClickListener(this);
        applyBtn.setOnClickListener(this);
        cancelBtn.setOnClickListener(this);
    }

    private void onClickApply() {
        dot.setCordX(preDot.getCordX());
        dot.setCordY(preDot.getCordY());
        dot.setRadius(preDot.getRadius());
        dot.setColor(preDot.getColor());
        listener.onEditApply();
    }

    private void onClickCancel() {
        listener.onEditApply();
    }

    private void onClickColorPicker() {
        buildColorPicker();
    }

    private void buildColorPicker(){
        ColorPickerDialogBuilder
                .with(getContext())
                .setTitle("Select Color")
                .initialColor(preDot.getColor())
                .wheelType(ColorPickerView.WHEEL_TYPE.CIRCLE)
                .density(12)
                .setOnColorSelectedListener(new OnColorSelectedListener() {
                    @Override
                    public void onColorSelected(int selectedColor) {
                    }
                })
                .setPositiveButton("Set", new ColorPickerClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int selectedColor, Integer[] allColors) {
                        colorPicker.setBackgroundColor(selectedColor);
                        updatePreDotColor(selectedColor);
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                })
                .build()
                .show();
    }

    private void updatePreDotColor(int color) {
        preDot.setColor(color);
        dotPreview.setDot(preDot);
        dotPreview.invalidate();
    }

    private void updatePreDotRadius(int radius) {
        preDot.setRadius(radius);
        dotPreview.setDot(preDot);
        dotPreview.invalidate();
    }


    public static EditorFragment newInstance(Dot dot){
        Bundle args = new Bundle();
        EditorFragment editorFragment = new EditorFragment();
        args.putParcelable("dot", dot);
        editorFragment.setArguments(args);
        return editorFragment;
    }

    public void setListener(EditorFragmentListener listener) {
        this.listener = listener;
    }
}
