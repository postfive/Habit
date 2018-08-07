package com.postfive.habit.view.habit;


import android.annotation.SuppressLint;
import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.postfive.habit.R;
import com.postfive.habit.db.Habit;
import com.postfive.habit.db.UserHabitDetail;
import com.postfive.habit.db.UserHabitRespository;
import com.postfive.habit.db.UserHabitState;
import com.postfive.habit.habits.HabitMaker;
import com.postfive.habit.habits.factory.HabitFactory;
import com.postfive.habit.view.HabitList.HabitListActivity;
import com.postfive.habit.view.myhabitlist.MyHabitListActivity;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;


public class HabitActivity extends AppCompatActivity {

    private static final String TAG = "HabitActivity";
    private static final int GET_HABIT = 9000;

    private UserHabitRespository mUserHabitRespository;

    private HashMap<String, Integer> mUnitMap = new HashMap<>();
    private HabitFactory mHabitFactory;
    private UserHabitDetail mHabit;

    /* 화면 component */
    private TextView mActivityTitleName;
    private TextView mHabitName;
    private Button mHabitListBtn;
    private EditText mUserNameEdtText;        // 사용자 설정 이름
    private ImageButton mUserNameClearBtn;
    private TextView mUserNameHintTextView;

    private EditText mGoalEdtText;
    private ImageButton mGoalClearBtn;
    private TextView mGoalHintTextView;

    private Spinner mSpinnerUnit;               // 단위
    private int [] mDayofWeekToggleBtnId;       // 요일 버튼 id
    private ToggleButton[] mDayofWeekToggleBtn; // 요일 버튼


    private ToggleButton mMorningTimeToggleBtn;     // 아침 버튼
    private ToggleButton mAfternoonTimeToggleBtn;   // 오후 버튼
    private ToggleButton mNightTimeToggleBtn;       // 저녁 버튼
    private ToggleButton mAllTimeToggleBtn;         // 하루종일 버튼

    private ArrayAdapter<String> arrayAdapter;

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

        mHabit = (UserHabitDetail) receivedIntent.getSerializableExtra("object");

        // 추가 인놈
        if(mHabit == null){
            mActivityTitleName.setText(getResources().getText(R.string.add_habit_name));
            return;
        }
        mActivityTitleName.setText(getResources().getText(R.string.edit_habit));
        mHabitListBtn.setVisibility(View.GONE);
        Log.d(TAG, "seq " +mHabit.getName()+" / " +Integer.toString(mHabit.getTime()));

        setComponent(mHabit);
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
        finish();
    }
    private void goMyHabitList(){
        Intent intent = new Intent(this, MyHabitListActivity.class);

        startActivity(intent);
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
//        myToolbar.setPadding(0, getStatusBarHeight(), 0, 0);

        // 액션바 뒤로가기 버튼
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayShowTitleEnabled(false);
        actionBar.setHomeAsUpIndicator(R.drawable.ic_chevron_left);

        mActivityTitleName = (TextView)findViewById(R.id.textview_habit_title);

        mHabitName = (TextView)findViewById(R.id.textview_habit_name);
        mUserNameHintTextView = (TextView)findViewById(R.id.textview_habit_username_hint);
        mUserNameEdtText = (EditText)findViewById(R.id.edittext_habit_username);
        mUserNameClearBtn = (ImageButton)findViewById(R.id.btn_habit_username_clear);
        mUserNameClearBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mUserNameEdtText.setText("");
            }
        });
        mUserNameEdtText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @SuppressLint("ResourceType")
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                // 힌트 보이기 버튼 보이기
                if(hasFocus) {
                    mUserNameClearBtn.setVisibility(View.VISIBLE);
                    mUserNameHintTextView.setVisibility(View.VISIBLE);
                    setEditTextColor(mUserNameEdtText, mUserNameHintTextView, 10);
                }else{
                    mUserNameClearBtn.setVisibility(View.INVISIBLE);
                    mUserNameHintTextView.setVisibility(View.INVISIBLE);
                    mUserNameEdtText.setBackgroundResource(R.drawable.drawable_edittext);
                    mUserNameHintTextView.setTextColor(getResources().getColor(R.color.hintTextColor));
                }
            }
        });
        mUserNameEdtText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                setEditTextColor(mUserNameEdtText, mUserNameHintTextView, 10);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        mHabitListBtn = (Button)findViewById(R.id.btn_habit_list);
        mGoalHintTextView = (TextView)findViewById(R.id.textview_goal_hint);
        mGoalEdtText = (EditText)findViewById(R.id.edittext_goal);
        mGoalClearBtn = (ImageButton)findViewById(R.id.btn_clear_goal);
        mGoalClearBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mGoalEdtText.setText(null);
            }
        });

        mGoalEdtText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                // 힌트 보이기 버튼 보이기
                if(hasFocus) {
                    mGoalClearBtn.setVisibility(View.VISIBLE);
                    mGoalHintTextView.setVisibility(View.VISIBLE);
                }else{
                    mGoalClearBtn.setVisibility(View.INVISIBLE);
                    mGoalHintTextView.setVisibility(View.INVISIBLE);
                    mUserNameEdtText.setBackgroundResource(R.drawable.drawable_edittext);
                }
            }
        });

        mGoalEdtText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                //setEditTextColor(mGoalEdtText, mGoalHintTextView,2);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
