package com.postfive.habit.view.HabitList;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;

import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;

import com.postfive.habit.ItemClickSupport;
import com.postfive.habit.R;
import com.postfive.habit.adpater.habitlist.HabitRecyclerViewAdapter;
import com.postfive.habit.habits.HabitMaker;
import com.postfive.habit.habits.factory.Habit;
import com.postfive.habit.view.habit.HabitActivity;

import java.util.ArrayList;
import java.util.List;

public class HabitListActivity extends AppCompatActivity {

    static final String TAG ="HabitListActivity";

    RecyclerView mRecyclerView;
    LinearLayout mCustomLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_habit_list);


        // Toolbar 설정
        Toolbar myToolbar = (Toolbar) findViewById(R.id.toolbar_habit_list);
        setSupportActionBar(myToolbar);

        // 액션바 뒤로가기 버튼
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayShowTitleEnabled(false);

        List<Habit> habitList= new ArrayList<>();
        HabitMaker habitMaker = new HabitMaker();
        for(int i = 1 ; i < 4 ; i ++) {
            Habit habit = habitMaker.createHabit(i);
            habit.prepare();
            habitList.add(habit);
        }

        mRecyclerView = (RecyclerView)findViewById(R.id.recyclerview_habit_list);
        mCustomLayout =  (LinearLayout)findViewById(R.id.layout_usercustom);

        int width =getResources().getDisplayMetrics().widthPixels / 2 - 12;

        // 그리드 뷰로 만들것을 정의하는 부분
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(this,2);
        mRecyclerView.setLayoutManager(layoutManager);

        for(int i = 0 ; i < 3 ; i ++) {
            Log.d(TAG, "목표 : "+habitList.get(i).getGoal());
        }

        // 어댑터를 연결 시켜 주는 부분
        final HabitRecyclerViewAdapter myRecyclerViewAdapter = new HabitRecyclerViewAdapter(habitList, width);
        mRecyclerView.setAdapter(myRecyclerViewAdapter);


        ItemClickSupport itemClickSupport = ItemClickSupport.addTo(mRecyclerView).setOnItemClickListener(new ItemClickSupport.OnItemClickListener() {
            @Override
            public void onItemClicked(RecyclerView recyclerView, int position, View v) {
                Habit habit = myRecyclerViewAdapter.getHabit(position);
                Intent intent = new Intent(getApplicationContext(), HabitActivity.class);

                intent.putExtra("habit", habit.getType());
                startActivity(intent);
            }
        });
    }
}
