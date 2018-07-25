package com.postfive.habit.adpater.myhabit;

import android.content.Context;
import android.content.Intent;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.dd.processbutton.iml.SubmitProcessButton;
import com.postfive.habit.R;
import com.postfive.habit.db.Habit;
import com.postfive.habit.db.UserHabitRespository;
import com.postfive.habit.db.UserHabitState;
import com.postfive.habit.view.habit.HabitActivity;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class CustomPagerAdapter extends PagerAdapter {

    private Context mContext;
    ViewPager pager;
    ArrayList<Integer> wrap_id = new ArrayList<>();
    ArrayList<Integer> btn_id = new ArrayList<>();
    ArrayList<View> mViews = new ArrayList<>();

    List<List<UserHabitState>> days = new ArrayList<>();
    ArrayList<UserHabitState> day = null;

    UserHabitRespository mUserHabitRespository;
    private String[] strArryDayofWeek = {"일", "월", "화", "수", "목", "금", "토"};

    View tempV;
    TextView tempTv;
    SubmitProcessButton tempPi; // Progress Indicator

    int child_id = 0;
    ViewGroup layout;

    public CustomPagerAdapter(Context context, UserHabitRespository mUserHabitRespository) {
        mContext = context;
        this.mUserHabitRespository = mUserHabitRespository;
    }

    @Override
    public Object instantiateItem(ViewGroup collection, int position) {
        CustomPagerEnum customPagerEnum = CustomPagerEnum.values()[position];
        LayoutInflater inflaterP = LayoutInflater.from(mContext);
        layout = (ViewGroup) inflaterP.inflate(customPagerEnum.getLayoutResId(), collection, false);

        day = new ArrayList<>(); //0 yesterday 1 today 2 tomorrow
        days.add(day);

        wrap_id.add(0); // each row
        btn_id.add(0);
        int todayOfWeek = Calendar.getInstance().get(Calendar.DAY_OF_WEEK);
        collection.addView(layout);
        if(position == 0)
            createSet(position, mUserHabitRespository.getDayHabit(todayOfWeek));
//        createSet(position, mUserHabitRespository.getDayHabit(16));

        // 습관 가져오기 시작 ///////////////////////////////////////////////////////////////////////////
//        Calendar day = Calendar.getInstance();
        // 오늘 습관 가져오기
//        List<UserHabitState> userHabitStatesList =
        /*// 오늘 요일
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
        mUserHabitStatesYesterdayList = mUserHabitRespository.getDayHabit(yesterday);*/
        // 습관 가져오기 종료 ///////////////////////////////////////////////////////////////////////////
        return layout;
    }

    public void setData(List<UserHabitState> list) {
        for (UserHabitState tmp : list) {
            Log.d("CustomPagerAdapter", " " + tmp.getGoal());
        }
    }

    // 맨처음 db에서 값 가져올때
    public void createSet(int nDay, List<UserHabitState> List) {
        List<UserHabitState> tmpList = null;
        days.set(nDay, List);
        tmpList = List;

        //// view 에 데이터 붙이기
        for(UserHabitState U : tmpList){
                addCell(nDay, U);
        }
    }

    public void addCell(int nDay, UserHabitState userHabitState) {

        LinearLayout pL = (LinearLayout) ((ViewGroup) layout.getParent()).getParent();
        pager = (ViewPager) pL.findViewById(R.id.pager);
        int pageNum = pager.getCurrentItem();
        LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        // 껍데기
        FrameLayout route_info_tab = (FrameLayout) inflater.inflate(R.layout.bt, null);
        // 각 페이지
        LinearLayout inLayout = (LinearLayout) layout.findViewById(R.id.inLayout);
        inLayout.addView(route_info_tab);

        //Set Id
        int temp_wrap_id = wrap_id.get(nDay);
        ViewGroup wrapView = (ViewGroup) inLayout.getChildAt(temp_wrap_id);

        SubmitProcessButton progressIndi = (SubmitProcessButton) wrapView.getChildAt(1);
        ViewGroup innerWrapView = (ViewGroup) wrapView.getChildAt(2);

        progressIndi.setProgress(0);

        View setupBtn = innerWrapView.getChildAt(0);
        ImageView habitImg = (ImageView) innerWrapView.getChildAt(1);
        ViewGroup descLayout = (ViewGroup) innerWrapView.getChildAt(2);

        TextView titleV = (TextView) descLayout.getChildAt(0);
        TextView wDayV = (TextView) descLayout.getChildAt(1);

        View minusBtn = innerWrapView.getChildAt(3);
        TextView curValue = (TextView) innerWrapView.getChildAt(4);
        TextView valueUnit = (TextView) innerWrapView.getChildAt(5);
        TextView maxValue = (TextView) innerWrapView.getChildAt(6);
        View plusBtn = innerWrapView.getChildAt(7);
        View modiBtn = innerWrapView.getChildAt(8);

        //Hide childViews
        setupBtn.setVisibility(View.GONE); // 버튼 띄우기
        minusBtn.setVisibility(View.GONE); // 감소
        plusBtn.setVisibility(View.GONE); // 증가
        modiBtn.setVisibility(View.GONE); // 수정


        //Init & Add curValue
//        mValues.get(nDay).add(0);

        //Add ArrayList
        mViews.add(innerWrapView);
        mViews.add(setupBtn);
        mViews.add(minusBtn);
        mViews.add(plusBtn);
        mViews.add(modiBtn);

        child_id = temp_wrap_id * 100;

        //Set ID to each View
        innerWrapView.setId(child_id++); //0
        progressIndi.setId(child_id++); //1
        setupBtn.setId(child_id++); //2
        minusBtn.setId(child_id++); //3
        curValue.setId(child_id++); //4
        valueUnit.setId(child_id++); //5
        plusBtn.setId(child_id++); //6
        modiBtn.setId(child_id++); //7
//        descLayout.setId(child_id++);//8
        titleV.setId(child_id++);//8
        wDayV.setId(child_id++);//9

        temp_wrap_id++;
        wrap_id.set(nDay, temp_wrap_id);

        curValue.setText("" + userHabitState.getDid());
        //userHabitState.getGoal();
        titleV.setText(userHabitState.getName());
        valueUnit.setText(userHabitState.getUnit());
        maxValue.setText(""+userHabitState.getFull());
        String tmpDayofWeek ="";
        int intDayofWeek = userHabitState.getDaysum();
        for(int i = 1 ; i < 8 ; i ++){
            if((intDayofWeek & ( 1<< i) ) > 0) {
                tmpDayofWeek += strArryDayofWeek[i-1];
            }
        }
        //habitImg.setImageDrawable();
        wDayV.setText(""+tmpDayofWeek);


        //Set onClickListener to each View
        for (View view : mViews) {
            view.setOnClickListener(onClickListener);
        }
    }

//    public void createBtnOnClickListener(final ViewGroup layout){
//        Button createBtn = (Button) layout.findViewById(R.id.aaa);
//        createBtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                LinearLayout pL = (LinearLayout) ((ViewGroup) layout.getParent()).getParent();
//                pager = (ViewPager) pL.findViewById(R.id.pager);
//                int pageNum = pager.getCurrentItem();
//                LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//                // 껍데기
//                FrameLayout route_info_tab = (FrameLayout) inflater.inflate(R.layout.bt, null);
//                // 각 페이지
//                LinearLayout inLayout = (LinearLayout) layout.findViewById(R.id.inLayout);
//                inLayout.addView(route_info_tab);
//
//                //Set Id
//                int temp_wrap_id = wrap_id.get(pageNum);
//                ViewGroup wrapView = (ViewGroup) inLayout.getChildAt(temp_wrap_id);
//                //wrapView.setId(pageNum * 10000 + temp_wrap_id);
//
//
//                SubmitProcessButton progressIndi = (SubmitProcessButton) wrapView.getChildAt(1);
//                ViewGroup innerWrapView = (ViewGroup) wrapView.getChildAt(2);
//
//                progressIndi.setProgress(0);
//
//                View setupBtn = innerWrapView.getChildAt(0);
//                View descLayout = innerWrapView.getChildAt(2);
//                View minusBtn = innerWrapView.getChildAt(3);
//                View curValue = innerWrapView.getChildAt(4);
//                View valueUnit = innerWrapView.getChildAt(5);
//                View plusBtn = innerWrapView.getChildAt(6);
//                View modiBtn = innerWrapView.getChildAt(7);
//
//                //Hide childViews
//                setupBtn.setVisibility(View.GONE);
//                minusBtn.setVisibility(View.GONE);
//                plusBtn.setVisibility(View.GONE);
//                modiBtn.setVisibility(View.GONE);
//
//                //Init & Add curValue
//                mValues.get(pageNum).add(0);
//
//                //Add ArrayList
//                mViews.add(innerWrapView);
//                mViews.add(setupBtn);
//                mViews.add(minusBtn);
//                mViews.add(plusBtn);
//                mViews.add(modiBtn);
//
//                child_id = temp_wrap_id * 100;
//
//                //Set ID to each View
//                innerWrapView.setId(child_id++); //0
//                progressIndi.setId(child_id++); //1
//                setupBtn.setId(child_id++); //2
//                minusBtn.setId(child_id++); //3
//                curValue.setId(child_id++); //4
//                valueUnit.setId(child_id++); //5
//                plusBtn.setId(child_id++); //6
//                modiBtn.setId(child_id++); //7
//                descLayout.setId(child_id++);//8
//                temp_wrap_id++;
//                wrap_id.set(pageNum, temp_wrap_id);
//
//                //Set onClickListener to each View
//                for (View view : mViews) {
//                    view.setOnClickListener(onClickListener);
//                }
//            }
//        });
//    }

    private View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            int id = v.getId();
            int index = 0;
            if (id >= 100) {
                index = (id - (id % 100)) / 100;
            }
            // 어제 오늘
//            int pageNumU = pager.getCurrentItem();
            int pageNumU = pager.getCurrentItem();
            Log.e("Test", "" + pageNumU);

            UserHabitState tmpState = days.get(pageNumU).get(index);
            // 오늘////////////////////////////////
            if (pageNumU == 1) {
//                tmpState = todayList.get(index);
            }
            // 어제 내일
            //////////////////////////////////////////

//            int curVal = mValues.get(pageNumU).get(index);
//            int curVal = mValues.get(pageNumU).get(index);
            int curVal2 = tmpState.getDid();
            Log.e("Get curVal", "Position: " + pageNumU + ", Index: " + index + ", CurVal: " + curVal2);
            Log.e("Btn", "Clicked: " + id);

            ViewGroup pL = (ViewGroup) ((ViewGroup) v.getParent()).getParent();
            switch (id % 100) {
                case 0: // 수정모드 버튼 변경
                    //Set Visibility to Visible
                    for (int i = 2; i < 8; i++) {
                        tempV = v.findViewById(id + i);
                        Log.e("RT", "" + (id + i));
                        tempV.setVisibility(View.VISIBLE);
                    }
                    for (int i = 8; i < 10; i++) {
                        tempV = v.findViewById(id + i);
                        tempV.setVisibility(View.GONE);
                    }
                    break;
                case 2: // setup
                    //Set Visibility to Invisible
                    int arr[] = {0, 1, 4, 5};
                    for (int i : arr) {
                        tempV = pL.findViewById(id + i);
                        Log.e("RT", "" + (id + i));
                        tempV.setVisibility(View.GONE);
                    }
                    for (int i = 6; i < 8; i++) {
                        tempV = pL.findViewById(id + i);
                        tempV.setVisibility(View.VISIBLE);
                    }
                    break;
                case 3: // -
                    Log.e("Btn", "Clicked" + id + ": " + curVal2);
                    //Decrease curValue
                    curVal2--;
                    tmpState.setDid(curVal2);
                    tempTv = (TextView) pL.findViewById(id - 2);
                    tempTv.setText("" + curVal2);
                    tempPi = (SubmitProcessButton) pL.findViewById(id - 5);
                    tempPi.setProgress(curVal2);

//                    if (curVal > 0) {
//                        curVal--;
//                        tempTv = (TextView) pL.findViewById(id + 1);
//                        tempTv.setText("" + curVal);
//                        tempPi = (SubmitProcessButton) pL.findViewById(id - 2);
//                        tempPi.setProgress(curVal);
//                        mValues.get(pageNumU).set(index, curVal);
//                        Log.e("MinusBtn", index + "/" + curVal);
//                    }
                    break;
                case 6: //+
                    Log.e("Btn", "Clicked" + id + ": " + curVal2);
                    //Cap to max value
                    curVal2++;
                    tmpState.setDid(curVal2);
                    tempTv = (TextView) pL.findViewById(id - 2);
                    tempTv.setText("" + curVal2);
                    tempPi = (SubmitProcessButton) pL.findViewById(id - 5);
                    tempPi.setProgress(curVal2);
//                    if (curVal < 100) {
//                        //Increase curValue
//                        curVal++;
//                        tempTv = (TextView) pL.findViewById(id - 2);
//                        tempTv.setText("" + curVal);
//                        tempPi = (SubmitProcessButton) pL.findViewById(id - 5);
//                        tempPi.setProgress(curVal);
//                        mValues.get(pageNumU).set(index, curVal);
//                        Log.e("PlusBtn", index + "/" + curVal);
//                    }
                    break;
                case 7:
                    //Go modiActivity
                    Intent intent = new Intent(mContext.getApplicationContext(), HabitActivity.class);
// DB 에서 HabitDetail 가져오기
//            ////////////////

//                    intent.putExtra("object", habit);
                    mContext.startActivity(intent);

                    break;
                default:
                    Log.e("Btn", "Default");
                    break;
            }

            /// db update
//            tmpState 이 객체 통째로 update;
            ////////////////
//            todayList.set(index,tmpState);
    /*    //
        public void updateCell(CustomPagerEnum day, UserHabitState userHabitState){
            List<UserHabitState> tmpList = null;
            if(day == CustomPagerEnum.TODAY)
                tmpList = todayList;

            int idx = tmpList.indexOf(userHabitState);

            UserHabitState tmpUserHabitState = tmpList.get(idx);

            tmpUserHabitState = userHabitState;

            tmpUserHabitState.getGoal();
            //// idx로 view 에 데이터 찾아서 바꾸기

            /////////////////////
        }*/
        }
    };

    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return 3;
    }

    @Override
    public void destroyItem(ViewGroup collection, int position, Object view) {
        collection.removeView((View) view);
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }
}

