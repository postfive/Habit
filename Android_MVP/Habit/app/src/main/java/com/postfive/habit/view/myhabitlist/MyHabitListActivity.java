package com.postfive.habit.view.myhabitlist;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.GenericTypeIndicator;
import com.postfive.habit.FirebaseUse;
import com.postfive.habit.ItemClickSupport;
import com.postfive.habit.R;
import com.postfive.habit.adpater.myhabitlist.MyHabitRecyclerViewAdapter;
import com.postfive.habit.habits.HabitMaker;
import com.postfive.habit.habits.factory.Habit;
import com.postfive.habit.habits.factory.HabitFactory;
import com.postfive.habit.habits.storage.DrinkWaterHabit;
import com.postfive.habit.habits.storage.PreStudyHabit;
import com.postfive.habit.habits.storage.SkipRopeHabit;
import com.postfive.habit.habits.storage.UserSetHabit;
import com.postfive.habit.view.HabitList.HabitListActivity;
import com.postfive.habit.view.habit.HabitActivity;
import com.postfive.habit.view.login.LoginActivity;
import com.postfive.habit.view.main.MainActivity;

import java.util.List;
import java.util.Map;

public class MyHabitListActivity extends AppCompatActivity implements View.OnClickListener{

    private static final String TAG = "MyHabitListActivity";
    private Button mBtnHait;
    private RecyclerView mRecyclerView;
    private MyHabitRecyclerViewAdapter mRecyclerViewAdapter;
    /* firebase */
    private FirebaseAuth mFirebaseAuth;
    private FirebaseUser mFirebaseUser;
    private FirebaseDatabase mFirebaseDatabase;
    private HabitFactory mHabitFactory;

    @Override
    protected void onStart() {
        super.onStart();

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_habit_list);

        initComponent();

        initFirebase();
        displayMyHabitList();

        Log.d(TAG, "MyHabitListActivity Create get Uid "+mFirebaseUser.getUid());


    }

    private void initComponent() {
        // Toolbar 설정
        Toolbar myToolbar = (Toolbar) findViewById(R.id.toolbar_habit_list);
        setSupportActionBar(myToolbar);

        // 액션바 뒤로가기 버튼
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayShowTitleEnabled(false);

        mBtnHait = (Button)findViewById(R.id.btn_habit);
        mBtnHait.setOnClickListener(this);

        mRecyclerView = (RecyclerView)findViewById(R.id.recyclerview_my_habit_list);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(layoutManager);

        mRecyclerViewAdapter = new MyHabitRecyclerViewAdapter();
        mRecyclerView.setAdapter(mRecyclerViewAdapter);

        ItemClickSupport itemClickSupport = ItemClickSupport.addTo(mRecyclerView).setOnItemClickListener(new ItemClickSupport.OnItemClickListener() {
            @Override
            public void onItemClicked(RecyclerView recyclerView, int position, View v) {
                Habit habit = mRecyclerViewAdapter.getHabit(position);
                Intent intent = new Intent(getApplicationContext(), HabitActivity.class);

                intent.putExtra("habit", habit.getType());
                intent.putExtra("key", habit.getKey());
                intent.putExtra("object", habit);
                startActivity(intent);
            }
        });

        mHabitFactory = new HabitMaker();
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
        finish();
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.btn_habit :
                Intent intent = new Intent(this, HabitListActivity.class);
                startActivity(intent);
                break;
            default:
                break;
        }
    }

    private void initFirebase(){

        mFirebaseUser = FirebaseUse.getUser();

        if(mFirebaseUser == null){
            Intent intent = new Intent(this, LoginActivity.class);
            startActivity(intent);
            finish();
        }
        mFirebaseDatabase = FirebaseUse.getDatabase();
    }

    private void displayMyHabitList(){

        mFirebaseDatabase.getReference("user_habit_d/"+mFirebaseUser.getUid())
                .addChildEventListener(new ChildEventListener() {
                    @Override
                    public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                        // 추가 됬을때
/////////////// 나중에 함수로 묶자
                        String key = dataSnapshot.getKey();

                        Map<String, Object> map = (Map<String, Object>) dataSnapshot.getValue();

//                        mHabitFactory.createHabit();
                        String strType = map.get("type").toString();
                        Habit habit = mHabitFactory.createHabit(strType);
                        habit.prepare();
                        habit.setGoal(map.get("goal").toString());
                        habit.setKey(key);
                        habit.setOnce(Integer.parseInt(map.get("once").toString()));
                        habit.setFull(Integer.parseInt(map.get("full").toString()));
                        habit.setDayFull(Integer.parseInt(map.get("dayFull").toString()));
                        habit.setDidDay(Integer.parseInt(map.get("didDay").toString()));
                        habit.setDayofWeek(Integer.parseInt(map.get("dayofWeek").toString()));
                        habit.setUnit(map.get("unit").toString());
                        List<String> timeList = (List<String>)map.get("time");
                        for(String tmp : timeList){
                            if(tmp == null)
                                continue;
                            habit.setTime(tmp);
                            Log.d(TAG, tmp);
                        }
/////////////////// 나중에 함수로 묶자

//                        //TODO recyclerviewadapter 에 추가
                        mRecyclerViewAdapter.addHabit(habit);
                    }

                    @Override
                    public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                        // 변경 됬을때
                        Habit habit = null;

                        Map<String, String> map = (Map<String, String>) dataSnapshot.getValue();
                        String strType =  map.get("type");
                       // habit = getHabitData(strType, dataSnapshot);

                        //TODO recyclerviewadapter 에서 변경
                       // mRecyclerViewAdapter.changeHabit(habit);
                    }

                    @Override
                    public void onChildRemoved(DataSnapshot dataSnapshot) {
                        Toast.makeText(MyHabitListActivity.this
                                ,"삭제",Toast.LENGTH_LONG).show();
                        Habit habit = null;

                        Map<String, String> map = (Map<String, String>) dataSnapshot.getValue();
                        String strType =  map.get("type");
                    //    habit = getHabitData(strType, dataSnapshot);

                        //TODO recyclerviewadapter 에서 제거
                       // mRecyclerViewAdapter.removeHabit(habit);
                    }

                    @Override
                    public void onChildMoved(DataSnapshot dataSnapshot, String s) {

                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        // 읽기 취소 됬을때, 데이터 읽을 권한이 없을때
                        //databaseError.toException()
                    }
                });
    }


    private Habit getHabitData(String strType, DataSnapshot dataSnapshot) {
        Habit habit = null;
        if(strType.equals("drinkwater")){
            habit = dataSnapshot.getValue(DrinkWaterHabit.class);
        }else if (strType.equals("skiprope")){
            habit = dataSnapshot.getValue(SkipRopeHabit.class);
        }else if (strType.equals("prestudy")) {
            habit = dataSnapshot.getValue(PreStudyHabit.class);
        }else {
            habit = dataSnapshot.getValue(UserSetHabit.class);
        }

        return habit;
    }
}
