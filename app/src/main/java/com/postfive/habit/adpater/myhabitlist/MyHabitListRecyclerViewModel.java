package com.postfive.habit.adpater.myhabitlist;

import com.postfive.habit.db.UserHabitDetail;

import java.util.List;

public interface MyHabitListRecyclerViewModel {
    public UserHabitDetail getHabit(int position);

    public List<UserHabitDetail> getAllHabit();

    public void removeHabit(int position);

    public void changeHabit(int position, UserHabitDetail habit);

    public void addHabit(UserHabitDetail habit);

    public void setHabit(List<UserHabitDetail> habitList);

    public void setAllHabit(List<UserHabitDetail> habitList);

    void deleteHabit(UserHabitDetail tmp);
}
