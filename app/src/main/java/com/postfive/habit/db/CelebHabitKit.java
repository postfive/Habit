package com.postfive.habit.db;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.support.annotation.NonNull;

@Entity(tableName ="celeb_kit",
        primaryKeys = { "celebcode", "name"})
public class CelebHabitKit {
    @NonNull
    @ColumnInfo(name="celebcode")
    private int celebcode;

    @NonNull
    @ColumnInfo(name ="name")
    private String name;

    @NonNull
    @ColumnInfo(name ="explanation")
    private String explanation;

    @NonNull
    @ColumnInfo(name ="drawable")
    private int drawable;

    /**
     *
     * @param celebcode   : 유명인 코드
     * @param name        : 이름
     * @param explanation : 설명
     * @param drawable    : 이미지리소스
     */
    public CelebHabitKit(@NonNull int celebcode, @NonNull String name, @NonNull String explanation, @NonNull int drawable) {
        this.celebcode = celebcode;
        this.name = name;
        this.explanation = explanation;
        this.drawable = drawable;
    }

    @NonNull
    public int getCelebcode() {
        return celebcode;
    }

    public void setCelebcode(@NonNull int celebcode) {
        this.celebcode = celebcode;
    }

    @NonNull
    public String getName() {
        return name;
    }

    public void setName(@NonNull String name) {
        this.name = name;
    }

    @NonNull
    public String getExplanation() {
        return explanation;
    }

    public void setExplanation(@NonNull String explanation) {
        this.explanation = explanation;
    }

    @NonNull
    public int getDrawable() {
        return drawable;
    }

    public void setDrawable(@NonNull int drawable) {
        this.drawable = drawable;
    }
}
