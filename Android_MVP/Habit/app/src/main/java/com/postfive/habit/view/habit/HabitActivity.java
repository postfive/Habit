package com.postfive.habit.view.habit;

import android.app.TimePickerDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
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
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.postfive.habit.FirebaseUse;
import com.postfive.habit.R;
import com.postfive.habit.adpater.habittime.HabitTimeRecyclerViewAdapter;
import com.postfive.habit.habits.HabitMaker;
import com.postfive.habit.habits.factory.Habit;
import com.postfive.habit.habits.factory.HabitFactory;
import com.postfive.habit.view.myhabitlist.MyHabitListActivity;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Map;


public class HabitActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener, View.OnClickListener, CompoundButton.OnCheckedChangeListener {


    private static final String TAG = "HabitActivity";
    /* firebase */
    private FirebaseAuth mFirebaseAuth;
    private FirebaseUser mFirebaseUser;
    private FirebaseDatabase mFirebaseDatabase;
    private HabitFactory mHabitFactory;
    private Habit mHabit;

    /* 화면 component */
    private TextView mHabitType ;             //목표
    private EditText mGoalEdtText ;             //목표
    private EditText mDayGoalEdtText;       // 일목표
    private EditText mOnceEdtText ;             // 일 회 수행 양(?)
    private Spinner mSpinnerUnit;               // 단위
    private TextView mUnitTextview;
    private int [] mDayofWeekToggleBtnId;       // 요일 버튼 id
    private ToggleButton[] mDayofWeekToggleBtn; // 요일 버튼
    private ToggleButton mEveryDayToggleBtn;    // 매일 버튼
    private ToggleButton mEveryWeekToggleBtn;   // 매주 버튼


    private ToggleButton mMorningTimeToggleBtn;     // 아침 버튼
    private ToggleButton mAfternoonTimeToggleBtn;   // 오후 버튼
    private ToggleButton mNightTimeToggleBtn;       // 저녁 버튼

    private Button mBtnAddTime;
    private RecyclerView mTimeRecyclerView ;
    private HabitTimeRecyclerViewAdapter mHabitTimeRecyclerViewAdapter;

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
        String habitType = receivedIntent.getStringExtra("habit");
        mHabit = (Habit) receivedIntent.getSerializableExtra("object");

        if(mHabit == null) {
            mHabit = mHabitFactory.createHabit(habitType);
            mHabit.prepare();

        }

        //TODO 수정인 경우 여기서 mHabit에 모든 값 setting  해야겟군
        setComponent(mHabit);
    }

    private void getHabit(String habitkey) {

        Query query = mFirebaseDatabase.getReference("user_habit_d/"+mFirebaseUser.getUid()+"/"+habitkey).orderByKey();
        Log.d(TAG, "habit type "+query.endAt("type").toString());
        /*
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {

/////////////// 나중에 함수로 묶자
                        String key = dataSnapshot.getKey();

                        Map<String, Object> map = (Map<String, Object>) dataSnapshot.getValue();

//                        mHabitFactory.createHabit();
                        String strType = map.get("type").toString();
                        mHabit = mHabitFactory.createHabit(strType);
                        mHabit.setGoal(map.get("goal").toString());
                        mHabit.setKey(key);
                        mHabit.setOnce(Integer.parseInt(map.get("once").toString()));
                        mHabit.setFull(Integer.parseInt(map.get("full").toString()));
                        mHabit.setDayFull(Integer.parseInt(map.get("dayofWeek").toString()));
                        mHabit.setDidDay(Integer.parseInt(map.get("didDay").toString()));
                        mHabit.setDayofWeek(Integer.parseInt(map.get("didDay").toString()));
                        mHabit.setUnit(map.get("unit").toString());
                        List<String> timeList = (List<String>)map.get("time");
                        for(String tmp : timeList){
                            if(tmp == null)
                                continue;
                            mHabit.setTime(tmp);
                            Log.d(TAG, tmp);
                        }
/////////////////// 나중에 함수로 묶자
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        // 읽기 실패
                        finish();
                    }
                });*/
    }

    ;
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

        // 액션바 뒤로가기 버튼
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayShowTitleEnabled(false);

        //
        mHabitType = (TextView)findViewById(R.id.textView_habit_type);
        // 목표 설정
        mGoalEdtText = (EditText)findViewById(R.id.edittext_goal);

        mDayGoalEdtText = (EditText)findViewById(R.id.edittext_daygoal);       // 일목표
        mOnceEdtText = (EditText)findViewById(R.id.edittext_once);             // 일 회 수행 양(?)
        mSpinnerUnit = (Spinner)findViewById(R.id.spinner_unit);               // 단위
        mUnitTextview = (TextView)findViewById(R.id.textview_unit);

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
        //
