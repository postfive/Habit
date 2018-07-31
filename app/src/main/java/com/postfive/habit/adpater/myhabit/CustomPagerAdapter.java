package com.postfive.habit.adpater.myhabit;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.dd.processbutton.iml.SubmitProcessButton;
import com.postfive.habit.R;
import com.postfive.habit.db.Habit;
import com.postfive.habit.db.UserHabitDetail;
import com.postfive.habit.db.UserHabitRespository;
import com.postfive.habit.db.UserHabitState;
import com.postfive.habit.view.habit.HabitActivity;
import com.postfive.habit.view.myhabitlist.MyHabitListActivity;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class CustomPagerAdapter extends PagerAdapter {

    private Context mContext;
    ViewPager pager;
    ArrayList<Integer> wrap_id = new ArrayList<>();
    ArrayList<Integer> wrap_id1 = new ArrayList<>();
    ArrayList<Integer> wrap_id2 = new ArrayList<>();

    ArrayList<Integer> btn_id = new ArrayList<>();
    ArrayList<View> mViews = new ArrayList<>();

    List<List<UserHabitState>> days = new ArrayList<>();
    ArrayList<UserHabitState> day = null;

    UserHabitRespository mUserHabitRepository;
    private String[] strArrayDayOfWeek = {"일", "월", "화", "수", "목", "금", "토"};

    View tempV;
    TextView tempTv;
    SubmitProcessButton tempPi; // Progress Indicator

    int child_id = 0;
    ViewGroup layout;

    public CustomPagerAdapter(Context context, UserHabitRespository mUserHabitRepository) {
        mContext = context;
        this.mUserHabitRepository = mUserHabitRepository;
    }

    @Override
    public Object instantiateItem(ViewGroup collection, int position) {
        CustomPagerEnum customPagerEnum = CustomPagerEnum.values()[position];
        LayoutInflater inflaterP = LayoutInflater.from(mContext);
        layout = (ViewGroup) inflaterP.inflate(customPagerEnum.getLayoutResId(), collection, false);

        day = new ArrayList<>(); //0 yesterday 1 today 2 tomorrow
        days.add(day);

        btn_id.add(0);
        collection.addView(layout);

        // 습관 가져오기 시작 ///////////////////////////////////////////////////////////////////////////
        // 오늘 습관 가져오기

        // 오늘 요일
        int today = Calendar.getInstance().get(Calendar.DAY_OF_WEEK);
        int nowTime = Habit.NIGHT_TIME;
        if (position == 0) {
            // TODO 현재 지금
            wrap_id.add(0); // each row
            List<UserHabitState> mUserHabitStatesList = mUserHabitRepository.getNowHabit(nowTime);
            createSet(0, position, mUserHabitStatesList);
            // TODO 오늘 완성
            wrap_id1.add(1); // each row

            List<UserHabitState> mTodayCompleteHabitStatesList = mUserHabitRepository.getComplite();
            createSet(1, position, mTodayCompleteHabitStatesList);
            // TODO 오늘 놓친것
            wrap_id2.add(1); // each row

            List<UserHabitState> mTodayMissedHabitStatesList = mUserHabitRepository.getPassHabit(nowTime);
            createSet(2, position, mTodayMissedHabitStatesList);
        }
        // TODO 어제 습관
        else if (position == 1) {
            int yesterday = today - 1;
            if (yesterday < 1)
                yesterday = 7;
            wrap_id.add(0); // each row
            List<UserHabitState> mUserHabitStatesYesterdayList = mUserHabitRepository.getDayHabit(yesterday);
            createSet(0, position, mUserHabitStatesYesterdayList);
        }
        // TODO 내일 습관
        else if (position == 2) {
            int tomorrow = today + 1;
            if (tomorrow > 7)
                tomorrow = 1;
            wrap_id.add(0); // each row
            List<UserHabitState> mUserHabitStatesTomorrowList = mUserHabitRepository.getDayHabit(tomorrow);
            createSet(0, position, mUserHabitStatesTomorrowList);
        }

        // 습관 가져오기 종료 ///////////////////////////////////////////////////////////////////////////
        return layout;
    }

    public void setData(List<UserHabitState> list) {
        for (UserHabitState tmp : list) {
            Log.d("CustomPagerAdapter", " " + tmp.getGoal());
        }
    }

    // 맨처음 db에서 값 가져올때
    public void createSet(int status, int nDay, List<UserHabitState> List) {
        List<UserHabitState> tmpList = null;
        if (status == 0)
            days.set(nDay, List);
        tmpList = List;
        //// view 에 데이터 붙이기
        for (UserHabitState U : tmpList) {
            addCell(status, nDay, U);
        }
    }

    public void addCell(int status, int nDay, UserHabitState userHabitState) {
        LinearLayout pL = (LinearLayout) ((ViewGroup) layout.getParent()).getParent();
        pager = (ViewPager) pL.findViewById(R.id.pager);
        // int pageNum = pager.getCurrentItem();
        LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        // 껍데기
        FrameLayout route_info_tab = (FrameLayout) inflater.inflate(R.layout.bt, null);
        LinearLayout inLayout;
        // 각 페이지
        if (status == 1) {
            inLayout = (LinearLayout) layout.findViewById(R.id.inLayout1);
            inLayout.setVisibility(View.VISIBLE);
        } else if (status == 2) {
            inLayout = (LinearLayout) layout.findViewById(R.id.inLayout2);
            inLayout.setVisibility(View.VISIBLE);
        } else {// status == 0
            inLayout = (LinearLayout) layout.findViewById(R.id.inLayout);
        }
        inLayout.addView(route_info_tab);

        //Set Id
        int temp_wrap_id;

        if (status == 1)
            temp_wrap_id = wrap_id1.get(nDay);
        else if (status == 2)
            temp_wrap_id = wrap_id2.get(nDay);
        else
            temp_wrap_id = wrap_id.get(nDay);

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
        if (status == 0) {
            mViews.add(innerWrapView);
            mViews.add(setupBtn);
            mViews.add(minusBtn);
            mViews.add(plusBtn);
            mViews.add(modiBtn);
        }
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
        if (status == 1)
            wrap_id1.set(nDay, temp_wrap_id);
        else if (status == 2)
            wrap_id2.set(nDay, temp_wrap_id);
        else
            wrap_id.set(nDay, temp_wrap_id);


        curValue.setText("" + userHabitState.getDid());
        //userHabitState.getGoal();
        titleV.setText(userHabitState.getName());
        valueUnit.setText(userHabitState.getUnit());
        maxValue.setText("" + userHabitState.getFull());
        String tmpDayofWeek = "";
        int intDayofWeek = userHabitState.getDaysum();
        for (int i = 1; i < 8; i++) {
            if ((intDayofWeek & (1 << i)) > 0) {
                tmpDayofWeek += strArrayDayOfWeek[i - 1];
            }
        }
        Drawable drawable = mContext.getResources().getDrawable(userHabitState.getIcon());

        habitImg.setImageDrawable(drawable);
        wDayV.setText("" + tmpDayofWeek);

        int maxVal = userHabitState.getFull();
        int conVal = (int) Math.ceil(100 / (float) maxVal);
        int curVal2 = userHabitState.getDid();
        int pVal;
        if (curVal2 > maxVal)
            curVal2 = maxVal;
        pVal = curVal2 * conVal;
        if (pVal > 100)
            pVal = 100;

        //tempPi = (SubmitProcessButton) pL.findViewById(cellCnt * 100 + 1);
        progressIndi.setProgress(pVal);

        //Set onClickListener to each View
        if (nDay == 0) {
            for (View view : mViews) {
                view.setOnClickListener(onClickListener);
            }
            Button showAllBtn = (Button) layout.findViewById(R.id.showAllBtn);
            showAllBtn.setOnClickListener(onClickListener);
        }
    }

    private View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            int id = v.getId();
            if (id == R.id.showAllBtn) {
                Intent intent = new Intent(mContext, MyHabitListActivity.class);
                //intent.putExtra("object", habit);
                mContext.startActivity(intent);
                return;
            }

            int index = 0;
            if (id >= 100) {
                index = (id - (id % 100)) / 100;
            }
            // 어제 오늘
//            int pageNum = pager.getCurrentItem();
            int pageNum = pager.getCurrentItem();
            Log.e("Test", "" + pageNum);

            UserHabitState tmpState = days.get(pageNum).get(index);
            // 오늘////////////////////////////////
            if (pageNum == 1) {
//                tmpState = todayList.get(index);
            }
            // 어제 내일
            //////////////////////////////////////////
            int maxVal = tmpState.getFull();
            int conVal = (int) Math.ceil(100 / (float) maxVal);
            int curVal2 = tmpState.getDid();
            int pVal;

            Log.e("Get curVal", "Position: " + pageNum + ", Index: " + index + ", CurVal: " + curVal2 + "/" + conVal);
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
                    if (curVal2 == maxVal) {
                        ViewGroup parent = (ViewGroup) pL.getParent();
                        parent.removeView(pL);
                        ViewGroup gparent = (ViewGroup) parent.getParent();
                        ViewGroup completeV = (ViewGroup) gparent.getChildAt(1);
                        completeV.addView(pL);
                        completeV.setVisibility(View.VISIBLE);
                    } else if (curVal2 < maxVal) {

                        ViewGroup parent = (ViewGroup) pL.getParent();
                        ViewGroup gparent = (ViewGroup) parent.getParent();

                        if (gparent.getChildAt(1) == parent) {
                            parent.removeView(pL);
                            ViewGroup completeV = (ViewGroup) gparent.getChildAt(0);
                            completeV.addView(pL);
                            parent.setVisibility(View.INVISIBLE);
                        }
                    }
                    mUserHabitRepository.updateUserHabitState(tmpState);

                    break;
                case 3: // -
                    //Decrease curValue
                    curVal2--;

                    tmpState.setDid(curVal2);
                    tempTv = (TextView) pL.findViewById(id + 1);
                    if (curVal2 < 0)
                        curVal2 = 0;
                    pVal = curVal2 * conVal;
                    Log.e("DEC", "Clicked" + id + ": " + curVal2 + "/" + pVal);
                    tempTv.setText("" + curVal2);
                    tempPi = (SubmitProcessButton) pL.findViewById(id - 2);
                    tempPi.setProgress(pVal);

//                    if (curVal > 0) {
//                        curVal--;
//                        tempTv = (TextView) pL.findViewById(id + 1);
//                        tempTv.setText("" + curVal);
//                        tempPi = (SubmitProcessButton) pL.findViewById(id - 2);
//                        tempPi.setProgress(curVal);
//                        mValues.get(pageNum).set(index, curVal);
//                        Log.e("MinusBtn", index + "/" + curVal);
//                    }
                    break;
                case 6: //+
                    //Cap to max value
                    curVal2++;
                    tmpState.setDid(curVal2);
                    tempTv = (TextView) pL.findViewById(id - 2);
                    if (curVal2 > maxVal)
                        curVal2 = maxVal;
                    pVal = curVal2 * conVal;
                    if (pVal > 100)
                        pVal = 100;
                    Log.e("INC", "Clicked" + id + ": " + curVal2 + "/" + pVal);
                    tempTv.setText("" + curVal2);
                    tempPi = (SubmitProcessButton) pL.findViewById(id - 5);
                    tempPi.setProgress(pVal);
//                    if (curVal < 100) {
//                        //Increase curValue
//                        curVal++;
//                        tempTv = (TextView) pL.findViewById(id - 2);
//                        tempTv.setText("" + curVal);
//                        tempPi = (SubmitProcessButton) pL.findViewById(id - 5);
//                        tempPi.setProgress(curVal);
//                        mValues.get(pageNum).set(index, curVal);
//                        Log.e("PlusBtn", index + "/" + curVal);
//                    }
                    break;
                case 7:
                    //Go modiActivity
                    Intent intent = new Intent(mContext, HabitActivity.class);
                    UserHabitDetail habit = new UserHabitDetail(0, tmpState);
                    intent.putExtra("object", habit);
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

