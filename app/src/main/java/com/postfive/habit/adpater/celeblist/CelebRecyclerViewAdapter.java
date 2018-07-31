package com.postfive.habit.adpater.celeblist;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.LinearLayoutCompat;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.postfive.habit.R;
import com.postfive.habit.db.CelebHabitMaster;
import com.postfive.habit.db.Habit;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class CelebRecyclerViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements CelebRecyclerViewModel {

    private List<CelebHabitMaster> mCelebHabitMaster ;

    public CelebRecyclerViewAdapter(List<CelebHabitMaster> CelebHabitMasterList){
        if(CelebHabitMasterList == null){
            this.mCelebHabitMaster = new ArrayList<>();
        }else{
            this.mCelebHabitMaster = CelebHabitMasterList;
        }

    }
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // XML, 디자인 한 부분 적용

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclerview_item_celeb_list, parent, false);

        return new RowCell(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        // XML 디자인한 부분 안에 내용 변경
        CelebHabitMaster celebHabitMaster = mCelebHabitMaster.get(position);
        Log.d("HabitRecyclerView", celebHabitMaster.getName() +"/ "+ Integer.toString(position));

        ((RowCell)holder).textViewTitle.setText( celebHabitMaster.getTitle());
        ((RowCell)holder).textViewSubTiutle.setText( celebHabitMaster.getSubtitle());
        ((RowCell)holder).imageView.setImageResource(celebHabitMaster.getDrawable());

    }

    @Override
    public int getItemCount() {
        // ItenCnt
        return mCelebHabitMaster.size();
    }

    private class RowCell extends RecyclerView.ViewHolder {
        public ImageView imageView;
        public TextView textViewTitle;
        public TextView textViewSubTiutle;
        public RowCell(View view) {
            super(view);
            imageView = (ImageView)view.findViewById(R.id.image_celeb_list_item);
            textViewTitle = (TextView) view.findViewById(R.id.textview_title_celeb_list_item);
            textViewSubTiutle = (TextView) view.findViewById(R.id.textview_subtitle_celeb_list_item);
        }
    }

    public CelebHabitMaster getHabit(int position){
        if( mCelebHabitMaster.size() < 1){
            return null;
        }

        return mCelebHabitMaster.get(position);
    }

    @Override
    public void setHabit(CelebHabitMaster celebHabitMaster) {
        mCelebHabitMaster.add(celebHabitMaster);
        notifyItemInserted(mCelebHabitMaster.indexOf(celebHabitMaster));
    }
    @Override
    public void setAllHabit(List<CelebHabitMaster> celebHabitMaster) {
        mCelebHabitMaster.clear();
        mCelebHabitMaster = celebHabitMaster;
        notifyDataSetChanged();
    }

}
