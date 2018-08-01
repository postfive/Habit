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
    private int selectedPostion = -1;

    public HabitRecyclerViewAdapter(){
        this.mHabitList = new ArrayList<>();
    }
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // XML, 디자인 한 부분 적용

        Log.d("HabitRecyclerView", "onCreateViewHolder");
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclerview_item_habit, parent, false);
////        view.setLayoutParams(new LinearLayoutCompat.LayoutParams(width,height));
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
//        selected(position);

//        notifyItemChanged(position);
        return mHabitList.get(position);
    }

    public void setAllHabit(List<Habit> allHabit){
        mHabitList.clear();
        mHabitList = allHabit;

        notifyDataSetChanged();
    }

    private void selected(int position){
    }
}
