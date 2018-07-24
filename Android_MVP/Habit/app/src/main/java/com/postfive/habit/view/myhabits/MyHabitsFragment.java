package com.postfive.habit.view.myhabits;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.postfive.habit.ItemClickSupport;
import com.postfive.habit.R;
import com.postfive.habit.adpater.myhabit.MyHabitRecyclerViewAdapter;
import com.postfive.habit.adpater.myhabitlist.MyHabitListRecyclerViewAdapter;
import com.postfive.habit.db.UserHabitDetail;
import com.postfive.habit.db.UserHabitRespository;
import com.postfive.habit.db.UserHabitState;
import com.postfive.habit.db.UserHabitViewModel;
import com.postfive.habit.view.HabitList.HabitListActivity;
import com.postfive.habit.view.habit.HabitActivity;
import com.postfive.habit.view.myhabitlist.MyHabitListActivity;

import java.util.Calendar;
import java.util.List;


public class MyHabitsFragment extends Fragment implements View.OnClickListener{

    private static final String TAG = "MyHabitsFragment";
    private Button btnHabitsList;
    private Button btnTodayHabits;
    private UserHabitRespository mUserHabitRespository;

    private MyHabitRecyclerViewAdapter mMyHabitRecyclerViewAdapter;

    private int day_count = 0;

    public MyHabitsFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mUserHabitRespository = new UserHabitRespository(getActivity().getApplication());
        Calendar today = Calendar.getInstance();


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_my_habits, container, false);

        btnHabitsList = (Button)view.findViewById(R.id.btn_my_habits_ist);
        btnHabitsList.setOnClickListener(this);

        btnTodayHabits = (Button)view.findViewById(R.id.btn_today);
        btnTodayHabits.setOnClickListener(this);


        mMyHabitRecyclerViewAdapter = new MyHabitRecyclerViewAdapter();
        RecyclerView recyclerView = (RecyclerView)view.findViewById(R.id.recyclerview_my_habits_fragemnt_list);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setAdapter(mMyHabitRecyclerViewAdapter);
        recyclerView.setLayoutManager(layoutManager);

        Calendar day = Calendar.getInstance();


        // 오늘 습관 가져오기
        List<UserHabitState> userHabitStatesList = mUserHabitRespository.getDayHabit(day.get(Calendar.DAY_OF_WEEK));

/*
        Log.d(TAG, "요일  |  타입  |  목표  |  오늘한량 | 오늘목표량 | ");
        for(UserHabitState tmp : userHabitStatesList) {

            Log.d(TAG, "오늘 습관 "+tmp.getDayofweek()+" "+tmp.getHabitcode()+" "+tmp.getGoal() +"  " +tmp.getDid() +" "+tmp.getFull());
        }
*/

        mMyHabitRecyclerViewAdapter.setAllHabit(userHabitStatesList);
        ItemClickSupport itemClickSupport = ItemClickSupport.addTo(recyclerView).setOnItemClickListener(new ItemClickSupport.OnItemClickListener() {
            @Override
            public void onItemClicked(RecyclerView recyclerView, int position, View v) {
                UserHabitState tmp = mMyHabitRecyclerViewAdapter.getHabit(position);

                tmp.setDid(tmp.getDid()+tmp.getOnce());
                mMyHabitRecyclerViewAdapter.changeHabit(position, tmp);
                // update
                mUserHabitRespository.updateUserHabitState(tmp);
            }
        });

        /* UserHabitViewModel userHabitViewModel = ViewModelProviders.of(this).get(UserHabitViewModel.class);
        // 지금 할일
       userHabitViewModel.getTodayUserHabitStateLive().observe(this, new Observer<List<UserHabitState>>() {
            @Override
            public void onChanged(@Nullable List<UserHabitState> userHabitStates) {
                Log.d(TAG, "hi size "+ Integer.toString(userHabitStates.size()));


                mMyHabitRecyclerViewAdapter.setAllHabit(userHabitStates);
            }
        });*/

        // 다한일

        // 놓친일



        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    @Override
    public void onStop() {
        super.onStop();
        mUserHabitRespository.destroyInstance();
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.btn_my_habits_ist:
                Intent intent = new Intent(getContext(), MyHabitListActivity.class);
                startActivity(intent);
                break;
            case R.id.btn_today :
                Calendar day = Calendar.getInstance();

                List<UserHabitState> userHabitStatesList = mUserHabitRespository.getDayHabit(day.get(Calendar.DAY_OF_WEEK));

                Log.d(TAG, "요일  |  타입  |  목표  |  오늘한량 | 오늘목표량 | ");
                for(UserHabitState tmp : userHabitStatesList) {
                    Log.d(TAG, tmp.getDayofweek()+" "+tmp.getHabitcode()+" "+tmp.getGoal() +"  " +tmp.getDid() +" "+tmp.getFull());
                }
            default:
                break;
        }
    }


}
