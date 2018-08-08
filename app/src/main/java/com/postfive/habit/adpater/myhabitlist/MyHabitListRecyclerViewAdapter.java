package com.postfive.habit.adpater.myhabitlist;

import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.support.graphics.drawable.VectorDrawableCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.postfive.habit.R;
import com.postfive.habit.db.UserHabitDetail;

import java.util.ArrayList;
import java.util.List;

public class MyHabitListRecyclerViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements MyHabitListRecyclerViewModel {

    private List<UserHabitDetail> mMyHabitList;
    private int index = -1;
    private String[] strArryDayofWeek = {"일", "월", "화", "수", "목", "금", "토"};

    public MyHabitListRecyclerViewAdapter(){
        mMyHabitList = new ArrayList<>();

    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // XML, 디자인 한 부분 적용

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclerview_item_my_habit, parent, false);

        return new RowCell(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        // XML 디자인한 부분 안에 내용 변경
        ((RowCell)holder).imageView.setImageResource(mMyHabitList.get(position).getIcon());
        ((RowCell)holder).customnameText.setText( mMyHabitList.get(position).getCustomname());
        ((RowCell)holder).dayFullText.setText( mMyHabitList.get(position).getGoal()+""+mMyHabitList.get(position).getUnit());
        //((RowCell)holder).didDayText.setText( mMyHabitList.get(position).getDidDay()+""+mMyHabitList.get(position).getUnit());


        String tmpDayofWeek ="";
        int intDayofWeek = mMyHabitList.get(position).getDaysum();
        for(int i = 1 ; i < 8 ; i ++){
            if((intDayofWeek & ( 1<< i) ) > 0) {
                tmpDayofWeek += strArryDayofWeek[i-1];
            }
        }

        ((RowCell)holder).dayofweekText.setText( tmpDayofWeek);

    }

    @Override
    public int getItemCount() {
        // ItenCnt
        return mMyHabitList.size();
    }

    private class RowCell extends RecyclerView.ViewHolder {

        public ImageView imageView;
        public TextView customnameText;
        public TextView dayofweekText;
        public TextView dayFullText;
        public TextView didDayText;

        public RowCell(View view) {
            super(view);
            imageView = (ImageView)view.findViewById(R.id.image_my_habit_item);
            customnameText = (TextView)view.findViewById(R.id.textview_customname_my_habit_item);
            dayofweekText = (TextView)view.findViewById(R.id.textview_dayofweek_my_habit_item);
            dayFullText = (TextView)view.findViewById(R.id.textview_dayFull_my_habit_item);
            didDayText = (TextView)view.findViewById(R.id.textview_didday_my_habit_item);
        }
    }

    @Override
    public UserHabitDetail getHabit(int position){
        if( mMyHabitList.isEmpty()){
            return null;
        }

        return mMyHabitList.get(position);
    }


    @Override
    public List<UserHabitDetail> getAllHabit(){
        if( mMyHabitList.isEmpty() ){
            return null;
        }

        return mMyHabitList;
    }

    @Override
    public void addHabit(UserHabitDetail habit){

        this.mMyHabitList.add(habit);
        notifyItemInserted(mMyHabitList.indexOf(habit));
    }

    @Override
    public void setHabit(List<UserHabitDetail> habitList){
        this.mMyHabitList.addAll(habitList);
        notifyDataSetChanged();
    }

    @Override
    public void setAllHabit(List<UserHabitDetail> habitList){
        this.mMyHabitList.clear();
        this.mMyHabitList.addAll(habitList);
        notifyDataSetChanged();
    }


    @Override
    public void removeHabit(int position) {
        this.mMyHabitList.remove(position);
        notifyItemRemoved(position);
    }

    public void removeHabit(UserHabitDetail habit) {
        this.mMyHabitList.remove(habit);
        notifyDataSetChanged();
    }

    @Override
    public void changeHabit(int position, UserHabitDetail habit) {
        this.mMyHabitList.remove(position);
        this.mMyHabitList.add(habit);

        notifyDataSetChanged();
    }
    public void changeHabit (UserHabitDetail habit) {
        this.mMyHabitList.remove(habit);
        this.mMyHabitList.add(habit);

        notifyDataSetChanged();
    }

    public void deleteHabit(UserHabitDetail habit){

        this.mMyHabitList.remove(habit);
        notifyItemRemoved(mMyHabitList.indexOf(habit));
    }
}
