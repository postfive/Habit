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
import android.widget.Toast;

import com.postfive.habit.R;
import com.postfive.habit.adpater.celebdetaillist.celeblist.CelebDetailRecyclerViewAdapter;
import com.postfive.habit.adpater.celebdetaillist.celeblist.HabitKitRecyclerViewAdapter;
import com.postfive.habit.db.CelebHabitDetail;
import com.postfive.habit.db.CelebHabitKit;
import com.postfive.habit.db.CelebHabitMaster;
import com.postfive.habit.db.HabitRespository;
import com.postfive.habit.db.UserHabitDetail;
import com.postfive.habit.db.UserHabitRespository;
import com.postfive.habit.db.UserHabitState;

import java.util.ArrayList;
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
    private Button btnStart, btnCancel;

    private boolean isCompleteResolution;


    private RecyclerView mRecyclerViewCelebDetailList;
    private RecyclerView mRecyclerViewCelebKitList;
    private CelebDetailRecyclerViewAdapter mCelebRecyclerViewAdapter;
    private HabitKitRecyclerViewAdapter    mHabitKitRecyclerViewAdapter;
    private HabitRespository mHabitRespository;
    private List<CelebHabitDetail> mCelebHabitDetailList;
    private List<CelebHabitKit>    mCelebHabitKits;
    private CelebHabitMaster mCelebHabitMaster;


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
//        int celebCode = receivedIntent.getIntExtra("celebcode", 0);
        int celebCode = 1;

        if(celebCode == 0){
            finish();
        }
        readCelebDetail(celebCode);
    }

    @Override
    protected void onStart() {
        super.onStart();
        componentInit();

        setComponent();
    }

    private void setComponent() {

        Log.d(TAG, Integer.toString(mCelebHabitMaster.getDrawable()));
        imageViewTitle.setImageResource(mCelebHabitMaster.getDrawabledetail());

        textViewTitle.setText(mCelebHabitMaster.getTitle());
        textViewSubTitle.setText(mCelebHabitMaster.getTitle());

        Log.d(TAG, "mCelebHabitDetailList size " + mCelebHabitDetailList.size());

        mCelebRecyclerViewAdapter.setAllHabit(mCelebHabitDetailList);
        mHabitKitRecyclerViewAdapter.setAllHabit(mCelebHabitKits);

    }

    private void readCelebDetail(int celebCode){
        Log.d(TAG, "readCelebDetail "+Integer.toString(celebCode));
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        executorService.isTerminated();
        mCelebHabitDetailList = mHabitRespository.getCelebHabit(celebCode);
        mCelebHabitMaster     =  mHabitRespository.getCelebHabitMater(celebCode);

        mCelebHabitKits = mHabitRespository.getHabitKit(celebCode);

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

    private void componentInit(){
        // Toolbar 설정
        Toolbar myToolbar = (Toolbar) findViewById(R.id.toolbar_celeb);
        setSupportActionBar(myToolbar);

        // 액션바 뒤로가기 버튼
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayShowTitleEnabled(false);


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
                    // 다짐 완료시
                    //  1. editTextResolution 초기 색으로 fix
                    //  2. editTextResolution 수정 안됨
                    //  3. 완료 여부 isCompleteResolution true
                    editTextResolution.setBackgroundResource(R.drawable.drawable_edittext_01_none);
                    editTextResolution.setEnabled(false);
                    isCompleteResolution = true;
                    mHintTextView.setTextColor(getResources().getColor(R.color.hintTextColor));
                    mHintTextView.setVisibility(View.INVISIBLE);

                }else{
                    // 다짐 수정시
                    //  1. editTextResolution 기본색으로 변경(클릭시 배경 흰줄로 바뀌는 색)
                    //  2. editTextResolution 수정 가능
                    //  3. 완료 여부 isCompleteResolution false
                    editTextResolution.setBackgroundResource(R.drawable.drawable_edittext_01);
                    editTextResolution.setEnabled(true);
                    isCompleteResolution = false;
                    mHintTextView.setTextColor(getResources().getColor(R.color.hintTextColor));
                    mHintTextView.setVisibility(View.VISIBLE);
                    // 글자수에 따라서 색 변경
                    setEditTextColor(editTextResolution.getText().toString());
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


        if(strResolution.length() < 1){

            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);

            // 제목셋팅
            alertDialogBuilder.setTitle("다짐을 입력하지 않으셨습니다!");

            // AlertDialog 셋팅
            alertDialogBuilder
                    .setMessage("습관시작 전\n다짐을 입력해주세요")
                    .setCancelable(false)
                    .setPositiveButton("확인", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                            editTextResolution.requestFocus();
                        }
                    });
                    /*.setNegativeButton("그냥시작하기", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which){
                        }
                    });*/

            AlertDialog alertDialog = alertDialogBuilder.create();

            alertDialog.show();

        }else{
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

        Toast.makeText(this,"저장! 완료", Toast.LENGTH_LONG).show();;

        mUserHabitRespository.destroyInstance();

        finish();
    }


    public void myCustomDialog(){
        MyDialog = new Dialog(CelebActivity.this);
        MyDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        MyDialog.setContentView(R.layout.dialog_habit_start);
        MyDialog.setTitle("My Custom Dialog");

        btnStart = (Button)MyDialog.findViewById(R.id.btn_alert_start);
        btnCancel = (Button)MyDialog.findViewById(R.id.btn_alert_cancel);

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
