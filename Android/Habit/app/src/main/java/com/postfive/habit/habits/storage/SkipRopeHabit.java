package com.postfive.habit.habits.storage;

import com.postfive.habit.habits.factory.Habit;

public class SkipRopeHabit extends Habit {


    public SkipRopeHabit( ) {
    }

    @Override
    public void prepare() {
        setGoal("줄넘기하기");
        setFull(66*1);
        setDayFull(1);
        setUnit("회");
        setCount(1);
        setTime("night");
        setType("skiprope");

        for(int i = 1 ;  i < 8 ; i++)
            setDayofWeek(i, true);
        
    }
}
