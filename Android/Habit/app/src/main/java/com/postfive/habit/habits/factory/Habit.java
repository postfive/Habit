package com.postfive.habit.habits.factory;

public abstract class Habit {
    protected String goal;
    protected int dayofWeek;
    protected int full;
    protected int dayFull;
    protected int count;
    protected String unit;
    protected String type;
    protected String time;
    String key = "";



    public Habit() {
    }

    public abstract void prepare();


    public String getGoal() {
        return goal;
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

    public int getCount() {
        return count;
    }


    public String getUnit() {
        return unit;
    }


    public void setGoal(String mGoal) {
        this.goal = mGoal;
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

    public void setFull(int mFull) {
        this.full = mFull;
    }

    public void setCount(int mCount) {
        this.count = mCount;
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

    public void doit(){

    }

    protected String getType() {
        return type;
    }

    protected void setType(String mType) {
        this.type = mType;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }
}
