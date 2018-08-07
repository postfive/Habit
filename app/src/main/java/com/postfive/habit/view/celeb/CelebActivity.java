package com.postfive.habit.view.celeb;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.postfive.habit.R;
import com.postfive.habit.UserSettingValue;
import com.postfive.habit.adpater.celebdetaillist.celeblist.CelebDetailRecyclerViewAdapter;
import com.postfive.habit.adpater.celebdetaillist.celeblist.HabitKitRecyclerViewAdapter;
import com.postfive.habit.db.CelebHabitDetail;
import com.postfive.habit.db.CelebHabitKit;
import com.postfive.habit.db.CelebHabitMaster;
import com.postfive.habit.db.HabitRespository;
import com.postfive.habit.db.UserHabitDetail;
import com.postfive.habit.db.UserHabitRespository;
import com.postfive.habit.db.UserHabitState;
import com.postfive.habit.view.main.MainActivity;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class CelebActivity extends AppCompatActivity  {

    private static final String TAG = "CelebActivity";

    private TextView textViewTitle;
    private TextView textViewSubTitle;
    private TextView textViewSubTitle2;
    private ImageView imageViewTitle;
    private Button mBtnResolution;
    private EditText editTextResolution;
    private TextView mHintTextView;
    private Dialog MyDialog;

    private boolean isCompleteResolution;

    private RecyclerView mRecyclerViewCelebDetailList;
    private RecyclerView mRecyclerViewCelebKitList;
    private CelebDetailRecyclerViewAdapter mCelebRecyclerViewAdapter;
    private HabitKitRecyclerViewAdapter    mHabitKitRecyclerViewAdapter;
    private HabitRespository mHabitRespository;
    private List<CelebHabitDetail> mCelebHabitDetailList;
    private List<CelebHabitKit>    mCelebHabitKits;
    private CelebHabitMaster mCelebHabitMaster;

    private String startDate = null;
    private String endDate = null;

    private UserHabitRespository mUserHabitRespository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_celeb);

        componentInit();
        connectCelebDB();

        processIntent();


    }

    private void connectCelebDB(){
        mHabitRespository = new HabitRespository(getApplication());
    }

    private void disconnectDB(){
        mHabitRespository.destroyInstance();
    }

    @Override
    protected void onNewIntent(Intent intent) {
        processIntent();
    }

    private void processIntent() {
        Intent receivedIntent = getIntent();
        mCelebHabitMaster = (CelebHabitMaster) receivedIntent.getSerializableExtra("celebcode");
        int celebCode = 1;

        if(mCelebHabitMaster == null){
            finish();
        }
        readCelebDetail(mCelebHabitMaster);
//        readCelebDetail(celebCode);
    }

    @Override
    protected void onStart() {
        super.onStart();
        componentInit();

        setComponent();
    }

    private void setComponent() {

        Log.d(TAG, "????" + Integer.toString(mCelebHabitMaster.getDrawable()));
        imageViewTitle.setImageResource(mCelebHabitMaster.getDrawabledetail());

        textViewTitle.setText(mCelebHabitMaster.getTitle());
        textViewSubTitle.setText(mCelebHabitMaster.getTitle());

        Log.d(TAG, "mCelebHabitDetailList size " + mCelebHabitDetailList.size());

        mCelebRecyclerViewAdapter.setAllHabit(mCelebHabitDetailList);
        mHabitKitRecyclerViewAdapter.setAllHabit(mCelebHabitKits);

    }
    private void readCelebDetail(CelebHabitMaster celeb){
        Log.d(TAG, "readCelebDetail "+Integer.toString(celeb.getCelebcode()));
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        executorService.isTerminated();
        mCelebHabitDetailList = mHabitRespository.getCelebHabit(celeb.getCelebcode());
//        mCelebHabitMaster     =  mHabitRespository.getCelebHabitMater(celebCode);

        mCelebHabitKits = mHabitRespository.getHabitKit(celeb.getCelebcode());

        disconnectDB();
        if(mCelebHabitDetailList == null) {
            finish();
        }
        if(mCelebHabitMaster == null) {
            finish();
        }
        if(mCelebHabitKits == null) {
            finish();
        }
    }
