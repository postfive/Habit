package com.postfive.habit.view.HabitList;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.postfive.habit.ItemClickSupport;
import com.postfive.habit.R;
import com.postfive.habit.adpater.habitlist.HabitRecyclerViewAdapter;
import com.postfive.habit.db.Habit;
import com.postfive.habit.db.HabitViewModel;

import java.util.ArrayList;
import java.util.List;

public class HabitListActivity extends AppCompatActivity {

    static final String TAG ="HabitListActivity";

    private RecyclerView mHealthHabitRecyclerView;
    private RecyclerView mEatHabitRecyclerView;

    private HabitViewModel mHabitViewModel;


    List<Habit> mHealthHabitList = new ArrayList<>();
    List<Habit> mEatHabitList    = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_habit_list);


        // Toolbar 설정
        Toolbar myToolbar = (Toolbar) findViewById(R.id.toolbar_habit_list);
        setSupportActionBar(myToolbar);

        // 액션바 뒤로가기 버튼
        ActionBar actionBar = getSupportActionBar();

        actionBar.setDisplayShowTitleEnabled(false);
        actionBar.setDisplayHomeAsUpEnabled(true); // 뒤로가기 버튼, 디폴트로 true만 해도 백버튼이 생김
        actionBar.setHomeAsUpIndicator(R.drawable.ic_close); //뒤로가기 버튼을 본인이 만든 아이콘으로 하기 위해 필요




        mHealthHabitRecyclerView = (RecyclerView)findViewById(R.id.recyclerview_health_habit_list);
        mEatHabitRecyclerView = (RecyclerView)findViewById(R.id.recyclerview_eat_habit_list);


        // 그리드 뷰로 만들것을 정의하는 부분
        RecyclerView.LayoutManager layoutManagerHealth = new LinearLayoutManager(getApplication());
        RecyclerView.LayoutManager layoutManagerEat = new LinearLayoutManager(getApplication());
        mHealthHabitRecyclerView.setLayoutManager(layoutManagerHealth);
        mEatHabitRecyclerView.setLayoutManager(layoutManagerEat);

        // 어댑터를 연결 시켜 주는 부분
        final HabitRecyclerViewAdapter myHealthRecyclerViewAdapter = new HabitRecyclerViewAdapter();
        final HabitRecyclerViewAdapter myRecyclerViewAdapter = new HabitRecyclerViewAdapter();
        mHealthHabitRecyclerView.setAdapter(myHealthRecyclerViewAdapter);
        mEatHabitRecyclerView.setAdapter(myRecyclerViewAdapter);


        ItemClickSupport healthClickSupport = ItemClickSupport.addTo(mHealthHabitRecyclerView).setOnItemClickListener(new ItemClickSupport.OnItemClickListener() {
            @Override
            public void onItemClicked(RecyclerView recyclerView, int position, View v) {
                Habit habit = myHealthRecyclerViewAdapter.getHabit(position);
                Toast.makeText(getApplication(), "건강 클릭", Toast.LENGTH_SHORT).show();
            }
        });
        ItemClickSupport eatClickSupport = ItemClickSupport.addTo(mEatHabitRecyclerView).setOnItemClickListener(new ItemClickSupport.OnItemClickListener() {
            @Override
            public void onItemClicked(RecyclerView recyclerView, int position, View v) {
                Habit habit = myHealthRecyclerViewAdapter.getHabit(position);
                Toast.makeText(getApplication(), "먹는거 클릭", Toast.LENGTH_SHORT).show();
            }
        });


        mHabitViewModel =  ViewModelProviders.of(this).get(HabitViewModel.class);
        mHabitViewModel.getAllHabitLive().observe(this, new Observer<List<Habit>>() {
                    @Override
                    public void onChanged(@Nullable List<Habit> habits) {

                        Log.d(TAG, "Habit "+habits.size());

                        for(Habit tmp : habits){
                            Log.d(TAG, "Habit "+tmp.getName());
                            if(tmp.getCategory() == Habit.HEALTH_CATEGORY){
                                mHealthHabitList.add(tmp);
                            } else if (tmp.getCategory() == Habit.EAT_CATEGORY){
                                mEatHabitList.add(tmp);
                            }
                        }

                        myHealthRecyclerViewAdapter.setAllHabit(mHealthHabitList);
                        myRecyclerViewAdapter.setAllHabit(mEatHabitList);
                    }
                }
        );
    }

    /* 커스텀 툴바 이용
    https://stablekernel.com/using-custom-views-as-menu-items/
     */

    /* toolbar 붙이기 */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu items for use in the action bar
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_habit_list_appbar, menu);


        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {

        final MenuItem alertMenuItem = menu.findItem(R.id.action_select);
        FrameLayout rootView = (FrameLayout) alertMenuItem.getActionView();

        TextView habitSelect = (TextView)rootView.findViewById(R.id.btn_habit_select);
        habitSelect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplication(),"선택완료", Toast.LENGTH_SHORT).show();

            }
        });

        return super.onPrepareOptionsMenu(menu);
    }


    /* toolbar, action bar 버튼 클릭 이벤트 */
    public boolean onOptionsItemSelected(android.view.MenuItem item) {

        Toast.makeText(this,"앵 왜 안나옴 ", Toast.LENGTH_SHORT).show();
        switch (item.getItemId()) {
            case android.R.id.home:
                onClickClose();
                return true;
            case R.id.action_select:
//                onClickSelect();
                return true;
            default :
                return super.onOptionsItemSelected(item);

        }
    }




    private void onClickClose(){
        Intent result = new Intent();
        setResult(RESULT_CANCELED, result);
        finish();
    }
    private void onClickSelect(){
        Intent result = new Intent();

        setResult(RESULT_OK, result);
        finish();
    }
}
