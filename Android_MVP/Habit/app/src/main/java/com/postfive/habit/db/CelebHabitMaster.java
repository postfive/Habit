package com.postfive.habit.db;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

@Entity(tableName ="celeb_habit_m",
        primaryKeys = { "celebcode"})
public class CelebHabitMaster {
    @NonNull
    @ColumnInfo(name ="name")
    private String name;  // 유명인사 이름

    @NonNull
    @ColumnInfo(name ="celebcode")
    private int celebcode; // 유명인사 코드

    @NonNull
    @ColumnInfo(name ="title")
    private String title;  // 제목

    @NonNull
    @ColumnInfo(name ="subtitle")
    private String subtitle; // 부제목

    @NonNull
    @ColumnInfo(name ="resolution")
    private String resolution; // 다짐


    @NonNull
    @ColumnInfo(name ="img")
    private String img; // 이미지


    public CelebHabitMaster(@NonNull String name, @NonNull int celebcode, @NonNull String title, @NonNull String subtitle, @NonNull String resolution, @NonNull String img ){
        this.name = name;
        this.celebcode = celebcode;
        this.title = title;
        this.subtitle = subtitle;
        this.resolution = resolution;
        this.img = img;
    }

    @NonNull
    public String getName() {
        return name;
    }

    public void setName(@NonNull String name) {
        this.name = name;
    }

    @NonNull
    public int getCelebcode() {
        return celebcode;
    }

    public void setCelebcode(@NonNull int celebcode) {
        this.celebcode = celebcode;
    }

    @NonNull
    public String getTitle() {
        return title;
    }

    public void setTitle(@NonNull String title) {
        this.title = title;
    }

    @NonNull
    public String getSubtitle() {
        return subtitle;
    }

    public void setSubtitle(@NonNull String subtitle) {
        this.subtitle = subtitle;
    }

    @NonNull
    public String getResolution() {
        return resolution;
    }

    public void setResolution(@NonNull String resolution) {
        this.resolution = resolution;
    }


    @NonNull
    public String getImg() {
        return img;
    }

    public void setImg(@NonNull String img) {
        this.img = img;
    }
}
