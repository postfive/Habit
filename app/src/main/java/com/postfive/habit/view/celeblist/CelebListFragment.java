package com.postfive.habit.view.celeblist;

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

import com.postfive.habit.ItemClickSupport;
import com.postfive.habit.R;
import com.postfive.habit.Utils;
import com.postfive.habit.adpater.celeblist.CelebRecyclerViewAdapter;
import com.postfive.habit.db.CelebHabitMaster;
import com.postfive.habit.db.CelebHabitViewModel;
import com.postfive.habit.view.celeb.CelebActivity;

import java.util.List;


public class CelebListFragment extends Fragment {

    private CelebRecyclerViewAdapter mCelebRecyclerViewAdapter;
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

        NestedScrollView nestedscrollview = (NestedScrollView)view.findViewById(R.id.nestedscrollview);

        LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) nestedscrollview.getLayoutParams();
        layoutParams.topMargin = Utils.getStatusBarHeight(getContext());
        nestedscrollview.setLayoutParams(layoutParams);

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
            }
        });

        ItemClickSupport itemClickSupport = ItemClickSupport.addTo(recyclerView).setOnItemClickListener(new ItemClickSupport.OnItemClickListener() {
            @Override
            public void onItemClicked(RecyclerView recyclerView, int position, View v) {

                CelebHabitMaster habit = mCelebRecyclerViewAdapter.getHabit(position);

                Intent intent = new Intent(getContext(), CelebActivity.class);
                intent.putExtra("celebcode", habit.getCelebcode());
                startActivity(intent);
            }
        });

        return view;
    }

    public void onStop() {
        super.onStop();
//        mCelebRecyclerViewAdapter = null;
    }
}
