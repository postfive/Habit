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

    @Insert(onConflict = IGNORE)
    void insertAllHabit(List<Habit> habit);

    @Query("DELETE FROM HABIT_M")
    void delAllHabit();

}
