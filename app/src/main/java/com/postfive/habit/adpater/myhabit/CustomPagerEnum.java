package com.postfive.habit.adpater.myhabit;

import com.postfive.habit.R;

public enum CustomPagerEnum {

    YESTERDAY(R.layout.day_0),
    TODAY(R.layout.day_1),
    TOMORROW(R.layout.day_2);

    private int mLayoutResId;
    CustomPagerEnum(int layoutResId) {
        mLayoutResId = layoutResId;
    }
//    CustomPagerEnum(int titleResId, int layoutResId) {
//        mTitleResId = titleResId;
//        mLayoutResId = layoutResId;
//    }
//
//    public int getTitleResId() {
//        return mTitleResId;
//    }

    public int getLayoutResId() {
        return mLayoutResId;
    }
}