//        for(int i = 0 ; i < 7 ; i ++){
//            mDayofWeekToggleBtn[i].setOnClickListener(this);
//        }

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

        // 시간 아침 오후 저녁toggleBtn_morning
        mMorningTimeToggleBtn   = (ToggleButton)findViewById(R.id.toggleBtn_morning);     // 아침 버튼
        mAfternoonTimeToggleBtn = (ToggleButton)findViewById(R.id.toggleBtn_afternoon);   // 오후 버튼
        mNightTimeToggleBtn     = (ToggleButton)findViewById(R.id.toggleBtn_night);       // 저녁 버튼
        mMorningTimeToggleBtn.setOnCheckedChangeListener(this);
        mAfternoonTimeToggleBtn.setOnCheckedChangeListener(this);
        mNightTimeToggleBtn.setOnCheckedChangeListener(this);

        mHabitFactory = new HabitMaker();

    }


    /**
     *  취미 선택 시 컴포넌트 set
     * @param habit
     */
    private void setComponent(Habit habit) {
        if(habit == null)
            return;

        mHabitType.setText(habit.getTypeName());

        // 목표 set
        mGoalEdtText.setText(habit.getGoal());

        // 1일 목표
        mDayGoalEdtText.setText(Integer.toString(habit.getDayFull()));
        mOnceEdtText.setText(Integer.toString(habit.getOnce()));

        //


        // unit spinner 설정
//        List<String> unitList = new ArrayList<>(habit.getUnitList().keySet());
//        List<String> unitList = new ArrayList<String>();
//        for(String tmp : habit.getUnitList().split("/")) {
//            unitList.add(tmp);
//        }
//        unitList.addAll();
        List<String> unitList = habit.getUnitList();
        arrayAdapter = new ArrayAdapter<String>(this, R.layout.support_simple_spinner_dropdown_item, unitList );
        mSpinnerUnit.setAdapter(arrayAdapter);
        mSpinnerUnit.setOnItemSelectedListener(this);
        mSpinnerUnit.setId(0);
        mUnitTextview.setText(unitList.get(0));

//         시간 시간 설정
//        List<String> time  = new ArrayList<>(habit.getTime().keySet());
//        List<String> time  = new ArrayList<>();
//        for(String tmp : habit.getTime().split("/")) {
//            unitList.add(tmp);
//        }
        List<String> time = habit.getTime();

//        for(String tmpTime : time){
        for(int i = 0 ; i < time.size() ; i++){
            Log.d(TAG, "tmp Time "+Integer.toString(i));
            String tmpTime = time.get(i);

            Log.d(TAG, "tmp Time "+tmpTime);
            if(tmpTime.equals("m")){
                mMorningTimeToggleBtn.setChecked(true);
            }else if(tmpTime.equals("a")){
                mAfternoonTimeToggleBtn.setChecked(true);
            }else if(tmpTime.equals("n")){
                mNightTimeToggleBtn.setChecked(true);
            }else if(tmpTime.indexOf(":") > 0) {
                // 그냥 시간
                mHabitTimeRecyclerViewAdapter.setHabitTime(tmpTime);
            }

        }

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

        switch(v.getId()){
            case R.id.togglebtn_sunday :
                mHabit.setDayofWeek(1, mDayofWeekToggleBtn[0].isChecked());
                break;
            case R.id.togglebtn_monday :
                mHabit.setDayofWeek(2, mDayofWeekToggleBtn[1].isChecked());
                break;
            case R.id.togglebtn_tuesday :
                mHabit.setDayofWeek(3, mDayofWeekToggleBtn[2].isChecked());
                break;
            case R.id.togglebtn_wednesday :
                mHabit.setDayofWeek(4, mDayofWeekToggleBtn[3].isChecked());
                break;
            case R.id.togglebtn_thursday :
                mHabit.setDayofWeek(5, mDayofWeekToggleBtn[4].isChecked());
                break;
            case R.id.togglebtn_friday :
                mHabit.setDayofWeek(6, mDayofWeekToggleBtn[5].isChecked());
                break;
            case R.id.togglebtn_saturday :
                mHabit.setDayofWeek(7, mDayofWeekToggleBtn[6].isChecked());
                break;
            default :
                break;

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
            mEveryDayToggleBtn.setChecked(true);
            return;
        }

        mEveryWeekToggleBtn.setChecked(false);

        // 모두 비활성화
        for(int i = 1 ; i < 8 ; i ++){
            ToggleButton tmpDayofWeekToggleBtn = mDayofWeekToggleBtn[i-1];

            tmpDayofWeekToggleBtn.setChecked(false);
            mHabit.setDayofWeek(i, false);
        }
        // 오늘만 활성화
        Calendar calendar = Calendar.getInstance();
        int tmpDayofWeek = calendar.get(Calendar.DAY_OF_WEEK);
        mHabit.setDayofWeek(tmpDayofWeek, true);
        mDayofWeekToggleBtn[tmpDayofWeek].setChecked(true);
    }
    /* Spinner 선택 이벤트 */
    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

        if(mHabit == null)
            return;
        // 스피너에서 선택한 단위로 set
        List<String> unitList = mHabit.getUnitList();
