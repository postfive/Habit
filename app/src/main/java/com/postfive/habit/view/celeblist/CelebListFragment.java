package com.postfive.habit.view.celeblist;

import android.annotation.SuppressLint;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.postfive.habit.ItemClickSupport;
import com.postfive.habit.R;
import com.postfive.habit.Utils;
import com.postfive.habit.adpater.celeblist.CelebRecyclerViewAdapter;
import com.postfive.habit.db.CelebHabitMaster;
import com.postfive.habit.db.CelebHabitViewModel;
import com.postfive.habit.view.celeb.CelebActivity;
import com.postfive.habit.view.main.MainActivity;

import java.util.List;


public class CelebListFragment extends Fragment {

    private CelebRecyclerViewAdapter mCelebRecyclerViewAdapter;
    boolean init = false;

    @SuppressLint("ValidFragment")
    public CelebListFragment(boolean init) {
        this.init = init;
    }
    public CelebListFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_celeb_list, container, false);

        NestedScrollView nestedscrollview = (NestedScrollView)view.findViewById(R.id.nestedscrollview_celeb_list);
/*

        LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) nestedscrollview.getLayoutParams();
        layoutParams.topMargin = Utils.getStatusBarHeight(getContext());
        nestedscrollview.setLayoutParams(layoutParams);
*/


        // 최초 일때
        if(init) {
            TextView textViewTitle = (TextView) view.findViewById(R.id.textview_title_celeb_list_itemtitle);
            LinearLayout linearLayout = (LinearLayout) view.findViewById(R.id.init_title_celeb_list);

            linearLayout.setVisibility(View.VISIBLE);
            textViewTitle.setVisibility(View.GONE);
        }

        final ProgressBar progressBar = (ProgressBar)view.findViewById(R.id.progressbar_celeb_list);
        progressBar.setVisibility(View.VISIBLE);
        int width = getResources().getDisplayMetrics().widthPixels;
        mCelebRecyclerViewAdapter = new CelebRecyclerViewAdapter(null);

        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.recyclerview_celeb_list);
        recyclerView.setAdapter(mCelebRecyclerViewAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setNestedScrollingEnabled(false);

        // DB ViewModelProviders  설정
        CelebHabitViewModel celebHabitViewModel = ViewModelProviders.of(this).get(CelebHabitViewModel.class);

        // 데이터가 변경될 때 호출
        celebHabitViewModel.getCelebHabitList().observe(this, new Observer<List<CelebHabitMaster>>() {
            @Override
            public void onChanged(@Nullable List<CelebHabitMaster> celebHabitMasters) {

                mCelebRecyclerViewAdapter.setAllHabit(celebHabitMasters);
                progressBar.setVisibility(View.GONE);
            }
        });

        ItemClickSupport itemClickSupport = ItemClickSupport.addTo(recyclerView).setOnItemClickListener(new ItemClickSupport.OnItemClickListener() {
            @Override
            public void onItemClicked(RecyclerView recyclerView, int position, View v) {

                CelebHabitMaster habit = mCelebRecyclerViewAdapter.getHabit(position);

                if(habit.getCelebcode() > 2){
                    Toast.makeText(getContext(),"중비중입니다.", Toast.LENGTH_SHORT).show();
                    return;
                }
                Intent intent = new Intent(getContext(), CelebActivity.class);
                intent.putExtra("celebcode", habit);
                startActivityForResult(intent, MainActivity.GET_CELEB_HABIT);
            }
        });

        return view;
    }

    public void onStop() {
        super.onStop();
//        mCelebRecyclerViewAdapter = null;
    }
}
