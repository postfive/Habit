package com.postfive.habit.habits.storage;

import com.postfive.habit.habits.factory.Habit;

public class UserSetHabit extends Habit {
    @Override
    public void prepare() {
        setGoal("사용자 정의");
        setFull(2*66);
        setDayFull(4);
        setUnit("L");
        setCount(2);
        setType("userset");

        dayofWeek = 124;
//        for(int i = 1 ;  i < 8 ; i++)
//            setDayofWeek(i, true);
    }
}
