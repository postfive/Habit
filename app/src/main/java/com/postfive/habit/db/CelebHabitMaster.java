package com.postfive.habit.db;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.support.annotation.NonNull;

import java.io.Serializable;

@Entity(tableName ="celeb_habit_m",
        primaryKeys = { "celebcode"})
public class CelebHabitMaster implements Serializable {
    @NonNull
    @ColumnInfo(name ="name")
    public String name;  // 유명인사 이름

    @NonNull
    @ColumnInfo(name ="celebcode")
    public int celebcode; // 유명인사 코드

    @NonNull
    @ColumnInfo(name ="title")
    public String title;  // 제목

    @NonNull
    @ColumnInfo(name ="subtitle")
    public String subtitle; // 부제목1

    @NonNull
    @ColumnInfo(name ="subtitle2")
    public String subtitle2; // 부제목2


    @NonNull
    @ColumnInfo(name ="subtitle3")
    public String subtitle3; // 부제목3

    @NonNull
    @ColumnInfo(name ="img")
    public String img; // 이미지

    @NonNull
    @ColumnInfo(name ="drawable")
    public int drawable; // 이미지


    @NonNull
    @ColumnInfo(name ="drawabledetail")
    public int drawabledetail; // 이미지


    public CelebHabitMaster(@NonNull String name, @NonNull int celebcode, @NonNull String title, @NonNull String subtitle, @NonNull String subtitle2, @NonNull String subtitle3, @NonNull String img, @NonNull int drawable, @NonNull int drawabledetail) {
        this.name = name;
        this.celebcode = celebcode;
        this.title = title;
        this.subtitle = subtitle;
        this.subtitle2 = subtitle2;
        this.subtitle3 = subtitle3;
        this.img = img;
        this.drawable = drawable;
        this.drawabledetail = drawabledetail;
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
    public String getSubtitle2() {
        return subtitle2;
    }

    public void setSubtitle2(@NonNull String subtitle2) {
        this.subtitle2 = subtitle2;
    }

    @NonNull
    public String getSubtitle3() {
        return subtitle3;
    }

    public void setSubtitle3(@NonNull String subtitle3) {
        this.subtitle3 = subtitle3;
    }

    @NonNull
    public String getImg() {
        return img;
    }

    public void setImg(@NonNull String img) {
        this.img = img;
    }

    @NonNull
    public int getDrawable() {
        return drawable;
    }

    public void setDrawable(@NonNull int drawable) {
        this.drawable = drawable;
    }

    @NonNull
    public int getDrawabledetail() {
        return drawabledetail;
    }

    public void setDrawabledetail(@NonNull int drawabledetail) {
        this.drawabledetail = drawabledetail;
    }
}
