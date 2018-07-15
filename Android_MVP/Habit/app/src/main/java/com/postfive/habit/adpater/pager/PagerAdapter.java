package com.postfive.habit.adpater.pager;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.postfive.habit.view.celeblist.CelebListFragment;
import com.postfive.habit.view.myhabits.*;
import com.postfive.habit.view.statistics.*;
import com.postfive.habit.view.setting.*;

public class PagerAdapter extends FragmentStatePagerAdapter {

    int mNumberOfTabs;

    public PagerAdapter(FragmentManager fm, int numberOfTabs){
        super(fm);
        this.mNumberOfTabs = numberOfTabs;
    }
    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0 :
                MyHabitsFragment myHabitsFragment = new MyHabitsFragment();
                return myHabitsFragment;
            case 1:
                StatisticsFragment statisticsFragment = new StatisticsFragment();
                return statisticsFragment;
            case 2:
                CelebListFragment celebListFragment = new CelebListFragment();
                return celebListFragment;
            case 3:
                SettingFragment settingFragment = new SettingFragment();
                return settingFragment;
            default :
                return null;
        }
    }

    @Override
    public int getCount() {
        return this.mNumberOfTabs;
    }
}
