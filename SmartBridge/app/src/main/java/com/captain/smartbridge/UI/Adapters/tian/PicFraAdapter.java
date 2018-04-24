package com.captain.smartbridge.UI.Adapters.tian;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.captain.smartbridge.UI.Fragment.PicFragment;

/**
 * Created by captain on 18-4-12.
 */

public class PicFraAdapter extends FragmentPagerAdapter {
    final int PAGE_COUNT = 2;
    private String tabTitles[] = new String[]{"当前图像", "历史对比"};
    private Context context;

    private String id;
    private String sensor;


    public PicFraAdapter(FragmentManager fm, Context context, String id, String sensor) {
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
        return PicFragment.newInstance(position, id, sensor);
    }

    @Override
    public int getCount() {
        return PAGE_COUNT;
    }
}
