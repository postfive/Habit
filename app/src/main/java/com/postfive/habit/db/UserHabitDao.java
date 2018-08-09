package com.postfive.habit.db;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.postfive.habit.view.statistics.HabitStatistics;
import com.postfive.habit.view.statistics.HabitStatisticsCalendar;

import java.util.List;

import static android.arch.persistence.room.OnConflictStrategy.REPLACE;

@Dao
public interface UserHabitDao {

    @Query("SELECT habitseq, user_habit_d.name, sum(user_habit_s.did) as did, sum(user_habit_s.goal) as goal  " +
            "FROM USER_HABIT_D, " +
            "       user_habit_s " +
            "WHERE USER_HABIT_D.habitseq = USER_HABIT_S.masterseq " +
            "GROUP BY habitseq, user_habit_d.name " +
            "ORDER BY sum(user_habit_s.did) DESC ")
    LiveData<List<HabitStatistics>> getHabitStatics();

    @Query("SELECT USER_HABIT_S.dayofweek, sum(user_habit_s.did) as did, sum(user_habit_s.goal) as goal  " +
            "FROM USER_HABIT_D, " +
            "       user_habit_s " +
            "WHERE USER_HABIT_D.habitseq = USER_HABIT_S.masterseq " +
            "GROUP BY USER_HABIT_S.dayofweek " +
            "ORDER BY USER_HABIT_S.dayofweek DESC ")
    LiveData<List<HabitStatisticsCalendar>> getHabitStaticsCalendar();

    // 전체 습관
    @Query("SELECT * FROM USER_HABIT_D ORDER BY habitseq ASC")
    List<UserHabitDetail> getAllHabit();

    // 오늘 습관
    @Query("SELECT * FROM USER_HABIT_S WHERE DAYOFWEEK = :dayofweek ORDER BY TIME")
    List<UserHabitState> getTodayHabit(int dayofweek);

    // 유저 습관 상태 수정
    @Update(onConflict = REPLACE)
    void updateUserHabitState(UserHabitState a);

    // count
    @Query("SELECT MAX(habitstateseq) FROM USER_HABIT_S ")
    int getMaxSeqUserHabitState();
    // count
    @Query("SELECT MAX(habitseq) FROM USER_HABIT_D ")
    int getMaxSeqUserHabitDetail();

    @Query("SELECT unit_d.unit FROM unit_d WHERE unitcode =:unitcode")
    List<String> getUnit(int unitcode);

    @Query("SELECT unit_d.unit FROM unit_d INNER JOIN habit_m where unit_d.unitcode = habit_m.unitcode and habit_m.habitcode =:habitcode")
    List<String> getHabitUnitList(int habitcode);

    @Insert
    void insertUnit(List<Unit> unit);

    @Query("SELECT * FROM user_habit_s " +
            "WHERE DAYOFWEEK =:dayofweek " +
            "AND TIME IN(:timeList) " +
            "AND DID < GOAL " +
            "ORDER BY TIME, MASTERSEQ ASC")
    List<UserHabitState> getTodayTimeHabit(int dayofweek, List<Integer> timeList);

    @Query("SELECT * FROM user_habit_s " +
            "WHERE DAYOFWEEK =:dayofweek " +
            "AND DID >= GOAL " +
            "ORDER BY TIME, MASTERSEQ ASC")
    List<UserHabitState> getCompliteHabit(int dayofweek);

}
