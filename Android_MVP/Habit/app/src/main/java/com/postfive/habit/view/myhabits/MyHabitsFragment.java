package com.postfive.habit.view.myhabits;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
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
import com.postfive.habit.db.Habit;
import com.postfive.habit.db.UserHabitDetail;
import com.postfive.habit.db.UserHabitRespository;
import com.postfive.habit.db.UserHabitState;
import com.postfive.habit.view.myhabitlist.MyHabitListActivity;

import java.util.Calendar;
import java.util.List;


public class MyHabitsFragment extends Fragment implements View.OnClickListener{

    private static final String TAG = "MyHabitsFragment";
    private Button btnHabitsList;
    private Button btnTodayHabits; // 오늘 습관 보기 테스트 용 버튼 나중에 삭제할 것
    private int day = 0; // 습관 보기 테스트 용 버튼 나중에 삭제할 것
    private int time = 0; // 습관 보기 테스트 용 버튼 나중에 삭제할 것
    private UserHabitRespository mUserHabitRespository;

    private MyHabitRecyclerViewAdapter mMyHabitRecyclerViewAdapter;

    private List<UserHabitState> mUserHabitStatesList;
    private List<UserHabitState> mTodayCompliteHabitStatesList;
    private List<UserHabitState> mTodayPassCompliteHabitStatesList;
    private List<UserHabitState> mUserHabitStatesTomorrowList;
    private List<UserHabitState> mUserHabitStatesYesterdayList;

    public MyHabitsFragment() {
        // Required empty public constructor
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
//        Toast.makeText(context, "onAttach", Toast.LENGTH_SHORT).show();

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        Toast.makeText(getContext(), "onCreate", Toast.LENGTH_SHORT).show();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

//        Toast.makeText(getContext(), "onCreateView", Toast.LENGTH_SHORT).show();

        mUserHabitRespository = new UserHabitRespository(getActivity().getApplication());
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

        // 습관 가져오기 시작 ///////////////////////////////////////////////////////////////////////////
        Calendar day = Calendar.getInstance();
        // 오늘 습관 가져오기
        List<UserHabitState> userHabitStatesList = mUserHabitRespository.getDayHabit(day.get(Calendar.DAY_OF_WEEK));
        // 오늘 요일
        int today = day.get(Calendar.DAY_OF_WEEK);

        int nowTime = Habit.ALLDAY_TIME;
        // TODO 현재 지금
        mUserHabitStatesList = mUserHabitRespository.getNowHabit(nowTime);

        // TODO 오늘 완성
        mTodayCompliteHabitStatesList = mUserHabitRespository.getComplite();

        // TODO 오늘 놓친것
        mTodayPassCompliteHabitStatesList = mUserHabitRespository.getPassHabit(nowTime);

        ///github update test

        // TODO 내일 습관
        int tomorrow = today +1;
        if(tomorrow > 7)
            tomorrow =1 ;
        mUserHabitStatesTomorrowList = mUserHabitRespository.getDayHabit(tomorrow);
        // TODO 어제 습관
        int yesterday = today - 1;
        if(yesterday < 1)
            yesterday = 7 ;
        mUserHabitStatesYesterdayList = mUserHabitRespository.getDayHabit(yesterday);
        // 습관 가져오기 종료 ///////////////////////////////////////////////////////////////////////////


        mMyHabitRecyclerViewAdapter.setAllHabit(mUserHabitStatesList);

        ItemClickSupport itemClickSupport = ItemClickSupport.addTo(recyclerView).setOnItemClickListener(new ItemClickSupport.OnItemClickListener() {
            @Override
            public void onItemClicked(RecyclerView recyclerView, int position, View v) {
                // TODO 리스트 클릭시  수행

                UserHabitState tmp = mMyHabitRecyclerViewAdapter.getHabit(position);

                tmp.setDid(tmp.getDid()+tmp.getOnce());
                mMyHabitRecyclerViewAdapter.changeHabit(position, tmp);
                // update
                mUserHabitRespository.updateUserHabitState(tmp);
            }
        });

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        //UI 변경 작업 가능
//        Toast.makeText(getContext(), "onActivityCreated", Toast.LENGTH_SHORT).show();
    }
    @Override
    public void onStart() {
        super.onStart();
        // 습관 가져오기 시작 ///////////////////////////////////////////////////////////////////////////
/*        Calendar day = Calendar.getInstance();
        // 오늘 요일
        int today = day.get(Calendar.DAY_OF_WEEK);
        // TODO 오늘 습관 가져오기
        mUserHabitStatesList = mUserHabitRespository.getDayHabit(today);
        // TODO 내일 습관
        int tomorrow = today + 1;
        if (tomorrow > 7)
            tomorrow = 1;
        mUserHabitStatesTomorrowList = mUserHabitRespository.getDayHabit(tomorrow);
        // TODO 어제 습관
        int yesterday = today - 1;
        if (yesterday < 1)
            yesterday = 7;
        mUserHabitStatesYesterdayList = mUserHabitRespository.getDayHabit(yesterday);
        // 습관 가져오기 종료 ///////////////////////////////////////////////////////////////////////////


        mMyHabitRecyclerViewAdapter.setAllHabit(mUserHabitStatesList);*/

        // Fragment가 화면에 표시될때 사용자의 Action과 상호작용 불가
//        Toast.makeText(getContext(), "onStart", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onResume() {
        super.onResume();
        // Fragment가 화면에 완전히 표시되었을때 사용자의 Action과 상호작용 가능
//        Toast.makeText(getContext(), "onResume", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onPause() {
        super.onPause();
        // 사용자와 상호작용 중지
        /// 리소스 해제
//        Toast.makeText(getContext(), "onPause", Toast.LENGTH_SHORT).show();
        mUserHabitRespository.destroyInstance();
    }


