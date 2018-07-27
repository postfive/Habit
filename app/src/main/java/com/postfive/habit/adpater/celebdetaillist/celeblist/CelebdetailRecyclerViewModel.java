package com.postfive.habit.adpater.celebdetaillist.celeblist;

import com.postfive.habit.db.CelebHabitDetail;
import com.postfive.habit.db.CelebHabitMaster;

import java.util.List;


public interface CelebdetailRecyclerViewModel {

    public CelebHabitDetail getHabit(int position);
    public void setHabit(CelebHabitDetail celebHabitMaster);
    public void setAllHabit(List<CelebHabitDetail> celebHabitMaster) ;


    }
