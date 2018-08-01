package com.postfive.habit.adpater.celebdetaillist.celeblist;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.postfive.habit.R;
import com.postfive.habit.Utils;
import com.postfive.habit.db.CelebHabitDetail;

import java.util.ArrayList;
import java.util.List;

public class CelebDetailRecyclerViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements CelebDetailRecyclerViewModel {

    private static final String TAG = "CelebDetailRecyclerViewAdapter";
    private List<CelebHabitDetail> mCelebHabitDetail ;


    public CelebDetailRecyclerViewAdapter(){
        mCelebHabitDetail = new ArrayList<>();

//        this.mHeight = height;
    }
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // XML, 디자인 한 부분 적용
       /* mWidth =parent.getResources().getDisplayMetrics().widthPixels;
        mHeight =parent.getResources().getDisplayMetrics().heightPixels / 3 ;*/

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclerview_item_celeb_detail_list, parent, false);
//        view.setLayoutParams(new LinearLayoutCompat.LayoutParams(mWidth,mHeight));

        return new RowCell(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        // XML 디자인한 부분 안에 내용 변경
        CelebHabitDetail tmpCelebDetail = mCelebHabitDetail.get(position);
        Log.d("HabitRecyclerView", tmpCelebDetail.getName() +"/ "+ Integer.toString(position));


        // 맨위 시간 설정
        String time = "#"+Integer.toString(position+1);
        if (tmpCelebDetail.getTime() == 1){
            ((RowCell)holder).textViewTime.setText("오전");
            time+=" 오전 ";
        } else if (tmpCelebDetail.getTime() == 2){
            ((RowCell)holder).textViewTime.setText("오후");
            time+=" 오후 ";
        } else if (tmpCelebDetail.getTime() == 3){
            ((RowCell)holder).textViewTime.setText("저녁");
            time+=" 저녁 ";
        } else if (tmpCelebDetail.getTime() == 0){
            time+=" 하루종일 ";
        }
        time+=tmpCelebDetail.getRealime();
        ((RowCell)holder).textViewTime.setText(time);
        // 시간설정 종료

        // 요일 1회 수행 설정
        String dayofweek = Utils.getDayComma(tmpCelebDetail.getDaysum())
                        +" ("+Integer.toString(tmpCelebDetail.getFull()) + tmpCelebDetail.getUnit() +")";
        ((RowCell)holder).textViewDayofWeek.setText(dayofweek);
        // 요일 1회 수행 설정 종료

        //
        String title = tmpCelebDetail.getGoal();
        ((RowCell)holder).textViewTitle.setText(title);
        // 제목 종료

        // 사진 설정
        ((RowCell)holder).imageView.setImageResource(tmpCelebDetail.getDrawable());

        // comment
        if(tmpCelebDetail.getComment().length() > 1) {
            ((RowCell) holder).textViewComent.setText((tmpCelebDetail.getComment()));
        }else{
            ((RowCell) holder).textViewComent.setVisibility(View.GONE);
        }

        ((RowCell) holder).textViewMemo.setText(tmpCelebDetail.getMemo());
//        ((RowCell)holder).imageView.setImageResource();

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
        public TextView  textViewTime;
        public TextView  textViewDayofWeek;
        public TextView  textViewTitle;
        public TextView  textViewComent;
        public ImageView imageView;
        public TextView  textViewMemo;
        public RowCell(View view) {
            super(view);

            textViewTime      = (TextView)  view.findViewById(R.id.textview_celeb_detail_item_time);
            textViewDayofWeek = (TextView)  view.findViewById(R.id.textview_celeb_detail_item_dayofweek);
            textViewTitle     = (TextView)  view.findViewById(R.id.textview_celeb_detail_item_title);
            textViewComent     = (TextView)  view.findViewById(R.id.textview_celeb_detail_item_comment);
            imageView        = (ImageView) view.findViewById(R.id.imageview_celeb_detail_item_img);
            textViewMemo     = (TextView)  view.findViewById(R.id.textview_celeb_detail_item_memo);

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

}
