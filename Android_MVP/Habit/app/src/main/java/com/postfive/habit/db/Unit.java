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
    public static int LIQUID_UNIT = 1; // 액체

    @Ignore
    public static int NUMBER_UNIT = 2; // 횟수


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