//        List<String> unitList = new ArrayList<>(mHabit.getUnitList().keySet());
//        for(String tmp : mHabit.getUnitList().split("/")) {
//            unitList.add(tmp);
//        }

        mHabit.setUnit(unitList.get(position));

        mUnitTextview.setText(unitList.get(position));

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
    /* Spinner 선택 이벤트 종료 */

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
                if(isChecked){
                    //onClickEveryDay(isChecked);
                    Log.d(TAG, "togglebtn_everyday CheckedChanged true");
                }else{
                    //onClickEveryWeek(isChecked);
                    Log.d(TAG, "togglebtn_everyday CheckedChanged false");
                }
                break;
            case R.id.togglebtn_everyweek :
                if(isChecked){
                    //onClickEveryWeek(isChecked);
                    Log.d(TAG, "togglebtn_everyweek CheckedChanged true");
                }else{
                    //onClickEveryDay(isChecked);
                    Log.d(TAG, "togglebtn_everyweek CheckedChanged false");
                }
                break;
            case R.id.toggleBtn_morning :
                mHabit.setTime("m");
                break;
            case R.id.toggleBtn_afternoon :
                mHabit.setTime("a");
                break;
            case R.id.toggleBtn_night :
                mHabit.setTime("n");
                break;
            default :
                break;
        }
    }

    public void onClickSaveHabit(View v){
        if(mHabit == null){
            return;
        }

        // 타이틀 확인
        if(mGoalEdtText.getText().toString().length() < 1){
            Toast.makeText(this, "목표가 입력되지 않았습니다.", Toast.LENGTH_SHORT).show();
            return;
        }
        mHabit.setGoal(mGoalEdtText.getText().toString());

        // 하루목표 확인
        if(mDayGoalEdtText.getText().toString().length() < 1){
            Toast.makeText(this, "일 목표가 입력되지 않았습니다.", Toast.LENGTH_SHORT).show();
            return;
        }
        mHabit.setDayFull(Integer.parseInt(mDayGoalEdtText.getText().toString()));

        // 하루 수행 확인
        if(mOnceEdtText.getText().toString().length() < 1){
            Toast.makeText(this, "1회 수행 량이 입력되지 않았습니다.", Toast.LENGTH_SHORT).show();
            return;
        }
        mHabit.setOnce(Integer.parseInt(mOnceEdtText.getText().toString()));

        // 요일 확인
        if(mHabit.getDayofWeek() < 0){
            Toast.makeText(this, "요일이 입력되지 않았습니다.", Toast.LENGTH_SHORT).show();
            return;
        }

        // 시간 확인
        List<String> habitTime = mHabitTimeRecyclerViewAdapter.getAllHabit();

        if(habitTime != null) {
            for (String tmpTime : habitTime) {
                mHabit.setTime(tmpTime);
            }
        }

//        Log.d(TAG, "?? 왜지 "+ Integer.toString(mHabit.getTime().indexOf("/") ));
        if(mHabit.getTime().size() < 1){
            Toast.makeText(this, "시간이 입력되지 않았습니다.", Toast.LENGTH_SHORT).show();
            return;
        }

        initFirebase();

        Log.d(TAG, "HabitActivity save get Uid "+mFirebaseUser.getUid());

        if(mHabit.getKey() == null) {
            saveHait();
        }else{
            updateHait();
        }

    }

    private void saveHait() {

        if(mHabit == null){
            Toast.makeText(HabitActivity.this,"습관이 설정되지 않았습니다", Toast.LENGTH_SHORT).show();
            return;
        }

        // key 값 생성
        String key = mFirebaseDatabase.getReference("user_habit_d/"+mFirebaseUser.getUid()).push().getKey();
        mHabit.setKey(key);

        mFirebaseDatabase.getReference("user_habit_d/"+mFirebaseUser.getUid() +"/"+ key)
                .setValue(mHabit)
                .addOnSuccessListener(HabitActivity.this, new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(HabitActivity.this,"습관이 저장되었습니다.", Toast.LENGTH_SHORT).show();

                        goMyHabitList();
                    }
                })
                .addOnFailureListener(HabitActivity.this, new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
//                Snackbar.make(etContent, e.getMessage(), Snackbar.LENGTH_LONG).show();
                        Log.e("HabitActivity", e.getMessage());
                    }
                });
    }
    private void updateHait() {

        if(mHabit == null){
            Toast.makeText(HabitActivity.this,"습관이 설정되지 않았습니다", Toast.LENGTH_SHORT).show();
            return;
        }
        mFirebaseDatabase.getReference("user_habit_d/"+mFirebaseUser.getUid()+"/"+mHabit.getKey())
                .setValue(mHabit)
                .addOnSuccessListener(HabitActivity.this, new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(HabitActivity.this,"습관 수정이 완료 되었습니다.", Toast.LENGTH_SHORT).show();

                        goMyHabitList();
                    }
                });
    }

    private void initFirebase(){

        mFirebaseUser = FirebaseUse.getUser();

        mFirebaseDatabase = FirebaseUse.getDatabase();

    }
}
