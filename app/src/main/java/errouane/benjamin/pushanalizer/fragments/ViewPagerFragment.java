package errouane.benjamin.pushanalizer.fragments;

import android.support.v4.app.Fragment;

import java.util.Observable;
import java.util.Observer;

import errouane.benjamin.pushanalizer.dataListener.RotationDataListener;
import errouane.benjamin.pushanalizer.session.Session;

/**
 * Created by Benni on 31.10.2014.
 */
public abstract class ViewPagerFragment extends Fragment implements RotationDataListener, Observer {
    @Override
    public void update(Observable observable, Object data) {
        reset();
    }

    public abstract void reset();
    public abstract String getDescription();
}
