package com.postfive.habit.habits.storage;

import com.postfive.habit.habits.factory.Habit;

public class PreStudyHabit extends Habit {


    public PreStudyHabit( ) {
    }

    @Override
    public void prepare() {
        setGoal("예습하기");
        setFull(66*1);
        setDayFull(1);
        setUnit("시간");
        setCount(1);
        setType("prestudy");
        for(int i = 1 ;  i < 8 ; i++)
            setDayofWeek(i, true);

    }
}
