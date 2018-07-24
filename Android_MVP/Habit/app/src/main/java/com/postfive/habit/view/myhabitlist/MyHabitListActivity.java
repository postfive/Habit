package com.postfive.habit.view.myhabitlist;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;


import com.postfive.habit.ItemClickSupport;
import com.postfive.habit.R;
import com.postfive.habit.adpater.myhabitlist.MyHabitListRecyclerViewAdapter;
import com.postfive.habit.db.CelebHabitDetail;
import com.postfive.habit.db.CelebHabitMaster;
import com.postfive.habit.db.Habit;
import com.postfive.habit.db.HabitRespository;
import com.postfive.habit.db.UserHabitDetail;
import com.postfive.habit.db.UserHabitRespository;
import com.postfive.habit.db.UserHabitState;
import com.postfive.habit.habits.HabitMaker;
import com.postfive.habit.habits.factory.HabitFactory;
import com.postfive.habit.view.HabitList.HabitListActivity;
import com.postfive.habit.view.habit.HabitActivity;

import com.postfive.habit.view.main.MainActivity;

import java.util.List;

public class MyHabitListActivity extends AppCompatActivity implements View.OnClickListener{

    private static final String TAG = "MyHabitListActivity";
    private Button mBtnHait;
    private RecyclerView mRecyclerView;
    private MyHabitListRecyclerViewAdapter mRecyclerViewAdapter;

    private UserHabitRespository mUserHabitRespository;
    private HabitRespository mabitRespository;

