package com.postfive.habit.adpater.celebdetaillist.celeblist;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.postfive.habit.R;
import com.postfive.habit.db.CelebHabitDetail;
import com.postfive.habit.db.CelebHabitKit;

import java.util.ArrayList;
import java.util.List;

public class HabitKitRecyclerViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements HabitKitRecyclerViewModel {

    private static final String TAG = "CelebDetailRecyclerViewAdapter";
    private List<CelebHabitKit> mCelebHabitKit ;


    public HabitKitRecyclerViewAdapter(){
        mCelebHabitKit = new ArrayList<>();

//        this.mHeight = height;
    }
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // XML, 디자인 한 부분 적용
       /* mWidth =parent.getResources().getDisplayMetrics().widthPixels;
        mHeight =parent.getResources().getDisplayMetrics().heightPixels / 3 ;*/

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclerview_item_kit, parent, false);
//        view.setLayoutParams(new LinearLayoutCompat.LayoutParams(mWidth,mHeight));

        return new RowCell(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        // XML 디자인한 부분 안에 내용 변경
        CelebHabitKit tmpHabitKit = mCelebHabitKit.get(position);
        Log.d("HabitRecyclerView", tmpHabitKit.getName() +"/ "+ Integer.toString(position));

        ((RowCell)holder).textViewName.setText(tmpHabitKit.getName());
        ((RowCell)holder).imageView.setImageResource(tmpHabitKit.getDrawable());
        ((RowCell)holder).textViewExplanation.setText(tmpHabitKit.getExplanation());
    }

    @Override
    public int getItemCount() {
        // ItenCnt
        return mCelebHabitKit.size();
    }

    private class RowCell extends RecyclerView.ViewHolder {
        public TextView textViewName;
        public TextView  textViewExplanation;
        public ImageView imageView;
        public RowCell(View view) {
            super(view);

            textViewName = (TextView)  view.findViewById(R.id.textview_kit_title);
            textViewExplanation     = (TextView)  view.findViewById(R.id.textview_kit_explanation);
            imageView        = (ImageView) view.findViewById(R.id.imageview_kit_img);

        }
    }

    public CelebHabitKit getHabit(int position){
        if( mCelebHabitKit.size() < 1){
            return null;
        }

        return mCelebHabitKit.get(position);
    }

    @Override
    public void setHabit(CelebHabitKit CelebHabitDetail) {
        mCelebHabitKit.add(CelebHabitDetail);
        notifyItemInserted(mCelebHabitKit.indexOf(CelebHabitDetail));
    }
    @Override
    public void setAllHabit(List<CelebHabitKit> CelebHabitDetail) {
        mCelebHabitKit.clear();
        mCelebHabitKit = CelebHabitDetail;
        notifyDataSetChanged();
    }

}
