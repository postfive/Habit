package com.postfive.habit.adpater.myhabitlist;

import com.postfive.habit.habits.factory.Habit;

import java.util.List;

public interface MyHabitRecyclerViewModel {
    public Habit getHabit(int position);

    public List<Habit> getAllHabit();

    public void removeHabit(int position);

    public void changeHabit(int position, Habit habit);

    public void addHabit(Habit habit);

    public void setHabit(List<Habit> habitList);
}
