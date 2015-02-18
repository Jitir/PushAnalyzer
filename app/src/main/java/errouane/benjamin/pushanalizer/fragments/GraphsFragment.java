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
import errouane.benjamin.pushanalizer.dataListener.RotationDataEvent;
import errouane.benjamin.pushanalizer.fragments.graph.PlotSeries;
import errouane.benjamin.pushanalizer.session.Session;


public class GraphsFragment extends ViewPagerFragment {
    private XYPlot plot;
    PlotSeries speedSeries, accXSeries, accYSeries, accZSeries;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_graphs, container, false);
        plot = (XYPlot) view.findViewById(R.id.plot);

        speedSeries = new PlotSeries(Session.getInstance(), PlotSeries.ValueType.SPEED);
        accXSeries = new PlotSeries(Session.getInstance(), PlotSeries.ValueType.ACC_X);
        accYSeries = new PlotSeries(Session.getInstance(), PlotSeries.ValueType.ACC_Y);
        accZSeries = new PlotSeries(Session.getInstance(), PlotSeries.ValueType.ACC_Z);

        plot.addSeries(speedSeries, new LineAndPointFormatter(Color.YELLOW, Color.argb(0,0,0,0), Color.argb(0,0,0,0), null));
        plot.addSeries(accXSeries, new LineAndPointFormatter(Color.RED, Color.argb(0,0,0,0), Color.argb(0,0,0,0), null));
        //plot.addSeries(accYSeries, new LineAndPointFormatter(Color.GREEN, Color.argb(0,0,0,0), Color.argb(0,0,0,0), null));
        //plot.addSeries(accZSeries, new LineAndPointFormatter(Color.BLUE, Color.argb(0,0,0,0), Color.argb(0,0,0,0), null));
        return view;
    }

    @Override
    public void newRotationData(RotationDataEvent event) {
        if(plot != null)
            plot.redraw();
    }

    @Override
    public void newPush() {

    }

    @Override
    public String getDescription() {
        return "Graphs";
    }

    public void reset() {
        plot.removeSeries(speedSeries);
        plot.removeSeries(accXSeries);
        plot.removeSeries(accYSeries);
        plot.removeSeries(accZSeries);
        speedSeries = null;
        plot.clear();

        speedSeries = new PlotSeries(Session.getInstance(), PlotSeries.ValueType.SPEED);
        accXSeries = new PlotSeries(Session.getInstance(), PlotSeries.ValueType.ACC_X);
        accYSeries = new PlotSeries(Session.getInstance(), PlotSeries.ValueType.ACC_Y);
        accZSeries = new PlotSeries(Session.getInstance(), PlotSeries.ValueType.ACC_Z);

        plot.addSeries(speedSeries, new LineAndPointFormatter(Color.YELLOW, Color.argb(0,0,0,0), Color.argb(0,0,0,0), null));
        plot.addSeries(accXSeries, new LineAndPointFormatter(Color.RED, Color.argb(0,0,0,0), Color.argb(0,0,0,0), null));
    }
}
