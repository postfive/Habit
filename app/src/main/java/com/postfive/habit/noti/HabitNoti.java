package com.postfive.habit.noti;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

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

        UserSettingValue userSettingValue = new UserSettingValue(this.context);
        Calendar calendar = Calendar.getInstance();
        //알람시간 calendar에 set해주기
        int [] pushTimeArr = new int[6];
        pushTimeArr[0] = 0;
        pushTimeArr[1] = userSettingValue.getMorningPushHour()   * 100 + userSettingValue.getMorningPushMinute();
        pushTimeArr[2] = userSettingValue.getAfternoonPushHour() * 100 + userSettingValue.getAfternoonPushMinute();
        pushTimeArr[3] = userSettingValue.getNightPushHour()     * 100 + userSettingValue.getNightPushMinute();
        pushTimeArr[4] = userSettingValue.getGoodnightPushHour() * 100 + userSettingValue.getGoodnightPushMinute();
        pushTimeArr[5] = 2400;

        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("아침" +Integer.toString(userSettingValue.getMorningPushHour()  ) + "시 "+ Integer.toString(userSettingValue.getMorningPushMinute())+"\n");
        stringBuilder.append("오후" +Integer.toString(userSettingValue.getAfternoonPushHour()) + "시 "+ Integer.toString(userSettingValue.getAfternoonPushMinute())+"\n");
        stringBuilder.append("저녁" +Integer.toString(userSettingValue.getNightPushHour()    ) + "시 "+ Integer.toString(userSettingValue.getNightPushMinute())+"\n");
        stringBuilder.append("잘자" +Integer.toString(userSettingValue.getGoodnightPushHour()) + "시 "+ Integer.toString(userSettingValue.getGoodnightPushMinute())+"\n");
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

        Toast.makeText(context, "알람 시간 확인 " + Integer.toString(when) +" "+ calendar.get(Calendar.YEAR) +" "+ calendar.get(Calendar.MONTH) + " "+ calendar.get(Calendar.DATE) + " "+ calendar.get(Calendar.HOUR_OF_DAY)+ " "+ calendar.get(Calendar.MINUTE), Toast.LENGTH_LONG).show();
        Log.d( TAG,"알람 시간 확인 " + Integer.toString(when) +" "+ calendar.get(Calendar.YEAR) +" "+ calendar.get(Calendar.MONTH) + " "+ calendar.get(Calendar.DATE) + " "+ calendar.get(Calendar.HOUR_OF_DAY)+ " "+ calendar.get(Calendar.MINUTE));
        //알람 예약
        am.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), sender);
    }


}
