package errouane.benjamin.pushanalizer.fragments;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.androidplot.xy.LineAndPointFormatter;
import com.androidplot.xy.XYPlot;

import errouane.benjamin.pushanalizer.R;
import errouane.benjamin.pushanalizer.Session;
import errouane.benjamin.pushanalizer.dataListener.RotationDataEvent;
import errouane.benjamin.pushanalizer.fragments.graph.PlotSeries;


public class GraphsFragment extends ViewPagerFragment {
    private XYPlot plot;
    private Session session;
    private float time = 0;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_graphs, container, false);
        plot = (XYPlot) view.findViewById(R.id.plot);

        session = new Session();
        PlotSeries series = new PlotSeries(session, PlotSeries.ValueType.SPEED);

        // Create a formatter to use for drawing a series using LineAndPointRenderer
        // and configure it from xml:
        LineAndPointFormatter series1Format = new LineAndPointFormatter(Color.RED, Color.argb(0,0,0,0), Color.argb(0,0,0,0), null);

        plot.addSeries(series, series1Format);
        return view;
    }

    @Override
    public void newRotationData(RotationDataEvent event) {
        session.getTimes().add(time);
        session.getSpeeds().add(event.getSpeed());
        time += event.getDeltaTime();
        plot.redraw();
    }

    @Override
    public String getDescription() {
        return "Graphs";
    }
}
