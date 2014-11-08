package errouane.benjamin.pushanalizer.fragments;

import android.support.v4.app.Fragment;

import errouane.benjamin.pushanalizer.dataListener.RotationDataListener;

/**
 * Created by Benni on 31.10.2014.
 */
public abstract class ViewPagerFragment extends Fragment implements RotationDataListener {
    public abstract String getDescription();
}
