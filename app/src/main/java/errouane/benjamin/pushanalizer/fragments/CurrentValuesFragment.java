package errouane.benjamin.pushanalizer.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import errouane.benjamin.pushanalizer.R;
import errouane.benjamin.pushanalizer.ViewPagerFragment;
import errouane.benjamin.pushanalizer.dataListener.RotationDataEvent;
import errouane.benjamin.pushanalizer.dataListener.RotationDataListener;


public class CurrentValuesFragment extends ViewPagerFragment {
    private float currentSpeed = 1f;
    private TextView speedText;
    private String description;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_current_values, container, false);
        speedText = (TextView) view.findViewById(R.id.speedTextView);
        return view;
    }

    @Override
    public void newRotationData(RotationDataEvent event) {
        speedText.setText(Float.toString(event.getRotationSpeed()));
    }

    @Override
    public String getDescription() {
        return "Current Values";
    }
}
