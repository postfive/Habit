package com.postfive.habit.db;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.support.annotation.NonNull;

@Entity(tableName ="unit_d",
        primaryKeys = { "unitcode", "unit" })
public class Unit {


    @NonNull
    @ColumnInfo(name = "unitcode")
    private int unitcode;

    @NonNull
    @ColumnInfo(name = "unit")
    private String unit;

    /* Unit CODE */
    @Ignore
    public static int LIQUID_UNIT = 1; // 시간
    @Ignore
    public static int TIME_UNIT = 2; // 시간
    @Ignore
    public static int COUNT_UNIT = 3; // 횟수
    @Ignore
    public static int SET_UNIT = 4; // 세트
    @Ignore
    public static int WALK_UNIT = 5; // 걸음


    public Unit(@NonNull int unitcode, @NonNull String unit) {
        this.unitcode = unitcode;
        this.unit = unit;
    }

    @NonNull
    public int getUnitcode() {
        return unitcode;
    }

    public void setUnitcode(@NonNull int unitcode) {
        this.unitcode = unitcode;
    }

    @NonNull
    public String getUnit() {
        return unit;
    }

    public void setUnit(@NonNull String unit) {
        this.unit = unit;
    }
}
