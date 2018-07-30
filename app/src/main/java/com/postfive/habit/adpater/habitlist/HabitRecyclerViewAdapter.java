package com.postfive.habit.adpater.habitlist;

import android.support.v7.widget.LinearLayoutCompat;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.postfive.habit.R;
import com.postfive.habit.db.Habit;

import java.util.ArrayList;
import java.util.List;

public class HabitRecyclerViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements  HabitRecyclerViewModel{

    private List<Habit> mHabitList ;

    public HabitRecyclerViewAdapter(List<Habit> habitList){
        this.mHabitList = habitList;
    }
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // XML, 디자인 한 부분 적용
/*        int width =parent.getResources().getDisplayMetrics().widthPixels / 2 - 8;
        int height =parent.getResources().getDisplayMetrics().widthPixels / 5 ;

//        int width = parent.getWidth()/2;
//        int height = parent.getHeight()/5 ;
        Log.d("HabitRecyclerView", "mWidth" + Integer.toString(width));
        Log.d("HabitRecyclerView", "mHeight" + Integer.toString(height));*/

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclerview_item_habit, parent, false);
//        view.setLayoutParams(new LinearLayoutCompat.LayoutParams(width,height));
        return new RowCell(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        // XML 디자인한 부분 안에 내용 변경
        Log.d("HabitRecyclerView", mHabitList.get(position).getName() +"/ "+ Integer.toString(position));
        ((RowCell)holder).textView.setText( mHabitList.get(position).getName());
        ((RowCell)holder).imageView.setImageResource(mHabitList.get(position).getIcon());
    }

    @Override
    public int getItemCount() {
        // ItenCnt
        return mHabitList.size();
    }

    private class RowCell extends RecyclerView.ViewHolder {
        public ImageView imageView;
        public TextView textView;
        public RowCell(View view) {
            super(view);
            imageView = (ImageView)view.findViewById(R.id.image_habit_item);
            textView = (TextView) view.findViewById(R.id.textview_habit_item);
        }
    }

    public Habit getHabit(int position){
        if( mHabitList.size() < 1){
            return null;
        }

        return mHabitList.get(position);
    }

    public void setAllHabit(List<Habit> allHabit){
        mHabitList.clear();
        mHabitList = allHabit;
        notifyDataSetChanged();
    }
}
