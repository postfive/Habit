package com.postfive.habit.adpater.celebdetaillist.celeblist;

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
import com.postfive.habit.db.CelebHabitDetail;
import com.postfive.habit.db.CelebHabitMaster;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class CelebDetailRecyclerViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements CelebdetailRecyclerViewModel {

    private static final String TAG = "CelebDetailRecyclerViewAdapter";
    private List<CelebHabitDetail> mCelebHabitDetail ;
    private int mWidth;
    private int mHeight;


    public CelebDetailRecyclerViewAdapter(){
        mCelebHabitDetail = new ArrayList<>();

//        this.mHeight = height;
    }
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // XML, 디자인 한 부분 적용
        mWidth =parent.getResources().getDisplayMetrics().widthPixels;
        mHeight =parent.getResources().getDisplayMetrics().heightPixels / 3 ;

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclerview_item_celeb_detail_list, parent, false);
        view.setLayoutParams(new LinearLayoutCompat.LayoutParams(mWidth,mHeight));

        return new RowCell(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        // XML 디자인한 부분 안에 내용 변경
        CelebHabitDetail CelebHabitDetail = mCelebHabitDetail.get(position);
        Log.d("HabitRecyclerView", CelebHabitDetail.getName() +"/ "+ Integer.toString(position));
        ((RowCell)holder).textViewGoal.setText( CelebHabitDetail.getGoal());
        if (CelebHabitDetail.getTime() == 0){
            ((RowCell)holder).textViewTime.setText("하루종일");
        } else if (CelebHabitDetail.getTime() == 1){
            ((RowCell)holder).textViewTime.setText("오전");
        } else if (CelebHabitDetail.getTime() == 2){
            ((RowCell)holder).textViewTime.setText("오후");
        } else if (CelebHabitDetail.getTime() == 3){
            ((RowCell)holder).textViewTime.setText("저녁");
        }

        ((RowCell)holder).imageView.setImageDrawable(assignImage(((RowCell)holder).imageView, CelebHabitDetail.getImg()));
//        ViewGroup.LayoutParams params = (ViewGroup.LayoutParams)  ((RowCell)holder).imageView.getLayoutParams();
//        params.width = mWidth;
//        params.height = mHeight;
//        ((RowCell)holder).imageView.setLayoutParams(params);
        /*
        ViewGroup.LayoutParams params = (ViewGroup.LayoutParams)  ((RowCell)holder).imageView.getLayoutParams();
        params.width = mWidth;
        params.height = mHeight-5;
        ((RowCell)holder).imageView.setLayoutParams(params);

        ViewGroup.LayoutParams params2 = (ViewGroup.LayoutParams)  ((RowCell)holder).textView.getLayoutParams();
        params2.width = mWidth;
        params2.height = 5;
        ((RowCell)holder).textView.setLayoutParams(params2);*/

    }

    @Override
    public int getItemCount() {
        // ItenCnt
        return mCelebHabitDetail.size();
    }

    private class RowCell extends RecyclerView.ViewHolder {
        public ImageView imageView;
        public TextView textViewTime;
        public TextView textViewGoal;
        public RowCell(View view) {
            super(view);
            imageView = (ImageView)view.findViewById(R.id.imgview_celeb_detail);
            textViewTime = (TextView) view.findViewById(R.id.textview_celeb_detail_time);
            textViewGoal = (TextView) view.findViewById(R.id.textview_celeb_detail_list_item);
        }
    }

    public CelebHabitDetail getHabit(int position){
        if( mCelebHabitDetail.size() < 1){
            return null;
        }

        return mCelebHabitDetail.get(position);
    }

    @Override
    public void setHabit(CelebHabitDetail CelebHabitDetail) {
        mCelebHabitDetail.add(CelebHabitDetail);
        notifyItemInserted(mCelebHabitDetail.indexOf(CelebHabitDetail));
    }
    @Override
    public void setAllHabit(List<CelebHabitDetail> CelebHabitDetail) {
        mCelebHabitDetail.clear();
        mCelebHabitDetail = CelebHabitDetail;
        notifyDataSetChanged();
    }

    private Drawable assignImage(View v, String imgUri){
        InputStream inputStream = null;
        Drawable img = null;

        try{
//            Log.d(TAG, "imgUri "+ imgUri.toString());
            inputStream = v.getContext().getResources().getAssets().open(imgUri);
            img = Drawable.createFromStream(inputStream, null);
            inputStream.close();
        }catch (IOException e) {
            e.printStackTrace();
        }

        return img;
    }
}