    public void onStop() {
        super.onStop();
        // 현재 프래그먼트가 안보일때 다른 프래그먼트가 보일때
//        Toast.makeText(getContext(), "onStop", Toast.LENGTH_SHORT).show();

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        // 리소스 해제
        // backstack을 사용했다면 다시 돌아올때 onCreateView 호출
//        Toast.makeText(getContext(), "onDestroyView", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        // Activity와 동일 Fragment가 사랄질때 호출
        // Bundle 상태를 저장할 수 있도록 호출
//        Toast.makeText(getContext(), "onSaveInstanceState", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.btn_my_habits_ist:
                Intent intent = new Intent(getContext(), MyHabitListActivity.class);
                startActivity(intent);
                break;
            case R.id.btn_today :   // 오늘 습관 보기 테스트 용 버튼 나중에 삭제할 것

                if(day == 8)
                    day = 1;


                List<UserHabitDetail> userHabitDetailList = mUserHabitRespository.getAllUserHabitDetail();
                StringBuilder sb1 = new StringBuilder();
                sb1.append("\nD_seq | 날짜합 | 시간 |  습관코드 |  이름 | 목표 | 하루 목표 | 날짜합 | 전체 | 단위 \n");

                for(int i = 0 ; i < userHabitDetailList.size(); i ++){
                    UserHabitDetail tmp = userHabitDetailList.get(i);
                    ///Log.d(TAG, tmp.getHabitcode() +"/"+ tmp.getPriority() +"/"+ tmp.getTime() +"/"+ tmp.getName() +"/"+ tmp.getGoal() +"/"+ tmp.getDaysum() +"/"+ tmp.getFull() +"/"+ tmp.getUnit() );
                    sb1.append( tmp.getHabitseq() +"  | "+  tmp.getDaysum() +"  | "+tmp.getTime() +"    |    "+tmp.getHabitcode() +"    | "+ tmp.getName() +"|"+ tmp.getGoal() +"|  "+ tmp.getFull() +"   |  "+ tmp.getUnit());
                    sb1.append("\n");
                }

                Log.d(TAG, "DB TEST SELECT USER HABIT Detail " + sb1.toString());
                Log.d(TAG,"finish");

                List<UserHabitState> userHabitStatesList = mUserHabitRespository.getDayHabit(day);
                day ++;
                StringBuilder sb = new StringBuilder();
                sb.append("\n요일 | 시간 |  습관코드 | D_seq |  이름 | 목표 | 날짜합 | 전체 | 단위 \n");

                for(int i = 0 ; i < userHabitStatesList.size(); i ++){
                    UserHabitState tmp = userHabitStatesList.get(i);
                    ///Log.d(TAG, tmp.getHabitcode() +"/"+ tmp.getPriority() +"/"+ tmp.getTime() +"/"+ tmp.getName() +"/"+ tmp.getGoal() +"/"+ tmp.getDaysum() +"/"+ tmp.getFull() +"/"+ tmp.getUnit() );
                    sb.append( tmp.getDayofweek()+"   | "+  tmp.getTime() +" |    "+tmp.getHabitcode() +"   |   "+ tmp.getMasterseq()  +"  | "+ tmp.getName() +"|"+ tmp.getGoal() +"|  "+ tmp.getDaysum() +"  |  "+ tmp.getFull() +"   |  "+ tmp.getUnit());
                    sb.append("\n");
                }

                Log.d(TAG, "DB TEST SELECT USER HABIT State " + sb.toString());
                Log.d(TAG,"finish");


                mUserHabitRespository.getNowHabit(time);

                List<UserHabitState> userNowHabitStatesList = mUserHabitRespository.getDayHabit(day);

                time ++;
                if(time==4)
                    time =0;
                StringBuilder sb3 = new StringBuilder();
                sb.append("\n"+Integer.toString(time)+"시간 :: 요일 | 시간 |  습관코드 | D_seq |  이름 | 목표 | 날짜합 | 전체 | 단위 \n");

                for(int i = 0 ; i < userNowHabitStatesList.size(); i ++){
                    UserHabitState tmp = userNowHabitStatesList.get(i);
                    ///Log.d(TAG, tmp.getHabitcode() +"/"+ tmp.getPriority() +"/"+ tmp.getTime() +"/"+ tmp.getName() +"/"+ tmp.getGoal() +"/"+ tmp.getDaysum() +"/"+ tmp.getFull() +"/"+ tmp.getUnit() );
                    sb3.append( tmp.getDayofweek()+"   | "+  tmp.getTime() +" |    "+tmp.getHabitcode() +"   |   "+ tmp.getMasterseq()  +"  | "+ tmp.getName() +"|"+ tmp.getGoal() +"|  "+ tmp.getDaysum() +"  |  "+ tmp.getFull() +"   |  "+ tmp.getUnit());
                    sb3.append("\n");
                }

                Log.d(TAG, "DB TEST SELECT Now USER HABIT State " + sb3.toString());
                Log.d(TAG,"finish");


                // TODO 오늘 놓친것
                List<UserHabitState> userPassHabitStatesList = mUserHabitRespository.getPassHabit(time);

                StringBuilder sb4 = new StringBuilder();
                sb.append("\n"+Integer.toString(time)+"시간 :: 요일 | 시간 |  습관코드 | D_seq |  이름 | 목표 | 날짜합 | 전체 | 단위 \n");

                for(int i = 0 ; i < userPassHabitStatesList.size(); i ++){
                    UserHabitState tmp = userPassHabitStatesList.get(i);
                    ///Log.d(TAG, tmp.getHabitcode() +"/"+ tmp.getPriority() +"/"+ tmp.getTime() +"/"+ tmp.getName() +"/"+ tmp.getGoal() +"/"+ tmp.getDaysum() +"/"+ tmp.getFull() +"/"+ tmp.getUnit() );
                    sb4.append( tmp.getDayofweek()+"   | "+  tmp.getTime() +" |    "+tmp.getHabitcode() +"   |   "+ tmp.getMasterseq()  +"  | "+ tmp.getName() +"|"+ tmp.getGoal() +"|  "+ tmp.getDaysum() +"  |  "+ tmp.getFull() +"   |  "+ tmp.getUnit());
                    sb4.append("\n");
                }

                Log.d(TAG, "DB TEST SELECT Pass USER HABIT State " + sb4.toString());
                Log.d(TAG,"finish");

            default:
                break;
        }
    }


}
