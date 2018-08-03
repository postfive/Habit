package com.postfive.habit.adpater.celebdetaillist.celeblist;

import com.postfive.habit.db.CelebHabitKit;

import java.util.List;


public interface HabitKitRecyclerViewModel {

    public CelebHabitKit getHabit(int position);
    public void setHabit(CelebHabitKit celebHabitMaster);
    public void setAllHabit(List<CelebHabitKit> celebHabitMaster) ;


    }
