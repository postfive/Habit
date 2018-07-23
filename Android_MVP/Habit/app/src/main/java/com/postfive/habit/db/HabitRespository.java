package com.postfive.habit.db;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.os.AsyncTask;

import java.util.List;
import java.util.concurrent.ExecutionException;

// 알람 추가할때만 사용
public class HabitRespository {
    static String TAG ="HabitRespository";
    private AppDatabase db ;
    private HabitDao mHabitDao;
    private UserHabitDao mUserHabitDao;
    private CelebHabit mCelebHabitDao;

    private LiveData<List<Habit>> mHabitList;
    private LiveData<List<CelebHabitMaster>> mCelebList;

    public HabitRespository(Application application) {
        this.db = AppDatabase.getInMemoryDatabase(application);

        this.mHabitDao = db.habitModel();
        this.mUserHabitDao = db.userhabitModel();
        this.mCelebHabitDao = db.celebModel();

//        this.mHabitList = this.mHabitDao.allHabitLive();
        this.mCelebList = this.mCelebHabitDao.allHabitLive();
    }

    public void destroyInstance(){
        db.destroyInstance();
        this.mHabitDao = null;
        this.mUserHabitDao = null;
        this.mCelebHabitDao = null;
    }


    /* 알람 새로 추가하는 부분에서만 사용할 것 */
    /* 다른데는 일괄적으로 데이터 넣어놓을 것이기 때문에 필요 없은 다른것은 구현 안할꺼임 */

    // 전체 가져오기 실시간
    public LiveData<List<Habit>> getAllHabitLive() {return mHabitList; }
    // 전체 가져오기 실시간

    // 전체 가져오기 한번에
    public List<Habit> getAllHabit(){
        List<Habit> habitList = null;

        try {
            habitList = new QueryHabitListAsyncTask(mHabitDao).execute().get();
        } catch (InterruptedException e){
            e.printStackTrace();
        } catch (ExecutionException e){
            e.printStackTrace();
        }
        return habitList;
    }


    // 전체 가져오기 실시간
    public LiveData<List<CelebHabitMaster>> getAllCelebLive() {return mCelebList; }
    // 전체 가져오기 실시간




    private class QueryHabitListAsyncTask extends AsyncTask<Void, Void, List<Habit>> {
        private HabitDao mAsyncTaskHabitDao;

        QueryHabitListAsyncTask(HabitDao mHabitDao) {
            this.mAsyncTaskHabitDao = mHabitDao;
        }

        @Override
        protected List<Habit> doInBackground(Void... voids) {
            List<Habit> habit = mAsyncTaskHabitDao.allHabit();
            return habit;
        }

        @Override
        protected void onPostExecute(List<Habit> habit) {
            super.onPostExecute(habit);
        }

    }

    // 하나 선택
    public Habit getHabit(int code){
        Habit habit = null;
        try {
            habit = new QueryHabitAsyncTask(mHabitDao).execute(code).get();
        } catch (InterruptedException e){
            e.printStackTrace();
        } catch (ExecutionException e){
            e.printStackTrace();
        }
        return habit;
    }

    private class QueryHabitAsyncTask extends AsyncTask<Integer, Void, Habit> {
        private HabitDao mAsyncTaskHabitDao;

        QueryHabitAsyncTask(HabitDao mHabitDao) {
            this.mAsyncTaskHabitDao = mHabitDao;
        }

        @Override
        protected Habit doInBackground(Integer... integers) {
            Habit habit = mAsyncTaskHabitDao.selectHabit(integers[0]);
            return habit;
        }

        @Override
        protected void onPostExecute(Habit habit) {
            super.onPostExecute(habit);
        }
    }





    //// celeb


    public List<CelebHabitMaster> getAllCelebHabitMater() {
        List<CelebHabitMaster>  celebHabitMaster = null;

        try {
            celebHabitMaster = new QueryCelebMasterListAsyncTask(mCelebHabitDao).execute().get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }


        return celebHabitMaster;
    }




