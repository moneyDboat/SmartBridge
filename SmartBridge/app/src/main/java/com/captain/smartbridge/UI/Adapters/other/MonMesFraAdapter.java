package com.captain.smartbridge.UI.Adapters.other;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.captain.smartbridge.UI.Fragment.MonFragement;

/**
 * Created by Captain on 17/8/21.
 */

public class MonMesFraAdapter extends FragmentPagerAdapter {
    final int PAGE_COUNT = 3;
    private String tabTitles[] = new String[]{"结构性监测", "施工监测", "特殊监测"};
    private Context context;


    public MonMesFraAdapter(FragmentManager fm, Context context) {
        super(fm);
        this.context = context;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return tabTitles[position];
    }

    @Override
    public Fragment getItem(int position) {
        return MonFragement.newInstance(position);
    }

    @Override
    public int getCount() {
        return PAGE_COUNT;
    }
}
