package com.postfive.habit.db;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.support.annotation.NonNull;

@Entity(tableName = "user_habit_s",
        primaryKeys = { "habitstateseq" })
public class UserHabitState {

    @NonNull
    @ColumnInfo(name ="habitstateseq")
    public int habitstateseq;

    @NonNull
    @ColumnInfo(name ="dayofweek")
    public int dayofweek;


    @NonNull
    @ColumnInfo(name ="priority")
    public int priority;  // 1++ 그냥 max


    @NonNull
    @ColumnInfo(name ="habitcode")
    public int habitcode;

/*
    @NonNull
    @ColumnInfo(name ="type")
    public String type;
*/

    @NonNull
    @ColumnInfo(name ="masterseq")
    public int masterseq;  // 1++ 그냥 max //  pk user_habit_d 와 연결되는 priority


    @NonNull
    @ColumnInfo(name = "daysum")
    public int daysum; // 요일표시

    @NonNull
    @ColumnInfo(name ="time")
    public int time; // 1 : mornig  / 2 : after / 3 : night / 0 : all

    @NonNull
    @ColumnInfo(name ="name")
    public String name; // 이름 ex 물마시기

    @NonNull
    @ColumnInfo(name ="goal")
    public String goal; // 이름 보여주는 이름  물마시기

    @NonNull
    @ColumnInfo(name ="did")
    public int did; // 하루 수행한 양

    @NonNull
    @ColumnInfo(name ="once")
    public int once; // 한번 수행할때 양

    @NonNull
    @ColumnInfo(name ="full")
    public int full; // 하루 정한양

    @NonNull
    @ColumnInfo(name ="unit")
    public String unit; // 단위

    public UserHabitState(@NonNull int habitstateseq, @NonNull int habitcode, @NonNull int masterseq, @NonNull int dayofweek, @NonNull int priority, @NonNull int daysum, @NonNull int time, @NonNull String name, @NonNull String goal, @NonNull int did, @NonNull int once, @NonNull int full, @NonNull String unit) {
        this.habitstateseq = habitstateseq;
        this.habitcode = habitcode;
        this.masterseq = masterseq;
        this.dayofweek = dayofweek;
        this.priority = priority;
        this.daysum = daysum;
        this.time = time;
        this.name = name;
        this.goal = goal;
//        this.type = type;
        this.did = did;
        this.once = once;
        this.full = full;
        this.unit = unit;
    }

    public UserHabitState(@NonNull int habitstateseq, @NonNull int priority, @NonNull int dayofweek, UserHabitDetail habitDetail) {
        this.habitstateseq = habitstateseq;
        this.habitcode =  habitDetail.getHabitcode();
        this.masterseq = habitDetail.getHabitseq();
        this.dayofweek = dayofweek;
        this.priority = priority;
        this.daysum = habitDetail.getDaysum();
        this.time = habitDetail.getTime();
        this.name = habitDetail.getName();
        this.goal =habitDetail. getGoal();
        this.did = 0;
        this.once = habitDetail.getOnce();
        this.full = habitDetail.getFull();
        this.unit = habitDetail.getUnit();
    }


    @NonNull
    public int getHabitstateseq() {
        return habitstateseq;
    }

    public void setHabitstateseq(@NonNull int habitstateseq) {
        this.habitstateseq = habitstateseq;
    }

    @NonNull
    public int getHabitcode() {
        return habitcode;
    }

    public void setHabitcode(@NonNull int habitcode) {
        this.habitcode = habitcode;
    }

    @NonNull
    public int getMasterseq() {
        return masterseq;
    }

    public void setMasterseq(@NonNull int masterseq) {
        this.masterseq = masterseq;
    }

    @NonNull
    public int getDayofweek() {
        return dayofweek;
    }

    public void setDayofweek(@NonNull int dayofweek) {
        this.dayofweek = dayofweek;
    }

    @NonNull
    public int getPriority() {
        return priority;
    }

    public void setPriority(@NonNull int priority) {
        this.priority = priority;
    }

    @NonNull
    public int getDaysum() {
        return daysum;
    }

    public void setDaysum(@NonNull int daysum) {
        this.daysum = daysum;
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
    public String getGoal() {
        return goal;
    }

    public void setGoal(@NonNull String goal) {
        this.goal = goal;
    }

    @NonNull
    public int getDid() {
        return did;
    }

    public void setDid(@NonNull int did) {
        this.did = did;
    }

    @NonNull
    public int getOnce() {
        return once;
    }

    public void setOnce(@NonNull int once) {
        this.once = once;
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


/*    @NonNull
    public String getType() {
        return type;
    }

    public void setType(@NonNull String type) {
        this.type = type;
    }*/

}
