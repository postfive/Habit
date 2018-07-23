package com.postfive.habit;

public class Utils {
    public int setDaySum(int mDayofWeek, boolean isSet) {
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
}
