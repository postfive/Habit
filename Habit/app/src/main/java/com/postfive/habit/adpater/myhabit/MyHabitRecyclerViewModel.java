package com.postfive.habit.adpater.myhabit;

import com.postfive.habit.db.UserHabitState;

import java.util.List;

public interface MyHabitRecyclerViewModel {
    public UserHabitState getHabit(int position);

    public List<UserHabitState> getAllHabit();

    public void removeHabit(int position);

    public void changeHabit(int position, UserHabitState habit);

    public void addHabit(UserHabitState habit);

    public void setHabit(List<UserHabitState> habitList);
    public void setAllHabit(List<UserHabitState> allHabit);
}
