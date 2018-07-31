package com.postfive.habit;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.widget.LinearLayout;

import java.io.IOException;
import java.io.InputStream;

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

    public static int setStatusBarHeight(Context context){
    int result = 0;
    int resourceId = context.getResources().getIdentifier("status_bar_height", "dimen", "android");
        result = context.getResources().getDimensionPixelSize(resourceId);

        return result;
    }

    public static Drawable assignImage(View v, String imgUri){
        InputStream inputStream = null;
        Drawable img = null;

        try{
            inputStream = v.getContext().getResources().getAssets().open(imgUri);
            img = Drawable.createFromStream(inputStream, null);
            inputStream.close();
        }catch (IOException e) {
            e.printStackTrace();
        }

        return img;
    }
}
