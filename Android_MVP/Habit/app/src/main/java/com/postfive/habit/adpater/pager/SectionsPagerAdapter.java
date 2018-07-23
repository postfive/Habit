package com.postfive.habit.adpater.pager;


import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.util.Log;


import com.postfive.habit.view.celeblist.CelebListFragment;
import com.postfive.habit.view.myhabits.MyHabitsFragment;
import com.postfive.habit.view.setting.SettingFragment;

import java.util.ArrayList;

public class SectionsPagerAdapter extends FragmentPagerAdapter implements  SectionsPagerModel {
    private static String TAG ="SectionsPagerAdapter";
    private FragmentManager fm = null;
    private ArrayList<Integer> itemList = new ArrayList<Integer>();

    public SectionsPagerAdapter(FragmentManager fm) {
        super(fm);

        this.fm = fm;
    }

    @Override
    public Fragment getItem(int position) {
        Log.d(TAG,"getItem "+position);

        switch (position) {
            case 0:
                return new MyHabitsFragment();
            case 1:
                return new CelebListFragment();
            case 2:
                return new SettingFragment();
            default :
                break;
        }
        return null;
    }

    @Override
    public void setListItem(int position) {
        itemList.add(position);
    }

    @Override
    public int getListItem(int position) {
        return itemList.get(position);
    }

    @Override
    public int getCount() {
        return itemList.size();
    }


}
