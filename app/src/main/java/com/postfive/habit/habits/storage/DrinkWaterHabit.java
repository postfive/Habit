package com.postfive.habit.habits.storage;


import com.postfive.habit.db.Habit;

public class DrinkWaterHabit extends Habit {


    public DrinkWaterHabit( ) {
    }

    @Override
    public void prepare() {
        setHabitcode(1);
        setName("물마시기");
        setUnitcode(1);
        setTime(0);
        setFull(10);
        setOnce(2);
        setDaysum(6);
    }

}
