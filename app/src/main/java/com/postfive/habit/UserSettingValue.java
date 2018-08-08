package com.postfive.habit;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

public class UserSettingValue {
    private static UserSettingValue mUserSettingValue = null;
    private static SharedPreferences settings = null ;
    private static final String PREFS_NAME = "setValue";
    private Context mContext;

    private static String MORNING_PUSH   = "morningPush"  ;
    private static String AFTERNOON_PUSH = "afternoonPush";
    private static String NIGHT_PUSH     = "nightPush"    ;
    private static String GOODNIGHT_PUSH = "goodNightPush";
    private static String MORNING_FROM   = "morningFrom"  ;
    private static String AFTERNOON_FROM = "afternoonFrom";
    private static String NIGHT_FROM     = "nightFrom"    ;
    private static String RESOLUTION     = "resolution"      ;


    private static String START_DATE     = "startDate"    ;
    private static String END_DATE       = "endDate"      ;
    private static String MAIN_IMG_RESOURCE = "MAIN_IMG_RESOURCE"      ;

    private static int morningPushHour = -1;
    private static int morningPushMinute = -1;
    private static int afternoonPushHour = -1;
    private static int afternoonPushMinute = -1;
    private static int nightPushHour = -1;
    private static int nightPushMinute = -1;
    private static int goodnightPushHour = -1;
    private static int goodnightPushMinute = -1;
    private static int morningFromHour = -1;
    private static int morningFromMinute = -1;
    private static int afternoonFromHour = -1;
    private static int afternoonFromMinute = -1;
    private static int nightFromHour = -1;
    private static int nightFromMinute = -1;
    private static String resolutionValue = ""      ;


    private static String startDate ="";
    private static String endDate ="";


    private static int mainImgResource = -1;

    private UserSettingValue(){

    }

    public UserSettingValue(Context context){
        this.mContext = context;
        if(mUserSettingValue == null) {
            mUserSettingValue = new UserSettingValue();
            settings = context.getSharedPreferences(PREFS_NAME, context.MODE_PRIVATE);
            readValue();
        }
    }


    private boolean setInnerMorningPush(String strTime) {
        int idx = strTime.indexOf(":");

        if(idx < 0)
            return false;

        morningPushHour = Integer.parseInt(strTime.substring(0, idx));
        morningPushMinute = Integer.parseInt(strTime.substring(idx+1, strTime.length()));

        return true;
    }
    private boolean setInnerAfternoonPush(String strTime) {
        int idx = strTime.indexOf(":");

        if(idx < 0)
            return false;

        afternoonPushHour = Integer.parseInt(strTime.substring(0, idx));
        afternoonPushMinute = Integer.parseInt(strTime.substring(idx+1, strTime.length()));

        return true;
    }

    private boolean setInnerNightPush(String strTime) {
        int idx = strTime.indexOf(":");

        if(idx < 0)
            return false;

        nightPushHour = Integer.parseInt(strTime.substring(0, idx));
        nightPushMinute = Integer.parseInt(strTime.substring(idx+1, strTime.length()));

        return true;
    }

    private boolean setInnerGoodnightPush(String strTime) {
        int idx = strTime.indexOf(":");

        if(idx < 0)
            return false;

        goodnightPushHour = Integer.parseInt(strTime.substring(0, idx));
        goodnightPushMinute = Integer.parseInt(strTime.substring(idx+1, strTime.length()));

        return true;
    }

    private boolean setInnerMorningFrom(String strTime) {
        int idx = strTime.indexOf(":");

        if(idx < 0)
            return false;

        morningFromHour = Integer.parseInt(strTime.substring(0, idx));
        morningFromMinute = Integer.parseInt(strTime.substring(idx+1, strTime.length()));

        return true;
    }


    private boolean setInnerAfternoonFrom(String strTime) {

        int idx = strTime.indexOf(":");

        if(idx < 0)
            return false;

        afternoonFromHour = Integer.parseInt(strTime.substring(0, idx));
        afternoonFromMinute = Integer.parseInt(strTime.substring(idx+1, strTime.length()));

        return true;
    }


    private boolean setInnerNightFrom(String strTime) {

        int idx = strTime.indexOf(":");

        if(idx < 0)
            return false;

        nightFromHour = Integer.parseInt(strTime.substring(0, idx));
        nightFromMinute = Integer.parseInt(strTime.substring(idx+1, strTime.length()));

        return true;

    }



    private boolean setInnerResolutionValue(String resolutionValue) {
        if(resolutionValue.length() < 1)
            return false;

        UserSettingValue.resolutionValue = resolutionValue;

        return true;
    }

    private void setInnerStartDate(String startDate) {
        this.startDate = startDate;
    }

    private void setInneMainImgResource(int mainImgResource) {
        this.mainImgResource = mainImgResource;
    }
    private void setInnerEndDate(String endDate) {
        this.endDate = endDate;
    }



