package com.postfive.habit;

import android.content.Context;
import android.content.SharedPreferences;
import android.widget.Toast;

public class UserSettingValue {
    private static UserSettingValue mUserSettingValue = null;
    private static SharedPreferences settings = null ;
    private static final String PREFS_NAME = "setValue";
    private Context mContext;

    public static String MORNING_PUSH   = "morningPush"  ;
    public static String AFTERNOON_PUSH = "afternoonPush";
    public static String NIGHT_PUSH     = "nightPush"    ;
    public static String GOODNIGHT_PUSH = "goodNightPush";
    public static String MORNING_FROM   = "morningFrom"  ;
//    public static String MORNING_TO     = "morningTo"    ;
    public static String AFTERNOON_FROM = "afternoonFrom";
//    public static String AFTERNOON_TO   = "afternoonTo"  ;
    public static String NIGHT_FROM     = "nightFrom"    ;
    public static String RESOLUTION     = "resolution"      ;

    private static int MORNING_PUSH_HOUR      = -1;
    private static int MORNING_PUSH_MINUTE    = -1;
    private static int AFTERNOON_PUSH_HOUR    = -1;
    private static int AFTERNOON_PUSH_MINUTE  = -1;
    private static int NIGHT_PUSH_HOUR        = -1;
    private static int NIGHT_PUSH_MINUTE      = -1;
    private static int GOODNIGHT_PUSH_HOUR    = -1;
    private static int GOODNIGHT_PUSH_MINUTE  = -1;
    private static int MORNING_FROM_HOUR      = -1;
    private static int MORNING_FROM_MINUTE    = -1;
//    private static int MORNING_TO_HOUR        = -1;
//    private static int MORNING_TO_MINUTE      = -1;
    private static int AFTERNOON_FROM_HOUR    = -1;
    private static int AFTERNOON_FROM_MINUTE  = -1;
//    private static int AFTERNOON_TO_HOUR      = -1;
//    private static int AFTERNOON_TO_MINUTE    = -1;
    private static int NIGHT_FROM_HOUR        = -1;
    private static int NIGHT_FROM_MINUTE      = -1;
    private static String RESOLUTION_VALUE     = ""      ;


    private UserSettingValue(){

    }

    public UserSettingValue(Context context){
        this.mContext = context;
        if(mUserSettingValue == null) {
            mUserSettingValue = new UserSettingValue();
            settings = context.getSharedPreferences(PREFS_NAME, context.MODE_PRIVATE);
        }
    }


    public static boolean setMorningPush(String strTime) {
        int idx = strTime.indexOf(":");

        if(idx < 0)
            return false;

        MORNING_PUSH_HOUR   = Integer.parseInt(strTime.substring(0, idx));
        MORNING_PUSH_MINUTE = Integer.parseInt(strTime.substring(idx+1, strTime.length()-1));
/*
        SharedPreferences.Editor editor = settings.edit();
        editor.putString(MORNING_PUSH, strTime);*/

//        return editor.commit();
        return true;
    }
    public static boolean setAfternoonPush(String strTime) {
        int idx = strTime.indexOf(":");

        if(idx < 0)
            return false;

        AFTERNOON_PUSH_HOUR   = Integer.parseInt(strTime.substring(0, idx));
        AFTERNOON_PUSH_MINUTE = Integer.parseInt(strTime.substring(idx+1, strTime.length()-1));

/*
        SharedPreferences.Editor editor = settings.edit();
        editor.putString(MORNING_PUSH, strTime);*/

//        return editor.commit();
        return true;
    }

    public static boolean setNightPush(String strTime) {
        int idx = strTime.indexOf(":");

        if(idx < 0)
            return false;

        NIGHT_PUSH_HOUR   = Integer.parseInt(strTime.substring(0, idx));
        NIGHT_PUSH_MINUTE = Integer.parseInt(strTime.substring(idx+1, strTime.length()-1));

/*
        SharedPreferences.Editor editor = settings.edit();
        editor.putString(MORNING_PUSH, strTime);*/

//        return editor.commit();
        return true;
    }

    public static boolean setGoodnightPush(String strTime) {
        int idx = strTime.indexOf(":");

        if(idx < 0)
            return false;

        GOODNIGHT_PUSH_HOUR = Integer.parseInt(strTime.substring(0, idx));
        GOODNIGHT_PUSH_MINUTE = Integer.parseInt(strTime.substring(idx+1, strTime.length()-1));

/*
        SharedPreferences.Editor editor = settings.edit();
        editor.putString(MORNING_PUSH, strTime);*/

//        return editor.commit();
        return true;
    }

