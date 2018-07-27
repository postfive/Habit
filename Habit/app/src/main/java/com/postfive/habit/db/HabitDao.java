package com.postfive.habit.db;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import java.util.List;

import static android.arch.persistence.room.OnConflictStrategy.IGNORE;

@Dao
public interface HabitDao {

    @Query("SELECT * FROM habit_m")
    LiveData<List<Habit>> allHabitLive();

    @Query("SELECT * FROM habit_m")
    List<Habit> allHabit();

    @Query("SELECT * FROM habit_m where habitcode =:habitcode")
    Habit selectHabit(int habitcode);


    @Query("SELECT max(habitcode) FROM habit_m")
    int getMaxCode();

    @Insert(onConflict = IGNORE)
    void insertAllHabit(List<Habit> habit);

    @Insert(onConflict = IGNORE)
    void insertHabit(Habit habit);

    @Query("DELETE FROM HABIT_M")
    void delAllHabit();

}
