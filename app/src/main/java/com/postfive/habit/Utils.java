package com.postfive.habit;

import android.content.Context;
import android.widget.LinearLayout;

public class Utils {
    public static int setDaySum(int mDayofWeek, boolean isSet) {
        int daysum = 0;
        if(isSet){
            // 해당요일 설정
            daysum |= (1 << mDayofWeek);
        }else {
            // 해당요일 해제
            daysum &= ~(1 << mDayofWeek);
        }
        return daysum;
    }

    public static int getStatusBarHeight(Context context){
        int result = 0;
        int resourceId = context.getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            result = context.getResources().getDimensionPixelSize(resourceId);
        }

        return result;
    }
}
