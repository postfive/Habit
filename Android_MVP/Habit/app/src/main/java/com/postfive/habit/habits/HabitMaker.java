package com.postfive.habit.habits;

import com.postfive.habit.db.Habit;

import com.postfive.habit.habits.factory.HabitFactory;
import com.postfive.habit.habits.storage.DrinkWaterHabit;
import com.postfive.habit.habits.storage.PreStudyHabit;
import com.postfive.habit.habits.storage.SkipRopeHabit;
import com.postfive.habit.habits.storage.UserSetHabit;

public class HabitMaker extends HabitFactory {
    @Override
    public Habit createHabit(int habitId) {

        Habit result = null;

        if(habitId == 1){
            result = new DrinkWaterHabit();
        }else if (habitId == 2){
            result = new SkipRopeHabit();
        }else if (habitId == 3){
            result = new PreStudyHabit();
        }else if (habitId == 0){
            result = new UserSetHabit();
        }

        return result;
    }

}

