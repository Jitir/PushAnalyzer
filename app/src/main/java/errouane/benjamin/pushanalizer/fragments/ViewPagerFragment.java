package errouane.benjamin.pushanalizer.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;

import java.util.Observable;
import java.util.Observer;

import errouane.benjamin.pushanalizer.Session;
import errouane.benjamin.pushanalizer.activities.MainTabbedActivity;
import errouane.benjamin.pushanalizer.dataListener.RotationDataListener;

/**
 * Created by Benni on 31.10.2014.
 */
public abstract class ViewPagerFragment extends Fragment implements RotationDataListener, Observer {
    @Override
    public void update(Observable observable, Object data) {
        reset();
    }

    protected Session session = Session.getInstance();
    public abstract void reset();
    public abstract String getDescription();
}
