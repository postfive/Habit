package com.postfive.habit.habits.storage;

import com.postfive.habit.db.Habit;
import com.postfive.habit.habits.factory.OldHabit;

public class UserSetHabit extends Habit {

    @Override
    public void prepare() {
        setHabitcode(0);
        setName("줄넘기하기");
        setUnitcode(2);
        setTime(2);
        setFull(10);
        setOnce(1);
        setDaysum(130);
    }
}
