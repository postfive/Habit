package com.postfive.habit.view.statistics;

import android.arch.persistence.room.ColumnInfo;
import android.support.annotation.NonNull;

public class HabitStatistics {

    @NonNull
    @ColumnInfo(name = "habitseq")
    private String habitseq;
    @NonNull
    @ColumnInfo(name = "name")
    private String name;
    @NonNull
    @ColumnInfo(name = "did")
    private int did;
    @NonNull
    @ColumnInfo(name = "goal")
    private int goal;

    public HabitStatistics(@NonNull String habitseq, @NonNull String name, @NonNull int did, @NonNull int goal) {
        this.habitseq = habitseq;
        this.name = name;
        this.did = did;
        this.goal = goal;
    }

    public String getHabitseq() {
        return habitseq;
    }
    @NonNull
    public void setHabitseq(String habitseq) {
        this.habitseq = habitseq;
    }

    public String getName() {
        return name;
    }
    @NonNull
    public void setName(String name) {
        this.name = name;
    }

    public int getDid() {
        return did;
    }
    @NonNull
    public void setDid(int did) {
        this.did = did;
    }

    public int getGoal() {
        return goal;
    }
    @NonNull
    public void setGoal(int goal) {
        this.goal = goal;
    }
}
