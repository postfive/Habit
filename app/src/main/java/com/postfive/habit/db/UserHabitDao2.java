package com.postfive.habit.db;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Transaction;
import android.arch.persistence.room.Update;

import java.util.List;

import static android.arch.persistence.room.OnConflictStrategy.IGNORE;

@Dao
public abstract class UserHabitDao2 {



    /*=========== 추가 시작 ============*/
    @Insert(onConflict = IGNORE)
    abstract void insertUserHabitDetail(UserHabitDetail a);

    // 유저 습관 디테일 수정
    @Insert(onConflict = IGNORE)
    abstract void insertUserHabitState(UserHabitState a);



    @Insert(onConflict = IGNORE)
    abstract void insertAllUserHabitDetail(List<UserHabitDetail> a);


    // 유저 습관 상태 추가
    @Insert(onConflict = IGNORE)
    abstract void insertAllUserHabitState(List<UserHabitState> a);
    /*=========== 추가 끝 ============*/


    @Update
    abstract void updateUserHabitDetail(UserHabitDetail a);

    /*=========== 삭제 시작 ============*/
    // 유저 습관 디테일 삭제
    @Delete
    abstract void deleteUserHabitDetail(UserHabitDetail a);

    // 유저 습관 디테일 수정
    @Delete
    abstract void deleteUserHabitState(UserHabitState a);


    // 유저 습관 디테일 삭제
    @Query("DELETE FROM USER_HABIT_D")
    abstract void deleteAllUserHabitDetail();

    // 유저 습관 디테일 수정
    @Query("DELETE FROM USER_HABIT_S")
    abstract void deleteAllUserHabitState();

    @Query("DELETE FROM USER_HABIT_S WHERE masterseq =:masterseq")
    abstract void deleteUserHabitState(int masterseq);
    /*=========== 삭제 끝 ============*/


    @Transaction
    void updatehabit(UserHabitDetail oldDetail, UserHabitDetail newDetail, UserHabitState oldState, UserHabitState newState){
        deleteUserHabitDetail(oldDetail);
        deleteUserHabitState(oldState);
        insertUserHabitDetail(newDetail);
        insertUserHabitState(newState);
    }

    @Transaction
    void insertUserHabit(UserHabitDetail userHabitDetail, List<UserHabitState> userHabitStateList ){
        insertUserHabitDetail(userHabitDetail);
        insertAllUserHabitState(userHabitStateList);
    }

    @Transaction
    void insertUserAllHabit(List<UserHabitDetail> userHabitDetail, List<UserHabitState> userHabitStateList ){
        deleteAllUserHabitDetail();
        deleteAllUserHabitState();
        insertAllUserHabitDetail(userHabitDetail);
        insertAllUserHabitState(userHabitStateList);
    }
    @Transaction
    void updateUserHabit(UserHabitDetail mHabit, int habitseq, List<UserHabitState> mUserHabitStateList) {
        updateUserHabitDetail(mHabit);
        deleteUserHabitState(habitseq);
        insertAllUserHabitState(mUserHabitStateList);
    }
}
