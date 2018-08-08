package com.postfive.habit.adpater.myhabit;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
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
    private ViewPager pager;
    private ArrayList<Integer> wrap_id = new ArrayList<>();
    private ArrayList<Integer> wrap_id1 = new ArrayList<>();
    private ArrayList<Integer> wrap_id2 = new ArrayList<>();
    private ArrayList<Integer> btn_id = new ArrayList<>();
    private ArrayList<View> mViews = new ArrayList<>();
    private ArrayList<View> mViews1 = new ArrayList<>();
    private ArrayList<View> mViews2 = new ArrayList<>();
    private List<List<UserHabitState>> days = new ArrayList<>();
    private List<List<UserHabitState>> days1 = new ArrayList<>();
    private List<List<UserHabitState>> days2 = new ArrayList<>();
    private ArrayList<UserHabitState> day = null;
    private UserHabitRespository mUserHabitRepository;
    private String[] strArrayDayOfWeek = {"일", "월", "화", "수", "목", "금", "토"};
    private View tempV;
    private TextView tempTv;
    private SubmitProcessButton tempPi; // Progress Indicator
    private int child_id = 0;
    private ViewGroup layout;
    private int initFirstFlag = 0;
    private int cont_flag = 0;
    int[] colors = {Color.parseColor("#75d185"), Color.parseColor("#65bfce")};
    GradientDrawable gd = new GradientDrawable(GradientDrawable.Orientation.LEFT_RIGHT, colors);

    public CustomPagerAdapter(Context context, UserHabitRespository mUserHabitRepository) {
        mContext = context;
        this.mUserHabitRepository = mUserHabitRepository;
    }

    @Override
    public Object instantiateItem(ViewGroup collection, int position) {
        CustomPagerEnum customPagerEnum = CustomPagerEnum.values()[position];
        LayoutInflater inflaterP = LayoutInflater.from(mContext);
        layout = (ViewGroup) inflaterP.inflate(customPagerEnum.getLayoutResId(), collection, false);

        if (initFirstFlag == 0) {
            for (int i = 0; i < getCount(); i++) {
                day = new ArrayList<>(); //0 yesterday 1 today 2 tomorrow
                days.add(day);
                btn_id.add(0);
                initFirstFlag = 1;
                wrap_id.add(0);
            }
            days1.add(day);
            days2.add(day);
        }
        collection.addView(layout);

        // 습관 가져오기 시작 ///////////////////////////////////////////////////////////////////////////

        // 오늘 요일
        int today = Calendar.getInstance().get(Calendar.DAY_OF_WEEK);
        int nowTime = Habit.NIGHT_TIME;

        // TODO 어제 습관
        if (position == 0) {
            int yesterday = today - 1;
            if (yesterday < 1)
                yesterday = 7;
            List<UserHabitState> mUserHabitStatesYesterdayList = mUserHabitRepository.getDayHabit(yesterday);
            createSet(0, position, mUserHabitStatesYesterdayList);
        } else if (position == 1) {
            // TODO 현재 지금
            List<UserHabitState> mUserHabitStatesList = mUserHabitRepository.getNowHabit(nowTime);
            createSet(0, position, mUserHabitStatesList);
            // TODO 오늘 완성
            wrap_id1.add(0); // each row

            List<UserHabitState> mTodayCompleteHabitStatesList = mUserHabitRepository.getComplite();
            createSet(1, position, mTodayCompleteHabitStatesList);
            // TODO 오늘 놓친것
            wrap_id2.add(0); // each row

            List<UserHabitState> mTodayMissedHabitStatesList = mUserHabitRepository.getPassHabit(nowTime);
            createSet(2, position, mTodayMissedHabitStatesList);
        }
        // TODO 내일 습관
        else if (position == 2) {
            int tomorrow = today + 1;
            if (tomorrow > 7)
                tomorrow = 1;
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
    private void createSet(int status, int position, List<UserHabitState> List) {
        List<UserHabitState> tmpList;
        cont_flag = 0;
        if (status == 0)
            days.set(position, List);
        else if (status == 1)
            days1.set(0, List); // first arg. is not relate to position... it could be confuse..
        else if (status == 2)
            days2.set(0, List); // first arg. is not relate to position... it could be confuse..
        tmpList = List;
        //// view 에 데이터 붙이기
        for (UserHabitState U : tmpList) {
            addCell(status, position, U);
        }
    }

    private void addCell(int status, int position, UserHabitState userHabitState) {
        LinearLayout pL = (LinearLayout) (layout.getParent()).getParent();
        pager = (ViewPager) pL.findViewById(R.id.pager);
        LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        // 껍데기
        FrameLayout route_info_tab = (FrameLayout) inflater.inflate(R.layout.bt, null);
        LinearLayout inLayout;
        // 각 페이지
        if (status == 1) {
            inLayout = (LinearLayout) layout.findViewById(R.id.inLayout1);
            layout.findViewById(R.id.tv_done).setVisibility(View.VISIBLE);
            inLayout.setVisibility(View.VISIBLE);
        } else if (status == 2) {
            inLayout = (LinearLayout) layout.findViewById(R.id.inLayout2);
            layout.findViewById(R.id.tv_missed).setVisibility(View.VISIBLE);
            inLayout.setVisibility(View.VISIBLE);
        } else {// status == 0
            inLayout = (LinearLayout) layout.findViewById(R.id.inLayout);
        }
        inLayout.addView(route_info_tab);

        //Set Id
        int temp_wrap_id;

        if (status == 1)
            temp_wrap_id = wrap_id1.get(0);
        else if (status == 2)
            temp_wrap_id = wrap_id2.get(0);
        else
            temp_wrap_id = wrap_id.get(position);

        ViewGroup wrapView = (ViewGroup) inLayout.getChildAt(temp_wrap_id);
        if (status != 0 && cont_flag == 0) {
//            temp_wrap_id--;
            cont_flag = 1;
        }
        if (wrapView == null) {
            Log.e("Stop", "here");
        }
        SubmitProcessButton progressIndi = (SubmitProcessButton) wrapView.getChildAt(1);
        ViewGroup innerWrapView = (ViewGroup) wrapView.getChildAt(2);

        progressIndi.setProgress(0);

        View setupBtn = innerWrapView.getChildAt(0);
        ImageView habitImg = (ImageView) innerWrapView.getChildAt(1);
        ViewGroup descLayout = (ViewGroup) innerWrapView.getChildAt(3);

        TextView titleV = (TextView) descLayout.getChildAt(0);
        TextView wDayV = (TextView) descLayout.getChildAt(1);

        ViewGroup descLayout2 = (ViewGroup) innerWrapView.getChildAt(4);
        ViewGroup descLayout2_1 = (ViewGroup) descLayout2.getChildAt(0);
        ViewGroup descLayout2_2 = (ViewGroup) descLayout2.getChildAt(1);

        View minusBtn = descLayout2_1.getChildAt(0);
        TextView curValue = (TextView) descLayout2_1.getChildAt(1);
        TextView valueUnit = (TextView) descLayout2_1.getChildAt(2);
        View plusBtn = descLayout2_1.getChildAt(3);
        TextView modiBtn = (TextView) descLayout2_1.getChildAt(4);
        TextView maxValue = (TextView) descLayout2_2.getChildAt(0);
        TextView valueUnit2 = (TextView) descLayout2_2.getChildAt(1);

        if(status != 0){
            habitImg.setColorFilter(Color.parseColor("#ffffff"));
            innerWrapView.getChildAt(2).setBackgroundColor(Color.parseColor("#ffffff"));
            titleV.setTextColor(Color.parseColor("#ffffff"));
            wDayV.setTextColor(Color.parseColor("#ffffff"));
            curValue.setTextColor(Color.parseColor("#ffffff"));
            valueUnit.setTextColor(Color.parseColor("#ffffff"));
            maxValue.setTextColor(Color.parseColor("#ffffff"));
            valueUnit2.setTextColor(Color.parseColor("#ffffff"));
        }


        modiBtn.setPaintFlags(Paint.UNDERLINE_TEXT_FLAG);


        //Hide childViews
        setupBtn.setVisibility(View.GONE); // 버튼 띄우기
        minusBtn.setVisibility(View.GONE); // 감소
        plusBtn.setVisibility(View.GONE); // 증가
        modiBtn.setVisibility(View.GONE); // 수정

        //Add ArrayList
        ArrayList<View> tempMViews;
        if (status == 1) {
            tempMViews = mViews1;
            wrapView.getChildAt(0).setBackground(gd);

        } else if (status == 2) {
            tempMViews = mViews2;
            wrapView.getChildAt(0).setBackgroundColor(Color.parseColor("#bdbdbd"));
        } else
            tempMViews = mViews;

        tempMViews.add(innerWrapView);
        tempMViews.add(setupBtn);
        tempMViews.add(minusBtn);
        tempMViews.add(plusBtn);
        tempMViews.add(modiBtn);

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
        valueUnit2.setId(child_id++); //10
        descLayout2_2.setId(child_id); //11

        temp_wrap_id++;
        if (status == 1)
            wrap_id1.set(0, temp_wrap_id);
        else if (status == 2)
            wrap_id2.set(0, temp_wrap_id);
        else
            wrap_id.set(position, temp_wrap_id);

        curValue.setText("" + userHabitState.getDid());
        titleV.setText(userHabitState.getName());
        valueUnit.setText(userHabitState.getUnit());

        valueUnit2.setText(userHabitState.getUnit());
        maxValue.setText("" + userHabitState.getGoal());
        String tmpDayOfWeek = "";
        int intDayOfWeek = userHabitState.getDaysum();

        for (int i = 1; i < 8; i++) {
            if ((intDayOfWeek & (1 << i)) > 0) {
                tmpDayOfWeek += strArrayDayOfWeek[i - 1];
            }
        }
        Drawable drawable = mContext.getResources().getDrawable(userHabitState.getIcon());

        habitImg.setImageDrawable(drawable);
        wDayV.setText("" + tmpDayOfWeek);

        int maxVal = userHabitState.getGoal();
        int conVal = (int) Math.ceil(100 / (float) maxVal);
        int curVal2 = userHabitState.getDid();
        int pVal;
        if (curVal2 > maxVal)
            curVal2 = maxVal;
        pVal = curVal2 * conVal;
        if (pVal > 100)
            pVal = 100;

        progressIndi.setProgress(pVal);

        //Set onClickListener to each View
        if (position == 1) {
            for (View view : tempMViews) {
                if (status == 0)
                    view.setOnClickListener(onClickListener);
                else if (status == 1)
                    view.setOnClickListener(onClickListener1);
                else
                    view.setOnClickListener(onClickListener2);
            }
            Button showAllBtn = (Button) layout.findViewById(R.id.showAllBtn);
            showAllBtn.setOnClickListener(onClickListener);
        } else {
            for (View view : tempMViews) {
                view.setOnClickListener(onClickListener);
            }
        }
    }

    private View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            int id = v.getId();
            if (id == R.id.showAllBtn) {
                Intent intent = new Intent(mContext, MyHabitListActivity.class);
                mContext.startActivity(intent);
                return;
            }
            int index = 0;
            if (id >= 100) {
                index = (id - (id % 100)) / 100;
            }
            int pageNum = pager.getCurrentItem();
            Log.e("Test", "index" + index + ", pageNum: " + pageNum);
            UserHabitState tmpState;
            tmpState = days.get(pageNum).get(index);

            int maxVal = tmpState.getGoal();
            int conVal = (int) Math.ceil(100 / (float) maxVal);
            int curVal2 = tmpState.getDid();
            int pVal;

            Log.e("Get curVal", "Position: " + pageNum + ", Index: " + index + ", CurVal: " + curVal2 + "/" + conVal);
            Log.e("Btn", "Clicked: " + id);

            ViewGroup pL = (ViewGroup) (v.getParent()).getParent();
            ViewGroup parent = (ViewGroup) pL.getParent();
            ViewGroup gparent = (ViewGroup) parent.getParent();

            switch (id % 100) {
                case 0: // 수정모드 버튼
                    ViewGroup vParent = (ViewGroup) v.getParent();
                    ViewGroup backLayout = (ViewGroup) vParent.findViewById(R.id.fraLayout_0);

                    ViewGroup backLayout2 = (ViewGroup) backLayout.getChildAt(0);
                    backLayout2.setBackgroundColor(Color.parseColor("#eeeeee"));
                    ViewGroup wrapL = (ViewGroup) backLayout.getChildAt(2);
                    //Set Visibility to Visible
                    for (int i = 2; i < 8; i++) {
                        tempV = v.findViewById(id + i);
                        tempV.setVisibility(View.VISIBLE);
                    }
                    for (int i = 8; i < 12; i++) {
                        tempV = v.findViewById(id + i);
                        tempV.setVisibility(View.GONE);
                    }
                    LinearLayout tempP = (LinearLayout) tempV.getParent();
                    LinearLayout.LayoutParams attrLayoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
//                    attrLayoutParams.gravity = Gravity.CENTER_HORIZONTAL;
                    attrLayoutParams.setMargins(90, 40, 0, 0);

                    ViewGroup tempVg = (ViewGroup) tempP.findViewById(R.id.desc1);
                    TextView tempView = (TextView) tempVg.getChildAt(1);
                    tempView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 24);
                    tempView.setTextColor(Color.parseColor("#de000000"));
                    tempView = (TextView) tempVg.getChildAt(2);
                    tempView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 24);
                    tempView.setTextColor(Color.parseColor("#de000000"));
                    tempVg.setLayoutParams(attrLayoutParams);

                    wrapL.getChildAt(1).setVisibility(View.GONE);
                    wrapL.getChildAt(3).setVisibility(View.GONE);
                    break;
                case 2: // setup
                    //Set Visibility to Invisible
                    int arr[] = {0, 1, 4, 5};
                    for (int i : arr) {
                        tempV = pL.findViewById(id + i);
                        Log.e("RT", "" + (id + i));
                        tempV.setVisibility(View.GONE);
                    }
                    for (int i = 6; i < 10; i++) {
                        tempV = pL.findViewById(id + i);
                        tempV.setVisibility(View.VISIBLE);
                    }

                    ViewGroup vg = (ViewGroup) pL.getChildAt(2);
                    ViewGroup pg = (ViewGroup) parent.getChildAt(index);
                    ViewGroup pp = (ViewGroup) pg.getChildAt(0);
                    pp.setBackgroundColor(Color.parseColor("#ffffff"));
                    vg.getChildAt(1).setVisibility(View.VISIBLE);
                    vg.getChildAt(3).setVisibility(View.VISIBLE);
                    pg.getChildAt(1).setVisibility(View.VISIBLE);

                    LinearLayout.LayoutParams attrLayoutParams1 = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
//                    attrLayoutParams.gravity = Gravity.CENTER_HORIZONTAL;

                    attrLayoutParams1.setMargins(0, 28, 0, 0);
                    ViewGroup tempVg1 = (ViewGroup) vg.getChildAt(4).findViewById(R.id.desc1);
                    TextView tempView1 = (TextView) tempVg1.getChildAt(1);
                    tempView1.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16);
                    tempView = (TextView) tempVg1.getChildAt(2);
                    tempView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16);
                    tempVg1.setLayoutParams(attrLayoutParams1);

                    mUserHabitRepository.updateUserHabitState(tmpState);

                    if (curVal2 == maxVal) {
                        if (gparent.findViewById(R.id.inLayout1) != parent) {
                            parent.removeView(pL);
                            ViewGroup completeV = (ViewGroup) gparent.findViewById(R.id.inLayout1);
                            completeV.addView(pL);
                            completeV.setVisibility(View.VISIBLE);
                            gparent.findViewById(R.id.tv_done).setVisibility(View.VISIBLE);
                            init();
                            notifyDataSetChanged();
                        }
                    } else if (curVal2 < maxVal) {
                        if (gparent.findViewById(R.id.inLayout1) == parent) {
                            parent.removeView(pL);
                            ViewGroup completeV = (ViewGroup) gparent.findViewById(R.id.inLayout);
                            completeV.addView(pL);
                            if (parent.getChildCount() == 0) {
                                parent.setVisibility(View.GONE);
                                gparent.findViewById(R.id.tv_done).setVisibility(View.GONE);
                            }
                            init();
                            notifyDataSetChanged();
                        }
                    }
                    break;
                case 3: // -
                    //Decrease curValue
                    curVal2--;
                    tmpState.setDid(curVal2);
                    tempTv = (TextView) pL.getChildAt(0).findViewById(id + 1);
                    if (curVal2 < 0)
                        curVal2 = 0;
                    pVal = curVal2 * conVal;
                    Log.e("DEC", "Clicked" + id + ": " + curVal2 + "/" + pVal);
                    tempTv.setText("" + curVal2);
                    tempPi = (SubmitProcessButton) gparent.findViewById(id - 2);
                    tempPi.setProgress(pVal);
                    break;
                case 6: //+
                    //Cap to max value
                    curVal2++;
                    tmpState.setDid(curVal2);
                    tempTv = (TextView) pL.getChildAt(0).findViewById(id - 2);
                    if (curVal2 > maxVal)
                        curVal2 = maxVal;
                    pVal = curVal2 * conVal;
                    if (pVal > 100)
                        pVal = 100;
                    Log.e("INC", "Clicked" + id + ": " + curVal2 + "/" + pVal);
                    tempTv.setText("" + curVal2);
                    tempPi = (SubmitProcessButton) gparent.findViewById(id - 5);
                    tempPi.setProgress(pVal);
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
        }
    };

    private View.OnClickListener onClickListener1 = new View.OnClickListener() {
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
            int pageNum = pager.getCurrentItem();
            Log.e("Test", "index" + index + ", pageNum: " + pageNum);
            UserHabitState tmpState;
            tmpState = days1.get(0).get(index);

//            UserHabitState tmpState = tempDay.get(pageNum).get(index);

            int maxVal = tmpState.getGoal();
            int conVal = (int) Math.ceil(100 / (float) maxVal);
            int curVal2 = tmpState.getDid();
            int pVal;

            Log.e("Get curVal", "Position: " + pageNum + ", Index: " + index + ", CurVal: " + curVal2 + "/" + conVal);
            Log.e("Btn", "Clicked: " + id);

            ViewGroup pL = (ViewGroup) (v.getParent()).getParent(); // inlay
            ViewGroup parent = (ViewGroup) pL.getParent(); //linear lay
            ViewGroup gparent = (ViewGroup) parent.getParent();

            switch (id % 100) {
                case 0: // 수정모드 버튼// v liLayout
                    ViewGroup vParent = (ViewGroup) v.getParent();
                    ViewGroup backLayout = (ViewGroup) vParent.findViewById(R.id.fraLayout_0);

                    ViewGroup backLayout2 = (ViewGroup) backLayout.getChildAt(0);
                    backLayout2.setBackgroundColor(Color.parseColor("#eeeeee"));
                    //ViewGroup wrapL = (ViewGroup) backLayout.getChildAt(2);
                    //Set Visibility to Visible
                    for (int i = 2; i < 8; i++) {
                        tempV = v.findViewById(id + i);
                        tempV.setVisibility(View.VISIBLE);
                    }
                    for (int i = 8; i < 12; i++) {
                        tempV = v.findViewById(id + i);
                        tempV.setVisibility(View.GONE);
                    }
                    LinearLayout tempP = (LinearLayout) tempV.getParent();
                    LinearLayout.LayoutParams attrLayoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
//                    attrLayoutParams.gravity = Gravity.CENTER_HORIZONTAL;
                    attrLayoutParams.setMargins(90, 40, 0, 0);

                    ViewGroup tempVg = (ViewGroup) tempP.findViewById(R.id.desc1);
                    TextView tempView = (TextView) tempVg.getChildAt(1);
                    tempView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 24);
                    tempView.setTextColor(Color.parseColor("#de000000"));
                    tempView = (TextView) tempVg.getChildAt(2);
                    tempView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 24);
                    tempView.setTextColor(Color.parseColor("#de000000"));
                    tempVg.setLayoutParams(attrLayoutParams);

                    ViewGroup temp_vg = (ViewGroup) v;
                    temp_vg.getChildAt(1).setVisibility(View.GONE);
                    temp_vg.getChildAt(2).setBackgroundColor(Color.parseColor("#e0e0e0"));
                    temp_vg.getChildAt(3).setVisibility(View.GONE);

