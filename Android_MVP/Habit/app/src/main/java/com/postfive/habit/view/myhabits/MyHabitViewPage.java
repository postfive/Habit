package com.postfive.habit.view.myhabits;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.postfive.habit.R;
import com.postfive.habit.adpater.myhabit.CustomPagerAdapter;
import com.postfive.habit.db.UserHabitRespository;
import com.postfive.habit.db.UserHabitState;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class MyHabitViewPage extends AppCompatActivity {
    ViewPager pager;
    private UserHabitRespository mUserHabitRespository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.e("Main","onCreated");
        setContentView(R.layout.myhabitviewpage);
        pager = (ViewPager) findViewById(R.id.pager);
        CustomPagerAdapter adapter = new CustomPagerAdapter(this, mUserHabitRespository);
        pager.setOffscreenPageLimit(3);
        pager.setAdapter(adapter);
        pager.setCurrentItem(1);

        mUserHabitRespository = new UserHabitRespository(getApplication());

        // TODO 현재 지금
        List<UserHabitState> mUserHabitStatesList = mUserHabitRespository.getNowHabit(1);

        // TODO 오늘 완성
        List<UserHabitState> mTodayCompliteHabitStatesList = mUserHabitRespository.getComplite();

        // TODO 오늘 놓친것
        List<UserHabitState> mTodayPassCompliteHabitStatesList = mUserHabitRespository.getPassHabit(8);

        TextView date_tv = (TextView) findViewById(R.id.date_tv);
        setToday(date_tv);
    }

    public void mOnClick(View v){
        int position;

        switch (v.getId()){
            case R.id.prev_tv:
                position = pager.getCurrentItem();
                pager.setCurrentItem(position - 1, true);
                break;
            case R.id.next_tv:
                position = pager.getCurrentItem();
                pager.setCurrentItem(position + 1, true);
                break;
        }
    }

    public void setToday (TextView view){
        Date today = Calendar.getInstance().getTime();//getting date
        SimpleDateFormat formatter = new SimpleDateFormat("오늘 yyyy.MM.dd");
        String date = formatter.format(today);
        view.setText(date);
    }
//    public void addDate (TextView view){
//        Date today = Calendar.getInstance().getTime();//getting date
//        SimpleDateFormat formatter = new SimpleDateFormat("yyyy.MM.dd");
//        String date = formatter.format(today);
//        view.setText(date);
//    }
}

