package com.captain.smartbridge.UI.Adapters.other;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.captain.smartbridge.UI.Fragment.fourgFragment;

/**
 * Created by Captain on 17/8/21.
 */

public class FourFraAdapter extends FragmentPagerAdapter {
    final int PAGE_COUNT = 3;
    private String tabTitles[] = new String[]{"当前数据", "预警数据", "参数分析"};
    private Context context;

    String id = "";
    String sensor = "";


    public FourFraAdapter(FragmentManager fm, Context context, String id, String sensor) {
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
        return fourgFragment.newInstance(position, id, sensor);
    }

    @Override
    public int getCount() {
        return PAGE_COUNT;
    }
}
