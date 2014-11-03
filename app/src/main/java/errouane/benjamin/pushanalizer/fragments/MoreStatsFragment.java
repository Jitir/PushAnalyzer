package errouane.benjamin.pushanalizer.fragments;

import android.bluetooth.BluetoothDevice;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import errouane.benjamin.pushanalizer.R;
import errouane.benjamin.pushanalizer.ViewPagerFragment;
import errouane.benjamin.pushanalizer.dataListener.RotationDataEvent;
import errouane.benjamin.pushanalizer.dataListener.RotationDataListener;


public class MoreStatsFragment extends ViewPagerFragment {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_more_stats, container, false);

        return view;
    }

    @Override
    public void newRotationData(RotationDataEvent event) {

    }

    @Override
    public String getDescription() {
        return "More Stats";
    }
}
