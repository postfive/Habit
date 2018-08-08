package com.postfive.habit.db;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.os.AsyncTask;
import android.provider.ContactsContract;
import android.util.Log;

import com.postfive.habit.UserSettingValue;
import com.postfive.habit.view.statistics.HabitStatistics;
import com.postfive.habit.view.statistics.HabitStatisticsCalendar;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class UserHabitRespository {

    private static final String TAG = "UserHabitRespository";
    private AppDatabase db;
    private UserHabitDao mUserHabitDao;
    private UserHabitDao2 mUserHabitDao2;
    private LiveData<List<HabitStatistics>> mHabitStatisticsList;
    private LiveData<List<HabitStatisticsCalendar>> mHabitStatisticsCalendarList;


    public UserHabitRespository(Application application ){
        this.db =  AppDatabase.getInMemoryDatabase(application);
        this.mUserHabitDao = db.userhabitModel();
        this.mUserHabitDao2 = db.userhabitModel2();

        this.mHabitStatisticsList = db.userhabitModel().getHabitStatics();
        this.mHabitStatisticsCalendarList = db.userhabitModel().getHabitStaticsCalendar();
    }

    // 데이터 변경되면 자동 변경 되도록 일단 만들어는 놓는다..
    LiveData<List<HabitStatistics>> getHabitStatics() {return mHabitStatisticsList;}
    LiveData<List<HabitStatisticsCalendar>> getHabitStaticsCalendar() {return mHabitStatisticsCalendarList;}

    public void destroyInstance(){
        db.destroyInstance();
        this.mUserHabitDao = null;
        this.mUserHabitDao2 = null;
    }



    // 전체 습관 get ////// 사용
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

    // 유저 습관 설정 사용
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
    // 유저 습관 설정 종료

    // 습관 update
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

    public void deleteUserHabitAll(){
        new DeleteUserHabitAllAsyncTask(mUserHabitDao2).execute();
    }
    private class DeleteUserHabitAllAsyncTask extends AsyncTask<Void, Void, Void> {
        private UserHabitDao2 mUserHabitDao2;


        DeleteUserHabitAllAsyncTask(UserHabitDao2 mUserHabitDao2) {
            this.mUserHabitDao2 = mUserHabitDao2;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            mUserHabitDao2.delUserAllHabit();
            return null;
        }
    }

    public void deleteUserHabit(int habitseq){
        new DeleteUserHabitAsyncTask(mUserHabitDao2).execute(habitseq);
    }
    private class DeleteUserHabitAsyncTask extends AsyncTask<Integer, Void, Void> {
        private UserHabitDao2 mUserHabitDao2;


        DeleteUserHabitAsyncTask(UserHabitDao2 mUserHabitDao2) {
            this.mUserHabitDao2 = mUserHabitDao2;
        }

        @Override
        protected Void doInBackground(Integer... integers) {
            this.mUserHabitDao2.delUserHabit(integers[0]);
            return null;
        }
    }


}
