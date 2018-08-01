package com.postfive.habit.db;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.support.annotation.NonNull;

@Entity(tableName ="celeb_habit_d",
        primaryKeys = { "celebcode", "time","seq"})
public class CelebHabitDetail {
    @NonNull
    @ColumnInfo(name ="celebcode")
    public int celebcode; // 유명인사 1 2 3

    @NonNull
    @ColumnInfo(name ="time")
    public int time;   // 1 : mornig  / 2 : after / 3 : night / 0 : all

    @NonNull
    @ColumnInfo(name ="seq")
    public int seq; // 우선순위 1 2 3 4

    @NonNull
    @ColumnInfo(name ="habitcode")
    public int habitcode; // 습관 코드 1 2 3

    @NonNull
    @ColumnInfo(name ="name")
    public String name; // 목표 문구

    @NonNull
    @ColumnInfo(name ="goal")
    public String goal; //

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
    @ColumnInfo(name ="unitcode")
    public int unitcode; // 단위 코드

    @NonNull
    @ColumnInfo(name ="img")
    public String img; // 이미지

    @NonNull
    @ColumnInfo(name ="icon")
    private int icon; // 아이콘

    @NonNull
    @ColumnInfo(name ="color")
    private String color; // 단위

    @NonNull
    @ColumnInfo(name ="comment")
    private String comment; // 코멘트

    @NonNull
    @ColumnInfo(name ="memo")
    private String memo; // 메모

    @NonNull
    @ColumnInfo(name ="drawable")
    private int drawable; // 이미지

    @NonNull
    @ColumnInfo(name ="realime")
    public String realime;


    public CelebHabitDetail(@NonNull int celebcode, @NonNull int seq, @NonNull int time,  @NonNull int habitcode, @NonNull String name, @NonNull String goal, @NonNull String comment, @NonNull String memo, @NonNull String realime, @NonNull int daysum, @NonNull int full, @NonNull int once, @NonNull int unitcode, @NonNull String unit, @NonNull String img, @NonNull String color, @NonNull int icon, @NonNull int drawable) {
        this.celebcode = celebcode;
        this.time      = time;
        this.seq       = seq;
        this.habitcode = habitcode;
        this.name      = name;
        this.goal      = goal;
        this.comment   = comment;
        this.daysum    = daysum;
        this.full      = full;
        this.once      = once;
        this.unit      = unit;
        this.img       = img;
        this.icon      = icon;
        this.color     = color;
        this.memo      = memo;
        this.drawable  = drawable;
        this.realime   = realime;
        this.unitcode  = unitcode;
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
    public int getSeq() {
        return seq;
    }

    public void setSeq(@NonNull int priority) {
        this.seq = priority;
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


    @NonNull
    public int getIcon() {
        return icon;
    }

    public void setIcon(@NonNull int icon) {
        this.icon = icon;
    }

    @NonNull
    public String getColor() {
        return color;
    }

    public void setColor(@NonNull String color) {
        this.color = color;
    }

    @NonNull
    public String getComment() {
        return comment;
    }

    public void setComment(@NonNull String comment) {
        this.comment = comment;
    }

    @NonNull
    public String getMemo() {
        return memo;
    }

    public void setMemo(@NonNull String memo) {
        this.memo = memo;
    }

    @NonNull
    public int getDrawable() {
        return drawable;
    }

    public void setDrawable(@NonNull int drawable) {
        this.drawable = drawable;
    }
    @NonNull
    public String getRealime() {
        return realime;
    }

    public void setRealime(@NonNull String realime) {
        this.realime = realime;
    }

    @NonNull
    public int getUnitcode() {
        return unitcode;
    }

    public void setUnitcode(@NonNull int unitcode) {
        this.unitcode = unitcode;
    }
}
