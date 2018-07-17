package com.postfive.habit.habits.storage;

import com.postfive.habit.habits.factory.Habit;

public class DrinkWaterHabit extends Habit {


    public DrinkWaterHabit( ) {
    }

    @Override
    public void prepare() {
        setGoal("물마시기");
        setTypeName("물마시기");
        setFull(4*66);
        setDayFull(4);
        setUnit("L");
        setOnce(2);
        setType("drinkwater");
        setTime("m");

        addUnitList("L");
        addUnitList("mL");
        addUnitList("cc");

        dayofWeek = 124;

//        for(int i = 1 ;  i < 8 ; i++)
//            setDayofWeek(i, true);
    }


}
