package com.postfive.habit.view.myhabits;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.postfive.habit.R;
import com.postfive.habit.adpater.myhabit.CustomPagerAdapter;
import com.postfive.habit.adpater.myhabit.MyHabitRecyclerViewAdapter;
import com.postfive.habit.db.UserHabitDetail;
import com.postfive.habit.db.UserHabitRespository;
import com.postfive.habit.db.UserHabitState;
import com.postfive.habit.view.myhabitlist.MyHabitListActivity;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;


public class MyHabitsFragment extends Fragment implements View.OnClickListener{

    private static final String TAG = "MyHabitsFragment";
    private Button btnHabitsList;
    private Button btnTodayHabits; // 오늘 습관 보기 테스트 용 버튼 나중에 삭제할 것
    private int day = 0; // 습관 보기 테스트 용 버튼 나중에 삭제할 것
    private int time = 0; // 습관 보기 테스트 용 버튼 나중에 삭제할 것
    private UserHabitRespository mUserHabitRespository;

    ViewPager pager;

    private MyHabitRecyclerViewAdapter mMyHabitRecyclerViewAdapter;

    private List<UserHabitState> mUserHabitStatesList;
    private List<UserHabitState> mTodayCompliteHabitStatesList;
    private List<UserHabitState> mTodayPassCompliteHabitStatesList;
    private List<UserHabitState> mUserHabitStatesTomorrowList;
    private List<UserHabitState> mUserHabitStatesYesterdayList;

    CustomPagerAdapter adapter;
    public MyHabitsFragment() {
        // Required empty public constructor
    }
    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser) {
            // Refresh your fragment here
            pager.setAdapter(adapter);
        }
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
        View view = inflater.inflate(R.layout.myhabitviewpage, container, false);

//        super.onCreate(savedInstanceState);
        Log.e("Main","onCreated");
//        setContentView(R.layout.myhabitviewpage);
        pager = (ViewPager) view.findViewById(R.id.pager);
        adapter = new CustomPagerAdapter(getContext(), mUserHabitRespository );
        pager.setOffscreenPageLimit(3);
        pager.setAdapter(adapter);
        //pager.setCurrentItem(1);

//
//        mUserHabitRespository = new UserHabitRespository(getActivity().getApplication(), );


        // TODO 현재 지금
        List<UserHabitState> mUserHabitStatesList = mUserHabitRespository.getNowHabit(1);

        adapter.setData(mUserHabitStatesList);
//        // TODO 오늘 완성
//        List<UserHabitState> mTodayCompliteHabitStatesList = mUserHabitRespository.getComplite();
//
//        // TODO 오늘 놓친것
//        List<UserHabitState> mTodayPassCompliteHabitStatesList = mUserHabitRespository.getPassHabit(8);

        final TextView date_tv = (TextView) view.findViewById(R.id.date_tv);
        setDay(date_tv, 0);

        TextView prev_tv = (TextView) view.findViewById(R.id.prev_tv);
        TextView next_tv = (TextView) view.findViewById(R.id.next_tv);

        prev_tv.setOnClickListener(this);
        next_tv.setOnClickListener(this);