//                    wrapL.getChildAt(7).setVisibility(View.GONE);
                    break;
                case 2: // setup
                    //Set Visibility to Invisible
                    int arr[] = {0, 1, 4, 5};
                    for (int i : arr) {
                        tempV = pL.findViewById(id + i);
                        Log.e("RT", "" + (id + i));
                        tempV.setVisibility(View.GONE);
                    }
                    for (int i = 6; i < 10; i++) {
                        tempV = pL.findViewById(id + i);
                        tempV.setVisibility(View.VISIBLE);
                    }
                    ViewGroup vg = (ViewGroup) pL.getChildAt(2);
                    ViewGroup pg = (ViewGroup) parent.getChildAt(index);
                    ViewGroup pp = (ViewGroup) pg.getChildAt(0);
                    pp.setBackgroundColor(Color.parseColor("#ffffff"));
                    vg.getChildAt(1).setVisibility(View.VISIBLE);
                    vg.getChildAt(2).setBackgroundColor(Color.parseColor("#ffffff"));
                    vg.getChildAt(3).setVisibility(View.VISIBLE);
                    pg.getChildAt(1).setVisibility(View.VISIBLE);
                    //tempP1.setVisibility(View.VISIBLE);

                    LinearLayout.LayoutParams attrLayoutParams1 = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
