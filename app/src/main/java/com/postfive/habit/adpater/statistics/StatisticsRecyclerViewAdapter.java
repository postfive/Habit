package com.postfive.habit.adpater.statistics;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.v4.content.ContextCompat;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.postfive.habit.R;
import com.postfive.habit.view.statistics.HabitStatistics;

import java.util.ArrayList;
import java.util.List;

import static java.security.AccessController.getContext;

public class StatisticsRecyclerViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements StatisticsRecyclerViewModel {

    private List<HabitStatistics> mMyHabitList;
    private int index = -1;
    private int []color = {R.color.statistics_25, R.color.statistics_50, R.color.statistics_75, R.color.statistics_100};
    private Context mContext;
    public StatisticsRecyclerViewAdapter(Context context){
        mMyHabitList = new ArrayList<>();
        this.mContext = context;

    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // XML, 디자인 한 부분 적용

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclerview_item_statistics, parent, false);

        return new RowCell(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        // XML 디자인한 부분 안에 내용 변경
//        ((RowCell)holder).imageView.setImageDrawable();
        ((RowCell)holder).habitName.setText( mMyHabitList.get(position).getName());
        int percent = ((mMyHabitList.get(position).getDid()* 100)/mMyHabitList.get(position).getGoal());
        ((RowCell)holder).habitDid.setText( Integer.toString(percent)+"%");

        int i = 0 ;
        while(i < 3 ){
            if(percent > i*25 && percent <= (i+1)*25){
                break;
            }
            i++;
        }
        Log.d("색이 왜 안바껴 ", "25");
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {

            Drawable wrapDrawable = DrawableCompat.wrap(((RowCell)holder).progressBar.getIndeterminateDrawable());
            DrawableCompat.setTint(wrapDrawable, ContextCompat.getColor(mContext, color[i]));
            ((RowCell)holder).progressBar.setIndeterminateDrawable(DrawableCompat.unwrap(wrapDrawable));
        }else {
            ((RowCell) holder).progressBar.setProgressTintList(ContextCompat.getColorStateList(mContext, color[i]));
        }

        /*
        if(percent <= 25 ){
            Log.d("색이 왜 안바껴 ", "25");
            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {

                Drawable wrapDrawable = DrawableCompat.wrap(((RowCell)holder).progressBar.getIndeterminateDrawable());
                DrawableCompat.setTint(wrapDrawable, ContextCompat.getColor(mContext, R.color.statistics_25));
                ((RowCell)holder).progressBar.setIndeterminateDrawable(DrawableCompat.unwrap(wrapDrawable));
            }else {
                ((RowCell) holder).progressBar.setProgressTintList(ContextCompat.getColorStateList(mContext,R.color.statistics_25));
            }

        } else if(percent > 25 && percent <= 50){
            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {

                Drawable wrapDrawable = DrawableCompat.wrap(((RowCell) holder).progressBar.getIndeterminateDrawable());
                DrawableCompat.setTint(wrapDrawable, ContextCompat.getColor(mContext, R.color.statistics_50));
                ((RowCell) holder).progressBar.setIndeterminateDrawable(DrawableCompat.unwrap(wrapDrawable));
            }else {
                ((RowCell) holder).progressBar.setProgressTintList(ContextCompat.getColorStateList(mContext,R.color.statistics_50));
            }
        }else if(percent > 50 && percent <= 75){

            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {

                Drawable wrapDrawable = DrawableCompat.wrap(((RowCell) holder).progressBar.getIndeterminateDrawable());
                DrawableCompat.setTint(wrapDrawable, ContextCompat.getColor(mContext, R.color.statistics_75));
                ((RowCell) holder).progressBar.setIndeterminateDrawable(DrawableCompat.unwrap(wrapDrawable));
            }else {
                ((RowCell) holder).progressBar.setProgressTintList(ContextCompat.getColorStateList(mContext,R.color.statistics_75));
            }
        }else if(percent > 75 ){
            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {

                Drawable wrapDrawable = DrawableCompat.wrap(((RowCell) holder).progressBar.getIndeterminateDrawable());
                DrawableCompat.setTint(wrapDrawable, ContextCompat.getColor(mContext, R.color.statistics_100));
                ((RowCell) holder).progressBar.setIndeterminateDrawable(DrawableCompat.unwrap(wrapDrawable));
            }else {
                ((RowCell) holder).progressBar.setProgressTintList(ContextCompat.getColorStateList(mContext,R.color.statistics_100));
            }
        }*/
        ((RowCell)holder).progressBar.setProgress(percent);
    }

    @Override
    public int getItemCount() {
        // ItenCnt
        return mMyHabitList.size();
    }

    private class RowCell extends RecyclerView.ViewHolder {

        public TextView habitName;
        public TextView habitDid;
        public ProgressBar progressBar;

        public RowCell(View view) {
            super(view);
            habitName = (TextView)view.findViewById(R.id.textview_statistics_item_habit_name);
            habitDid = (TextView)view.findViewById(R.id.textview_statistics_item_habit_did);
            progressBar = (ProgressBar)view.findViewById(R.id.progressbar_statistics_item);
        }
    }

    @Override
    public HabitStatistics getHabit(int position){
        if( mMyHabitList.isEmpty()){
            return null;
        }

        return mMyHabitList.get(position);
    }


    @Override
    public List<HabitStatistics> getAllHabit(){
        if( mMyHabitList.isEmpty() ){
            return null;
        }

        return mMyHabitList;
    }

    @Override
    public void addHabit(HabitStatistics habit){

        this.mMyHabitList.add(habit);
        notifyItemInserted(mMyHabitList.indexOf(habit));
    }

    @Override
    public void setHabit(List<HabitStatistics> habitList){
        this.mMyHabitList.addAll(habitList);
        notifyDataSetChanged();
    }

    @Override
    public void setAllHabit(List<HabitStatistics> habitList){
        this.mMyHabitList.clear();
        this.mMyHabitList.addAll(habitList);
        notifyDataSetChanged();
    }


    @Override
    public void removeHabit(int position) {
        this.mMyHabitList.remove(position);
        notifyItemRemoved(position);
    }

    public void removeHabit(HabitStatistics habit) {
        this.mMyHabitList.remove(habit);
        notifyDataSetChanged();
    }

    @Override
    public void changeHabit(int position, HabitStatistics habit) {
        this.mMyHabitList.remove(position);
        this.mMyHabitList.add(habit);

        notifyDataSetChanged();
    }
    public void changeHabit (HabitStatistics habit) {
        this.mMyHabitList.remove(habit);
        this.mMyHabitList.add(habit);

        notifyDataSetChanged();
    }
}