//        mOnceEdtText = (EditText)findViewById(R.id.edittext_once);             // 일 회 수행 양(?)
        mSpinnerUnit = (Spinner)findViewById(R.id.spinner_unit);               // 단위
//        mUnitTextview = (TextView)findViewById(R.id.textview_unit);

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


        // 시간 아침 오후 저녁toggleBtn_morning
        mMorningTimeToggleBtn   = (ToggleButton)findViewById(R.id.toggleBtn_morning);     // 아침 버튼
        mAfternoonTimeToggleBtn = (ToggleButton)findViewById(R.id.toggleBtn_afternoon);   // 오후 버튼
        mNightTimeToggleBtn     = (ToggleButton)findViewById(R.id.toggleBtn_night);       // 저녁 버튼
        mAllTimeToggleBtn       = (ToggleButton)findViewById(R.id.toggleBtn_all);       // 저녁 버튼

/*        for(int i = 0 ; i < 7 ; i++){
            mDayofWeekToggleBtn[i].setOnCheckedChangeListener(this);
        }
        mMorningTimeToggleBtn.setOnCheckedChangeListener(this);
        mAfternoonTimeToggleBtn.setOnCheckedChangeListener(this);
        mNightTimeToggleBtn.setOnCheckedChangeListener(this);
        mAllTimeToggleBtn.setOnCheckedChangeListener(this);*/

        mHabitFactory = new HabitMaker();

    }


    private void setEditTextColor(EditText editText, TextView hint,int maxLength){
        if(editText.getText().length() > maxLength-1){
            editText.setBackgroundResource(R.drawable.drawable_edittext_error);
            hint.setTextColor(getResources().getColor(R.color.errorrColor));
        }else{

            editText.setBackgroundResource(R.drawable.drawable_edittext);
            hint.setTextColor(getResources().getColor(R.color.hintTextColor));
        }
    }
    /**
     *  취미 선택 시 컴포넌트 set
     * @param habit
     */
    private void setComponent(UserHabitDetail habit) {
        if(habit == null)
            return;

        //습관 이름
        mHabitName.setText(habit.getName());
        // 사용자 설정 습관 이름
        mUserNameEdtText.setText(habit.getCustomname());

        // 1일 목표
        mGoalEdtText.setText(Integer.toString(habit.getGoal()));

        connectDB();
        List<String> unitList =
                mUserHabitRespository.getHabitUnit(habit.getHabitcode());
        disconnectDB();
        for(int i =  0 ; i < unitList.size() ; i++){
            unitList.set(i, unitList.get(i)+" / 일");
        }

        // spinner
        arrayAdapter = new ArrayAdapter<>(this, R.layout.spinner, unitList );
        arrayAdapter.setDropDownViewResource(R.layout.spinner_dropdown);
        mSpinnerUnit.setAdapter(arrayAdapter);
        mSpinnerUnit.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                mHabit.setUnit((String) mSpinnerUnit.getItemAtPosition(position));
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        mSpinnerUnit.setId(0);
        mHabit.setUnit(unitList.get(0));

        // 시간SET
        if(habit.getTime() == Habit.MORNING_TIME){
            mMorningTimeToggleBtn.setChecked(true);
            mAfternoonTimeToggleBtn.setChecked(false);
            mNightTimeToggleBtn.setChecked(false);
            mAllTimeToggleBtn.setChecked(false);
        }else if(habit.getTime() == Habit.AFTERNOON_TIME){
            mMorningTimeToggleBtn.setChecked(false);
            mAfternoonTimeToggleBtn.setChecked(true);
            mNightTimeToggleBtn.setChecked(false);
            mAllTimeToggleBtn.setChecked(false);
        }else if(habit.getTime() == Habit.NIGHT_TIME){
            mMorningTimeToggleBtn.setChecked(false);
            mAfternoonTimeToggleBtn.setChecked(false);
            mNightTimeToggleBtn.setChecked(true);
            mAllTimeToggleBtn.setChecked(false);
        }else if(habit.getTime() == Habit.ALLDAY_TIME) {
            // TODO 하루종일
            mMorningTimeToggleBtn.setChecked(false);
            mAfternoonTimeToggleBtn.setChecked(false);
            mNightTimeToggleBtn.setChecked(false);
            mAllTimeToggleBtn.setChecked(true);
        }else{
            mMorningTimeToggleBtn.setChecked(false);
            mAfternoonTimeToggleBtn.setChecked(false);
            mNightTimeToggleBtn.setChecked(false);
            mAllTimeToggleBtn.setChecked(false);
        }

//        Log.d(TAG, "what time??? "+ mHabit.getTime());
        setDayofWeekToggle(habit);

    }

    /**
     *  취미 요일 set
     * @param habit
     */
    private void setDayofWeekToggle(UserHabitDetail habit) {

        if(habit == null){
            return;
        }
        int tmpDaySum = habit.getDaysum();

        for(int i = 1 ; i < 8 ; i ++){
            if((tmpDaySum & ( 1<< i) ) > 0){
                mDayofWeekToggleBtn[i-1].setChecked(true);
            }
        }

        Log.d(TAG, "DayofWeek "+ Integer.toString(tmpDaySum));

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

        switch(v.getId()){
            case R.id.togglebtn_sunday :
                mHabit.setDaysumUsingOf(Calendar.SUNDAY, mDayofWeekToggleBtn[0].isChecked());
                break;
            case R.id.togglebtn_monday :
                mHabit.setDaysumUsingOf(Calendar.MONDAY, mDayofWeekToggleBtn[1].isChecked());
                break;
            case R.id.togglebtn_tuesday :
                mHabit.setDaysumUsingOf(Calendar.TUESDAY, mDayofWeekToggleBtn[2].isChecked());
                break;
            case R.id.togglebtn_wednesday :
                mHabit.setDaysumUsingOf(Calendar.WEDNESDAY, mDayofWeekToggleBtn[3].isChecked());
                break;
            case R.id.togglebtn_thursday :
                mHabit.setDaysumUsingOf(Calendar.THURSDAY, mDayofWeekToggleBtn[4].isChecked());
                break;
            case R.id.togglebtn_friday :
                mHabit.setDaysumUsingOf(Calendar.FRIDAY, mDayofWeekToggleBtn[5].isChecked());
                break;
            case R.id.togglebtn_saturday :
                mHabit.setDaysumUsingOf(Calendar.SATURDAY, mDayofWeekToggleBtn[6].isChecked());
                break;
            default :
                break;

        }

        Log.d(TAG, "Every dayday" +Integer.toString(mHabit.getDaysum()));
    }

    public void onClickTime(View v){

        switch (v.getId()){
            case R.id.toggleBtn_all:
                mMorningTimeToggleBtn.setChecked(false);
                mAfternoonTimeToggleBtn.setChecked(false);
                mNightTimeToggleBtn.setChecked(false);
                mAllTimeToggleBtn.setChecked(true);
                mHabit.setTime(Habit.ALLDAY_TIME);
                break;
            case R.id.toggleBtn_morning :
                mMorningTimeToggleBtn.setChecked(true);
                mAfternoonTimeToggleBtn.setChecked(false);
                mNightTimeToggleBtn.setChecked(false);
                mAllTimeToggleBtn.setChecked(false);
                mHabit.setTime(Habit.MORNING_TIME);
                break;
            case R.id.toggleBtn_afternoon :
                mMorningTimeToggleBtn.setChecked(false);
                mAfternoonTimeToggleBtn.setChecked(true);
                mNightTimeToggleBtn.setChecked(false);
                mAllTimeToggleBtn.setChecked(false);
                mHabit.setTime(Habit.AFTERNOON_TIME);
                break;
            case R.id.toggleBtn_night :
                mMorningTimeToggleBtn.setChecked(false);
                mAfternoonTimeToggleBtn.setChecked(false);
                mNightTimeToggleBtn.setChecked(true);
                mAllTimeToggleBtn.setChecked(false);
                mHabit.setTime(Habit.NIGHT_TIME);
                break;
            default :
                mMorningTimeToggleBtn.setChecked(false);
                mAfternoonTimeToggleBtn.setChecked(false);
                mNightTimeToggleBtn.setChecked(false);
                mAllTimeToggleBtn.setChecked(false);
                mHabit.setTime(-1); // 마지막에 -1이면 안되게 하기
                break;
        }

    }

    public void onClickSaveHabit(View v){
        if(mHabit == null){
            Toast.makeText(this,"습관을 선택하지 않으셨습니다.", Toast.LENGTH_SHORT).show();
            return;
        }

        // 타이틀 확인
        if(mUserNameEdtText.getText().toString().length() < 1){
            Toast.makeText(this, "습관이름이 입력되지 않았습니다.", Toast.LENGTH_SHORT).show();
            return;
        }
        mHabit.setCustomname(mUserNameEdtText.getText().toString());

        // 하루목표 확인
        if(mGoalEdtText.getText().toString().length() < 1){
            Toast.makeText(this, "목표가 입력되지 않았습니다.", Toast.LENGTH_SHORT).show();
            return;
        }
        mHabit.setGoal(Integer.parseInt(mGoalEdtText.getText().toString()));

        // 요일 확인
        if(mHabit.getDaysum() < 1){
            Toast.makeText(this, "요일이 입력되지 않았습니다.", Toast.LENGTH_SHORT).show();
            return;
        }

        Toast.makeText(this, "getTime "+Integer.toString(mHabit.getTime()), Toast.LENGTH_SHORT).show();
        if(mHabit.getTime() < 0 ){
            Toast.makeText(this, "시간이 입력되지 않았습니다.", Toast.LENGTH_SHORT).show();
            return;
        }

        if(mHabit.getUnit().length()<1){
            Toast.makeText(this, "단위 선택이 되지 않았습니다.", Toast.LENGTH_SHORT).show();
            return;
        }

        connectDB();

        if(mHabit.getHabitseq() == 0 ) {
//            Log.d(TAG, "DB TEST 날짜 확인 :  " +mHabit.getDaysum() + " 시간 확인 : "+mHabit.getTime() +" 단위 확인 : "+mHabit.getUnit());
            saveHait();
        }else{
            updateHait();
        }
//        disconnectDB();
        finish();
    }
    public void onClickDelHabit(View v){
        if(mHabit == null){
            Toast.makeText(this,"습관삭제할 습관이 없습니다.", Toast.LENGTH_SHORT).show();
            return;
        }

        int seq = mHabit.getHabitseq();
        connectDB();

        mUserHabitRespository.deleteUserHabit(mHabit.getHabitseq());

        disconnectDB();


/*        Intent intent = new Intent();
        intent.putExtra("seq", seq);
        setResult(RESULT_OK, intent);*/
        finish();


    }

    private void saveHait() {
        int detailSeq = mUserHabitRespository.getMaxSeqHabitDetail();
        int stateSeq  = mUserHabitRespository.getMaxSeqUserHabitState();

        mHabit.setHabitseq(detailSeq+1);

        Toast.makeText(this, "DB TEST get Seq "+Integer.toString(detailSeq) + " / "+ Integer.toString(stateSeq) , Toast.LENGTH_SHORT).show();

        List<UserHabitState> userHabitStateList  = new ArrayList<>();
        int userStatepriority =0;

//        int userStateSeq = mUserHabitRespository.getMaxSeqUserHabitState();
        Log.d(TAG, "DB TEST time "+mHabit.getTime()  );

        Log.d(TAG, "DB TEST "+userStatepriority+1 +"  "+stateSeq+1);
        // user state 습관 넣기

        for (int dayofweek = 1; dayofweek < 8; dayofweek++) {
            if ((mHabit.getDaysum() & (1 << dayofweek)) > 0) {
                //userStatepriority = mUserHabitRespository.getMaxPriorityUserHabitState(mHabit.getTime(), dayofweek);
                Log.d(TAG, "DB TEST userstatepri "+userStatepriority);
                userStatepriority++;
                stateSeq++;
                Log.d(TAG, "DB TEST userstatepri "+userStatepriority);
//                UserHabitState tmpState = new UserHabitState(userStateSeq,userStatepriority, dayofweek, mHabit);
                UserHabitState tmpState = new UserHabitState(stateSeq, dayofweek, mHabit);
                userHabitStateList.add(tmpState);
//                Log.d(TAG,  "DB TEST  make state "+tmpState.getDayofweek() +"/"+tmpState.getPriority()+"/"+tmpState.getDaysum()+"/"+tmpState.getTime() +"/"+ tmpState.getMasterseq()  +"/"+ tmpState.getHabitcode() +"/"+  tmpState.getName() +"/"+ tmpState.getGoal() +"/"+ tmpState.getDaysum() +"/"+ tmpState.getFull() +"/"+ tmpState.getUnit() );
                Log.d(TAG,  "DB TEST  make state "+tmpState.getDayofweek() +"/"+tmpState.getDaysum()+"/"+tmpState.getTime() +"/"+ tmpState.getMasterseq()  +"/"+ tmpState.getHabitcode() +"/"+  tmpState.getName() +"/"+ tmpState.getCustomname() +"/"+ tmpState.getDaysum() +"/"+ tmpState.getGoal() +"/"+ tmpState.getUnit() );
            }

        }

        mUserHabitRespository.insertUserHabit(mHabit , userHabitStateList);
    }

    private void updateHait() {
        int stateseq = mUserHabitRespository.getMaxSeqUserHabitState();
        List<UserHabitState> insertHabitStateList = new ArrayList<>();
        int daySum = mHabit.getDaysum();
        for(int dayofweek = 1 ; dayofweek < 8 ; dayofweek ++){
            if ((mHabit.getDaysum() & (1 << dayofweek)) > 0) {
                stateseq++;
                insertHabitStateList.add(new UserHabitState(stateseq, dayofweek, mHabit));
            }
        }

        mUserHabitRespository.updateUserHabit(mHabit, insertHabitStateList);

    }

    private void connectDB(){
        mUserHabitRespository = new UserHabitRespository(getApplication());
    }

    private void disconnectDB(){
        mUserHabitRespository.destroyInstance();
    }

    public void onClickHabitList(View v){
        Intent intent = new Intent(this, HabitListActivity.class);
        startActivityForResult(intent, GET_HABIT);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        Log.d(TAG, "완료 되었네 ?");
        if(requestCode == GET_HABIT && resultCode == RESULT_OK){
            Log.d(TAG, "완료 되었네 ");
            mHabit = (UserHabitDetail) data.getSerializableExtra("object");
            setComponent(mHabit);
        }

    }
}
