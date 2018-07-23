package com.postfive.habit.habits.factory;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class OldHabit implements Serializable {
    protected String goal;
    protected String typeName;
    protected int dayofWeek;
    protected int full;
    protected int dayFull;
    protected int once;
    protected int didDay=0;
    protected String unit;
    protected String type;
    protected List<String> time = new ArrayList<>();
    protected List<String> unitList = new ArrayList<>();
    protected String key = null;

    public OldHabit() {
    }

    public abstract void prepare();


    public String getGoal() {
        return goal;
    }
    public void setGoal(String mGoal) {
        this.goal = mGoal;
    }
/*
    public int [] getDayofWeekArray() {
        int [] result = new int[7];

        for(int i = 1 ; i < 8 ; i ++){

            if((dayofWeek & ( 1<< i) ) > 0){
                result[i-1] = 1;
            }
        }
        return result;
    }*/

    public boolean isDayofWeek(int day) {
        if((dayofWeek & ( 1<< day) ) > 0){
            return true;
        }
        return false;
    }

    public int getFull() {
        return full;
    }

    public int getOnce() {
        return once;
    }


    public String getUnit() {
        return unit;
    }




    /**
     * 요일 설정
     * @param mDayofWeek 일:1 월:2 ~ 토 : 7
     * @param isSet      true : 설정/ false : 해제
     */
    public void setDayofWeek(int mDayofWeek, boolean isSet) {

        if(isSet){
            // 해당요일 설정
            this.dayofWeek |= (1 << mDayofWeek);
        }else {
            // 해당요일 해제
            this.dayofWeek &= ~(1 << mDayofWeek);
        }
    }
    public void setDayofWeek(int DayofWeek) {
        this.dayofWeek = DayofWeek ;
    }
    public int getDayofWeek() {
        return dayofWeek;
    }

    public void setFull(int mFull) {
        this.full = mFull;
    }

    public void setOnce(int mOnce) {
        this.once = mOnce;
    }

    public void setUnit(String mUnit) {
        this.unit = mUnit;
    }

    public int getDayFull() {
        return dayFull;
    }

    public void setDayFull(int mDayFull) {
        this.dayFull = mDayFull;
    }

    public void doIt(){
        didDay+=once;
    }

    public void cancelIt(){
        didDay-=once;

    }
    public int getDidDay() {
        return didDay;
    }
    public void setDidDay(int didDay) {
        this.didDay = didDay;
    }
    public String getType() {
        return type;
    }

    protected void setType(String mType) {
        this.type = mType;
    }

    public List<String> getTime() {
        return time;
    }

    public void setTime(String time) {
        if(time == null || time.length() < 1)
            return;


        for(String tmp : this.time){
            if(tmp.equals(time))
                break;
        }
        this.time.add(time);
    }
    /*public void setTime(List<String> time) {
        for(String tmp : time){
            if(tmp == null){
                continue;
            }
            if(tmp.length() < 1){
                continue;
            }
            this.time.add(tmp);
        }
    }*/
    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public List<String> getUnitList() {
        return unitList;

    }
    public String selectUnitList(int position) {
        return unitList.get(position);
    }

    public void addUnitList(String unit) {
        this.unitList.add(unit);

    }


    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }
}
