package com.postfive.habit.db;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.support.annotation.NonNull;

import java.io.Serializable;

@Entity(tableName = "user_habit_d",
        primaryKeys = { "habitseq" })
public class UserHabitDetail implements Serializable {


    @NonNull
    @ColumnInfo(name = "habitseq")
    private int habitseq=-1;  // 키값 1 2 3 4 ~

    @NonNull
    @ColumnInfo(name = "habitcode")
    private int habitcode;  // 물마시기 0001

    @NonNull
    @ColumnInfo(name = "time")
    private int time; // 1 : mornig  / 2 : after / 3 : night / 0 : all

    @NonNull
    @ColumnInfo(name = "name")
    private String name; // 이름 ex 물마시기

    @NonNull
    @ColumnInfo(name = "customname")
    private String customname; // 목표 문구 개인 설정 가능

    @NonNull
    @ColumnInfo(name = "daysum")
    private int daysum; // 요일표시

    @NonNull
    @ColumnInfo(name = "goal")
    private int goal; // 하루 할 양

    @NonNull
    @ColumnInfo(name = "once")
    private int once; // 하루 할 양

    @NonNull
    @ColumnInfo(name = "unit")
    private String unit ="";  // 단위 L ml 번

    @NonNull
    @ColumnInfo(name = "color")
    private String color;  // 이미지 url

    @NonNull
    @ColumnInfo(name ="icon")
    private int icon; // 단위

    public UserHabitDetail(@NonNull int habitseq, @NonNull int habitcode, @NonNull int time, @NonNull String name, @NonNull String customname, @NonNull int daysum, @NonNull int goal, @NonNull int once, @NonNull String unit, @NonNull String color, @NonNull int icon) {
        this.habitseq = habitseq;
        this.habitcode = habitcode;
        this.time = time;
        this.name = name;
        this.customname = customname;
        this.daysum = daysum;
        this.goal = goal;
        this.once = once;
        this.unit = unit;
        this.color = color;
        this.icon = icon;
    }

    public UserHabitDetail(@NonNull int habitseq , @NonNull CelebHabitDetail celebHabit) {
        this.habitseq = habitseq;
        this.habitcode = celebHabit.getHabitcode();
        this.time = celebHabit.getTime();     //
        this.name = celebHabit.getName();
        this.customname = celebHabit.getName();     //
        this.daysum = celebHabit.getDaysum(); //
        this.goal = celebHabit.getFull();     //
        this.once = celebHabit.getOnce();     //
        this.unit = celebHabit.getUnit();     //
        this.color = celebHabit.getColor();   //
        this.icon = celebHabit.getIcon();     //
    }

    public UserHabitDetail(@NonNull int habitseq , @NonNull UserHabitState userHabitState) {
        this.habitseq = userHabitState.getMasterseq();
        this.habitcode = userHabitState.getHabitcode();
        this.time = userHabitState.getTime();
        this.name = userHabitState.getName();
        this.customname = userHabitState.getCustomname();
        this.daysum = userHabitState.getDaysum();
        this.goal = userHabitState.getGoal();
        this.once = userHabitState.getOnce();
        this.unit = userHabitState.getUnit();
        this.color = userHabitState.getColor();
        this.icon = userHabitState.getIcon();
    }

    public UserHabitDetail( @NonNull Habit habit) {
        this.habitseq = 0;
        this.habitcode = habit.getHabitcode();
        this.time = habit.getTime();
        this.name = habit.getName();
        this.customname = habit.getName();
        this.daysum = habit.getDaysum();
        this.goal = habit.getFull();
        this.once = habit.getOnce();
        this.unit = "";
        this.color = habit.getColor();
        this.icon = habit.getIcon();
    }

    @Ignore
    public UserHabitDetail(UserHabitDetail mHabit) {
        this.habitseq = mHabit.getHabitseq();
        this.habitcode = mHabit.getHabitcode();
        this.time = mHabit.getTime();
        this.name = mHabit.getName();
        this.customname = mHabit.getName();
        this.daysum = mHabit.getDaysum();
        this.goal = mHabit.getGoal();
        this.once = mHabit.getOnce();
        this.unit = mHabit.getUnit();
        this.color = mHabit.getColor();
        this.icon = mHabit.getIcon();
    }

    @NonNull
    public int getHabitseq() {
        return habitseq;
    }

    public void setHabitseq(@NonNull int habitseq) {
        this.habitseq = habitseq;
    }

    @NonNull
    public int getHabitcode() {
        return habitcode;
    }

    public void setHabitcode(@NonNull int habitcode) {
        this.habitcode = habitcode;
    }

    @NonNull
    public int getTime() {
        return time;
    }

    public void setTime(@NonNull int time) {
        this.time = time;
    }

    @NonNull
    public String getName() {
        return name;
    }

    public void setName(@NonNull String name) {
        this.name = name;
    }

    @NonNull
    public String getCustomname() {
        return customname;
    }

    public void setCustomname(@NonNull String customname) {
        this.customname = customname;
    }

    @NonNull
    public int getDaysum() {
        return daysum;
    }

    public void setDaysum(@NonNull int daysum) {
        this.daysum = daysum;
    }

    @NonNull
    public int getGoal() {
        return goal;
    }

    public void setGoal(@NonNull int goal) {
        this.goal = goal;
    }

    @NonNull
    public int getOnce() {
        return once;
    }

    public void setOnce(@NonNull int once) {
        this.once = once;
    }

    @NonNull
    public String getUnit() {
        return unit;
    }

    public void setUnit(@NonNull String unit) {
        this.unit = unit;
    }

    @NonNull
    public String getColor() {
        return color;
    }

    public void setColor(@NonNull String color) {
        this.color = color;
    }

    @NonNull
    public int getIcon() {
        return icon;
    }

    public void setIcon(@NonNull int icon) {
        this.icon = icon;
    }

    public void setDaysumUsingOf(int mDayofWeek, boolean isSet) {

        if(isSet){
        // 해당요일 설정
            this.daysum |= (1 << mDayofWeek);
        }else {
        // 해당요일 해제
            this.daysum &= ~(1 << mDayofWeek);
        }
    }
}