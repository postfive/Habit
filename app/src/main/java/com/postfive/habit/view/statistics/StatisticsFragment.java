package com.postfive.habit.view.statistics;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TabHost;
import android.widget.Toast;

import com.postfive.habit.R;
import com.postfive.habit.Utils;
import com.postfive.habit.adpater.celebdetaillist.celeblist.HabitKitRecyclerViewModel;
import com.postfive.habit.adpater.statistics.StatisticsRecyclerViewAdapter;
import com.postfive.habit.calendar.CalendarView;
import com.postfive.habit.db.CelebHabitMaster;
import com.postfive.habit.db.CelebHabitViewModel;
import com.postfive.habit.db.UserHabitDetail;
import com.postfive.habit.db.UserHabitRespository;
import com.postfive.habit.db.UserHabitState;
import com.postfive.habit.db.UserHabitViewModel;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashSet;
import java.util.List;


public class StatisticsFragment extends Fragment {

    private static final String TAG = "StatisticsFragment";
    private UserHabitRespository mUserHabitRespository;

    private RecyclerView mRecyclerView;
    private StatisticsRecyclerViewAdapter mRecyclerViewAdapter;
    private UserHabitViewModel userHabitViewModel;
    private List<UserHabitDetail> mHabitList;

    public StatisticsFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_statistics, container, false);

        LinearLayout linearLayoutview = (LinearLayout)view.findViewById(R.id.linearlayout_statistics);

        FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams) linearLayoutview.getLayoutParams();
        layoutParams.topMargin = Utils.getStatusBarHeight(getContext());
        linearLayoutview.setLayoutParams(layoutParams);

        /* 탭 설정 */
        TabHost tabHost1 = (TabHost) view.findViewById(R.id.tabHost1);
        tabHost1.setup();


        TabHost.TabSpec ts1 = tabHost1.newTabSpec("Tab Spec 1");
        ts1.setContent(R.id.content1);
        ts1.setIndicator("전체 통계");
        tabHost1.addTab(ts1);

        TabHost.TabSpec ts2 = tabHost1.newTabSpec("Tab Spec 2");
        ts2.setContent(R.id.content2);
        ts2.setIndicator("계별 통계");
        tabHost1.addTab(ts2);




        /* 탭1 달력 설정 */
        HashSet<Date> events = new HashSet<>();
        events.add(new Date());

        CalendarView cv = ((CalendarView)view.findViewById(R.id.calendar_view));
        cv.updateCalendar(events);

        // assign event handler
        cv.setEventHandler(new CalendarView.EventHandler()
        {
            @Override
            public void onDayLongPress(Date date)
            {
                // show returned day
                DateFormat df = SimpleDateFormat.getDateInstance();
                Toast.makeText(getActivity(), df.format(date), Toast.LENGTH_SHORT).show();
            }
        });


        /* 탭2 각각 통계 설정 */
        //        recyclerview_statistics_habit_list

        mRecyclerView = (RecyclerView)view.findViewById(R.id.recyclerview_statistics_habit_list);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
        mRecyclerView.setLayoutManager(layoutManager);

        mRecyclerViewAdapter = new StatisticsRecyclerViewAdapter(getContext());
        mRecyclerView.setAdapter(mRecyclerViewAdapter);
/*        connectDB();
        mHabitList = mUserHabitRespository.getAllUserHabitDetail();

        mRecyclerViewAdapter.setHabit(mHabitList);*/

        // DB ViewModelProviders  설정
        userHabitViewModel = ViewModelProviders.of(this).get(UserHabitViewModel.class);

        // 데이터가 변경될 때 호출
        userHabitViewModel.getHabitStatics().observe(this, observer);
//        userHabitViewModel.getHabitStaticsCalendar().observe(this, observer2);


        return view;
    }
    Observer observer = new Observer<List<HabitStatistics>>() {
        @Override
        public void onChanged(@Nullable List<HabitStatistics> habitStatistics) {
            for(HabitStatistics tmp : habitStatistics) {
                Log.d(TAG, "show "+ tmp.getName()+" "+ tmp.getDid()+" " +tmp.getGoal()+" "+ ((tmp.getDid()* 100)/tmp.getGoal()));
            }
            mRecyclerViewAdapter.setAllHabit(habitStatistics);
        }
    };
/*    Observer observer2 = new Observer<List<HabitStatisticsCalendar>>() {
        @Override
        public void onChanged(@Nullable List<HabitStatisticsCalendar> habitStatistics) {
            updateCalendar
        }
    };*/

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {

    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

    }

    @Override
    public void onDetach() {
        super.onDetach();
//        userHabitViewModel.getHabitStatics().observe(this, null);
    }


}
