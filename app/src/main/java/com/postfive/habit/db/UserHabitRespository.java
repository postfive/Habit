package com.postfive.habit.db;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.os.AsyncTask;
import android.util.Log;

import com.postfive.habit.UserSettingValue;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class UserHabitRespository {

    private static final String TAG = "UserHabitRespository";
    private AppDatabase db;
    private UserHabitDao mUserHabitDao;
    private UserHabitDao2 mUserHabitDao2;
    private LiveData<List<UserHabitDetail>> mUserHabitList;
    private LiveData<List<UserHabitState>> mTodayHabitList;
    private LiveData<List<UserHabitState>> mYesterdayHabitList;
    private LiveData<List<UserHabitState>> mTomorrowHabitList;


    public UserHabitRespository(Application application ){
        this.db =  AppDatabase.getInMemoryDatabase(application);
        this.mUserHabitDao = db.userhabitModel();
        this.mUserHabitDao2 = db.userhabitModel2();
        this.mUserHabitList = this.mUserHabitDao.getAllHabitLive();
        Calendar day = Calendar.getInstance();
        int today = day.get(Calendar.DAY_OF_WEEK);
        int realTime = day.get(Calendar.HOUR_OF_DAY)*100 + day.get(Calendar.MINUTE);
        int time = 0;


        if(realTime < UserSettingValue.getMorning()){
            // 저녁
            time = 3;
        } else if(realTime >= UserSettingValue.getMorning() && realTime < UserSettingValue.getAfternoon()){
            // 오전
            time = 1;
        }else if(realTime >= UserSettingValue.getAfternoon() && realTime < UserSettingValue.getNight()){
            // 오후
            time = 2;
        }else if(realTime >= UserSettingValue.getNight()){
            // 저녁
            time = 3;
        }

        int yesterday = today - 1;
        int tomorrow = today + 1;
        if(yesterday == 0 ){
            yesterday=7;
        }
        if (tomorrow==8) {
            tomorrow =1;
        }
        this.mTodayHabitList = this.mUserHabitDao.getTodayHabitLive(today);

        // 어제 내일은 시간 상관 없으므로
        this.mYesterdayHabitList = this.mUserHabitDao.getTodayHabitLive(yesterday);
        this.mTomorrowHabitList = this.mUserHabitDao.getTodayHabitLive(tomorrow);

    }

    // 데이터 변경되면 자동 변경 되도록 일단 만들어는 놓는다..
    LiveData<List<UserHabitDetail>> getUserAllHabitLive() {return mUserHabitList;}
    LiveData<List<UserHabitState>> getTodayHabitLive() {return mTodayHabitList;}
    LiveData<List<UserHabitState>> getTomorrowHabitLive() {return mTomorrowHabitList;}
    LiveData<List<UserHabitState>> getYesterdayHabitLive() {return mYesterdayHabitList;}


    public void destroyInstance(){
        db.destroyInstance();
        this.mUserHabitDao = null;
        this.mUserHabitDao2 = null;
    }



    // 전체 습관 get ////////
    public List<UserHabitDetail> getAllHabit(){
        List<UserHabitDetail> userHabitDetailList = null;

        try {
            userHabitDetailList = new QueryUserAllHabitListAsyncTask(mUserHabitDao).execute().get();
        } catch (InterruptedException e){
            e.printStackTrace();
        } catch (ExecutionException e){
            e.printStackTrace();
        }
        return userHabitDetailList;
    }

    public void insertAllUserHabit(List<UserHabitDetail> userHabitDetailList, List<UserHabitState> userHabitStateList) {
        new insertAllUserHabitAsyncTask(mUserHabitDao2, userHabitDetailList, userHabitStateList).execute();

    }

    private class insertAllUserHabitAsyncTask extends AsyncTask<Void, Void, Void> {

        UserHabitDao2 mUserHabitDao2;
        List<UserHabitDetail> mUserHabitDetailList;
        List<UserHabitState> mUserHabitStateList;


        public insertAllUserHabitAsyncTask(UserHabitDao2 mUserHabitDao2, List<UserHabitDetail> userHabitDetailList, List<UserHabitState> userHabitStateList) {
            this.mUserHabitDao2 = mUserHabitDao2;
            this.mUserHabitDetailList = userHabitDetailList;
            this.mUserHabitStateList = userHabitStateList;
        }
        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            Log.d(TAG, "Progress TEST start");
            // show dialog
//            asyncDialog.show();
        }


        @Override
        protected Void doInBackground(Void... voids) {
            mUserHabitDao2.insertUserAllHabit(mUserHabitDetailList, mUserHabitStateList);
            return null;
        }


        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);
            Log.d(TAG, "Progress TEST onProgressUpdate");
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            Log.d(TAG, "Progress TEST onPostExecute");
        }

        @Override
        protected void onCancelled() {
            super.onCancelled();

            Log.d(TAG, "Progress TEST onCancelled");
        }

    }


    public void insertAllUserHabitState(List<UserHabitState> userHabitStateList) {
        new InsertUserAllUserHabitState(mUserHabitDao).execute(userHabitStateList);
    }

    private class InsertUserAllUserHabitState extends AsyncTask<List<UserHabitState>, Void, Void> {

        UserHabitDao mUserHabitDao;
        InsertUserAllUserHabitState(UserHabitDao mUserHabitDao) {
            this.mUserHabitDao = mUserHabitDao;
        }

        @Override
        protected Void doInBackground(List<UserHabitState>... lists) {
            List<UserHabitState> listDetail = lists[0];
            mUserHabitDao.insertAllUserHabitState(listDetail);
            return null;
        }
    }
    public void insertAllUserHabitDetail(List<UserHabitDetail> userHabitDetailList) {
        new InsertUserAllUserHabitDetail(mUserHabitDao).execute(userHabitDetailList);

    }


    private class InsertUserAllUserHabitDetail extends AsyncTask<List<UserHabitDetail>, Void, Void> {

        UserHabitDao mUserHabitDao;
        InsertUserAllUserHabitDetail(UserHabitDao mUserHabitDao) {
            this.mUserHabitDao = mUserHabitDao;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Void doInBackground(List<UserHabitDetail>... lists) {
            List<UserHabitDetail> listDetail = lists[0];
            mUserHabitDao.insertAllUserHabitDetail(listDetail);
            return null;
        }

        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
        }
    }

    public void updateUserHabit(UserHabitDetail mHabit, List<UserHabitState> insertHabitStateList) {

        new UpdateUserHabitAsyncTask(mUserHabitDao2, mHabit, insertHabitStateList).execute();
    }

    private class UpdateUserHabitAsyncTask extends AsyncTask<Void, Void, Void> {
        private UserHabitDao2 mUserHabitDao2;
        private UserHabitDetail mHabit;
        private List<UserHabitState> mUserHabitStateList;
        UpdateUserHabitAsyncTask(UserHabitDao2 mUserHabitDao2, UserHabitDetail mHabit, List<UserHabitState> insertHabitStateList) {
            this.mUserHabitDao2 = mUserHabitDao2;
            this.mHabit = mHabit;
            this.mUserHabitStateList = insertHabitStateList;
        }

        @Override
        protected Void doInBackground(Void... Voids) {
            mUserHabitDao2.updateUserHabit(mHabit, mHabit.getHabitseq(), mUserHabitStateList);
            return null;
        }

    }


    private class QueryUserAllHabitListAsyncTask extends AsyncTask<Void, Void, List<UserHabitDetail> > {
        private UserHabitDao mUserHabitDao;

        QueryUserAllHabitListAsyncTask(UserHabitDao mUserHabitDao) {
            this.mUserHabitDao = mUserHabitDao;
        }

        @Override
        protected List<UserHabitDetail> doInBackground(Void... Voids) {
            List<UserHabitDetail> habit = mUserHabitDao.getAllHabit();
            return habit;
        }

        @Override
        protected void onPostExecute(List<UserHabitDetail> habit) {
            super.onPostExecute(habit);
        }
    }
    // 전체 습관 get 종료 ////////


    public List<UserHabitState> getComplite() {
        List<UserHabitState> userHabitStatesList = null;

        try {
            userHabitStatesList = new QueryUserCompliteHabitListAsyncTask(mUserHabitDao).execute().get();
        } catch (InterruptedException e){
            e.printStackTrace();
        } catch (ExecutionException e){
            e.printStackTrace();
        }
        return userHabitStatesList;
    }


    private class QueryUserCompliteHabitListAsyncTask extends AsyncTask<Integer, Void, List<UserHabitState> > {
        private UserHabitDao mUserHabitDao;

        QueryUserCompliteHabitListAsyncTask(UserHabitDao mUserHabitDao) {
            this.mUserHabitDao = mUserHabitDao;
        }

        @Override
        protected List<UserHabitState> doInBackground(Integer... Integers) {

            List<UserHabitState> habit = mUserHabitDao.getCompliteHabit(Calendar.getInstance().get(Calendar.DAY_OF_WEEK));
            return habit;
        }

        @Override
        protected void onPostExecute(List<UserHabitState> habit) {
            super.onPostExecute(habit);
        }
    }


    public List<UserHabitState> getPassHabit(int nowTime) {
        List<UserHabitState> userHabitStatesList = null;

        try {
            userHabitStatesList = new QueryTodayPassHabitListAsyncTask(mUserHabitDao).execute(nowTime).get();
        } catch (InterruptedException e){
            e.printStackTrace();
        } catch (ExecutionException e){
            e.printStackTrace();
        }
        return userHabitStatesList;
    }


    private class QueryTodayPassHabitListAsyncTask extends AsyncTask<Integer, Void, List<UserHabitState> > {
        private UserHabitDao mUserHabitDao;

        QueryTodayPassHabitListAsyncTask(UserHabitDao mUserHabitDao) {
            this.mUserHabitDao = mUserHabitDao;
        }

        @Override
        protected List<UserHabitState> doInBackground(Integer... Integers) {

            List<Integer> timeList = new ArrayList<>();
            for(int i = 1 ; i < Integers[0] ; i ++){
                timeList.add(i);
            }

            List<UserHabitState> habit = mUserHabitDao.getTodayTimeHabit(Calendar.getInstance().get(Calendar.DAY_OF_WEEK), timeList);
            return habit;
        }

        @Override
        protected void onPostExecute(List<UserHabitState> habit) {
            super.onPostExecute(habit);
        }
    }

    public List<UserHabitState> getNowHabit(int time) {
        List<UserHabitState> userHabitStatesList = null;

        try {
            userHabitStatesList = new QueryUserNowHabitListAsyncTask(mUserHabitDao).execute(time).get();
        } catch (InterruptedException e){
            e.printStackTrace();
        } catch (ExecutionException e){
            e.printStackTrace();
        }
        return userHabitStatesList;
    }

    private class QueryUserNowHabitListAsyncTask extends AsyncTask<Integer, Void, List<UserHabitState> > {
        private UserHabitDao mUserHabitDao;

        QueryUserNowHabitListAsyncTask(UserHabitDao mUserHabitDao) {
            this.mUserHabitDao = mUserHabitDao;
        }

        @Override
        protected List<UserHabitState> doInBackground(Integer... Integers) {

            List<Integer> timeList = new ArrayList<>();
            for(int i = Integers[0] ; i < 4 ; i ++){
                timeList.add(i);
            }
            timeList.add(0);

            List<UserHabitState> habit = mUserHabitDao.getTodayTimeHabit(Calendar.getInstance().get(Calendar.DAY_OF_WEEK), timeList);
            return habit;
        }

        @Override
        protected void onPostExecute(List<UserHabitState> habit) {
            super.onPostExecute(habit);
        }
    }

    // 요일별 습관 get ////////
    public List<UserHabitState> getDayHabit(int dayofweek){
        List<UserHabitState> userHabitStatesList = null;

        try {
            userHabitStatesList = new QueryUserTodayHabitListAsyncTask(mUserHabitDao).execute(dayofweek).get();
        } catch (InterruptedException e){
            e.printStackTrace();
        } catch (ExecutionException e){
            e.printStackTrace();
        }
        return userHabitStatesList;
    }

    private class QueryUserTodayHabitListAsyncTask extends AsyncTask<Integer, Void, List<UserHabitState> > {
        private UserHabitDao mUserHabitDao;

        QueryUserTodayHabitListAsyncTask(UserHabitDao mUserHabitDao) {
            this.mUserHabitDao = mUserHabitDao;
        }

        @Override
        protected List<UserHabitState> doInBackground(Integer... Integers) {
            List<UserHabitState> habit = mUserHabitDao.getTodayHabit(Integers[0]);
            return habit;
        }

        @Override
        protected void onPostExecute(List<UserHabitState> habit) {
            super.onPostExecute(habit);
        }
    }

    /*

        @Query("SELECT MAX(habitseq) FROM USER_HABIT_S ")
    int getMaxSeqUserHabitState();
    // count
    @Query("SELECT MAX(habitseq) FROM USER_HABIT_D ")
    int getMaxSeqUserHabitDetail();

    * */
    // 최고 값
    public int getMaxSeqHabitDetail(){
        int result = 0;
        try {
            result = new QueryMaxSeqHabitDetailAsyncTask(mUserHabitDao).execute().get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        return result;
    }

    private class QueryMaxSeqHabitDetailAsyncTask extends AsyncTask<Void, Void, Integer > {
        private UserHabitDao mUserHabitDao;

        QueryMaxSeqHabitDetailAsyncTask(UserHabitDao mUserHabitDao) {
            this.mUserHabitDao = mUserHabitDao;
        }

        @Override
        protected Integer doInBackground(Void... voids) {
            Integer habit = mUserHabitDao.getMaxSeqUserHabitDetail();
            return habit;
        }

        @Override
        protected void onPostExecute(Integer habit) {
            super.onPostExecute(habit);
        }
    }


    public int getMaxSeqUserHabitState(){
        int result = 0;
        try {
            result = new QueryMaxSeqUserHabitStateAsyncTask(mUserHabitDao).execute().get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        return result;
    }

    private class QueryMaxSeqUserHabitStateAsyncTask extends AsyncTask<Void, Void, Integer > {
        private UserHabitDao mUserHabitDao;

        QueryMaxSeqUserHabitStateAsyncTask(UserHabitDao mUserHabitDao) {
            this.mUserHabitDao = mUserHabitDao;
        }

        @Override
        protected Integer doInBackground(Void... voids) {
            Integer habit = mUserHabitDao.getMaxSeqUserHabitState();
            return habit;
        }

        @Override
        protected void onPostExecute(Integer habit) {
            super.onPostExecute(habit);
        }
    }

    // 우선순위 구하기




/*

    public int getMaxPriorityHabitDetail(int time ) {

        int habit = 0;
        try {
            habit = new QueryMaxPriorityHabitDetail(mUserHabitDao).execute(time).get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        return habit;

    }

    private class QueryMaxPriorityHabitDetail extends AsyncTask<Integer , Void, Integer> {
        UserHabitDao mUserHabitDao;
        QueryMaxPriorityHabitDetail(UserHabitDao mUserHabitDao) {
            this.mUserHabitDao = mUserHabitDao;
        }

        @Override
        protected Integer doInBackground(Integer... integers) {
             Integer result = mUserHabitDao.getMaxPriorityHabitDetail(integers[0]);
            return result;
        }

    }
*/


//
//    public int getMaxPriorityUserHabitState(int time, int dayofweek) {
//        Integer[] input = new Integer[2];
//        input[0] = time;
//        input[1] = dayofweek;
//
//        int habit = 0;
//        try {
//            habit = new QueryMaxPriorityHabitState(mUserHabitDao).execute(input).get();
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        } catch (ExecutionException e) {
//            e.printStackTrace();
//        }
//
//        return habit;
//    }
//
//    private class QueryMaxPriorityHabitState extends AsyncTask<Integer[] , Void, Integer> {
//        UserHabitDao mUserHabitDao;
//        QueryMaxPriorityHabitState(UserHabitDao mUserHabitDao) {
//            this.mUserHabitDao = mUserHabitDao;
//        }
//
//        @Override
//        protected Integer doInBackground(Integer[]... integers) {
//            Integer[] input  = integers[0];
//            Integer result = mUserHabitDao.getMaxPriorityHabitState(input[0], input[1]);
//            return result;
//        }
//
//    }



    public List<UserHabitDetail> getAllUserHabitDetail() {
        List<UserHabitDetail> habitList = null;

        try {

            habitList = new QueryUserDetailListAsyncTask(mUserHabitDao).execute().get();

        } catch (InterruptedException e){
            e.printStackTrace();
        } catch (ExecutionException e){
            e.printStackTrace();
        }
        return habitList;
    }


    private class QueryUserDetailListAsyncTask extends AsyncTask<Void, Void, List<UserHabitDetail>> {
        private UserHabitDao mAsyncTaskHabitDao;

        QueryUserDetailListAsyncTask(UserHabitDao mUserHabitDao) {
            this.mAsyncTaskHabitDao = mUserHabitDao;
        }

        @Override
        protected List<UserHabitDetail> doInBackground(Void... voids) {
            List<UserHabitDetail> habit = mAsyncTaskHabitDao.getAllHabit();
            return habit;
        }

        @Override
        protected void onPostExecute(List<UserHabitDetail> habit) {
            super.onPostExecute(habit);
        }

    }

    public List<UserHabitState> getAllUserHabitState() {
        List<UserHabitState> habitList = null;

        try {
            habitList = new QueryUserStateListAsyncTask(mUserHabitDao).execute().get();
        } catch (InterruptedException e){
            e.printStackTrace();
        } catch (ExecutionException e){
            e.printStackTrace();
        }
        return habitList;
    }


    private class QueryUserStateListAsyncTask extends AsyncTask<Void, Void, List<UserHabitState>> {
        private UserHabitDao mAsyncTaskHabitDao;

        QueryUserStateListAsyncTask(UserHabitDao mUserHabitDao) {
            this.mAsyncTaskHabitDao = mUserHabitDao;
        }

        @Override
        protected List<UserHabitState> doInBackground(Void... voids) {
            List<UserHabitState> habit = mAsyncTaskHabitDao.getAllHabitState();
            return habit;
        }

        @Override
        protected void onPostExecute(List<UserHabitState> habit) {
            super.onPostExecute(habit);
        }

    }
    public List<UserHabitState> getHabitStateMasterSeq(int masterseq) {
        List<UserHabitState> habitList = null;
//        getAllHabitState
        try {
            habitList = new QueryStateListMasterSeqAsyncTask(mUserHabitDao).execute(masterseq).get();
        } catch (InterruptedException e){
            e.printStackTrace();
        } catch (ExecutionException e){
            e.printStackTrace();
        }
        return habitList;
    }


    private class QueryStateListMasterSeqAsyncTask extends AsyncTask<Integer, Void, List<UserHabitState>> {
        private UserHabitDao mHabitDao;

        QueryStateListMasterSeqAsyncTask(UserHabitDao mUserHabitDao) {
            this.mHabitDao = mUserHabitDao;
        }

        @Override
        protected List<UserHabitState> doInBackground(Integer... integers) {
            List<UserHabitState> userHabitStates = mHabitDao.getAllHabitState(integers[0]);
            return userHabitStates;
        }
    }

    /*=========== 추가 시작 ============*/
    public void insertUserHabitDetail(UserHabitDetail a){
        new InsertUserHabitDetailAsyncTask(mUserHabitDao2).execute(a);
    }


    private class InsertUserHabitDetailAsyncTask extends AsyncTask<UserHabitDetail, Void, Void> {
        private UserHabitDao2 mUserHabitDao2;

        InsertUserHabitDetailAsyncTask(UserHabitDao2 mUserHabitDao2) {
            this.mUserHabitDao2 = mUserHabitDao2;
        }

        @Override
        protected Void doInBackground(UserHabitDetail... userHabitDetails) {
            mUserHabitDao2.insertUserHabitDetail(userHabitDetails[0]);
            return null;
        }
    }



    public List<Unit> getUnit(int unitcode){

        List<Unit> unitList = null;
        try {
            unitList = new QueryUnitAsyncTask(mUserHabitDao).execute(unitcode).get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        return unitList;
    }

    private class QueryUnitAsyncTask extends AsyncTask<Integer, Void, List<Unit> > {
        private UserHabitDao mUserHabitDao;

        QueryUnitAsyncTask(UserHabitDao mUserHabitDao) {
            this.mUserHabitDao = mUserHabitDao;
        }

        @Override
        protected List<Unit> doInBackground(Integer... Integers) {
            List<Unit> unitList = mUserHabitDao.getUnit(Integers[0]);
            return unitList;
        }

        @Override
        protected void onPostExecute(List<Unit> unitList) {
            super.onPostExecute(unitList);
        }
    }
    public List<String> getHabitUnit(int habitCode){

        List<String> unitList = null;
        try {
            unitList = new QueryHabitUnitListAsyncTask(mUserHabitDao).execute(habitCode).get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        return unitList;
    }

    private class QueryHabitUnitListAsyncTask extends AsyncTask<Integer, Void, List<String> > {
        private UserHabitDao mUserHabitDao;

        QueryHabitUnitListAsyncTask(UserHabitDao mUserHabitDao) {
            this.mUserHabitDao = mUserHabitDao;
        }

        @Override
        protected List<String> doInBackground(Integer... Integers) {
            List<String> unitList = mUserHabitDao.getHabitUnitList(Integers[0]);
            return unitList;
        }

        @Override
        protected void onPostExecute(List<String> unitList) {
            super.onPostExecute(unitList);
        }
    }

    public void insertUnit(List<Unit> unitList){
        new InsertUnitAsyncTask(mUserHabitDao).execute(unitList);

    }

    private class InsertUnitAsyncTask extends AsyncTask<List<Unit>, Void, Void> {
        private UserHabitDao mUserHabitDao;

        InsertUnitAsyncTask(UserHabitDao mUserHabitDao) {
            this.mUserHabitDao = mUserHabitDao;
        }


        @Override
        protected Void doInBackground(List<Unit>... lists) {
            List<Unit> unit = lists[0];
            mUserHabitDao.insertUnit(unit);
            return null;
        }
    }

//



    public void updateUserHabitState(UserHabitState userHabitState){
        new UpdateUserHabitStateAsyncTask(mUserHabitDao).execute(userHabitState);

    }

    private class UpdateUserHabitStateAsyncTask extends AsyncTask<UserHabitState, Void, Void> {
        private UserHabitDao mUserHabitDao;

        UpdateUserHabitStateAsyncTask(UserHabitDao mUserHabitDao) {
            this.mUserHabitDao = mUserHabitDao;
        }

        @Override
        protected Void doInBackground(UserHabitState... userHabitStates) {
            mUserHabitDao.updateUserHabitState(userHabitStates[0]);
            return null;
        }
    }



//    insertUserHabit
    public void insertUserHabit(UserHabitDetail userHabitDetail, List<UserHabitState> userHabitStates){
        new InsertUserHabitAllAsyncTask(mUserHabitDao2, userHabitDetail, userHabitStates).execute();
    }


    private class InsertUserHabitAllAsyncTask extends AsyncTask<Void, Void, Void> {
        private UserHabitDao2 mUserHabitDao2;
        private UserHabitDetail mUserHabitDetail;
        private List<UserHabitState> mUserHabitStates;


        InsertUserHabitAllAsyncTask(UserHabitDao2 mUserHabitDao2, UserHabitDetail userHabitDetail, List<UserHabitState> userHabitStates) {
            this.mUserHabitDao2 = mUserHabitDao2;
            this.mUserHabitDetail = userHabitDetail;
            this.mUserHabitStates = userHabitStates;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            mUserHabitDao2.insertUserHabit(mUserHabitDetail, mUserHabitStates);
            return null;
        }
    }


}
