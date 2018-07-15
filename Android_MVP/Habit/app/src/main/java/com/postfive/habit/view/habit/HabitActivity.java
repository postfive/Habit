package com.postfive.habit.view.habit;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.ScrollingTabContainerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.ToggleButton;

import com.google.firebase.auth.FirebaseUser;
import com.postfive.habit.R;
import com.postfive.habit.habits.factory.Habit;
import com.postfive.habit.habits.factory.HabitFactory;
import com.postfive.habit.view.main.MainActivity;

public class HabitActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {


    /* firebase */
    private FirebaseUser mFirebaseUser;

    private HabitFactory mHabitFactory;
    private Habit mHabit;

    /* 화면 component */
    private EditText mGoalEdtText ;
    private int [] mDayofWeekToggleBtnId;
    private ToggleButton[] mDayofWeekToggleBtn;
    private Spinner mSpinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_habit);


        initComponent();

    }


    /* toolbar, action bar 버튼 클릭 이벤트 */
    public boolean onOptionsItemSelected(android.view.MenuItem item) {
        switch (item.getItemId()) {

            case android.R.id.home:
                onClickHome();
                break;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }
    // 뒤로
    private void onClickHome(){
        Intent intent = new Intent(this, MainActivity.class);
        //setResult(RESULT_CANCELED, intent);
        startActivity(intent);
    }

    /**
     *  initComponent
     *  화면 component set
     */
    private void initComponent() {

        // Toolbar 설정
        Toolbar myToolbar = (Toolbar) findViewById(R.id.toolbar_habit);
        setSupportActionBar(myToolbar);

        // 액션바 뒤로가기 버튼
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayShowTitleEnabled(false);


        // 목표 설정
        mGoalEdtText = (EditText)findViewById(R.id.edt_title);

        // 요일 토글 버튼 id array
        mDayofWeekToggleBtnId = new int[7];
        mDayofWeekToggleBtnId[0] = R.id.togglebtn_sunday;
        mDayofWeekToggleBtnId[1] = R.id.togglebtn_monday;
        mDayofWeekToggleBtnId[2] = R.id.togglebtn_tuesday;
        mDayofWeekToggleBtnId[3] = R.id.togglebtn_wednesday;
        mDayofWeekToggleBtnId[4] = R.id.togglebtn_thursday;
        mDayofWeekToggleBtnId[5] = R.id.togglebtn_friday;
        mDayofWeekToggleBtnId[6] = R.id.togglebtn_saturday;

        // 요일 버튼
        mDayofWeekToggleBtn = new ToggleButton[7];
        mDayofWeekToggleBtn[0] = (ToggleButton)findViewById(R.id.togglebtn_sunday);
        mDayofWeekToggleBtn[1] = (ToggleButton)findViewById(R.id.togglebtn_monday);
        mDayofWeekToggleBtn[2] = (ToggleButton)findViewById(R.id.togglebtn_tuesday);
        mDayofWeekToggleBtn[3] = (ToggleButton)findViewById(R.id.togglebtn_wednesday);
        mDayofWeekToggleBtn[4] = (ToggleButton)findViewById(R.id.togglebtn_thursday);
        mDayofWeekToggleBtn[5] = (ToggleButton)findViewById(R.id.togglebtn_friday);
        mDayofWeekToggleBtn[6] = (ToggleButton)findViewById(R.id.togglebtn_saturday);

        // 취미선택
//        mSpinner = (Spinner)findViewById(R.id.spinner);
//        mSpinner.setOnItemSelectedListener(this);
    }


    /**
     *  취미 선택 시 컴포넌트 set
     * @param habit
     */
    private void setComponent(Habit habit) {
        // 목표 set
        mGoalEdtText.setText(habit.getGoal());

        setDayofWeekToggle(habit);
    }

    /**
     *  취미 요일 set
     * @param habit
     */
    private void setDayofWeekToggle(Habit habit) {

        if(habit == null){
            return;
        }

        for(int i = 1 ; i < 8 ; i ++){
            boolean isDay = habit.isDayofWeek(i);
            mDayofWeekToggleBtn[i-1].setChecked(isDay);
        }
    }

    /**
     *
     * @param v
     */
    // 요일 버튼 클릭
    public void onClickDayofWeek(View v){
        // 습관 미선택시 안됨
        if(mHabit == null){
            return;
        }

        for(int i = 1 ; i < 8 ; i ++){
            int tmpDayofWeekToggleBtn = mDayofWeekToggleBtnId[i-1];

            if (v.getId() == tmpDayofWeekToggleBtn){
                boolean isDay = mHabit.isDayofWeek(i);
                mHabit.setDayofWeek(i, !isDay);
            }
        }
    }

    /* Spinner 선택 이벤트 */
    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

        mHabit = null;
        if(position  == 0){
            return;
        }
        mHabit = mHabitFactory.createHabit(position-1);

        if(mHabit != null) {
            mHabit.prepare();
            setComponent(mHabit);
        }

    }
    /* Spinner 선택 이벤트 */
    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }


}
