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
        Toolbar myToolbar = (Toolbar) findViewById(R.id.toolbar_my_habit_list);
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
}
