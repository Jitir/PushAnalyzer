package errouane.benjamin.pushanalizer.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Spinner;

import errouane.benjamin.pushanalizer.R;
import errouane.benjamin.pushanalizer.ViewPagerFragment;
import errouane.benjamin.pushanalizer.dataListener.RotationDataEvent;
import errouane.benjamin.pushanalizer.dataListener.RotationDataListener;


public class GraphsFragment extends ViewPagerFragment {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_graphs, container, false);
        Spinner spinner = (Spinner) view.findViewById(R.id.spinner);
        return view;
    }

    @Override
    public void newRotationData(RotationDataEvent event) {

    }

    @Override
    public String getDescription() {
        return "Graphs";
    }
}
