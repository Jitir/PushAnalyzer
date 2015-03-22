package errouane.benjamin.pushanalizer.fragments;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import errouane.benjamin.pushanalizer.R;
import errouane.benjamin.pushanalizer.algorithms.BrakeDetector;
import errouane.benjamin.pushanalizer.dataListener.RotationDataEvent;
import errouane.benjamin.pushanalizer.session.Session;

/**
 * Created by Benni on 22.03.2015.
 */
public class BrakeStatsFragment extends ViewPagerFragment {
    private TextView text;

    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_brake_stats, container, false);

        text = (TextView) view.findViewById(R.id.textView10);

        return view;
    }

    @Override
    public void reset() {
        text.setText("");
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
        final String s = String.format("\n\nAverage Receleration: %.1f\nDistance: %.1f\nStart Speed: %.1f\nEnd Speed: %.1f",
                results.getAverageDeceleration(),
                results.getDistance()*1000f,
                results.getStartSpeed(),
                results.getEndSpeed());

        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                text.setText(text.getText() + s);
            }
        });
    }
}
