package com.postfive.habit.habits.factory;

import com.postfive.habit.db.Habit;

public abstract class HabitFactory {
    public abstract Habit createHabit(int habitCode);
}
