package com.postfive.habit.adpater.habitlist;

import com.postfive.habit.db.Habit;

import java.util.List;

public interface HabitRecyclerViewModel {

    public Habit getHabit(int position);

    public void setAllHabit(List<Habit> allHabit);
}