    private class QueryCelebMasterListAsyncTask extends AsyncTask<Void, Void, List<CelebHabitMaster>> {
        private CelebHabit mCelebHabit;

        QueryCelebMasterListAsyncTask(CelebHabit mCelebHabit) {
            this.mCelebHabit = mCelebHabit;
        }

        @Override
        protected List<CelebHabitMaster> doInBackground(Void... voids) {
            List<CelebHabitMaster> habit = mCelebHabit.selectAllCelebMaster();
            return habit;
        }

    }

    public CelebHabitMaster getCelebHabitMater(int code) {

        CelebHabitMaster celebHabitMaster = null;

        try {
            celebHabitMaster = new QueryCelebMasterAsyncTask(mCelebHabitDao).execute(code).get();

        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }


        return celebHabitMaster;
    }




    private class QueryCelebMasterAsyncTask extends AsyncTask<Integer, Void, CelebHabitMaster> {
        private CelebHabit mCelebHabit;

        QueryCelebMasterAsyncTask(CelebHabit mCelebHabit) {
            this.mCelebHabit = mCelebHabit;
        }

        @Override
        protected CelebHabitMaster doInBackground(Integer...integers) {
            CelebHabitMaster habit = mCelebHabit.selectCelebMaster(integers[0]);
            return habit;
        }

    }
    public List<CelebHabitDetail> getAllCelebHabitDetail() {
        List<CelebHabitDetail> habitList = null;

        try {
            habitList = new QueryCelebListAsyncTask(mCelebHabitDao).execute().get();
        } catch (InterruptedException e){
            e.printStackTrace();
        } catch (ExecutionException e){
            e.printStackTrace();
        }
        return habitList;
    }


    private class QueryCelebListAsyncTask extends AsyncTask<Void, Void, List<CelebHabitDetail>> {
        private CelebHabit mCelebHabit;

        QueryCelebListAsyncTask(CelebHabit mCelebHabit) {
            this.mCelebHabit = mCelebHabit;
        }

        @Override
        protected List<CelebHabitDetail> doInBackground(Void... voids) {
            List<CelebHabitDetail> habit = mCelebHabit.selectAllCelebDetail();
            return habit;
        }

    }




    public List<CelebHabitDetail> getCelebHabit(int celebcode) {
        List<CelebHabitDetail> habitList = null;

        try {
            habitList = new SelectCelebListAsyncTask(mCelebHabitDao).execute(celebcode).get();

        } catch (InterruptedException e){
            e.printStackTrace();
        } catch (ExecutionException e){
            e.printStackTrace();
        }
        return habitList;
    }


    private class SelectCelebListAsyncTask extends AsyncTask<Integer, Void, List<CelebHabitDetail>> {
        private CelebHabit mCelebHabit;

        SelectCelebListAsyncTask(CelebHabit mCelebHabit) {
            this.mCelebHabit = mCelebHabit;
        }

        @Override
        protected List<CelebHabitDetail> doInBackground(Integer... integers) {
            List<CelebHabitDetail> habit = mCelebHabit.selectCelebDetail(integers[0]);
            return habit;
        }

    }