    private List<UserHabitDetail> mHabitList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_habit_list);

        initComponent();

        connectDB();
    }

    private void initComponent() {
        // Toolbar 설정
        Toolbar myToolbar = (Toolbar) findViewById(R.id.toolbar_habit_list);
        setSupportActionBar(myToolbar);

        // 액션바 뒤로가기 버튼
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayShowTitleEnabled(false);

        mBtnHait = (Button)findViewById(R.id.btn_add_habit);
        mBtnHait.setOnClickListener(this);

        mRecyclerView = (RecyclerView)findViewById(R.id.recyclerview_my_habit_list);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(layoutManager);

        mRecyclerViewAdapter = new MyHabitListRecyclerViewAdapter();
        mRecyclerView.setAdapter(mRecyclerViewAdapter);
        connectDB();
        mHabitList = mUserHabitRespository.getAllUserHabitDetail();

        mRecyclerViewAdapter.setHabit(mHabitList);
        ItemClickSupport itemClickSupport = ItemClickSupport.addTo(mRecyclerView).setOnItemClickListener(new ItemClickSupport.OnItemClickListener() {
            @Override
            public void onItemClicked(RecyclerView recyclerView, int position, View v) {
                UserHabitDetail habit = mRecyclerViewAdapter.getHabit(position);
                Intent intent = new Intent(getApplicationContext(), HabitActivity.class);

                intent.putExtra("object", habit);
                startActivity(intent);
            }
        });

//        mHabitFactory = new HabitMaker();


        disconnectDB();
    }

    @Override
    protected void onStart() {
        super.onStart();
        //Toast.makeText(this, "onStart ", Toast.LENGTH_SHORT).show();

    }

    @Override
    protected void onResume() {
        super.onResume();
        //Toast.makeText(this, "onResume ", Toast.LENGTH_SHORT).show();

    }

    @Override
    protected void onRestart() {
        super.onRestart();
        //Toast.makeText(this, "onRestart ", Toast.LENGTH_SHORT).show();
        // 다시 화면 돌아왔을때 리스트 다시 조회
        connectDB();
        mHabitList = mUserHabitRespository.getAllUserHabitDetail();
        mRecyclerViewAdapter.setAllHabit(mHabitList);
        disconnectDB();

    }

    @Override
    protected void onStop() {
        super.onStop();
        //Toast.makeText(this, "onStop ", Toast.LENGTH_SHORT).show();
        // 메모리 해제
        mHabitList = null;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //Toast.makeText(this, "onDestroy ", Toast.LENGTH_SHORT).show();

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
        startActivity(intent);
        finish();
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.btn_add_habit :
                Intent intent = new Intent(this, HabitListActivity.class);
                startActivity(intent);
                break;

            default:
                break;
        }
    }
    private void connectDB(){

        mUserHabitRespository = new UserHabitRespository(getApplication());
        // 아래 테스트 끝나면 제거
        mabitRespository = new HabitRespository(getApplication());
    }

    private void disconnectDB(){

        mUserHabitRespository.destroyInstance();

    }

    /* 확 인용 ~~~ */
    public void getAllUserD(View v)
    {
        connectDB();
        List<UserHabitDetail> detailList = mUserHabitRespository.getAllUserHabitDetail();
        Log.d(TAG,"go");

        StringBuilder sb = new StringBuilder();
        sb.append("\n순서 | 습관코드 |  시간 | 이름 | 목표 | 날짜합 | 전체 | 단위 \n");

        for(int i = 0 ; i < detailList.size(); i ++){
            UserHabitDetail tmp = detailList.get(i);
            ///Log.d(TAG, tmp.getHabitcode() +"/"+ tmp.getPriority() +"/"+ tmp.getTime() +"/"+ tmp.getName() +"/"+ tmp.getGoal() +"/"+ tmp.getDaysum() +"/"+ tmp.getFull() +"/"+ tmp.getUnit() );
//            sb.append(tmp.getHabitseq() + "    |     "+tmp.getHabitcode() +"    |    "+ tmp.getPriority() +"    |    "+ tmp.getTime() +"    |    "+ tmp.getName() +"    |    "+ tmp.getGoal() +"    |    "+ tmp.getDaysum() +"    |    "+ tmp.getFull() +"    |    "+ tmp.getUnit());
            sb.append(tmp.getHabitseq() + "    |     "+tmp.getHabitcode() +"    |     "+ tmp.getTime() +"    |    "+ tmp.getName() +"    |    "+ tmp.getGoal() +"    |    "+ tmp.getDaysum() +"    |    "+ tmp.getFull() +"    |    "+ tmp.getUnit());
            sb.append("\n");
        }

        Log.d(TAG, "DB TEST SELECT USER HABIT DETAIL " + sb.toString());
        //mRecyclerViewAdapter.addHabit(detailList.get(0));
        getAllUserS();
    }

    void getAllUserS()
    {
        List<UserHabitState> detailList = mUserHabitRespository.getAllUserHabitState();
        Log.d(TAG,"go");

        StringBuilder sb = new StringBuilder();
        sb.append("\n요일 |  순서  | 습관코드 | d_seq | 시간 | 이름 | 목표 | 날짜합 | 전체 | 단위 \n");

        for(int i = 0 ; i < detailList.size(); i ++){
            UserHabitState tmp = detailList.get(i);
            ///Log.d(TAG, tmp.getHabitcode() +"/"+ tmp.getPriority() +"/"+ tmp.getTime() +"/"+ tmp.getName() +"/"+ tmp.getGoal() +"/"+ tmp.getDaysum() +"/"+ tmp.getFull() +"/"+ tmp.getUnit() );
            sb.append(tmp.getDayofweek() +"    |     "+tmp.getHabitstateseq() + " |    "+tmp.getHabitcode() +"   |   "+ tmp.getMasterseq() +"  | "+  tmp.getTime() +"  | "+ tmp.getName() +"|"+ tmp.getGoal() +"|  "+ tmp.getDaysum() +"  |  "+ tmp.getFull() +"   |  "+ tmp.getUnit());
            sb.append("\n");
        }

        Log.d(TAG, "DB TEST SELECT USER HABIT STATE " + sb.toString());
        Log.d(TAG,"finish");
        disconnectDB();
    }



    public void getHabit(View v)
    {
        List<Habit> detailList = mabitRespository.getAllHabit();
        Log.d(TAG,"go");

        StringBuilder sb = new StringBuilder();
        for(int i = 0 ; i < detailList.size(); i ++){
            Habit tmp = detailList.get(i);
            sb.append(" / "+tmp.getHabitcode() +"/"+ tmp.getTime() +"/"+ tmp.getName() +"/"+  tmp.getDaysum() +"/"+ tmp.getFull());
            sb.append("\n");
        }

        Log.d(TAG, "DB TEST SELECT HABIT " + sb.toString());
        Log.d(TAG,"finish");
        getCelebDetail();
    }
    void getCelebDetail()
    {
        List<CelebHabitDetail> detailList = mabitRespository.getAllCelebHabitDetail();
        Log.d(TAG,"go");

        StringBuilder sb = new StringBuilder();
        for(int i = 0 ; i < detailList.size(); i ++){
            CelebHabitDetail tmp = detailList.get(i);
            sb.append(tmp.getCelebcode()+"/"+tmp.getName()+"/"+tmp.getHabitcode() +"/"+ tmp.getTime() +"/"+ tmp.getName() +"/"+  tmp.getDaysum() +"/"+ tmp.getFull());
            sb.append("\n");
        }

        Log.d(TAG, "DB TEST SELECT Celeb HABIT Detail " + sb.toString());
        Log.d(TAG,"finish");
        getCelebMaster();
    }
    void getCelebMaster()
    {
        List<CelebHabitMaster> detailList = mabitRespository.getAllCelebHabitMater();
        Log.d(TAG,"go");

        StringBuilder sb = new StringBuilder();
        for(int i = 0 ; i < detailList.size(); i ++){
            CelebHabitMaster tmp = detailList.get(i);
            sb.append(tmp.getCelebcode()+"/"+tmp.getName()+"/"+tmp.getName() +"/"+ tmp.getResolution() +"/"+ tmp.getTitle());
            sb.append("\n");
        }

        Log.d(TAG, "DB TEST SELECT Celeb HABIT Master " + sb.toString());
        Log.d(TAG,"finish");
        //
        // disconnectDB();
    }

/*
    //TODO recyclerviewadapter 에 추가
                        mRecyclerViewAdapter.addHabit(habit);
    //TODO recyclerviewadapter 에서 변경
    // mRecyclerViewAdapter.changeHabit(habit);
    //TODO recyclerviewadapter 에서 제거
    // mRecyclerViewAdapter.removeHabit(habit);*/
}
