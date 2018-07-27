package com.postfive.habit.noti;


import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.media.RingtoneManager;
import android.net.Uri;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.postfive.habit.R;
import com.postfive.habit.view.login.LoginActivity;
import com.postfive.habit.view.main.MainActivity;

public class BroadcastD extends BroadcastReceiver {


    @Override
    public void onReceive(Context context, Intent intent) {

        Log.e("Broadcastd"," HabitNoti BroadcastD BroadcastD ");
        NotificationManager notificationmanager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, new Intent(context, MainActivity.class), PendingIntent.FLAG_UPDATE_CURRENT);

        int strWhen = intent.getIntExtra("when",0);
        String text="";
        Log.d("Service", "HabitNoti get Extra text: " + strWhen);

        if(strWhen ==1) {
            text = context.getString(R.string.morning_noti);
        }else if (strWhen == 2){
            text = context.getString(R.string.afternoon_noti);
        }else if (strWhen == 3){
            text = context.getString(R.string.night_noti);
        }else if (strWhen == 4){
            text = context.getString(R.string.goodnight_noti);
        }

        Uri uri= RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
//        Log.e("Service", "style: " + style);
        NotificationCompat.Builder builder = (NotificationCompat.Builder) new NotificationCompat.Builder(context)
                .setSmallIcon(R.drawable.ic_water)
                .setContentTitle("Biskit")
                .setContentText(text)
//                .setTicker("the best sandwiches in town")
                .setAutoCancel(true)
                .setLargeIcon(BitmapFactory.decodeResource(context.getResources(), R.drawable.ic_dry_fruits))
                .setContentIntent(pendingIntent)
                .setVibrate(new long[]{500, 500, 500})
                .setSound(uri)
                .setVisibility(NotificationCompat.VISIBILITY_PUBLIC) // show all contents
                //.addAction(R.drawable.ic_stat_burger, "Button1", )
//                .setStyle(bigTextStyle)
                ;

        notificationmanager.notify(1, builder.build());

        new HabitNoti(context).Alarm();
    }

}