//                    attrLayoutParams.gravity = Gravity.CENTER_HORIZONTAL;

                    attrLayoutParams1.setMargins(0, 28, 0, 0);
                    ViewGroup tempVg1 = (ViewGroup) vg.getChildAt(4).findViewById(R.id.desc1);
                    TextView tempView1 = (TextView) tempVg1.getChildAt(1);
                    tempView1.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16);
                    tempView1.setTextColor(Color.parseColor("#ffffff"));
                    tempView = (TextView) tempVg1.getChildAt(2);
                    tempView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16);
                    tempView.setTextColor(Color.parseColor("#ffffff"));
                    tempVg1.setLayoutParams(attrLayoutParams1);

                    mUserHabitRepository.updateUserHabitState(tmpState);

                    if (curVal2 == maxVal) {
                        pp.setBackground(gd);
                        if (gparent.findViewById(R.id.inLayout1) != parent) {
                            parent.removeView(pL);
                            ViewGroup completeV = (ViewGroup) gparent.findViewById(R.id.inLayout1);
                            completeV.addView(pL);
                            completeV.setVisibility(View.VISIBLE);
                            gparent.findViewById(R.id.tv_done).setVisibility(View.VISIBLE);
                            init();
                            notifyDataSetChanged();
                        }
                    } else if (curVal2 < maxVal) {
                        if (gparent.findViewById(R.id.inLayout1) == parent) {
                            parent.removeView(pL);
                            ViewGroup completeV = (ViewGroup) gparent.findViewById(R.id.inLayout);
                            completeV.addView(pL);
                            if (parent.getChildCount() == 0) {
                                parent.setVisibility(View.GONE);
                                gparent.findViewById(R.id.tv_done).setVisibility(View.GONE);
                            }
                            init();
                            notifyDataSetChanged();
                        }
                    }
                    break;
                case 3: // -
                    //Decrease curValue
                    curVal2--;
                    tmpState.setDid(curVal2);
                    tempTv = (TextView) pL.getChildAt(0).findViewById(id + 1);
                    if (curVal2 < 0)
                        curVal2 = 0;
                    pVal = curVal2 * conVal;
                    Log.e("DEC", "Clicked" + id + ": " + curVal2 + "/" + pVal);
                    tempTv.setText("" + curVal2);
                    tempPi = (SubmitProcessButton) gparent.findViewById(id - 2);
                    tempPi.setProgress(pVal);
                    break;
                case 6: //+
                    //Cap to max value
                    curVal2++;
                    tmpState.setDid(curVal2);
                    tempTv = (TextView) pL.getChildAt(0).findViewById(id - 2);
                    if (curVal2 > maxVal)
                        curVal2 = maxVal;
                    pVal = curVal2 * conVal;
                    if (pVal > 100)
                        pVal = 100;
                    Log.e("INC", "Clicked" + id + ": " + curVal2 + "/" + pVal);
                    tempTv.setText("" + curVal2);
                    tempPi = (SubmitProcessButton) gparent.findViewById(id - 5);
                    tempPi.setProgress(pVal);
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
        }
    };

    private View.OnClickListener onClickListener2 = new View.OnClickListener() {
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
            int pageNum = pager.getCurrentItem();
            Log.e("Test", "index" + index + ", pageNum: " + pageNum);
            UserHabitState tmpState;
            tmpState = days2.get(0).get(index);

            int maxVal = tmpState.getGoal();
            int conVal = (int) Math.ceil(100 / (float) maxVal);
            int curVal2 = tmpState.getDid();
            int pVal;

            Log.e("Get curVal", "Position: " + pageNum + ", Index: " + index + ", CurVal: " + curVal2 + "/" + conVal);
            Log.e("Btn", "Clicked: " + id);

            ViewGroup pL = (ViewGroup) (v.getParent()).getParent();
            ViewGroup parent = (ViewGroup) pL.getParent();
            ViewGroup gparent = (ViewGroup) parent.getParent();

            switch (id % 100) {
                case 0: // 수정모드 버튼
                    ViewGroup vParent = (ViewGroup) v.getParent();
                    ViewGroup backLayout = (ViewGroup) vParent.findViewById(R.id.fraLayout_0);

                    ViewGroup backLayout2 = (ViewGroup) backLayout.getChildAt(0);
                    backLayout2.setBackgroundColor(Color.parseColor("#eeeeee"));
                    //Set Visibility to Visible
                    for (int i = 2; i < 8; i++) {
                        tempV = v.findViewById(id + i);
                        tempV.setVisibility(View.VISIBLE);
                    }
                    for (int i = 8; i < 12; i++) {
                        tempV = v.findViewById(id + i);
                        tempV.setVisibility(View.GONE);
                    }
                    LinearLayout tempP = (LinearLayout) tempV.getParent();
                    LinearLayout.LayoutParams attrLayoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
//                    attrLayoutParams.gravity = Gravity.CENTER_HORIZONTAL;
                    attrLayoutParams.setMargins(90, 40, 0, 0);

                    ViewGroup tempVg = (ViewGroup) tempP.findViewById(R.id.desc1);
                    TextView tempView = (TextView) tempVg.getChildAt(1);
                    tempView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 24);
                    tempView.setTextColor(Color.parseColor("#de000000"));
                    tempView = (TextView) tempVg.getChildAt(2);
                    tempView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 24);
                    tempView.setTextColor(Color.parseColor("#de000000"));
                    tempVg.setLayoutParams(attrLayoutParams);

                    ViewGroup temp_vg = (ViewGroup) v;

                    temp_vg.getChildAt(1).setVisibility(View.GONE);
                    temp_vg.getChildAt(2).setBackgroundColor(Color.parseColor("#e0e0e0"));
                    temp_vg.getChildAt(3).setVisibility(View.GONE);
                    break;
                case 2: // setup
                    //Set Visibility to Invisible
                    int arr[] = {0, 1, 4, 5};
                    for (int i : arr) {
                        tempV = pL.findViewById(id + i);
                        Log.e("RT", "" + (id + i));
                        tempV.setVisibility(View.GONE);
                    }
                    for (int i = 6; i < 10; i++) {
                        tempV = pL.findViewById(id + i);
                        tempV.setVisibility(View.VISIBLE);
                    }
                    ViewGroup vg = (ViewGroup) pL.getChildAt(2);
                    ViewGroup pg = (ViewGroup) parent.getChildAt(index);
                    ViewGroup pp = (ViewGroup) pg.getChildAt(0);
                    pp.setBackgroundColor(Color.parseColor("#ffffff"));
                    vg.getChildAt(1).setVisibility(View.VISIBLE);
                    vg.getChildAt(2).setBackgroundColor(Color.parseColor("#ffffff"));
                    vg.getChildAt(3).setVisibility(View.VISIBLE);
                    pg.getChildAt(1).setVisibility(View.VISIBLE);

                    LinearLayout.LayoutParams attrLayoutParams1 = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
