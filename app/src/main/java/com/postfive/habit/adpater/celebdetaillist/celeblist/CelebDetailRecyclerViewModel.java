package com.postfive.habit.adpater.celebdetaillist.celeblist;

import com.postfive.habit.db.CelebHabitDetail;

import java.util.List;


public interface CelebDetailRecyclerViewModel {

    public CelebHabitDetail getHabit(int position);
    public void setHabit(CelebHabitDetail celebHabitMaster);
    public void setAllHabit(List<CelebHabitDetail> celebHabitMaster) ;


    }
