package com.captain.smartbridge.UI.Adapters;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.captain.smartbridge.UI.Fragment.PageFragement;

/**
 * Created by fish on 17-5-15.
 */

public class FragementAdapter extends FragmentPagerAdapter {
    final int PAGE_COUNT = 4;
    private String tabTitles[] = new String[]{"基本信息","构件","照片","三维模型"};
    private Context context;


    public FragementAdapter(FragmentManager fm, Context context) {
        super(fm);
        this.context = context;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return tabTitles[position];
    }

    @Override
    public Fragment getItem(int position) {
        return PageFragement.newInstance(position);
    }

    @Override
    public int getCount() {
        return PAGE_COUNT;
    }
}
