package com.postfive.habit.adpater.myhabit;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.ContentFrameLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.postfive.habit.R;
import com.postfive.habit.db.UserHabitDetail;
import com.postfive.habit.db.UserHabitState;

import java.util.ArrayList;
import java.util.List;

public class MyHabitRecyclerViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements MyHabitRecyclerViewModel {

    private List<UserHabitState> mMyHabitList;
    private int index = -1;
    private String[] strArryDayofWeek = {"일", "월", "화", "수", "목", "금", "토"};
    private Context mContext;

    public MyHabitRecyclerViewAdapter(Context context){
        mMyHabitList = new ArrayList<>();
        this.mContext = context;
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
        ((RowCell)holder).dayFullText.setText( mMyHabitList.get(position).getFull()+""+mMyHabitList.get(position).getUnit());
        ((RowCell)holder).didDayText.setText( mMyHabitList.get(position).getDid()+""+mMyHabitList.get(position).getUnit());
        Drawable drawable = mContext.getResources().getDrawable(mMyHabitList.get(position).getIcon());

        ((RowCell)holder).imageView.setImageDrawable(drawable);


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
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                }
            });
        }
    }

    @Override
    public UserHabitState getHabit(int position){
        if( mMyHabitList.isEmpty()){
            return null;
        }

        return mMyHabitList.get(position);
    }


    @Override
    public List<UserHabitState> getAllHabit(){
        if( mMyHabitList.isEmpty() ){
            return null;
        }

        return mMyHabitList;
    }
    @Override
    public void setAllHabit(List<UserHabitState> allHabit){
        if(allHabit == null)
            return;

        mMyHabitList.clear();
        mMyHabitList.addAll(allHabit);
        notifyDataSetChanged();
    }

    @Override
    public void addHabit(UserHabitState habit){

        this.mMyHabitList.add(habit);
        notifyItemInserted(mMyHabitList.indexOf(habit));
    }

    @Override
    public void setHabit(List<UserHabitState> habitList){
        this.mMyHabitList.addAll(habitList);
        notifyDataSetChanged();
    }

    @Override
    public void removeHabit(int position) {
        this.mMyHabitList.remove(position);
        notifyItemRemoved(position);
    }

    @Override
    public void changeHabit(int position, UserHabitState habit) {

        this.mMyHabitList.set(position, habit);

        notifyDataSetChanged();
    }
/*    public void changeHabit (UserHabitState habit) {
        this.mMyHabitList.remove(habit);
        this.mMyHabitList.add(habit);

        notifyDataSetChanged();
    }*/
}
