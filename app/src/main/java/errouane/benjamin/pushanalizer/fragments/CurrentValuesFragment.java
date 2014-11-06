package errouane.benjamin.pushanalizer.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import errouane.benjamin.pushanalizer.R;
import errouane.benjamin.pushanalizer.ViewPagerFragment;
import errouane.benjamin.pushanalizer.dataListener.RotationDataEvent;


public class CurrentValuesFragment extends ViewPagerFragment {
    private TextView speedText, distanceText;
    private float distance = 0;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_current_values, container, false);
        speedText = (TextView) view.findViewById(R.id.speedTextView);
        distanceText = (TextView) view.findViewById(R.id.distanceTextView);
        return view;
    }

    @Override
    public void newRotationData(final RotationDataEvent event) {
        if(isAdded()) {
            getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    speedText.setText(Float.toString((int)(event.getSpeed() * 10) / 10f));
                    distance += event.getDistance();
                    distanceText.setText(Integer.toString((int)(distance * 1000f)));
                }
            });
        }
    }

    @Override
    public String getDescription() {
        return "Current Values";
    }
}
