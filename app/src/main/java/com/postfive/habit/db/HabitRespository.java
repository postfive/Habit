package com.postfive.habit.db;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.os.AsyncTask;

import java.util.List;
import java.util.concurrent.ExecutionException;

// 알람 추가할때만 사용
public class HabitRespository {
    static String TAG ="HabitRespository";
    private AppDatabase db ;
    private HabitDao mHabitDao;
    private UserHabitDao mUserHabitDao;
    private UserHabitDao2 mUserHabitDao2;
    private CelebHabit mCelebHabitDao;

    private LiveData<List<Habit>> mHabitList;
    private LiveData<List<CelebHabitMaster>> mCelebList;


    public HabitRespository(Application application) {
        this.db = AppDatabase.getInMemoryDatabase(application);
        this.mHabitDao = db.habitModel();
        this.mUserHabitDao = db.userhabitModel();
        this.mUserHabitDao2 = db.userhabitModel2();
        this.mCelebHabitDao = db.celebModel();

        this.mHabitList = this.mHabitDao.allHabitLive();
        this.mCelebList = this.mCelebHabitDao.allHabitLive();
    }

    public void destroyInstance(){
        db.destroyInstance();
        this.mHabitDao = null;
        this.mUserHabitDao = null;
        this.mUserHabitDao2 = null;
        this.mCelebHabitDao = null;
    }

    // 전체 가져오기 실시간
    public LiveData<List<Habit>> getAllHabitLive() {return mHabitList; }
    public LiveData<List<CelebHabitMaster>> getAllCelebLive() {return mCelebList; }


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
        new DeleteAllAsyncTask(mUserHabitDao2, mHabitDao, mCelebHabitDao).execute();
    }

    private class DeleteAllAsyncTask extends AsyncTask<Void, Void, Void>{
        UserHabitDao2 mUserHabitDao2;
        HabitDao mHabitDao;
        CelebHabit mCelebHabit;


        public DeleteAllAsyncTask(UserHabitDao2 mUserHabitDao2
                , HabitDao mHabitDao
                , CelebHabit mCelebHabit) {
            this.mUserHabitDao2 = mUserHabitDao2;
            this.mHabitDao = mHabitDao;
            this.mCelebHabit = mCelebHabit;
        }


        @Override
        protected Void doInBackground(Void... voids) {
            mUserHabitDao2.deleteAllUnit();
            mUserHabitDao2.deleteAllUserHabitDetail();
            mUserHabitDao2.deleteAllUserHabitState();
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

    public List<CelebHabitKit> getHabitKit(int celebcode){
        List<CelebHabitKit> celebHabitKits = null;

        try {
            celebHabitKits = new QueryHabitKitAsyncTask(mCelebHabitDao).execute(celebcode).get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        return celebHabitKits;
    }

    private class QueryHabitKitAsyncTask extends AsyncTask<Integer, Void, List<CelebHabitKit>>{
        CelebHabit mCelebHabit;
        public QueryHabitKitAsyncTask(CelebHabit celebHabit) {
            this.mCelebHabit = celebHabit;
        }

        @Override
        protected List<CelebHabitKit> doInBackground(Integer... integers) {
            List<CelebHabitKit> celebHabitKits = mCelebHabit.selectKit(integers[0]);
            return celebHabitKits;
        }
    }

    public void insertHabitKit(List<CelebHabitKit> celebHabitKits){
        new InsertHabitKitAsyncTask(mCelebHabitDao).execute(celebHabitKits);
    }

    private class InsertHabitKitAsyncTask extends AsyncTask<List<CelebHabitKit>, Void, Void>{
        CelebHabit mCelebHabitDao;
        public InsertHabitKitAsyncTask(CelebHabit mCelebHabitDao) {
            this.mCelebHabitDao = mCelebHabitDao;
        }

        @Override
        protected Void doInBackground(List<CelebHabitKit>... lists) {
            mCelebHabitDao.insertAllKit(lists[0]);
            return null;
        }
    }
}