//                    attrLayoutParams.gravity = Gravity.CENTER_HORIZONTAL;

                    attrLayoutParams1.setMargins(0, 28, 0, 0);
                    ViewGroup tempVg1 = (ViewGroup) vg.getChildAt(4).findViewById(R.id.desc1);
                    TextView tempView1 = (TextView) tempVg1.getChildAt(1);
                    tempView1.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16);
                    tempView1.setTextColor(Color.parseColor("#ffffff"));
                    tempView = (TextView) tempVg1.getChildAt(2);
                    tempView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16);
                    tempView.setTextColor(Color.parseColor("#ffffff"));
                    tempVg1.setLayoutParams(attrLayoutParams1);

                    mUserHabitRepository.updateUserHabitState(tmpState);

                    if (curVal2 == maxVal) {

                        if (gparent.findViewById(R.id.inLayout1) != parent) {
                            parent.removeView(pL);
                            ViewGroup completeV = (ViewGroup) gparent.findViewById(R.id.inLayout1);
                            completeV.addView(pL);
                            completeV.setVisibility(View.VISIBLE);
                            gparent.findViewById(R.id.tv_done).setVisibility(View.VISIBLE);
                            init();
                            notifyDataSetChanged();
                        }
                    } else if (curVal2 < maxVal) {
                        pp.setBackgroundColor(Color.parseColor("#bdbdbd"));

                        if (gparent.findViewById(R.id.inLayout1) == parent) {
                            parent.removeView(pL);
                            ViewGroup completeV = (ViewGroup) gparent.findViewById(R.id.inLayout);
                            completeV.addView(pL);
                            if (parent.getChildCount() == 0) {
                                parent.setVisibility(View.GONE);
                                gparent.findViewById(R.id.tv_done).setVisibility(View.GONE);
                            }
                            init();
                            notifyDataSetChanged();
                        }
                    }
                    break;
                case 3: // -
                    //Decrease curValue
                    curVal2--;
                    tmpState.setDid(curVal2);
                    tempTv = (TextView) pL.getChildAt(0).findViewById(id + 1);
                    if (curVal2 < 0)
                        curVal2 = 0;
                    pVal = curVal2 * conVal;
                    Log.e("DEC", "Clicked" + id + ": " + curVal2 + "/" + pVal);
                    tempTv.setText("" + curVal2);
                    tempPi = (SubmitProcessButton) gparent.findViewById(id - 2);
                    tempPi.setProgress(pVal);
                    break;
                case 6: //+
                    //Cap to max value
                    curVal2++;
                    tmpState.setDid(curVal2);
                    tempTv = (TextView) pL.getChildAt(0).findViewById(id - 2);
                    if (curVal2 > maxVal)
                        curVal2 = maxVal;
                    pVal = curVal2 * conVal;
                    if (pVal > 100)
                        pVal = 100;
                    Log.e("INC", "Clicked" + id + ": " + curVal2 + "/" + pVal);
                    tempTv.setText("" + curVal2);
                    tempPi = (SubmitProcessButton) gparent.findViewById(id - 5);
                    tempPi.setProgress(pVal);
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
        }
    };

    @Override
    public int getItemPosition(Object object) {
        return POSITION_NONE;
    }

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

    public void init() {
        wrap_id.clear();
        wrap_id1.clear();
        wrap_id2.clear();
        btn_id.clear();
        mViews.clear();
        mViews1.clear();
        mViews2.clear();
        days.clear();
        days1.clear();
        days2.clear();
        day.clear();

        for (int i = 0; i < getCount(); i++) {
            day = new ArrayList<>(); //0 yesterday 1 today 2 tomorrow
            days.add(day);
            btn_id.add(0);
            wrap_id.add(0);
        }
        days1.add(day);
        days2.add(day);
    }
}