/*
    private void readCelebDetail(int celeb){
        Log.d(TAG, "readCelebDetail "+Integer.toString(celeb));
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        executorService.isTerminated();
        mCelebHabitDetailList = mHabitRespository.getCelebHabit(celeb);
        mCelebHabitMaster     =  mHabitRespository.getCelebHabitMater(celeb);

        mCelebHabitKits = mHabitRespository.getHabitKit(celeb);

        disconnectDB();
        if(mCelebHabitDetailList == null) {
            finish();
        }
*//*        if(mCelebHabitMaster == null) {
            finish();
        }*//*
        if(mCelebHabitKits == null) {
            finish();
        }
    }*/

    private void componentInit(){
        // Toolbar 설정
        Toolbar myToolbar = (Toolbar) findViewById(R.id.toolbar_celeb);
        setSupportActionBar(myToolbar);

        // 액션바 뒤로가기 버튼
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayShowTitleEnabled(false);
        actionBar.setHomeAsUpIndicator(R.drawable.ic_chevron_left);


        // 헤더 부분
        imageViewTitle = (ImageView)findViewById(R.id.image_celeb_title);
        textViewTitle = (TextView)findViewById(R.id.textview_celeb_title);

        // subtitle 부분
        textViewSubTitle = (TextView)findViewById(R.id.textview_celeb_subtitle);
        textViewSubTitle2 = (TextView)findViewById(R.id.textview_celeb_subtitle2);

        mCelebRecyclerViewAdapter = new CelebDetailRecyclerViewAdapter();
        mRecyclerViewCelebDetailList = (RecyclerView)findViewById(R.id.recyclerview_celeb_detail_list);

        mRecyclerViewCelebDetailList.setAdapter(mCelebRecyclerViewAdapter);
        mRecyclerViewCelebDetailList.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerViewCelebDetailList.setNestedScrollingEnabled(false);

        mHabitKitRecyclerViewAdapter = new HabitKitRecyclerViewAdapter();
        mRecyclerViewCelebKitList = (RecyclerView)findViewById(R.id.recyclerview_celeb_kit_list);

        mRecyclerViewCelebKitList.setAdapter(mHabitKitRecyclerViewAdapter);
        mRecyclerViewCelebKitList.setLayoutManager(new GridLayoutManager(this,2));
        mRecyclerViewCelebKitList.setNestedScrollingEnabled(false);

        // 다짐 입력
        mHintTextView = (TextView)findViewById(R.id.textview_resoution_hint);
        editTextResolution = (EditText)findViewById(R.id.edittext_celeb_resoution);
        mBtnResolution = (Button)findViewById(R.id.btn_celeb_resolution);
        isCompleteResolution = false;
        // 아래 힌트 보이기
        editTextResolution.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {

                Log.d(TAG, "여기 onFocusChange "+((TextView)v).getText());
                if(hasFocus) {
                    mHintTextView.setVisibility(View.VISIBLE);
                }else{
                    mHintTextView.setVisibility(View.INVISIBLE);
                }
            }
        });

        // 글자 수 25자 초과시 빨간 밑줄 and 힌트 빨간색으로
        editTextResolution.addTextChangedListener(new TextWatcher() {
            String strPrevious;
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                strPrevious = s.toString();
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                setEditTextColor(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {
                // 2줄 이상 못넘게
                if(editTextResolution.getLineCount() > 2){
                    editTextResolution.setText(strPrevious);
                    editTextResolution.setSelection(strPrevious.length()-1);
                }
            }
        });

        mBtnResolution.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 다짐 완료 여부
                if(!isCompleteResolution){
                    if(editTextResolution.getText().length() <1) {
                        comfirmResolutionDialog();
                        return;
                    }
                    // 다짐 완료시
                    //  1. editTextResolution 초기 색으로 fix
                    //  2. editTextResolution 수정 안됨
                    //  3. 완료 여부 isCompleteResolution true
                    editTextResolution.setBackgroundResource(R.drawable.drawable_edittext_01_none);
                    editTextResolution.setEnabled(false);
                    isCompleteResolution = true;
                    mHintTextView.setTextColor(getResources().getColor(R.color.hintTextColor));
                    mHintTextView.setVisibility(View.INVISIBLE);
                    mBtnResolution.setBackgroundResource(R.drawable.drawable_button_outlined_face);
                    mBtnResolution.setText(getResources().getText(R.string.modify_resolution));
                    mBtnResolution.setTextColor(getResources().getColor(R.color.button02ContainedHold));
                }else{
                    // 다짐 수정시
                    //  1. editTextResolution 기본색으로 변경(클릭시 배경 흰줄로 바뀌는 색)
                    //  2. editTextResolution 수정 가능
                    //  3. 완료 여부 isCompleteResolution false
                    editTextResolution.setEnabled(true);
                    editTextResolution.setTextColor(getResources().getColor(R.color.black));
                    isCompleteResolution = false;
                    mHintTextView.setTextColor(getResources().getColor(R.color.hintTextColor));
                    mHintTextView.setVisibility(View.VISIBLE);
                    // 글자수에 따라서 색 변경
                    setEditTextColor(editTextResolution.getText().toString());
                    mBtnResolution.setText(getResources().getText(R.string.complite_resolution));
                    mBtnResolution.setBackgroundColor(getResources().getColor(R.color.button02Contained));
                }
            }
        });
    }

    private void setEditTextColor(String s){
        if(s.length() > 23){
            editTextResolution.setBackgroundResource(R.drawable.drawable_edittext_01_error);
            mHintTextView.setTextColor(getResources().getColor(R.color.errorrColor));
        }else{
            editTextResolution.setBackgroundResource(R.drawable.drawable_edittext_01);
            mHintTextView.setTextColor(getResources().getColor(R.color.hintTextColor));
        }
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
        /*Intent intent = new Intent(this, MainActivity.class);
        //setResult(RESULT_CANCELED, intent);
        startActivity(intent);*/
        finish();
    }

    public void onClickSave(View v){
        String strResolution = editTextResolution.getText().toString();


        if(!isCompleteResolution){

            comfirmResolutionDialog();

/*
            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);

            // 제목셋팅
            alertDialogBuilder.setTitle(getResources().getText(R.string.alert_resolution_title));

            // AlertDialog 셋팅
            alertDialogBuilder
                    .setMessage(getResources().getText(R.string.alert_resolution))
                    .setCancelable(false)
                    .setPositiveButton("확인", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            editTextResolution.requestFocus();
                            dialog.dismiss();
                        }
                    });
                    *//*.setNegativeButton("그냥시작하기", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which){
                        }
                    });*//*

            AlertDialog alertDialog = alertDialogBuilder.create();

            alertDialog.show();*/

        }else{
            Calendar today = Calendar.getInstance();
            startDate = Integer.toString(today.get(Calendar.YEAR)) + "."
                    + Integer.toString(today.get(Calendar.MONTH) + 1) + "."
                    + Integer.toString(today.get(Calendar.DATE));
            endDate = Integer.toString(today.get(Calendar.YEAR)) + "."
                    + Integer.toString(today.get(Calendar.MONTH) + 1) + "."
                    + Integer.toString(today.get(Calendar.DATE) + 7);

            myCustomDialog();
        }
    }

    private void setHabit() {

        List<UserHabitDetail> userHabitDetailList  = new ArrayList<>();
        List<UserHabitState> userHabitStateList  = new ArrayList<>();
        int userDetailIdx = 0;
        int userStateseq = 0 ;

        // 시간별
        for(int i = 0 ; i < 4 ;i++) {

            List<CelebHabitDetail> celebTimeTmp  = new ArrayList<>();
            for(int k = 0 ; k < mCelebHabitDetailList.size() ; k ++){
                CelebHabitDetail tmp = mCelebHabitDetailList.get(k);
                if(tmp.getTime() == i){
                    celebTimeTmp.add(tmp);
                }
            }

            for (int j = 0; j < celebTimeTmp.size(); j++) {
                //user detail 습관 넣기
                CelebHabitDetail tmp = celebTimeTmp.get(j);

                if (tmp == null)
                    continue;

//                Log.d(TAG, "for j "+ Integer.toString(j) );
                userDetailIdx++;
                UserHabitDetail usrtmp = new UserHabitDetail(userDetailIdx, tmp);
                userHabitDetailList.add(usrtmp);
//                Log.d(TAG,   usrtmp.getTime() +"/"+ usrtmp.getHabitcode() +"/"+usrtmp.getName() +"/"+ usrtmp.getGoal() +"/"+ usrtmp.getDaysum() +"/"+ usrtmp.getFull() +"/"+ usrtmp.getUnit() );

                // user state 습관 넣기
                for (int dayofweek = 1; dayofweek < 8; dayofweek++) {
                    //Log.d(TAG, "for k "+ Integer.toString(k) );

                    if ((tmp.getDaysum() & (1 << dayofweek)) > 0) {
                        userStateseq++;
                        UserHabitState statetmp = new UserHabitState(userStateseq, dayofweek, usrtmp);
                        userHabitStateList.add(statetmp);

                    }
                }
            }

            celebTimeTmp = null;

        }

//        mUserHabitRespository = new UserHabitRespository(getApplication());

        mUserHabitRespository.insertAllUserHabit(userHabitDetailList, userHabitStateList);

//        Toast.makeText(this,"저장! 완료", Toast.LENGTH_LONG).show();;

        mUserHabitRespository.destroyInstance();

//        startDate + "~" + endDate
        if(startDate == null || endDate == null){

            Calendar today = Calendar.getInstance();
            startDate = Integer.toString(today.get(Calendar.YEAR)) + "."
                    + Integer.toString(today.get(Calendar.MONTH) + 1) + "."
                    + Integer.toString(today.get(Calendar.DATE));
            endDate = Integer.toString(today.get(Calendar.YEAR)) + "."
                    + Integer.toString(today.get(Calendar.MONTH) + 1) + "."
                    + Integer.toString(today.get(Calendar.DATE) + 7);
        }
        UserSettingValue userSettingValue = new UserSettingValue(getApplicationContext());
        userSettingValue.setStartDate(startDate);
        userSettingValue.setEndDate(endDate);
        userSettingValue.setMainImgResource(mCelebHabitMaster.getDrawable());
        userSettingValue.setResolutionValue(editTextResolution.getText().toString());
        Intent intent = new Intent();
        setResult(MainActivity.GET_CELEB_HABIT, intent);
        finish();
    }


    public void myCustomDialog(){
        MyDialog = new Dialog(CelebActivity.this);
        MyDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        MyDialog.setContentView(R.layout.dialog_habit_start);
        MyDialog.setTitle("My Custom Dialog");


        Button btnStart, btnCancel;
        ImageView imgviewAlert;
        TextView textviewTitle, textviewDate;

        btnStart = (Button)MyDialog.findViewById(R.id.btn_alert_start);
        btnCancel = (Button)MyDialog.findViewById(R.id.btn_alert_cancel);

        imgviewAlert =(ImageView)MyDialog.findViewById(R.id.imageview_alert);
        textviewTitle    = (TextView)MyDialog.findViewById(R.id.textview_alert_title);
        textviewDate = (TextView)MyDialog.findViewById(R.id.textview_alert_date);

        Log.d(TAG, " 어디갔어 ????" + Integer.toString(mCelebHabitMaster.getDrawable()));

        imgviewAlert.setImageResource(mCelebHabitMaster.getDrawable());
        textviewTitle.setText(editTextResolution.getText());
        textviewDate.setText(startDate + "~" + endDate);


        btnStart.setEnabled(true);
        btnCancel.setEnabled(true);

        btnStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MyDialog.dismiss();
                comfirmHabit();
            }
        });

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MyDialog.dismiss();
//                Toast.makeText(getApplication(), "Close", Toast.LENGTH_SHORT).show();
            }
        });

        MyDialog.show();
    }
    public void comfirmResolutionDialog(){
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);

        // 제목셋팅
        alertDialogBuilder.setTitle(getResources().getText(R.string.alert_resolution_title));

        // AlertDialog 셋팅
        alertDialogBuilder
                .setMessage(getResources().getText(R.string.alert_resolution))
                .setCancelable(false)
                .setPositiveButton("확인", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        editTextResolution.setFocusableInTouchMode(true);
                        editTextResolution.requestFocus();
                        dialog.dismiss();
                    }
                });
                    /*.setNegativeButton("그냥시작하기", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which){
                        }
                    });*/

        AlertDialog alertDialog = alertDialogBuilder.create();

        alertDialog.show();
    }

    private void comfirmHabit(){

        mUserHabitRespository = new UserHabitRespository(getApplication());

        if(mUserHabitRespository.getAllHabit().size() > 0){

            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);

            // 제목셋팅
            alertDialogBuilder.setTitle("이미 설정된 습관이 있습니다!");

            // AlertDialog 셋팅
            alertDialogBuilder
                    .setMessage("새롭게 습관을 시작 하시겠습니까?")
                    .setCancelable(false)
                    .setPositiveButton("네", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                            setHabit();
                        }
                    })
                    .setNegativeButton("아니요", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which){
                            dialog.dismiss();

                            mUserHabitRespository.destroyInstance();
                        }
                    });

            AlertDialog alertDialog = alertDialogBuilder.create();

            alertDialog.show();

        }else{
            setHabit();
        }

    }
}