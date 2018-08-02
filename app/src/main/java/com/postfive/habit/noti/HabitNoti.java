package com.postfive.habit.noti;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.postfive.habit.UserSettingValue;
import com.postfive.habit.db.UserHabitState;

import java.util.Calendar;

public class HabitNoti {
    private static String TAG = "HabitNoti";
    private Context context;
    private static HabitNoti mHabitNoti = null;

    private HabitNoti(){
        Log.d(TAG, "HabitNoti 생성자");

    }
    public HabitNoti(Context context) {

        this.context=context;
        this.mHabitNoti = new HabitNoti();

    }
    public void Alarm() {

        Log.d(TAG, "알람 설정 시작");
        AlarmManager am = (AlarmManager)this.context.getSystemService(Context.ALARM_SERVICE);

        Calendar calendar = Calendar.getInstance();
        //알람시간 calendar에 set해주기
        int [] pushTimeArr = new int[6];
        pushTimeArr[0] = 0;
        pushTimeArr[1] = UserSettingValue.getMorningPushHour()   * 100 + UserSettingValue.getMorningPushMinute();
        pushTimeArr[2] = UserSettingValue.getAfternoonPushHour() * 100 + UserSettingValue.getAfternoonPushMinute();
        pushTimeArr[3] = UserSettingValue.getNightPushHour()     * 100 + UserSettingValue.getNightPushMinute();
        pushTimeArr[4] = UserSettingValue.getGoodnightPushHour() * 100 + UserSettingValue.getGoodnightPushMinute();
        pushTimeArr[5] = 2400;

        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("아침" +Integer.toString(UserSettingValue.getMorningPushHour()  ) + "시 "+ Integer.toString(UserSettingValue.getMorningPushMinute())+"\n");
        stringBuilder.append("오후" +Integer.toString(UserSettingValue.getAfternoonPushHour()) + "시 "+ Integer.toString(UserSettingValue.getAfternoonPushMinute())+"\n");
        stringBuilder.append("저녁" +Integer.toString(UserSettingValue.getNightPushHour()    ) + "시 "+ Integer.toString(UserSettingValue.getNightPushMinute())+"\n");
        stringBuilder.append("잘자" +Integer.toString(UserSettingValue.getGoodnightPushHour()) + "시 "+ Integer.toString(UserSettingValue.getGoodnightPushMinute())+"\n");
        Log.d(TAG,  stringBuilder.toString());


        int nowTime = calendar.get(Calendar.HOUR_OF_DAY) * 100 + calendar.get(Calendar.MINUTE);
        int pushTime = 0 ;
        int when = 0;
        for(int j = 0 ; j < 5 ; j ++){
            if( pushTimeArr[j] <= nowTime && pushTimeArr[j+1]> nowTime ){
                pushTime = pushTimeArr[j+1];
                when = j+1;
                break;
            }
        }

        if(pushTime == 2400){
            calendar.set(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DATE)+1, pushTimeArr[1]/100, pushTimeArr[1]%100, 0);
            when =1;
        }else{
            calendar.set(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DATE), pushTime/100, pushTime%100, 0);
        }

        Intent intent = new Intent(context, BroadcastD.class);
        intent.putExtra("when", when);
        PendingIntent sender = PendingIntent.getBroadcast(context, 0, intent, when);

        Log.d(TAG, "알람 시간 확인 " + Integer.toString(when) +" "+ calendar.get(Calendar.YEAR) +" "+ calendar.get(Calendar.MONTH) + " "+ calendar.get(Calendar.DATE) + " "+ calendar.get(Calendar.HOUR_OF_DAY)+ " "+ calendar.get(Calendar.MINUTE));
        //알람 예약
        am.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), sender);
    }


}
