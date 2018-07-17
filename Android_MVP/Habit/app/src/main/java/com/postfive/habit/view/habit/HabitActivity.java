package com.postfive.habit.view.habit;

import android.app.TimePickerDialog;
import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TimePicker;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.google.firebase.auth.FirebaseUser;
import com.postfive.habit.R;
import com.postfive.habit.adpater.habittime.HabitTimeRecyclerViewAdapter;
import com.postfive.habit.habits.HabitMaker;
import com.postfive.habit.habits.factory.Habit;
import com.postfive.habit.habits.factory.HabitFactory;

import java.util.Calendar;

public class HabitActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener, View.OnClickListener, CompoundButton.OnCheckedChangeListener {


    private static final String TAG = "HabitActivity";
    /* firebase */
    private FirebaseUser mFirebaseUser;

    private HabitFactory mHabitFactory;
    private Habit mHabit;

    /* 화면 component */
    private EditText mGoalEdtText ;
    private int [] mDayofWeekToggleBtnId;
    private ToggleButton[] mDayofWeekToggleBtn;
    private ToggleButton mEveryDayToggleBtn;
    private ToggleButton mEveryWeekToggleBtn;

    private Button mBtnAddTime;
    private RecyclerView mTimeRecyclerView ;
    private HabitTimeRecyclerViewAdapter mHabitTimeRecyclerViewAdapter;
    private Spinner mSpinnerUnit;
    private ArrayAdapter arrayAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_habit);


        initComponent();

        processIntent();
    }

    protected void onNewIntent(Intent intent){
        processIntent();
        super.onNewIntent(intent);
    }

    private void processIntent() {
        Intent receivedIntent = getIntent();
        String habitType = receivedIntent.getStringExtra("habit");


        if(habitType == null || habitType.length() < 0){
            return;
        }

        mHabit = mHabitFactory.createHabit(habitType);

        if(mHabit != null) {
            mHabit.prepare();
            setComponent(mHabit);
        }


    };
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
//        Intent intent = new Intent(this, MainActivity.class);
        //setResult(RESULT_CANCELED, intent);
//        startActivity(intent);
        finish();
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

        // 시간 추가 버튼
        mBtnAddTime = (Button)findViewById(R.id.btn_addtime);
        mBtnAddTime.setOnClickListener(this);

        // 시간 추가되는 RecyclerView
        mTimeRecyclerView = (RecyclerView)findViewById(R.id.recyclerview_habit_time_list);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        mTimeRecyclerView.setLayoutManager(layoutManager);

        mHabitTimeRecyclerViewAdapter = new HabitTimeRecyclerViewAdapter();
        mTimeRecyclerView.setAdapter(mHabitTimeRecyclerViewAdapter);

        //
        mEveryDayToggleBtn = (ToggleButton)findViewById(R.id.togglebtn_everyday);
        mEveryWeekToggleBtn = (ToggleButton)findViewById(R.id.togglebtn_everyweek);
        mEveryDayToggleBtn.setOnCheckedChangeListener(this);
        mEveryWeekToggleBtn.setOnCheckedChangeListener(this);

        // mSpinnerUnit
        mSpinnerUnit = (Spinner)findViewById(R.id.spinner_unit);
//        arrayAdapter = new ArrayAdapter(this, R.layout.)


        mHabitFactory = new HabitMaker();

    }


    /**
     *  취미 선택 시 컴포넌트 set
     * @param habit
     */
    private void setComponent(Habit habit) {
        if(habit == null)
            return;

        // 목표 set
        mGoalEdtText.setText(habit.getGoal());

        setDayofWeekToggle(habit);
        mHabit.getUnit();
        mHabitFactory.getUnit(mHabit.getType());
//        mSpinnerUnit.setAdapter();
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
        Log.d(TAG, "DayofWeek "+ Integer.toString(habit.getDayofWeek()));
        // 메일
        if(habit.getDayofWeek() == 254) {
            mEveryDayToggleBtn.setChecked(true);
            mEveryWeekToggleBtn.setChecked(false);
        }
        else {
            mEveryDayToggleBtn.setChecked(false);
            mEveryWeekToggleBtn.setChecked(true);
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
        Log.d(TAG, " aa DayofWeek "+ Integer.toString(mHabit.getDayofWeek()));

        if (mHabit.getDayofWeek() != 254) {
            mEveryDayToggleBtn.setChecked(false);
            mEveryWeekToggleBtn.setChecked(true);
        }else {
            mEveryDayToggleBtn.setChecked(true);
            mEveryWeekToggleBtn.setChecked(false);
        }
    }

    // 매일 버튼 클릭
    public void onClickEveryDay(boolean isEveryDay){
        // 습관 미선택시 안됨
        if(mHabit == null){
            return;
        }

//        mEveryDayToggleBtn.setChecked(isEveryDay);
        mEveryWeekToggleBtn.setChecked(!isEveryDay);

        for(int i = 1 ; i < 8 ; i ++){
            ToggleButton tmpDayofWeekToggleBtn = mDayofWeekToggleBtn[i-1];

            tmpDayofWeekToggleBtn.setChecked(isEveryDay);
            mHabit.setDayofWeek(i, isEveryDay);
        }

        Calendar calendar = Calendar.getInstance();
        mHabit.setDayofWeek(calendar.get(Calendar.DAY_OF_WEEK), true);
        mDayofWeekToggleBtn[calendar.get(Calendar.DAY_OF_WEEK)].setChecked(true);
    }

    // 매주 선택
    public void onClickEveryWeek(boolean isEveryWeek){
        // 습관 미선택시 안됨
        if(mHabit == null){
            return;
        }

        if(mHabit.getDayofWeek() != 254){
            mEveryWeekToggleBtn.setChecked(true);
            return;
        }

        mEveryDayToggleBtn.setChecked(false);
//        mEveryWeekToggleBtn.setChecked(true);
        // 모두 비활성화
        for(int i = 1 ; i < 8 ; i ++){
            ToggleButton tmpDayofWeekToggleBtn = mDayofWeekToggleBtn[i-1];

            tmpDayofWeekToggleBtn.setChecked(false);
            mHabit.setDayofWeek(i, false);
        }
        // 오늘만 활성화
        Calendar calendar = Calendar.getInstance();
        mHabit.setDayofWeek(calendar.get(Calendar.DAY_OF_WEEK), true);
        mDayofWeekToggleBtn[calendar.get(Calendar.DAY_OF_WEEK)].setChecked(true);
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

    /* 버튼 클릭 이벤트 */
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_addtime :
                Calendar calendar = Calendar.getInstance();
                TimePickerDialog timePickerDialog = new TimePickerDialog(this, onTimeSetListener, calendar.get(Calendar.HOUR_OF_DAY), calendar.get(Calendar.MINUTE), true);
                timePickerDialog.show();
                break;
            default :
                break;
        }
    }


    private TimePickerDialog.OnTimeSetListener onTimeSetListener = new TimePickerDialog.OnTimeSetListener() {
        @Override
        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
            Toast.makeText(getApplicationContext(), hourOfDay + "시 " + minute + "분", Toast.LENGTH_SHORT).show();
            mHabitTimeRecyclerViewAdapter.setHabitTime(hourOfDay+":"+minute);
        }
    };

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        switch (buttonView.getId()){
            case R.id.togglebtn_everyday:
                //onClickEveryDay(isChecked);
                break;
            case R.id.togglebtn_everyweek :
                break;
            default :
                break;
        }
    }
}
