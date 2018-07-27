package com.postfive.habit.adpater.celeblist;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.LinearLayoutCompat;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
    private int mWidth;
    private int mHeight;

    public CelebRecyclerViewAdapter(List<CelebHabitMaster> CelebHabitMasterList, int width){
        if(CelebHabitMasterList == null){
            this.mCelebHabitMaster = new ArrayList<>();
        }else{
            this.mCelebHabitMaster = CelebHabitMasterList;
        }

        this.mWidth = width;
//        this.mHeight = height;
    }
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // XML, 디자인 한 부분 적용
        mWidth =parent.getResources().getDisplayMetrics().widthPixels;
        mHeight =parent.getResources().getDisplayMetrics().heightPixels / 3 ;

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclerview_item_celeb_list, parent, false);
        view.setLayoutParams(new LinearLayoutCompat.LayoutParams(mWidth,mHeight));

        return new RowCell(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        // XML 디자인한 부분 안에 내용 변경
        CelebHabitMaster celebHabitMaster = mCelebHabitMaster.get(position);
        Log.d("HabitRecyclerView", celebHabitMaster.getName() +"/ "+ Integer.toString(position));
        ((RowCell)holder).textView.setText( celebHabitMaster.getTitle());
        ((RowCell)holder).imageView.setImageDrawable(assignImage(((RowCell)holder).imageView, celebHabitMaster.getImg()));
        ViewGroup.LayoutParams params = (ViewGroup.LayoutParams)  ((RowCell)holder).imageView.getLayoutParams();
        params.width = mWidth;
        params.height = mHeight-5;
        ((RowCell)holder).imageView.setLayoutParams(params);

        ViewGroup.LayoutParams params2 = (ViewGroup.LayoutParams)  ((RowCell)holder).textView.getLayoutParams();
        params2.width = mWidth;
        params2.height = 5;
        ((RowCell)holder).textView.setLayoutParams(params2);

    }

    @Override
    public int getItemCount() {
        // ItenCnt
        return mCelebHabitMaster.size();
    }

    private class RowCell extends RecyclerView.ViewHolder {
        public ImageView imageView;
        public TextView textView;
        public RowCell(View view) {
            super(view);
            imageView = (ImageView)view.findViewById(R.id.image_celeb_list_item);
            textView = (TextView) view.findViewById(R.id.textview_celeb_list_item);
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

    private Drawable assignImage(View v, String imgUri){
        InputStream inputStream = null;
        Drawable img = null;

        try{
            inputStream = v.getContext().getResources().getAssets().open(imgUri);
            img = Drawable.createFromStream(inputStream, null);
            inputStream.close();
        }catch (IOException e) {
            e.printStackTrace();
        }

        return img;
    }
}
