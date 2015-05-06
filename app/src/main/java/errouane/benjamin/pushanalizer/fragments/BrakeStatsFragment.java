package errouane.benjamin.pushanalizer.fragments;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import errouane.benjamin.pushanalizer.R;
import errouane.benjamin.pushanalizer.algorithms.BrakeDetector;
import errouane.benjamin.pushanalizer.dataListener.RotationDataEvent;
import errouane.benjamin.pushanalizer.session.Session;

/**
 * Created by Benni on 22.03.2015.
 */
public class BrakeStatsFragment extends ViewPagerFragment {
    private TextView text;
    private List<BrakeDetector.BrakeData> brakes = new ArrayList<BrakeDetector.BrakeData>();

    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_brake_stats, container, false);

        text = (TextView) view.findViewById(R.id.textView10);

        return view;
    }

    @Override
    public void reset() {
        brakes.clear();
        updateText();
    }

    @Override
    public void onStart() {
        super.onStart();
        updateText();
    }

    @Override
    public void onStop() {
        super.onStop();
        Log.e("asd", "STOP");
    }

    @Override
    public String getDescription() {
        return "Brake Stats";
    }

    @Override
    public void newRotationData(RotationDataEvent event) {

    }

    @Override
    public void newPush() {

    }

    @Override
    public void newBrake(BrakeDetector.BrakeData results) {
        brakes.add(results);
        updateText();
    }

    private void updateText() {
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                StringBuilder b = new StringBuilder();
                for(BrakeDetector.BrakeData brake : brakes) {
                    b.append(String.format("\n\nAverage Deceleration: %.1f\nDistance: %.1f\nVariance: %.1f",
                            brake.getAverageDeceleration(),
                            brake.getDistance()*1000f,
                            brake.getBrakeVariance()));
                }
                final String s = b.toString();

                text.setText(text.getText() + s);
            }
        });
    }
}