    public List<CelebHabitDetail> selectTimeCeleb(int time, int celebcode) {
        Integer[] input = new Integer[2];
        input[0] = time;
        input[1] = celebcode;

        List<CelebHabitDetail> habit = null;
        try {
            habit = new SelectTimeCeleb(mCelebHabitDao).execute(input).get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        return habit;

    }

    private class SelectTimeCeleb extends AsyncTask<Integer[] , Void, List<CelebHabitDetail>> {
        CelebHabit mCelebHabitDao;
        SelectTimeCeleb(CelebHabit mCelebHabitDao) {
            this.mCelebHabitDao = mCelebHabitDao;
        }

        @Override
        protected List<CelebHabitDetail> doInBackground(Integer[]... integers) {
            Integer[] input  = integers[0];
            List<CelebHabitDetail> result = mCelebHabitDao.selectTimeCeleb(input[0], input[1]);
            return result;
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




    public void insertHabit(Habit habit){
        new InsertHabitListAsynkTask(mHabitDao).execute(habit);
    }

    private class InsertHabitListAsynkTask extends AsyncTask<Habit, Void, Void>{
        private HabitDao mHabitDao;

        public InsertHabitListAsynkTask(HabitDao mHabitDao) {
            this.mHabitDao = mHabitDao;
        }

        @Override
        protected Void doInBackground(Habit... habits) {
            mHabitDao.insertHabit(habits[0]);
            return null;
        }
    }


    public void insertAllHabit(List<Habit> unitList) {

        new InsertAllHabitListAsynkTask(mHabitDao).execute(unitList);
    }

    private class InsertAllHabitListAsynkTask extends AsyncTask<List<Habit>, Void, Void>{
        private HabitDao mHabitDao;

        public InsertAllHabitListAsynkTask(HabitDao mHabitDao) {
            this.mHabitDao = mHabitDao;
        }

        @Override
        protected Void doInBackground(List<Habit>... lists) {
            List<Habit> habitList = lists[0];
            mHabitDao.insertAllHabit(habitList);
            return null;
        }
    }



    public void deleteAll() {
        new DeleteAllAsyncTask(mUserHabitDao, mHabitDao, mCelebHabitDao).execute();
    }

    private class DeleteAllAsyncTask extends AsyncTask<Void, Void, Void>{
        UserHabitDao mUserHabitDao;
        HabitDao mHabitDao;
        CelebHabit mCelebHabit;
        public DeleteAllAsyncTask(UserHabitDao mUserHabitDao
                , HabitDao mHabitDao
                , CelebHabit mCelebHabit) {
            this.mUserHabitDao = mUserHabitDao;
            this.mHabitDao = mHabitDao;
            this.mCelebHabit = mCelebHabit;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            mUserHabitDao.deleteAllUnit();;
            mUserHabitDao.deleteAllUserHabitDetail();;
            mUserHabitDao.deleteAllUserHabitState();;
            mHabitDao.delAllHabit();
            mCelebHabit.delAllCelebHabitDetail();
            mCelebHabit.delAllCelebHabitMaster();
            return null;
        }
    }


    public void insertCelebHabitDetail(CelebHabitDetail celebHabitDetail){
        new InsertCelebHabitDetailAsyncTask(mCelebHabitDao).execute(celebHabitDetail);
    }


    private class InsertCelebHabitDetailAsyncTask extends AsyncTask<CelebHabitDetail, Void, Void> {
        private CelebHabit mCelebHabitDao;

        InsertCelebHabitDetailAsyncTask(CelebHabit mCelebHabitDao) {
            this.mCelebHabitDao = mCelebHabitDao;
        }

        @Override
        protected Void doInBackground(CelebHabitDetail... celebHabitDetails) {
            mCelebHabitDao.insertCelebDetail(celebHabitDetails[0]);
            return null;
        }
    }

    public void insertCelebHabitMaster(CelebHabitMaster celebHabitMaster){
        new InsertCelebHabitMasterAsyncTask(mCelebHabitDao).execute(celebHabitMaster);
    }
    private class InsertCelebHabitMasterAsyncTask extends AsyncTask<CelebHabitMaster, Void, Void> {
        private CelebHabit mCelebHabitDao;

        InsertCelebHabitMasterAsyncTask(CelebHabit mCelebHabitDao) {
            this.mCelebHabitDao = mCelebHabitDao;
        }

        @Override
        protected Void doInBackground(CelebHabitMaster... celebHabitMasters) {
            mCelebHabitDao.insertCelebMaster(celebHabitMasters[0]);
            return null;
        }
    }



}
