package com.postfive.habit.view.statistics;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Ignore;
import android.support.annotation.NonNull;

import java.util.Date;

public class HabitStatisticsCalendar {


    @NonNull
    @ColumnInfo(name = "dayofweek")
    private String dayofweek;

    @NonNull
    @ColumnInfo(name = "did")
    private int did;
    @NonNull
    @ColumnInfo(name = "goal")
    private int goal;

    @Ignore
    private Date date;



    public HabitStatisticsCalendar(@NonNull String dayofweek, @NonNull int did, @NonNull int goal) {
        this.dayofweek = dayofweek;
        this.did = did;
        this.goal = goal;
    }

    @NonNull
    public String getDayofweek() {
        return dayofweek;
    }

    public void setDayofweek(@NonNull String dayofweek) {
        this.dayofweek = dayofweek;
    }

    @NonNull
    public int getDid() {
        return did;
    }

    public void setDid(@NonNull int did) {
        this.did = did;
    }

    @NonNull
    public int getGoal() {
        return goal;
    }

    public void setGoal(@NonNull int goal) {
        this.goal = goal;
    }

    @Ignore
    public Date getDate() {
        return date;
    }

    @Ignore
    public void setDate(Date date) {
        this.date = date;
    }
}
