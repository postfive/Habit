package com.postfive.habit.adpater.habittime;

import java.util.List;

public interface HabitTimeRecyclerViewModel {
    public String getHabit(int position);

    public List<String> getAllHabit();

    public void setHabitTime(String time);
}
