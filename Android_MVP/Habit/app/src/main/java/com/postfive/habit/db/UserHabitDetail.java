package com.postfive.habit.db;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.support.annotation.NonNull;

import java.io.Serializable;

@Entity(tableName = "user_habit_d",
        primaryKeys = { "habitseq" })
public class UserHabitDetail implements Serializable {


    @NonNull
    @ColumnInfo(name = "habitseq")
    public int habitseq;  // 키값 1 2 3 4 ~

    @NonNull
    @ColumnInfo(name = "habitcode")
    public int habitcode;  // 물마시기 0001

    @NonNull
    @ColumnInfo(name = "priority")
    public int priority; // 1++ 그냥 max

    @NonNull
    @ColumnInfo(name = "time")
    public int time; // 1 : mornig  / 2 : after / 3 : night / 0 : all

/*
    @NonNull
    @ColumnInfo(name = "type")
    public String type; // 줄넘기 skiprope
*/

    @NonNull
    @ColumnInfo(name = "name")
    public String name; // 이름 ex 물마시기

    @NonNull
    @ColumnInfo(name = "goal")
    public String goal; // 목표 문구 개인 설정 가능

    @NonNull
    @ColumnInfo(name = "daysum")
    public int daysum; // 요일표시

    @NonNull
    @ColumnInfo(name = "full")
    public int full; // 하루 할 양

    @NonNull
    @ColumnInfo(name = "once")
    public int once; // 하루 할 양

    @NonNull
    @ColumnInfo(name = "unit")
    public String unit;  // 단위 L ml 번

    @ColumnInfo(name = "img")
    public String img;  // 이미지 url

    @ColumnInfo(name = "color")
    public String color;  // 이미지 url

    public UserHabitDetail(@NonNull int habitseq, @NonNull int habitcode, @NonNull int priority, @NonNull int time, @NonNull String name, @NonNull String goal, @NonNull int daysum, @NonNull int full, @NonNull int once, @NonNull String unit, String img, String color) {
        this.habitseq = habitseq;
        this.habitcode = habitcode;

        this.priority = priority;
        this.time = time;
        this.name = name;
        this.goal = goal;
//        this.type = type;
        this.daysum = daysum;
        this.full = full;
        this.once = once;
        this.unit = unit;
        this.img = img;
        this.color = color;
    }

    public UserHabitDetail(@NonNull int habitseq , @NonNull CelebHabitDetail celebHabit) {
        this.habitseq = habitseq;
        this.habitcode = celebHabit.getHabitcode();
        this.priority = celebHabit.getPriority();
        this.time = celebHabit.getTime();
        this.name = celebHabit.getName();
        this.goal = celebHabit.getGoal();
        this.daysum = celebHabit.getDaysum();
        this.full = celebHabit.getFull();
        this.once = celebHabit.getOnce();
        this.unit = celebHabit.getUnit();
        this.img = celebHabit.getImg();
        this.color = "";
    }

    public UserHabitDetail( @NonNull Habit habit) {
        this.habitseq = 0;
        this.habitcode = habit.getHabitcode();
        this.priority = 0;
        this.time = habit.getTime();
        this.name = habit.getName();
        this.goal = habit.getName();
        this.daysum = habit.getDaysum();
        this.full = habit.getFull();
        this.once = habit.getOnce();
        this.unit = "";
        this.img = habit.getImg();
        this.color = habit.getColor();
    }

    @NonNull
    public int getHabitseq() {
        return habitseq;
    }

    public void setHabitseq(@NonNull int habitseq) {
        this.habitseq = habitseq;
    }
    @NonNull
    public int getTime() {
        return time;
    }

    public void setTime(@NonNull int time) {
        if( this.time == time){
            this.time =0;
        }else {
            this.time = time;
        }


    }

    @NonNull
    public int getPriority() {
        return priority;
    }

    public void setPriority(@NonNull int priority) {
        this.priority = priority;
    }

    @NonNull
    public String getName() {
        return name;
    }

    public void setName(@NonNull String name) {
        this.name = name;
    }

    @NonNull
    public int getHabitcode() {
        return habitcode;
    }

    public void setHabitcode(@NonNull int habitcode) {
        this.habitcode = habitcode;
    }

    @NonNull
    public String getGoal() {
        return goal;
    }

    public void setGoal(@NonNull String goal) {
        this.goal = goal;
    }

    @NonNull
    public int getDaysum() {
        return daysum;
    }

    public void setDaysum(@NonNull int daysum) {
        this.daysum = daysum;
    }

    @NonNull
    public int getFull() {
        return full;
    }

    public void setFull(@NonNull int full) {
        this.full = full;
    }

    @NonNull
    public String getUnit() {
        return unit;
    }

    public void setUnit(@NonNull String unit) {
        this.unit = unit;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }


    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    @NonNull
    public int getOnce() {
        return once;
    }

    public void setOnce(@NonNull int once) {
        this.once = once;
    }

/*
    @NonNull
    public String getType() {
        return type;
    }

    public void setType(@NonNull String type) {
        this.type = type;
    }*/
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