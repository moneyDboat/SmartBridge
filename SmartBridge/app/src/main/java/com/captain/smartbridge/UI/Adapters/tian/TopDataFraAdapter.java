package com.captain.smartbridge.UI.Adapters.tian;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.captain.smartbridge.UI.Fragment.TopDataFragment;

/**
 * Created by captain on 18-5-30.
 */

public class TopDataFraAdapter extends FragmentPagerAdapter{
    final int PAGE_COUNT = 2;
    private String tabTitles[] = new String[]{"当前数据", "预警数据"};
    private Context context;

    String id = "";
    String sensor = "";

    public TopDataFraAdapter(FragmentManager fm, Context context, String id, String sensor) {
        super(fm);
        this.context = context;
        this.id = id;
        this.sensor = sensor;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return tabTitles[position];
    }

    @Override
    public Fragment getItem(int position) {
        return TopDataFragment.newInstance(position, id, sensor);
    }

    @Override
    public int getCount() {
        return PAGE_COUNT;
    }
}
