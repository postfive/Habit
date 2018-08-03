package com.postfive.habit;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.widget.LinearLayout;

import java.io.IOException;
import java.io.InputStream;

public class Utils {

    /**
     * getDaySum
     * 요일 합 구하기
     * @param mDayofWeek
     * @param isSet
     * @return
     */
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
    /**
     * getDaySum
     * 요일 합 찾기
     *
     * @param dyaofweek size 7  인 숫자 배열
     *        index 0 - 일
     *        index 1 - 월
     *        ....
     *        index 6 - 토
     *        선택한 요일에 1로 set 하여 호출
     * @return int 요일 합
     */
    public static int getDaysum(int[] dayofweek) {

        int daysum = 0;

        for(int i = 1 ; i < 8 ; i++) {
            if(dayofweek[i-1] >0) {
                daysum |= (1 << i);
            }
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

    public static String getDay(int daysum){

        String[] strArryDayofWeek = {"일", "월", "화", "수", "목", "금", "토"};
        String resultDayo ="";

        for(int i = 1 ; i < 8 ; i ++){
            if((daysum & ( 1<< i) ) > 0) {
                resultDayo += strArryDayofWeek[i-1];
            }
        }

        return resultDayo;
    }

    public static String getDayComma(int daysum){

        String[] strArryDayofWeek = {"일, ", "월, ", "화, ", "수, ", "목, ", "금, ", "토, "};
        String resultDayo ="";

        for(int i = 1 ; i < 8 ; i ++){
            if((daysum & ( 1<< i) ) > 0) {
                resultDayo += strArryDayofWeek[i-1];
            }
        }

        resultDayo = resultDayo.substring(0, resultDayo.length()-2);
        return resultDayo;
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