    public static boolean setMorningFrom(String strTime) {
        int idx = strTime.indexOf(":");

        if(idx < 0)
            return false;

        MORNING_FROM_HOUR = Integer.parseInt(strTime.substring(0, idx));
        MORNING_FROM_MINUTE = Integer.parseInt(strTime.substring(idx+1, strTime.length()-1));

/*
        SharedPreferences.Editor editor = settings.edit();
        editor.putString(MORNING_PUSH, strTime);*/

//        return editor.commit();
        return true;
    }


    public static boolean setAfternoonFrom(String strTime) {

        int idx = strTime.indexOf(":");

        if(idx < 0)
            return false;

        AFTERNOON_FROM_HOUR = Integer.parseInt(strTime.substring(0, idx));
        AFTERNOON_FROM_MINUTE = Integer.parseInt(strTime.substring(idx+1, strTime.length()-1));

/*
        SharedPreferences.Editor editor = settings.edit();
        editor.putString(MORNING_PUSH, strTime);*/

//        return editor.commit();
        return true;
    }


    public static boolean setNightFrom(String strTime) {

        int idx = strTime.indexOf(":");

        if(idx < 0)
            return false;

        NIGHT_FROM_HOUR = Integer.parseInt(strTime.substring(0, idx));
        NIGHT_FROM_MINUTE = Integer.parseInt(strTime.substring(idx+1, strTime.length()-1));

/*
        SharedPreferences.Editor editor = settings.edit();
        editor.putString(MORNING_PUSH, strTime);*/

//        return editor.commit();
        return true;

    }



    public static boolean setResolutionValue(String resolutionValue) {
        if(resolutionValue.length() < 1)
            return false;

        RESOLUTION_VALUE = resolutionValue;

/*
        SharedPreferences.Editor editor = settings.edit();
        editor.putString(MORNING_PUSH, strTime);*/

//        return editor.commit();
        return true;
    }



    /**
     *  resetValue()
     *  초기화 함수
     *
     */
    public void resetValue(){
        SharedPreferences.Editor editor = settings.edit();

        // SharedPreference 데이터 넣기 시작
        editor.clear();

        editor.putBoolean("isAppinit", true);
        editor.putString("morningPush", "08:00");
        editor.putString("afternoonPush", "11:30");
        editor.putString("nightPush", "16:30");
        editor.putString("goodNightPush", "22:00");

        editor.putString("morningFrom", "04:00");
        editor.putString("morningTo", "11:30");
        editor.putString("afternoonFrom", "11:30");
        editor.putString("afternoonTo", "18:00");
        editor.putString("nightFrom", "18:00");
        editor.putString("nightTo", "16:30");

        editor.putString("resolution", "안녕하세요! 비스켓과 함께 건강한 생활 만들어가요!");

        // Commit the edits!
        editor.commit();
        // SharedPreference 데이터 넣기 종료

        // 설정값 변수 설정 시작
        setMorningPush("08:00");
        setAfternoonPush("11:30");
        setNightPush("16:30");
        setGoodnightPush("22:00");
        setMorningFrom("04:00");
        setAfternoonFrom("11:30");
        setNightFrom("18:00");

        setResolutionValue("안녕하세요! 비스켓과 함께 건강한 생활 만들어가요!");

        // 설정값 변수 설정 종료

//        Toast.makeText(mContext, "설정값 초기화", Toast.LENGTH_LONG).show();
    }

