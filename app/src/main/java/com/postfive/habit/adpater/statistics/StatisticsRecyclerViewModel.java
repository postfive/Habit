package com.postfive.habit.adpater.statistics;

import com.postfive.habit.view.statistics.HabitStatistics;

import java.util.List;

public interface StatisticsRecyclerViewModel {
    public HabitStatistics getHabit(int position);

    public List<HabitStatistics> getAllHabit();

    public void removeHabit(int position);

    public void changeHabit(int position, HabitStatistics habit);

    public void addHabit(HabitStatistics habit);

    public void setHabit(List<HabitStatistics> habitList);

    public void setAllHabit(List<HabitStatistics> habitList);
}
