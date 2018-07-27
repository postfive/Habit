package com.postfive.habit.adpater.habittime;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.ToggleButton;

import com.postfive.habit.R;

import java.util.ArrayList;
import java.util.List;

public class HabitTimeRecyclerViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements  HabitTimeRecyclerViewModel {

    private List<String> mHabitTimeList;

    public HabitTimeRecyclerViewAdapter(){
        mHabitTimeList = new ArrayList<>();

    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // XML, 디자인 한 부분 적용

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclerview_item_habit_time, parent, false);

        return new RowCell(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        // XML 디자인한 부분 안에 내용 변경
        ((RowCell)holder).toggleButton.setTextOff( mHabitTimeList.get(position));
        ((RowCell)holder).toggleButton.setTextOn( mHabitTimeList.get(position));
        ((RowCell)holder).toggleButton.setText( mHabitTimeList.get(position));
    }

    @Override
    public int getItemCount() {
        // ItenCnt
        return mHabitTimeList.size();
    }

    private class RowCell extends RecyclerView.ViewHolder {
        public ToggleButton toggleButton;
        public RowCell(View view) {
            super(view);
            toggleButton = (ToggleButton) view.findViewById(R.id.togglebtn_habit_time);
            toggleButton.setChecked(true);
            toggleButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    Log.d("HabitTimeRecyclerView", buttonView.getText().toString());
                    if(!isChecked){
                        notifyItemRemoved(mHabitTimeList.indexOf(buttonView.getText()));
                        mHabitTimeList.remove(buttonView.getText());

                    }else {
                        setHabitTime(buttonView.getText().toString());
                    }
                }
            });
        }
    }

    @Override
    public String getHabit(int position){
        if( mHabitTimeList.isEmpty()){
            return null;
        }

        return mHabitTimeList.get(position);
    }

    @Override
    public List<String> getAllHabit(){
        if( mHabitTimeList.isEmpty() ){
            return null;
        }

        return mHabitTimeList;
    }

    @Override
    public void setHabitTime(String time){
        for(String tmp : mHabitTimeList){
            if(tmp.equals(time)){
                return;
            }
        }
        this.mHabitTimeList.add(time);
        notifyItemInserted (mHabitTimeList.lastIndexOf(time));
    }
}
