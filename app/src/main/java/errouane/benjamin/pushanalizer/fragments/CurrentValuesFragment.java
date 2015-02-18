package errouane.benjamin.pushanalizer.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import errouane.benjamin.pushanalizer.R;
import errouane.benjamin.pushanalizer.dataListener.RotationDataEvent;
import errouane.benjamin.pushanalizer.session.Session;


public class CurrentValuesFragment extends ViewPagerFragment {
    private TextView speedText, distanceText, topSpeedText, pushesTextView, distancePerPushText;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_current_values, container, false);
        topSpeedText = (TextView) view.findViewById(R.id.topSpeedTextView);
        speedText = (TextView) view.findViewById(R.id.speedTextView);
        distanceText = (TextView) view.findViewById(R.id.distanceTextView);
        pushesTextView = (TextView) view.findViewById(R.id.pushesTextView);
        distancePerPushText = (TextView) view.findViewById(R.id.distancePerPushTextView);
        return view;
    }

    @Override
    public void newRotationData(final RotationDataEvent event) {
        if(isAdded()) {
            getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    updateTextViews(event.getSpeed());
                }
            });
        }
    }

    @Override
    public void newPush() {
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                pushesTextView.setText(Integer.toString(Session.getInstance().getPushes().size()));
            }
        });
    }

    private void updateTextViews(float speed) {
        topSpeedText.setText(Float.toString((int)(Session.getInstance().getTopSpeed() * 10) / 10f));
        speedText.setText(Float.toString((int)(speed * 10) / 10f));
        distanceText.setText(Integer.toString((int)(Session.getInstance().getDistance() * 1000f)));
        distancePerPushText.setText(Float.toString((int)(Session.getInstance().getDistancePerPush() * 10) / 10f));
    }

    @Override
    public void reset() {
        updateTextViews(0);
    }

    @Override
    public String getDescription() {
        return "Current Values";
    }
}
