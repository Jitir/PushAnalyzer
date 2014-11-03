package errouane.benjamin.pushanalizer.adapters;


import android.bluetooth.BluetoothDevice;
import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import errouane.benjamin.pushanalizer.R;
import errouane.benjamin.pushanalizer.ViewPagerFragment;
import errouane.benjamin.pushanalizer.fragments.CurrentValuesFragment;
import errouane.benjamin.pushanalizer.fragments.GraphsFragment;
import errouane.benjamin.pushanalizer.fragments.MoreStatsFragment;

/**
 * Created by Benni on 29.10.2014.
 */
public class MyPagerAdapter extends FragmentPagerAdapter {
    private ViewPagerFragment[] fragments;

    public MyPagerAdapter(FragmentManager fm, ViewPagerFragment[] fragments) {
        super(fm);
        this.fragments = fragments;
    }

    @Override
    public Fragment getItem(int i) {
        return fragments[i];
    }

    @Override
    public int getCount() {
       return fragments.length;
    }

    @Override
    public CharSequence getPageTitle(int i) {
       return fragments[i].getDescription();
    }
}
