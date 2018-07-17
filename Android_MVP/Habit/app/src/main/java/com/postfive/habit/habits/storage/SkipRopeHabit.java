package com.postfive.habit.habits.storage;

import com.postfive.habit.habits.factory.Habit;

public class SkipRopeHabit extends Habit {


    public SkipRopeHabit( ) {
    }

    @Override
    public void prepare() {
        setGoal("줄넘기하기");
        setTypeName("줄넘기하기");
        setFull(66*1);
        setDayFull(1);
        setUnit("회");
        setOnce(1);
        setType("skiprope");

        addUnitList("회");
        addUnitList("번");

        for(int i = 1 ;  i < 8 ; i++)
            setDayofWeek(i, true);
        
    }
}
