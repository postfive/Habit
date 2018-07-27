package com.postfive.habit.habits.storage;

import com.postfive.habit.db.Habit;
import com.postfive.habit.habits.factory.OldHabit;

public class SkipRopeHabit extends Habit {


    public SkipRopeHabit( ) {
    }

    @Override
    public void prepare() {
        setHabitcode(3);
        setName("줄넘기하기");
        setType("skiprope");
        setUnitcode(2);
        setTime(1);
        setFull(10);
        setOnce(1);
        setDaysum(130);
    }
}
