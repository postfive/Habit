package com.postfive.habit.db;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

import static android.arch.persistence.room.OnConflictStrategy.REPLACE;

@Dao
public interface UserHabitDao {

    /*=========== 조회 시작 ============*/
    // 전체 습관
    @Query("SELECT * FROM USER_HABIT_D  ORDER BY TIME, PRIORITY ASC")
    LiveData<List<UserHabitDetail>> getAllHabitLive();

/*    // 현재 할 습관
    @Query("SELECT * FROM USER_HABIT_S " +
            "WHERE DAYOFWEEK = :dayofweek " +
            "AND   (FULL > DID)" +
            "ORDER BY TIME, PRIORITY")
    LiveData<List<UserHabitState>> getTodayHabitLive(int dayofweek, int timeExceptFrom , int timeExceptTo);*/

        // 현재 할 습관
        @Query("SELECT * FROM USER_HABIT_S " +
                "WHERE DAYOFWEEK = :dayofweek " +
                "AND   (FULL > DID)" +
                "ORDER BY TIME, PRIORITY")
        LiveData<List<UserHabitState>> getTodayHabitLive(int dayofweek);
    // 전체 습관
    @Query("SELECT * FROM USER_HABIT_D ORDER BY TIME, PRIORITY ASC")
    List<UserHabitDetail> getAllHabit();

    // 습관 상태 키값으로 찾기
    @Query("SELECT * FROM USER_HABIT_S ORDER BY TIME, PRIORITY ASC")
    List<UserHabitState> getAllHabitState();

    // 습관 상태 키값으로 찾기
    @Query("SELECT * FROM USER_HABIT_S WHERE masterseq =:masterseq")
    List<UserHabitState> getAllHabitState(int masterseq);

    // 오늘 습관
    @Query("SELECT * FROM USER_HABIT_S WHERE DAYOFWEEK = :dayofweek ORDER BY TIME, PRIORITY")
    List<UserHabitState> getTodayHabit(int dayofweek);

    // 선택한 습관
    @Query("SELECT * FROM USER_HABIT_D WHERE habitcode=:habitcode and priority =:masterpriority AND TIME = :time ")
    UserHabitDetail getHabit(int habitcode, int masterpriority, int time);

    // 우선순위 구하기
    @Query("SELECT MAX(PRIORITY) FROM USER_HABIT_D where TIME =:time GROUP BY TIME")
    int getMaxPriorityHabitDetail(int time);

    // 우선순위 구하기
    @Query("SELECT MAX(priority) FROM USER_HABIT_s where TIME =:time AND DAYOFWEEK =:dayofweek GROUP BY TIME")
    int getMaxPriorityHabitState(int time, int dayofweek);

    /*=========== 조회 끝 ============*/

    /*=========== 대량 넣기 끝 ============*/
    @Insert
    void insertAllUserHabitDetail(List<UserHabitDetail> userHabitDetailList);

    @Insert
    void insertAllUserHabitState(List<UserHabitState> userHabitStatesList);
    /*===========  대량 넣기 끝 ============*/

    /*=========== 삭제 시작 ============*/
    // 유저 습관 디테일 삭제
    @Query("DELETE FROM user_habit_d")
    void deleteAllUserHabitDetail();

    // 유저 습관 디테일 수정
    @Query("DELETE FROM user_habit_s")
    void deleteAllUserHabitState();


    // 유저 습관 디테일 수정
    @Query("DELETE FROM unit_d")
    void deleteAllUnit();
    /*=========== 삭제 끝 ============*/



    /*=========== 수정 시작 ============*/
    // 유저 습관 디테일 수정
    @Update(onConflict = REPLACE)
    void updateUserHabitDetail(UserHabitDetail a);

    // 유저 습관 상태 수정
    @Update(onConflict = REPLACE)
    void updateUserHabitState(UserHabitState a);

/*
    // 수행
    @Query("UPDATE USER_HABIT_S SET DID = FULL WHERE DAYOFWEEK =:dayofweek AND TIME =:time AND PRIORITY =:priority")
    void completeOneHabit(int dayofweek, int time, int priority);

    // 그냥 완료
    @Query("UPDATE USER_HABIT_S SET DID = DID + ONCE WHERE DAYOFWEEK =:dayofweek AND TIME =:time AND PRIORITY =:priority")
    void doHabit(int dayofweek, int time, int priority);

    // 수행 취소
    @Query("UPDATE USER_HABIT_S SET DID = DID - ONCE WHERE DAYOFWEEK =:dayofweek AND TIME =:time AND PRIORITY =:priority")
    void cancelHabit(int dayofweek, int time, int priority);
    // 완전 취소
    @Query("UPDATE USER_HABIT_S SET DID = 0 WHERE DAYOFWEEK =:dayofweek AND TIME =:time AND PRIORITY =:priority")
    void initOneHabit(int dayofweek, int time, int priority);*/

    /*=========== 수정 끝 ============*/

    // count
    @Query("SELECT MAX(habitstateseq) FROM USER_HABIT_S ")
    int getMaxSeqUserHabitState();
    // count
    @Query("SELECT MAX(habitseq) FROM USER_HABIT_D ")
    int getMaxSeqUserHabitDetail();

    @Query("SELECT * FROM unit_d WHERE unitcode =:unitcode")
    List<Unit> getUnit(int unitcode);


    @Insert
    void insertUnit(List<Unit> unit);
    // 이거 repository 에 추가

}
