package com.captain.smartbridge.UI.Activity.Evalute;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.captain.smartbridge.UI.Fragment.EvalFragement;

/**
 * Created by Captain on 17/7/8.
 */

public class EvalFragAdapter extends FragmentPagerAdapter {
    final int PAGE_COUNT = 4;
    private String tabTitles[] = new String[]{"评分", "历史记录", "寿命预测", "性能退化预测"};
    private Context context;


    public EvalFragAdapter(FragmentManager fm, Context context) {
        super(fm);
        this.context = context;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return tabTitles[position];
    }

    @Override
    public Fragment getItem(int position) {
        return EvalFragement.newInstance(position);
    }

    @Override
    public int getCount() {
        return PAGE_COUNT;
    }

}