    /**
     *  setValue
     *  시간 및 목표 설정
     *
     * @param strKey
     * @param strValue
     * @return
     */
    private boolean setValue(String strKey, String strValue){

        // value값 확인
        if(strValue == null || strValue.length() <1)
            return false;

        // Key 값 확인
        if(strKey == null || strKey.length() <1)
            return false;

        int idx = strValue.indexOf(":");

        if(strKey.equals(MORNING_PUSH)){
            if(idx < 0)
                return false;

            MORNING_PUSH_HOUR   = Integer.parseInt(strValue.substring(0, idx));
            MORNING_PUSH_MINUTE = Integer.parseInt(strValue.substring(idx+1, strValue.length()-1));
        }
        else if(strKey.equals(AFTERNOON_PUSH)){
            if(idx < 0)
                return false;

            AFTERNOON_PUSH_HOUR   = Integer.parseInt(strValue.substring(0, idx));
            AFTERNOON_PUSH_MINUTE = Integer.parseInt(strValue.substring(idx+1, strValue.length()-1));
        }
        else if(strKey.equals(NIGHT_PUSH)){
            if(idx < 0)
                return false;

            NIGHT_PUSH_HOUR   = Integer.parseInt(strValue.substring(0, idx));
            NIGHT_PUSH_MINUTE = Integer.parseInt(strValue.substring(idx+1, strValue.length()-1));
        }
        else if(strKey.equals(GOODNIGHT_PUSH)){
            if(idx < 0)
                return false;

            GOODNIGHT_PUSH_HOUR   = Integer.parseInt(strValue.substring(0, idx));
            GOODNIGHT_PUSH_MINUTE = Integer.parseInt(strValue.substring(idx+1, strValue.length()-1));
        }
        else if(strKey.equals(MORNING_FROM)){
            if(idx < 0)
                return false;

            MORNING_FROM_HOUR   = Integer.parseInt(strValue.substring(0, idx));
            MORNING_FROM_MINUTE = Integer.parseInt(strValue.substring(idx+1, strValue.length()-1));
        }
        /*else if(strKey.equals(MORNING_TO)){
            if(idx < 0)
                return false;

            MORNING_TO_HOUR   = Integer.parseInt(strValue.substring(0, idx));
            MORNING_TO_MINUTE = Integer.parseInt(strValue.substring(idx+1, strValue.length()-1));
        }*/
        else if(strKey.equals(AFTERNOON_FROM)){
            if(idx < 0)
                return false;

            AFTERNOON_FROM_HOUR   = Integer.parseInt(strValue.substring(0, idx));
            AFTERNOON_FROM_MINUTE = Integer.parseInt(strValue.substring(idx+1, strValue.length()-1));
        }
        /*else if(strKey.equals(AFTERNOON_TO)){
            if(idx < 0)
                return false;

            AFTERNOON_TO_HOUR   = Integer.parseInt(strValue.substring(0, idx));
            AFTERNOON_TO_MINUTE = Integer.parseInt(strValue.substring(idx+1, strValue.length()-1));
        }*/
        else if(strKey.equals(NIGHT_FROM)){
            if(idx < 0)
                return false;

            NIGHT_FROM_HOUR   = Integer.parseInt(strValue.substring(0, idx));
            NIGHT_FROM_MINUTE = Integer.parseInt(strValue.substring(idx+1, strValue.length()-1));
        }
        else if(strKey.equals(RESOLUTION)){
            RESOLUTION_VALUE = strValue;
        }
        else{
            return false;
        }

        SharedPreferences.Editor editor = settings.edit();
        editor.putString(strKey, strValue);

        return editor.commit();

    }

    /**
     *
     * @return 최초 실행 여부
     * 최초실행시 : false / 최초실행이 아닐시 : true
     */
    public boolean init() {
        boolean getBoolean = settings.getBoolean("isAppinit", false);

//        Toast.makeText(mContext, "SharedPreference read", Toast.LENGTH_LONG).show();

        if (getBoolean) {
//            Toast.makeText(mContext, "최초 실행 아님", Toast.LENGTH_LONG).show();
            readValue();
            return false;
        } else{
//            Toast.makeText(mContext, "최초 실행", Toast.LENGTH_LONG).show();
            resetValue();
            return true;
        }
    }


    private void readValue(){

        setMorningPush(settings.getString("morningPush", "08:00"));
        setAfternoonPush(settings.getString("afternoonPush", "11:30"));
        setNightPush(settings.getString("nightPush", "16:30"));
        setGoodnightPush(settings.getString("goodNightPush", "22:00"));
        setMorningFrom(settings.getString("morningFrom", "04:00"));
        setAfternoonFrom(settings.getString("afternoonFrom", "11:30"));
        setNightFrom(settings.getString("nightFrom", "18:00"));

        setResolutionValue(settings.getString("resolution", "안녕하세요! 비스켓과 함께 건강한 생활 만들어가요!"));

//        Toast.makeText(mContext, "설정값 읽어오기 ", Toast.LENGTH_LONG).show();

    }
    public static int getMorningPushHour() {
        return MORNING_PUSH_HOUR;
    }

    public static int getMorningPushMinute() {
        return MORNING_PUSH_MINUTE;
    }

    public static int getAfternoonPushHour() {
        return AFTERNOON_PUSH_HOUR;
    }

    public static int getAfternoonPushMinute() {
        return AFTERNOON_PUSH_MINUTE;
    }

    public static int getNightPushHour() {
        return NIGHT_PUSH_HOUR;
    }

    public static int getNightPushMinute() {
        return NIGHT_PUSH_MINUTE;
    }

    public static int getGoodnightPushHour() {
        return GOODNIGHT_PUSH_HOUR;
    }

    public static int getGoodnightPushMinute() {
        return GOODNIGHT_PUSH_MINUTE;
    }

    public static int getMorning() {
        return MORNING_FROM_HOUR *100 + MORNING_FROM_MINUTE;
    }

    public static int getAfternoon() {
        return AFTERNOON_FROM_HOUR*100 + AFTERNOON_FROM_MINUTE;
    }

    public static int getNight() {
        return NIGHT_FROM_HOUR*100 + NIGHT_FROM_MINUTE;
    }

    public static String getResolutionValue() {
        return RESOLUTION_VALUE;
    }

}
