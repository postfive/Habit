package com.postfive.habit.db;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.support.annotation.NonNull;

@Entity(tableName ="celeb_habit_d",
        primaryKeys = { "celebcode", "time","priority"})
public class CelebHabitDetail {
    @NonNull
    @ColumnInfo(name ="celebcode")
    public int celebcode; // 유명인사 1 2 3

    @NonNull
    @ColumnInfo(name ="time")
    public int time;   // 1 : mornig  / 2 : after / 3 : night / 0 : all

    @NonNull
    @ColumnInfo(name ="priority")
    public int priority; // 우선순위 1 2 3 4

    @NonNull
    @ColumnInfo(name ="habitcode")
    public int habitcode; // 습관 코드 0001 0002

    @NonNull
    @ColumnInfo(name ="name")
    public String name; // 목표 문구

    @NonNull
    @ColumnInfo(name ="goal")
    public String goal; // 상투적인 목표 문구

    @NonNull
    @ColumnInfo(name ="daysum")
    public int daysum; // 주

    @NonNull
    @ColumnInfo(name ="full")
    public int full; // 하루 할양


    @NonNull
    @ColumnInfo(name ="once")
    public int once; // 1회 양

    @NonNull
    @ColumnInfo(name ="unit")
    public String unit; // 단위

    @NonNull
    @ColumnInfo(name ="img")
    public String img; // 이미지

    public CelebHabitDetail(@NonNull int celebcode, @NonNull int time, @NonNull int priority, @NonNull int habitcode, @NonNull String name, @NonNull String goal, @NonNull int daysum, @NonNull int full, @NonNull int once, @NonNull String unit, @NonNull String img) {
        this.celebcode = celebcode;
        this.time = time;
        this.priority = priority;
        this.habitcode = habitcode;
        this.name = name;
        this.goal = goal;
        this.daysum = daysum;
        this.full = full;
        this.once = once;
        this.unit = unit;
        this.img = img;
    }

    @NonNull
    public int getCelebcode() {
        return celebcode;
    }

    public void setCelebcode(@NonNull int celebcode) {
        this.celebcode = celebcode;
    }

    @NonNull
    public int getTime() {
        return time;
    }

    public void setTime(@NonNull int time) {
        this.time = time;
    }

    @NonNull
    public int getPriority() {
        return priority;
    }

    public void setPriority(@NonNull int priority) {
        this.priority = priority;
    }

    @NonNull
    public int getHabitcode() {
        return habitcode;
    }

    public void setHabitcode(@NonNull int habitcode) {
        this.habitcode = habitcode;
    }

    @NonNull
    public String getName() {
        return name;
    }

    public void setName(@NonNull String name) {
        this.name = name;
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

    @NonNull
    public String getImg() {
        return img;
    }

    public void setImg(@NonNull String img) {
        this.img = img;
    }

    @NonNull
    public int getOnce() {
        return once;
    }

    public void setOnce(@NonNull int once) {
        this.once = once;
    }

    @NonNull
    public String getGoal() {
        return goal;
    }

    public void setGoal(@NonNull String goal) {
        this.goal = goal;
    }
}
