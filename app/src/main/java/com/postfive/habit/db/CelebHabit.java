package com.postfive.habit.db;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import java.util.List;

import static android.arch.persistence.room.OnConflictStrategy.IGNORE;

@Dao
public interface CelebHabit {

    @Query("SELECT * FROM CELEB_HABIT_M")
    LiveData<List<CelebHabitMaster>> allHabitLive();

    // 그냥 전체 리스트
    @Query("SELECT * FROM CELEB_HABIT_D" +
            " where celebcode =:celebcode")
    List<CelebHabitDetail> selectCelebDetail(int celebcode);

    @Insert
    void insertCelebMaster(CelebHabitMaster celebHabitMaster);

    @Insert(onConflict = IGNORE)
    void insertCelebDetail(CelebHabitDetail detail);

    @Query("DELETE FROM CELEB_HABIT_M")
    void delAllCelebHabitDetail();

    @Query("DELETE FROM CELEB_HABIT_D")
    void delAllCelebHabitMaster();

    @Insert(onConflict = IGNORE)
    void insertAllKit(List<CelebHabitKit> celebHabitKits);

    @Query("SELECT * FROM celeb_kit WHERE CELEBCODE =:celebcode")
    List<CelebHabitKit> selectKit(int celebcode);
}
