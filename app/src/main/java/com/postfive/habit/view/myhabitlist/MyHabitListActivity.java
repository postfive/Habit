package com.postfive.habit.view.myhabitlist;

import android.arch.lifecycle.Observer;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;


import com.postfive.habit.ItemClickSupport;
import com.postfive.habit.R;
import com.postfive.habit.UserSettingValue;
import com.postfive.habit.adpater.myhabitlist.MyHabitListRecyclerViewAdapter;
import com.postfive.habit.db.UserHabitDetail;
import com.postfive.habit.db.UserHabitRespository;
import com.postfive.habit.db.UserHabitViewModel;
import com.postfive.habit.view.habit.HabitActivity;

import com.postfive.habit.view.main.MainActivity;

import java.util.List;

public class MyHabitListActivity extends AppCompatActivity implements View.OnClickListener{

    private static final String TAG = "MyHabitListActivity";
    public static final int MODIFY_CELEB_HABIT = 6001;
    private Button mBtnHait;
    private Button mBtnInitHait;
    private RecyclerView mRecyclerView;
    private MyHabitListRecyclerViewAdapter mRecyclerViewAdapter;

    private List<UserHabitDetail> mHabitList;

    private UserHabitViewModel userHabitViewModel = null;

    private UserHabitRespository userHabitRespository = null ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_habit_list);

        initComponent();

    }

    private void initComponent() {
        // Toolbar 설정
        Toolbar myToolbar = (Toolbar) findViewById(R.id.toolbar_my_habit_list);
        setSupportActionBar(myToolbar);

        // 액션바 뒤로가기 버튼
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayShowTitleEnabled(false);
        actionBar.setHomeAsUpIndicator(R.drawable.ic_chevron_left);

        mBtnHait = (Button)findViewById(R.id.btn_add_habit);
        mBtnHait.setOnClickListener(this);

        mBtnInitHait = (Button)findViewById(R.id.btn_habit_init);
        mBtnInitHait.setOnClickListener(this);

        mRecyclerView = (RecyclerView)findViewById(R.id.recyclerview_my_habit_list);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(layoutManager);

        mRecyclerViewAdapter = new MyHabitListRecyclerViewAdapter();
        mRecyclerView.setAdapter(mRecyclerViewAdapter);

        // DB ViewModelProviders  설정
//        userHabitViewModel = ViewModelProviders.of(this).get(UserHabitViewModel.class);

        // 데이터가 변경될 때 호출
//        userHabitViewModel.getAllUserHabitDetailLive().observe(this, observer);

        userHabitRespository = new UserHabitRespository(getApplication());
        mHabitList = userHabitRespository.getAllUserHabitDetail();

        ItemClickSupport itemClickSupport = ItemClickSupport.addTo(mRecyclerView).setOnItemClickListener(new ItemClickSupport.OnItemClickListener() {
            @Override
            public void onItemClicked(RecyclerView recyclerView, int position, View v) {
                UserHabitDetail habit = mRecyclerViewAdapter.getHabit(position);
                Intent intent = new Intent(getApplicationContext(), HabitActivity.class);

                intent.putExtra("object", habit);
                startActivityForResult(intent, MODIFY_CELEB_HABIT);
            }
        });
        mRecyclerViewAdapter.setAllHabit(mHabitList);

    }
    Observer observer = new Observer<List<UserHabitDetail>>() {
        @Override
        public void onChanged(@Nullable List<UserHabitDetail> userHabitDetails) {
            for(UserHabitDetail tmp : userHabitDetails)
                Log.d(TAG, "changed "+tmp.getCustomname());
            mRecyclerViewAdapter.setAllHabit(userHabitDetails);
        }
    };


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
//        Toast.makeText(this, "onRestart ", Toast.LENGTH_SHORT).show();
        Log.d(TAG, "onRestart ");
        if(userHabitRespository != null) {
            userHabitRespository = new UserHabitRespository(getApplication());
            mHabitList = userHabitRespository.getAllUserHabitDetail();
            mRecyclerViewAdapter.setAllHabit(mHabitList);

            Log.d(TAG, "userHabitRespository getAllhabit ");

            if(mHabitList.size()<1){
                alertNoHabit();
            }
        }

    }

    @Override
    protected void onStop() {
        super.onStop();
//        Toast.makeText(this, "onStop ", Toast.LENGTH_SHORT).show();
        Log.d(TAG, "onStop ");
/*        if(userHabitViewModel !=null) {
            userHabitViewModel.getAllUserHabitDetailLive().removeObserver(observer);
        }*/
        // 메모리 해제
        mHabitList = null;
        if(userHabitRespository != null) {
            userHabitRespository.destroyInstance();
            Log.d(TAG, "userHabitRespository destroyInstance ");

        }
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
                Intent intent = new Intent(this, HabitActivity.class);
                startActivity(intent);
                break;
            case R.id.btn_habit_init :
                initHabit();

            default:
                break;
        }
    }
    private void initHabit(){
        UserSettingValue userSettingValue = new UserSettingValue(this);
        userSettingValue.resetAll();

        userHabitRespository = new UserHabitRespository(getApplication());
        userHabitRespository.deleteUserHabitAll();
        userHabitRespository.destroyInstance();

        MainActivity.mainActivity.finish();
        Intent intent2 = new Intent(this, MainActivity.class);
        startActivity(intent2);
        finish();
    }


    public void alertNoHabit(){
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);

        // 제목셋팅
        alertDialogBuilder.setTitle("설정된 습관이 없어요 ㅜㅜ");

        // AlertDialog 셋팅
        alertDialogBuilder
                .setMessage("다시 시작 해보시죠?")
                .setCancelable(false)
                .setPositiveButton("확인", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        initHabit();
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

}
