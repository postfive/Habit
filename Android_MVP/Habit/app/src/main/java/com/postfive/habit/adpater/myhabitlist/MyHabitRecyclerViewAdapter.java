package com.postfive.habit.adpater.myhabitlist;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.postfive.habit.R;
import com.postfive.habit.habits.factory.Habit;

import java.util.ArrayList;
import java.util.List;

public class MyHabitRecyclerViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements MyHabitRecyclerViewModel {

    private List<Habit> mMyHabitList;
    private int index = -1;
    private String[] strArryDayofWeek = {"일", "월", "화", "수", "목", "금", "토"};

    public MyHabitRecyclerViewAdapter(){
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
//        ((RowCell)holder).imageView.setImageDrawable();
        ((RowCell)holder).goalText.setText( mMyHabitList.get(position).getGoal());
        ((RowCell)holder).dayFullText.setText( mMyHabitList.get(position).getDayFull()+""+mMyHabitList.get(position).getUnit());
        ((RowCell)holder).didDayText.setText( mMyHabitList.get(position).getDidDay()+""+mMyHabitList.get(position).getUnit());


        String tmpDayofWeek ="";
        int intDayofWeek = mMyHabitList.get(position).getDayofWeek();
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
        public TextView goalText;
        public TextView dayofweekText;
        public TextView dayFullText;
        public TextView didDayText;

        public RowCell(View view) {
            super(view);
            imageView = (ImageView)view.findViewById(R.id.image_my_habit_item);
            goalText = (TextView)view.findViewById(R.id.textview_goal_my_habit_item);
            dayofweekText = (TextView)view.findViewById(R.id.textview_dayofweek_my_habit_item);
            dayFullText = (TextView)view.findViewById(R.id.textview_dayFull_my_habit_item);
            didDayText = (TextView)view.findViewById(R.id.textview_didday_my_habit_item);
        }
    }

    @Override
    public Habit getHabit(int position){
        if( mMyHabitList.isEmpty()){
            return null;
        }

        return mMyHabitList.get(position);
    }


    @Override
    public List<Habit> getAllHabit(){
        if( mMyHabitList.isEmpty() ){
            return null;
        }

        return mMyHabitList;
    }

    @Override
    public void addHabit(Habit habit){

        this.mMyHabitList.add(habit);
        notifyItemInserted(mMyHabitList.indexOf(habit));
    }

    @Override
    public void setHabit(List<Habit> habitList){
        this.mMyHabitList.addAll(habitList);
        notifyDataSetChanged();
    }

    @Override
    public void removeHabit(int position) {
        this.mMyHabitList.remove(position);
        notifyItemRemoved(position);
    }

    public void removeHabit(Habit habit) {
        this.mMyHabitList.remove(habit);
        notifyDataSetChanged();
    }

    @Override
    public void changeHabit(int position, Habit habit) {
        this.mMyHabitList.remove(position);
        this.mMyHabitList.add(habit);

        notifyDataSetChanged();
    }
    public void changeHabit (Habit habit) {
        this.mMyHabitList.remove(habit);
        this.mMyHabitList.add(habit);

        notifyDataSetChanged();
    }
}
