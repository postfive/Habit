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
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.postfive.habit.ItemClickSupport;
import com.postfive.habit.R;
import com.postfive.habit.adpater.habitlist.HabitRecyclerViewAdapter;
import com.postfive.habit.db.Habit;
import com.postfive.habit.db.HabitViewModel;
import com.postfive.habit.db.UserHabitDetail;

import java.util.ArrayList;
import java.util.List;

public class HabitListActivity extends AppCompatActivity {

    static final String TAG ="HabitListActivity";

    private RecyclerView mHealthHabitRecyclerView;
    private RecyclerView mEatHabitRecyclerView;
    private RecyclerView mSocialHabitRecyclerView;
    private RecyclerView mMindHabitRecyclerView;

    private HabitViewModel mHabitViewModel;

    private Habit habit;
    private View selectedView = null; // 선택된 item;

    List<Habit> mHealthHabitList = new ArrayList<>();
    List<Habit> mEatHabitList    = new ArrayList<>();
    List<Habit> mSocialHabitList    = new ArrayList<>();
    List<Habit> mMindHabitList    = new ArrayList<>();
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

        mSocialHabitRecyclerView = (RecyclerView)findViewById(R.id.recyclerview_social_habit_list);
        mMindHabitRecyclerView = (RecyclerView)findViewById(R.id.recyclerview_mind_habit_list);


        // 그리드 뷰로 만들것을 정의하는 부분
        RecyclerView.LayoutManager layoutManagerHealth = new LinearLayoutManager(getApplication());
        RecyclerView.LayoutManager layoutManagerEat = new LinearLayoutManager(getApplication());
        RecyclerView.LayoutManager layoutManagerSocial = new LinearLayoutManager(getApplication());
        RecyclerView.LayoutManager layoutManagerMind = new LinearLayoutManager(getApplication());
        mHealthHabitRecyclerView.setLayoutManager(layoutManagerHealth);
        mEatHabitRecyclerView.setLayoutManager(layoutManagerEat);
        mSocialHabitRecyclerView.setLayoutManager(layoutManagerSocial);
        mMindHabitRecyclerView.setLayoutManager(layoutManagerMind);

        // 어댑터를 연결 시켜 주는 부분
        final HabitRecyclerViewAdapter myHealthRecyclerViewAdapter = new HabitRecyclerViewAdapter();
        final HabitRecyclerViewAdapter myEatRecyclerViewAdapter = new HabitRecyclerViewAdapter();
        final HabitRecyclerViewAdapter mySocailRecyclerViewAdapter = new HabitRecyclerViewAdapter();
        final HabitRecyclerViewAdapter myMindRecyclerViewAdapter = new HabitRecyclerViewAdapter();
        mHealthHabitRecyclerView.setAdapter(myHealthRecyclerViewAdapter);
        mEatHabitRecyclerView.setAdapter(myEatRecyclerViewAdapter);
        mSocialHabitRecyclerView.setAdapter(mySocailRecyclerViewAdapter);
        mMindHabitRecyclerView.setAdapter(myMindRecyclerViewAdapter);


        ItemClickSupport healthClickSupport = ItemClickSupport.addTo(mHealthHabitRecyclerView).setOnItemClickListener(new ItemClickSupport.OnItemClickListener() {
            @Override
            public void onItemClicked(RecyclerView recyclerView, int position, View v) {
                habit = myHealthRecyclerViewAdapter.getHabit(position);
                if(selectedView != null) {
                    selectedView.setBackgroundColor(getResources().getColor(R.color.noneHabit));
                }
                v.setBackgroundColor(getResources().getColor(R.color.selectedHabit));
                selectedView = v;
            }
        });
        ItemClickSupport eatClickSupport = ItemClickSupport.addTo(mEatHabitRecyclerView).setOnItemClickListener(new ItemClickSupport.OnItemClickListener() {
            @Override
            public void onItemClicked(RecyclerView recyclerView, int position, View v) {
                habit = myEatRecyclerViewAdapter.getHabit(position);
                if(selectedView != null) {
                    selectedView.setBackgroundColor(getResources().getColor(R.color.noneHabit));
                }
                v.setBackgroundColor(getResources().getColor(R.color.selectedHabit));
                selectedView = v;
            }
        });

        ItemClickSupport socialClickSupport = ItemClickSupport.addTo(mSocialHabitRecyclerView).setOnItemClickListener(new ItemClickSupport.OnItemClickListener() {
            @Override
            public void onItemClicked(RecyclerView recyclerView, int position, View v) {
                habit = mySocailRecyclerViewAdapter.getHabit(position);
                if(selectedView != null) {
                    selectedView.setBackgroundColor(getResources().getColor(R.color.noneHabit));
                }
                v.setBackgroundColor(getResources().getColor(R.color.selectedHabit));
                selectedView = v;
            }
        });

        ItemClickSupport mindlickSupport = ItemClickSupport.addTo(mMindHabitRecyclerView).setOnItemClickListener(new ItemClickSupport.OnItemClickListener() {
            @Override
            public void onItemClicked(RecyclerView recyclerView, int position, View v) {
                habit = myMindRecyclerViewAdapter.getHabit(position);
                if(selectedView != null) {
                    selectedView.setBackgroundColor(getResources().getColor(R.color.noneHabit));
                }
                v.setBackgroundColor(getResources().getColor(R.color.selectedHabit));
                selectedView = v;
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
                            }else if (tmp.getCategory() == Habit.SOCIAL_LIFE_CATEGORY){
                                mSocialHabitList.add(tmp);
                            }else if (tmp.getCategory() == Habit.MIND_CATEGORY){
                                mMindHabitList.add(tmp);
                            }
                        }

                        myHealthRecyclerViewAdapter.setAllHabit(mHealthHabitList);
                        myEatRecyclerViewAdapter.setAllHabit(mEatHabitList);
                        mySocailRecyclerViewAdapter.setAllHabit(mSocialHabitList);
                        myMindRecyclerViewAdapter.setAllHabit(mMindHabitList);
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

        Button habitSelect = (Button)rootView.findViewById(R.id.btn_habit_select);
        habitSelect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplication(),"선택완료", Toast.LENGTH_SHORT).show();
                onClickSelect();
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
        if(habit == null){
            return;
        }
        Intent result = new Intent();

        UserHabitDetail userHabitDetail = new UserHabitDetail(habit);

        result.putExtra("object", userHabitDetail);
        setResult(RESULT_OK, result);
        finish();
    }
}