    public void resetAll() {
        SharedPreferences.Editor editor = settings.edit();

        // SharedPreference 데이터 넣기 시작
        editor.clear();

        editor.putBoolean("isAppinit", false);
        editor.putInt(MAIN_IMG_RESOURCE, -1);
        // Commit the edits!
        editor.commit();
        UserSettingValue.mainImgResource = -1;

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

        setMainImgResource(-1);
        // Commit the edits!
        editor.commit();
        // SharedPreference 데이터 넣기 종료

        // 설정값 변수 설정 시작
        setInnerMorningPush("08:00");
        setInnerAfternoonPush("11:30");
        setInnerNightPush("16:30");
        setInnerGoodnightPush("22:00");

        setInnerMorningFrom("04:00");
        setInnerAfternoonFrom("11:30");
        setInnerNightFrom("18:00");

        setInnerResolutionValue("안녕하세요! 비스켓과 함께 건강한 생활 만들어가요!");
        setInneMainImgResource(-1);

        // 설정값 변수 설정 종료

        //Toast.makeText(mContext, "설정값 초기화", //Toast.LENGTH_LONG).show();
    }

    /**
     *
     * @return 최초 실행 여부
     * 최초실행시 : false / 최초실행이 아닐시 : true
     */
    public boolean init() {
        boolean getBoolean = settings.getBoolean("isAppinit", false);

        if (getBoolean) {
            readValue();
            Log.d("setting","초기화 되어 있음" );
            return false;
        } else{
            Log.d("setting","초기화 시작" );
            resetValue();
            Log.d("setting","초기화 종료" );
            return true;
        }
    }


    private void readValue(){

        setInnerMorningPush(settings.getString(MORNING_PUSH, "08:00"));
        setInnerAfternoonPush(settings.getString(AFTERNOON_PUSH, "11:30"));
        setInnerNightPush(settings.getString(NIGHT_PUSH, "16:30"));
        setInnerGoodnightPush(settings.getString(GOODNIGHT_PUSH, "22:00"));
        setInnerMorningFrom(settings.getString(MORNING_FROM, "04:00"));
        setInnerAfternoonFrom(settings.getString(AFTERNOON_FROM, "11:30"));
        setInnerNightFrom(settings.getString(NIGHT_FROM, "18:00"));

        setInnerResolutionValue(settings.getString(RESOLUTION, "안녕하세요! 비스켓과 함께 건강한 생활 만들어가요!"));

        setInnerEndDate(settings.getString(END_DATE, ""));
        setInnerStartDate(settings.getString(START_DATE, ""));
        setInneMainImgResource(settings.getInt(MAIN_IMG_RESOURCE, -1));

        Log.d("setting", " 초기화 "+Integer.toString(getAfternoonPushHour()));

    }
    public static int getMorningPushHour() {
        return morningPushHour;
    }

    public static int getMorningPushMinute() {
        return morningPushMinute;
    }

    public static int getAfternoonPushHour() {
        return afternoonPushHour;
    }

    public static int getAfternoonPushMinute() {
        return afternoonPushMinute;
    }

    public static int getNightPushHour() {
        return nightPushHour;
    }

    public static int getNightPushMinute() {
        return nightPushMinute;
    }

    public static int getGoodnightPushHour() {
        return goodnightPushHour;
    }

    public static int getGoodnightPushMinute() {
        return goodnightPushMinute;
    }

    public static int getMorning() {
        return morningFromHour *100 + morningFromMinute;
    }

    public static int getAfternoon() {
        return afternoonFromHour *100 + afternoonFromMinute;
    }

    public static int getNight() {
        return nightFromHour *100 + nightFromMinute;
    }

    public static String getResolutionValue() {
        return resolutionValue;
    }



    public static boolean setResolutionValue(String resolutionValue) {

        SharedPreferences.Editor editor = settings.edit();
        editor.putString(RESOLUTION, resolutionValue);

        if(editor.commit()){
            UserSettingValue.resolutionValue = resolutionValue;
            return true;
        }else{
            return false;
        }
    }

    public static String getStartDate() {
        return startDate;
    }

    /**
     * setStartDate
     * 시작일 설정
     *
     * @param startDate 시작일
     * @return 실패 : false
     *          성공 : true
     */
    public static Boolean setStartDate(String startDate) {

        SharedPreferences.Editor editor = settings.edit();
        editor.putString(START_DATE, startDate);

        if(editor.commit()){
            UserSettingValue.startDate = startDate;
            return true;
        }else{
            return false;
        }

    }



    public static String getEndDate() {
        return endDate;
    }

    /**
     * setEndDate
     * 종료일 설정
     *
     * @param endDate :  종료일
     * @return 실패 : false
     *          성공 : true
     */
    public static boolean setEndDate(String endDate) {

        SharedPreferences.Editor editor = settings.edit();
        editor.putString(END_DATE, endDate);

        if(editor.commit()){
            UserSettingValue.endDate = endDate;
            return true;
        }else{
            return false;
        }
    }

    public static int getMainImgResource() {
        return mainImgResource;
    }

    /**
     * setMainImgResource
     * 시작일 설정
     *
     * @param mainImgResource :  dwrable
     * @return 실패 : false
     *          성공 : true
     */

    public static boolean setMainImgResource(int mainImgResource) {
        SharedPreferences.Editor editor = settings.edit();

        editor.putInt(MAIN_IMG_RESOURCE, mainImgResource);

        if(editor.commit()){
            UserSettingValue.mainImgResource = mainImgResource;
            return true;
        }else{
            return false;
        }
    }


}
