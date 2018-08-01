package com.postfive.habit.db;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import java.util.List;

import static android.arch.persistence.room.OnConflictStrategy.IGNORE;

@Dao
public interface CelebHabit {
    // 그냥 전체 리스트
    @Query("SELECT * FROM CELEB_HABIT_D" +
            " where celebcode =:celebcode")
    List<CelebHabitDetail> selectCelebDetail(int celebcode);

    @Query("SELECT * FROM CELEB_HABIT_D")
    List<CelebHabitDetail> selectAllCelebDetail();


    @Query("SELECT * FROM CELEB_HABIT_M" +
            " where celebcode =:celebcode")
    CelebHabitMaster selectCelebMaster(int celebcode);

    @Query("SELECT * FROM CELEB_HABIT_M")
    List<CelebHabitMaster> selectAllCelebMaster();


    @Insert
    void insertCelebMaster(CelebHabitMaster celebHabitMaster);

    @Insert
    void insertAllCelebMaster(List<CelebHabitMaster> celebHabitMaster);

    @Insert(onConflict = IGNORE)
    void insertCelebDetail(CelebHabitDetail detail);


    // 시간별로 get
    @Query("SELECT * FROM CELEB_HABIT_D" +
            " where time=:time AND  celebcode =:celebcode")
    List<CelebHabitDetail> selectTimeCeleb(int time, int celebcode);




    @Query("DELETE FROM CELEB_HABIT_M")
    void delAllCelebHabitDetail();

    @Query("DELETE FROM CELEB_HABIT_D")
    void delAllCelebHabitMaster();


    @Query("SELECT * FROM CELEB_HABIT_M")
    LiveData<List<CelebHabitMaster>> allHabitLive();


    @Insert(onConflict = IGNORE)
    void insertAllKit(List<CelebHabitKit> celebHabitKits);

    @Query("SELECT * FROM celeb_kit WHERE CELEBCODE =:celebcode")
    List<CelebHabitKit> selectKit(int celebcode);
}
