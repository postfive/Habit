package com.postfive.habit.view.HabitList;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.postfive.habit.R;
import com.postfive.habit.adpater.habitlist.HabitRecyclerViewAdapter;
import com.postfive.habit.habits.HabitMaker;
import com.postfive.habit.habits.factory.Habit;

import java.util.ArrayList;
import java.util.List;

public class HabitListActivity extends AppCompatActivity {

    RecyclerView mRecyclerView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_habit_list);

        List<Habit> habitList= new ArrayList<>();
        HabitMaker habitMaker = new HabitMaker();
        for(int i = 0 ; i < 4 ; i ++) {
            habitList.add(habitMaker.createHabit(i));
        }

        mRecyclerView = (RecyclerView)findViewById(R.id.recyclerview_habit_list);

        // 그리드 뷰로 만들것을 정의하는 부분
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(this,2);
        mRecyclerView.setLayoutManager(layoutManager);


        // 어댑터를 연결 시켜 주는 부분
        HabitRecyclerViewAdapter myRecyclerViewAdapter = new HabitRecyclerViewAdapter(habitList);
        mRecyclerView.setAdapter(myRecyclerViewAdapter);
    }
}
