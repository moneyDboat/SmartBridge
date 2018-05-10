package com.captain.smartbridge.UI.Adapters.tian;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.captain.smartbridge.UI.Fragment.TopFragment;
import com.captain.smartbridge.UI.Fragment.fourgFragment;

/**
 * Created by Captain on 17/8/21.
 */

public class TopFraAdapter extends FragmentPagerAdapter {
    final int PAGE_COUNT = 3;
    private String tabTitles[] = new String[]{"当前数据", "预警数据", "数据分布"};
    private Context context;

    String id = "";
    String sensor = "";


    public TopFraAdapter(FragmentManager fm, Context context, String id, String sensor) {
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
        return TopFragment.newInstance(position, id, sensor);
    }

    @Override
    public int getCount() {
        return PAGE_COUNT;
    }
}
