package com.postfive.habit.db;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.support.annotation.NonNull;

import java.io.Serializable;

@Entity(tableName ="habit_m",
        primaryKeys = { "habitcode" })
public class Habit  {

    @NonNull
    @ColumnInfo(name = "habitcode")
    private int habitcode; // code 0001 부터

    @NonNull
    @ColumnInfo(name = "name")
    private String name; // 이름 ex 물마시기

    @NonNull
    @ColumnInfo(name = "type")
    private String type; // water

    @NonNull
    @ColumnInfo(name = "daysum")
    private int daysum;   // 기본 주


    @NonNull
    @ColumnInfo(name = "unitcode")
    private int unitcode; // 1 2 3 4 5 6

    @NonNull
    @ColumnInfo(name = "time")
    private int time; // 1 : mornig  / 2 : after / 3 : night / 0 : all

    @NonNull
    @ColumnInfo(name = "full")
    private int full;    // 하루 할양

    @NonNull
    @ColumnInfo(name = "once")
    private int once;    // 한번 할양

    @NonNull
    @ColumnInfo(name = "img")
    private String img;    // 한번 할양

    @NonNull
    @ColumnInfo(name = "color")
    private String color;    // 한번 할양


    @Ignore
    public static int MORNING_TIME = 1;
    @Ignore
    public static int AFTERNOON_TIME = 2;
    @Ignore
    public static int NIGHT_TIME = 3;
    @Ignore
    public static int ALLDAY_TIME = 4;

    public Habit(){

    }
    @Ignore
    public Habit(@NonNull int habitcode, @NonNull String name, @NonNull String type, @NonNull int unitcode, @NonNull int time, @NonNull int full, @NonNull int once, @NonNull int daysum, @NonNull String color, @NonNull String img) {
        this.habitcode = habitcode;
        this.name = name;
        this.type = type;
        this.unitcode = unitcode;
        this.time = time;
        this.full = full;
        this.once = once;
        this.daysum = daysum;
        this.img = img;
        this.color = color;
    }

    public void prepare(){}


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
    public String getType() {
        return type;
    }

    public void setType(@NonNull String type) {
        this.type = type;
    }

    @NonNull
    public int getUnitcode() {
        return unitcode;
    }

    public void setUnitcode(@NonNull int unitcode) {
        this.unitcode = unitcode;
    }

    @NonNull
    public int getTime() {
        return time;
    }

    public void setTime(@NonNull int time) {
        this.time = time;
    }

    @NonNull
    public int getFull() {
        return full;
    }

    public void setFull(@NonNull int full) {
        this.full = full;
    }

    @NonNull
    public int getOnce() {
        return once;
    }

    public void setOnce(@NonNull int once) {
        this.once = once;
    }

    @NonNull
    public int getDaysum() {
        return daysum;
    }

    public void setDaysum(@NonNull int daysum) {
        this.daysum = daysum;
    }


    @NonNull
    public String getImg() {
        return img;
    }

    public void setImg(@NonNull String img) {
        this.img = img;
    }

    @NonNull
    public String getColor() {
        return color;
    }

    public void setColor(@NonNull String color) {
        this.color = color;
    }

}
