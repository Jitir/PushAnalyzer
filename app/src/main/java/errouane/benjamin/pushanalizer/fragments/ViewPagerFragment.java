package errouane.benjamin.pushanalizer.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;

import errouane.benjamin.pushanalizer.Session;
import errouane.benjamin.pushanalizer.activities.MainTabbedActivity;
import errouane.benjamin.pushanalizer.dataListener.RotationDataListener;

/**
 * Created by Benni on 31.10.2014.
 */
public abstract class ViewPagerFragment extends Fragment implements RotationDataListener {
    protected Session session = Session.getInstance();

    public abstract String getDescription();
}