/*        ItemClickSupport itemClickSupport = ItemClickSupport.addTo(recyclerView).setOnItemClickListener(new ItemClickSupport.OnItemClickListener() {
            @Override
            public void onItemClicked(RecyclerView recyclerView, int position, View v) {
                // TODO 리스트 클릭시  수행

                UserHabitState tmp = mMyHabitRecyclerViewAdapter.getHabit(position);

                tmp.setDid(tmp.getDid()+tmp.getOnce());
                mMyHabitRecyclerViewAdapter.changeHabit(position, tmp);
                // update
                mUserHabitRespository.updateUserHabitState(tmp);
            }
        });*/

        pager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                switch (position){
                    case 0:
                        setDay(date_tv, 0);
                        break;
                    case 1:
                        setDay(date_tv, -1);
                        break;
                    case 2:
                        setDay(date_tv, 1);
                        break;
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        return view;
    }

    public void setDay (TextView view, int n){
        final Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DATE, n);
        Date curDay = cal.getTime();//getting date
        SimpleDateFormat formatter;
        if(n == 0)
            formatter = new SimpleDateFormat("오늘 yyyy.MM.dd");
        else if(n == 1)
            formatter = new SimpleDateFormat("내일 yyyy.MM.dd");
        else // n == 2
            formatter = new SimpleDateFormat("어제 yyyy.MM.dd");
        String date = formatter.format(curDay);
        view.setText(date);
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
        int position;

        switch(v.getId()){
            case R.id.prev_tv:
                position = pager.getCurrentItem();
                pager.setCurrentItem(position - 1, true);

                break;
            case R.id.next_tv:
                position = pager.getCurrentItem();
                pager.setCurrentItem(position + 1, true);
                break;
//
//            case R.id.btn_my_habits_ist:
//                Intent intent = new Intent(getContext(), MyHabitListActivity.class);
//                startActivity(intent);
//                break;
//            case R.id.btn_today :   // 오늘 습관 보기 테스트 용 버튼 나중에 삭제할 것
//
//                if(day == 8)
//                    day = 1;
//
//
//                List<UserHabitDetail> userHabitDetailList = mUserHabitRespository.getAllUserHabitDetail();
//                StringBuilder sb1 = new StringBuilder();
//                sb1.append("\nD_seq | 날짜합 | 시간 |  습관코드 |  이름 | 목표 | 하루 목표 | 날짜합 | 전체 | 단위 \n");
//
//                for(int i = 0 ; i < userHabitDetailList.size(); i ++){
//                    UserHabitDetail tmp = userHabitDetailList.get(i);
//                    ///Log.d(TAG, tmp.getHabitcode() +"/"+ tmp.getPriority() +"/"+ tmp.getTime() +"/"+ tmp.getName() +"/"+ tmp.getGoal() +"/"+ tmp.getDaysum() +"/"+ tmp.getFull() +"/"+ tmp.getUnit() );
//                    sb1.append( tmp.getHabitseq() +"  | "+  tmp.getDaysum() +"  | "+tmp.getTime() +"    |    "+tmp.getHabitcode() +"    | "+ tmp.getName() +"|"+ tmp.getGoal() +"|  "+ tmp.getFull() +"   |  "+ tmp.getUnit());
//                    sb1.append("\n");
//                }
//
//                Log.d(TAG, "DB TEST SELECT USER HABIT Detail " + sb1.toString());
//                Log.d(TAG,"finish");
//
//                List<UserHabitState> userHabitStatesList = mUserHabitRespository.getDayHabit(day);
//                day ++;
//                StringBuilder sb = new StringBuilder();
//                sb.append("\n요일 | 시간 |  습관코드 | D_seq |  이름 | 목표 | 날짜합 | 전체 | 단위 \n");
//
//                for(int i = 0 ; i < userHabitStatesList.size(); i ++){
//                    UserHabitState tmp = userHabitStatesList.get(i);
//                    ///Log.d(TAG, tmp.getHabitcode() +"/"+ tmp.getPriority() +"/"+ tmp.getTime() +"/"+ tmp.getName() +"/"+ tmp.getGoal() +"/"+ tmp.getDaysum() +"/"+ tmp.getFull() +"/"+ tmp.getUnit() );
//                    sb.append( tmp.getDayofweek()+"   | "+  tmp.getTime() +" |    "+tmp.getHabitcode() +"   |   "+ tmp.getMasterseq()  +"  | "+ tmp.getName() +"|"+ tmp.getGoal() +"|  "+ tmp.getDaysum() +"  |  "+ tmp.getFull() +"   |  "+ tmp.getUnit());
//                    sb.append("\n");
//                }
//
//                Log.d(TAG, "DB TEST SELECT USER HABIT State " + sb.toString());
//                Log.d(TAG,"finish");
//
//
//                mUserHabitRespository.getNowHabit(time);
//
//                List<UserHabitState> userNowHabitStatesList = mUserHabitRespository.getDayHabit(day);
//
//                time ++;
//                if(time==4)
//                    time =0;
//                StringBuilder sb3 = new StringBuilder();
//                sb.append("\n"+Integer.toString(time)+"시간 :: 요일 | 시간 |  습관코드 | D_seq |  이름 | 목표 | 날짜합 | 전체 | 단위 \n");
//
//                for(int i = 0 ; i < userNowHabitStatesList.size(); i ++){
//                    UserHabitState tmp = userNowHabitStatesList.get(i);
//                    ///Log.d(TAG, tmp.getHabitcode() +"/"+ tmp.getPriority() +"/"+ tmp.getTime() +"/"+ tmp.getName() +"/"+ tmp.getGoal() +"/"+ tmp.getDaysum() +"/"+ tmp.getFull() +"/"+ tmp.getUnit() );
//                    sb3.append( tmp.getDayofweek()+"   | "+  tmp.getTime() +" |    "+tmp.getHabitcode() +"   |   "+ tmp.getMasterseq()  +"  | "+ tmp.getName() +"|"+ tmp.getGoal() +"|  "+ tmp.getDaysum() +"  |  "+ tmp.getFull() +"   |  "+ tmp.getUnit());
//                    sb3.append("\n");
//                }
//
//                Log.d(TAG, "DB TEST SELECT Now USER HABIT State " + sb3.toString());
//                Log.d(TAG,"finish");
//
//
//                // TODO 오늘 놓친것
//                List<UserHabitState> userPassHabitStatesList = mUserHabitRespository.getPassHabit(time);
//
//                StringBuilder sb4 = new StringBuilder();
//                sb.append("\n"+Integer.toString(time)+"시간 :: 요일 | 시간 |  습관코드 | D_seq |  이름 | 목표 | 날짜합 | 전체 | 단위 \n");
//
//                for(int i = 0 ; i < userPassHabitStatesList.size(); i ++){
//                    UserHabitState tmp = userPassHabitStatesList.get(i);
//                    ///Log.d(TAG, tmp.getHabitcode() +"/"+ tmp.getPriority() +"/"+ tmp.getTime() +"/"+ tmp.getName() +"/"+ tmp.getGoal() +"/"+ tmp.getDaysum() +"/"+ tmp.getFull() +"/"+ tmp.getUnit() );
//                    sb4.append( tmp.getDayofweek()+"   | "+  tmp.getTime() +" |    "+tmp.getHabitcode() +"   |   "+ tmp.getMasterseq()  +"  | "+ tmp.getName() +"|"+ tmp.getGoal() +"|  "+ tmp.getDaysum() +"  |  "+ tmp.getFull() +"   |  "+ tmp.getUnit());
//                    sb4.append("\n");
//                }
//
//                Log.d(TAG, "DB TEST SELECT Pass USER HABIT State " + sb4.toString());
//                Log.d(TAG,"finish");

            default:
                break;
        }
    }


}
