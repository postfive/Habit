package com.postfive.habit;

import android.arch.persistence.room.Ignore;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;

import com.postfive.habit.db.Habit;

import java.io.IOException;
import java.io.InputStream;
import java.util.Calendar;

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

    public static int getTime(Context context){
        Calendar calendar = Calendar.getInstance();

        /*
        * 아침 : 4시       ~ 11시 30분
        * 오후 : 11시 30분 ~ 18시
        * 저녁 : 18시      ~ 4시*/
        int [] timeArr = new int[5];
        timeArr[0] = 0;
        timeArr[1] = 400;
        timeArr[2] = 1130;
        timeArr[3] = 1800;
        timeArr[4] = 2400;

        int nowTime = calendar.get(Calendar.HOUR_OF_DAY) * 100 + calendar.get(Calendar.MINUTE);
        int when = 0;
        for(int j = 0 ; j < 4 ; j ++){
            if( timeArr[j] <= nowTime && timeArr[j+1]> nowTime ){
                when = j;
                /*
                * j : 0 이면 저녁
                *     1 이면 아침
                 *    2 이면 오후
                 *    3 이면 저녁 */
                break;
            }
        }
        /*        @Ignore
        public static int ALLDAY_TIME = 0;
        @Ignore
        public static int MORNING_TIME = 1;
        @Ignore
        public static int AFTERNOON_TIME = 2;
        @Ignore
        public static int NIGHT_TIME = 3;
        */
        if(when == 0){
            when = Habit.NIGHT_TIME;
        }

        return when;

    }
}
