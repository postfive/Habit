package com.postfive.habit.adpater.celeblist;

import com.postfive.habit.db.CelebHabitMaster;

import java.util.List;


public interface CelebRecyclerViewModel {

    public CelebHabitMaster getHabit(int position);
    public void setHabit(CelebHabitMaster celebHabitMaster);
    public void setAllHabit(List<CelebHabitMaster> celebHabitMaster) ;


    }
