package com.postfive.habit.habits.factory;

public abstract class HabitFactory {
    public abstract Habit createHabit(int habitID);
    public abstract Habit createHabit(String habitType);
    public abstract Class getHabit(String habitType);
    public abstract String getUnit(String habitType);

}
