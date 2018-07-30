package com.postfive.habit.view.login;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.postfive.habit.R;
import com.postfive.habit.UserSettingValue;
import com.postfive.habit.Utils;
import com.postfive.habit.db.AppDatabase;
import com.postfive.habit.db.CelebHabitDetail;
import com.postfive.habit.db.CelebHabitMaster;
import com.postfive.habit.db.Habit;
import com.postfive.habit.db.HabitRespository;
import com.postfive.habit.db.Unit;
import com.postfive.habit.db.UserHabitRespository;
import com.postfive.habit.noti.HabitNoti;
import com.postfive.habit.view.main.MainActivity;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener{

    private static final String TAG = "LoginActivity";
    private static final String PREFS_NAME = "Init";

    private AppDatabase mAppDatabase;
    private UserHabitRespository mUserHabitRespository;
    private HabitRespository mHabitRespository;

    UserSettingValue mUserSettingValue;

    private Button mBtnLookAround;
    private TextView mTextView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


        mUserSettingValue = new UserSettingValue(this);
        // 컴포넌트 초기화
        initComponent();



        // 앱 최초 실행 여부 확인
        if(mUserSettingValue.init()) {
            // 디비 초기화
            new CheckTypesTask().execute();
            connectDB();
            populateWithTestData();
        }else{
            new CheckTypesTask().execute();
        }

        new HabitNoti(this).Alarm();


    }

    @Override
    protected void onResume() {
        super.onResume();

    }

    private class CheckTypesTask extends AsyncTask<Void, Void, Void> {

        ProgressDialog asyncDialog = new ProgressDialog(
                LoginActivity.this);

        @Override
        protected void onPreExecute() {
            asyncDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            asyncDialog.setMessage("로딩중입니다..");

            // show dialog
            asyncDialog.show();
            super.onPreExecute();
        }

        @Override
        protected Void doInBackground(Void... arg0) {
            try {
                for (int i = 0; i < 5; i++) {
                    //asyncDialog.setProgress(i * 30);
                    Thread.sleep(500);
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            asyncDialog.dismiss();

            Intent lookAround = new Intent(LoginActivity.this, MainActivity.class);
            startActivity(lookAround);
            finish();
            super.onPostExecute(result);

        }
    }




    private void initComponent() {


        mBtnLookAround = (Button)findViewById(R.id.btn_look_around);
        mBtnLookAround.setOnClickListener(this);

        mTextView = (TextView)findViewById(R.id.textview_state);
    }

    @Override
    public void onClick(View v) {

        switch(v.getId()){
            case R.id.btn_look_around :
                Intent lookAround = new Intent(this, MainActivity.class);
                startActivity(lookAround);
                finish();
            default :
                break;
        }

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

    }

    @Override
    protected void onPause() {
        super.onPause();
        // 연결 끊기
        //disconnectDB();
    }

    private void connectDB(){

        mUserHabitRespository = new UserHabitRespository(getApplication());
        mHabitRespository = new HabitRespository(getApplication());
    }

    private void disconnectDB(){


        mUserHabitRespository.destroyInstance();
        mHabitRespository.destroyInstance();
    }



    private void populateWithTestData()
    {
        Log.d(TAG, "DB TEST  초기화 시작");
        // 설정 값 있을때는 그냥 종료

        // 다 지우고
        mHabitRespository.deleteAll();

        // 유명인 set

        Habit drinkwater = new Habit(1, "물마시기", "drinkwater", Unit.LIQUID_UNIT, Habit.ALLDAY_TIME, 10, 2, 6, "blue","water.jpg",  R.drawable.ic_water);
        Habit prestudy = new Habit(2, "예습하기", "prestudy", Unit.COUNT_UNIT, Habit.AFTERNOON_TIME, 10, 1, 12, "red", "study.jpg", R.drawable.ic_dry_fruits);
        Habit skiprope = new Habit(3, "줄넘기 하기", "skiprope", Unit.COUNT_UNIT, Habit.NIGHT_TIME, 10, 1, 12, "black","rope.jpg",  R.drawable.ic_walking);

        List<Habit> habitli = new ArrayList<>();
        habitli.add(drinkwater);
        habitli.add(prestudy);
        habitli.add(skiprope);
        mHabitRespository.insertAllHabit(habitli);


        CelebHabitMaster celebHabitmaster = new CelebHabitMaster("박보람",1,"박보람의 40kg \n다이어트 습관", "하루하루 0.5kg씩 줄이는 습관", "박보람 처럼 40kg 만들어서 하와이 가자!",  "img_parkboram_list.jpg");
        CelebHabitMaster celebHabitmaster2 = new CelebHabitMaster("호날두",2,"호날두 처럼 \n졸라 멋쟁이 되기", "호날두 같이 존멋 되기", "호날두 처럼 존멋되서 유벤 직관가자!",  "img_ronaldo_list.jpg");
        CelebHabitMaster celebHabitmaster3 = new CelebHabitMaster("안영이",3,"모든 팀에서 탐내\n는 슈퍼 신입 되기", "밟아보세요 선배님. 그래봤자 발만 아프실거에요", "슈퍼 신입! 슈퍼 직장인!",  "img_ahnyoungi.jpg");


        CelebHabitDetail celebHabitd1 = new CelebHabitDetail(1, Habit.ALLDAY_TIME, 1, 1, "물마시기", "하루에 6L 물마시기", 30, 6, 2, "L", "img_parkboram_detail_1.png", "blue", R.drawable.ic_water);
        CelebHabitDetail celebHabitd2 = new CelebHabitDetail(1, Habit.AFTERNOON_TIME, 1, 2, "예습하기", "다음날 예습하기", 28, 100, 5, "번", "img_parkboram_detail_2.png", "blue", R.drawable.ic_dry_fruits);
        CelebHabitDetail celebHabitd3 = new CelebHabitDetail(1, Habit.NIGHT_TIME, 2, 3, "줄넘기하기", "쌩쌩이 10번", 62, 10, 1, "번", "img_parkboram_detail_3.png", "blue", R.drawable.ic_walking);

        CelebHabitDetail celebHabitd21 = new CelebHabitDetail(2, Habit.MORNING_TIME, 1, 1, "물마시기", "하루에 10L 물마시기", 256 , 10, 2, "L", "aaaa", "blue",  R.drawable.ic_water);
        CelebHabitDetail celebHabitd22 = new CelebHabitDetail(2, Habit.AFTERNOON_TIME, 1, 2, "예습하기", "다음 경기 분석하기", 128, 1, 1, "번", "aaaa", "blue", R.drawable.ic_dry_fruits);
        CelebHabitDetail celebHabitd23 = new CelebHabitDetail(2, Habit.NIGHT_TIME, 1, 3, "줄넘기하기", "쌩쌩이 호날두 답게 100번", 64, 100, 1, "번", "aaaa", "blue", R.drawable.ic_walking);
        CelebHabitDetail celebHabitd24 = new CelebHabitDetail(2, Habit.ALLDAY_TIME, 1, 3, "줄넘기하기", "쌩쌩이 호날두 답게 하루종일 100번", 32, 100, 1, "번", "aaaa", "blue", R.drawable.ic_walking);

/*
        CelebHabitDetail celebHabitd21 = new CelebHabitDetail(2, Habit.MORNING_TIME, 1, 1, "물마시기", "하루에 10L 물마시기", Utils.setDaySum(Calendar.FRIDAY,true) , 10, 2, "L", "aaaa", "blue",  R.drawable.ic_water);
        CelebHabitDetail celebHabitd22 = new CelebHabitDetail(2, Habit.AFTERNOON_TIME, 1, 2, "예습하기", "다음 경기 분석하기", Utils.setDaySum(Calendar.FRIDAY,true) , 1, 1, "번", "aaaa", "blue", R.drawable.ic_dry_fruits);
        CelebHabitDetail celebHabitd23 = new CelebHabitDetail(2, Habit.NIGHT_TIME, 1, 3, "줄넘기하기", "쌩쌩이 호날두 답게 100번", Utils.setDaySum(Calendar.FRIDAY,true) , 100, 1, "번", "aaaa", "blue", R.drawable.ic_walking);
        CelebHabitDetail celebHabitd24 = new CelebHabitDetail(2, Habit.ALLDAY_TIME, 1, 3, "줄넘기하기", "쌩쌩이 호날두 답게 하루종일 100번", Utils.setDaySum(Calendar.FRIDAY,true) , 100, 1, "번", "aaaa", "blue", R.drawable.ic_walking);
*/


        mHabitRespository.insertCelebHabitMaster(celebHabitmaster);
        mHabitRespository.insertCelebHabitMaster(celebHabitmaster2);
        mHabitRespository.insertCelebHabitMaster(celebHabitmaster3);

        mHabitRespository.insertCelebHabitDetail(celebHabitd1);
        mHabitRespository.insertCelebHabitDetail(celebHabitd2);
        mHabitRespository.insertCelebHabitDetail(celebHabitd3);
        mHabitRespository.insertCelebHabitDetail(celebHabitd21);
        mHabitRespository.insertCelebHabitDetail(celebHabitd22);
        mHabitRespository.insertCelebHabitDetail(celebHabitd23);
        mHabitRespository.insertCelebHabitDetail(celebHabitd24);

        Unit unitLiquid1 = new Unit(Unit.LIQUID_UNIT, "L");
        Unit unitLiquid2 = new Unit(Unit.LIQUID_UNIT, "mL");
        Unit unitLiquid3 = new Unit(Unit.LIQUID_UNIT, "cc");


        Unit countUnit = new Unit(Unit.COUNT_UNIT, "회");

        Unit timeUnit1 = new Unit(Unit.TIME_UNIT, "분");
        Unit timeUnit2 = new Unit(Unit.TIME_UNIT, "시");

        Unit setUnit = new Unit(Unit.SET_UNIT, "Set");

        Unit walkUnit = new Unit(Unit.WALK_UNIT, "걸음");


        List<Unit> unitList = new ArrayList<>();
        unitList.add(unitLiquid1);
        unitList.add(unitLiquid2);
        unitList.add(unitLiquid3);
        unitList.add(countUnit);
        unitList.add(timeUnit1);
        unitList.add(timeUnit2);
        unitList.add(setUnit);
        unitList.add(walkUnit);
        mHabitRespository.insertUnit(unitList);

        Log.d(TAG, "DB TEST 초기화 종료 ");

        disconnectDB();
    }

}
