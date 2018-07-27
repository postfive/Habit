package com.postfive.habit.habits.storage;

import com.postfive.habit.db.Habit;

public class PreStudyHabit extends Habit {


    public PreStudyHabit( ) {
    }

    @Override
    public void prepare() {
        setHabitcode(1);
        setName("예습하기");
        setType("prestudy");
        setUnitcode(2);
        setTime(3);
        setFull(1);
        setOnce(1);
        setDaysum(30);
    }
}
