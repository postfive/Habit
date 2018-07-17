package com.postfive.habit.habits.storage;

import com.postfive.habit.habits.factory.Habit;

public class PreStudyHabit extends Habit {


    public PreStudyHabit( ) {
    }

    @Override
    public void prepare() {
        setGoal("예습하기");
        setTypeName("예습하기");
        setFull(66*1);
        setDayFull(1);
        setUnit("시간");
        setOnce(1);
        setType("prestudy");
        setTime("n");

        addUnitList("시간");
        addUnitList("분");
        addUnitList("초");

        for(int i = 1 ;  i < 8 ; i++)
            setDayofWeek(i, true);

    }
}
