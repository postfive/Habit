package com.postfive.habit.db;

import android.arch.persistence.db.SupportSQLiteDatabase;
import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.util.Log;

@Database(entities = { CelebHabitMaster.class
                        ,CelebHabitDetail.class
                        , UserHabitState.class
                        , UserHabitDetail.class
                        , Habit.class
                        , Unit.class}, version = 2)
public abstract class AppDatabase extends android.arch.persistence.room.RoomDatabase {
    private static final String TAG = "AppDatabase";

    private static AppDatabase INSTANCE = null;

    public abstract UserHabitDao userhabitModel();
    public abstract UserHabitDao2 userhabitModel2();
    public abstract CelebHabit celebModel();
    public abstract HabitDao habitModel();

    public static AppDatabase getInMemoryDatabase(Context context) {
        if (INSTANCE == null) {
            INSTANCE =
                    Room.databaseBuilder(context.getApplicationContext(), AppDatabase.class, "Habit_Database")
                    // To simplify the codelab, allow queries on the main thread.
                    // Don't do this on a real app! See PersistenceBasicSample for an example.
//                    .allowMainThreadQueries()
                            //.allowMainThreadQueries() // 메인스레드에서 호출가능하도록
                            .fallbackToDestructiveMigration()// 버전 upgrade 할때 데이터 마이그레이션 없이 다 지우고 할때 이거 호출
                            .addCallback(sRoomDataCallback)
                    .build();

        }
        return INSTANCE;
    }

    private static RoomDatabase.Callback sRoomDataCallback =
            new RoomDatabase.Callback(){

                @Override
                public void onOpen (@NonNull SupportSQLiteDatabase db){
                    super.onOpen(db);
                    Log.d(TAG, "DB TEST onOpen");
                }

                @Override
                public void onCreate(@NonNull SupportSQLiteDatabase db) {
                    super.onCreate(db);
                    Log.d(TAG, "DB TEST onCreate");
                    //new PopulateDbAsync(INSTANCE).execute();

                }
            };

    private static class PopulateDbAsync extends AsyncTask<Void, Void, Void> {

          UserHabitDao mUserHabitDao;
          UserHabitDao2 mUserHabitDao2;
          CelebHabit mCelebModel;
          HabitDao mHabitModel;

        PopulateDbAsync(AppDatabase db) {
            mUserHabitDao = db.userhabitModel();
            mUserHabitDao2 = db.userhabitModel2();
            mCelebModel = db.celebModel();
            mHabitModel = db.habitModel();
        }

        @Override
        protected Void doInBackground(final Void... params) {

//            populateWithTestData(mUserHabitDao, mUserHabitDao2, mCelebModel,  mHabitModel);
            return null;
        }
    }



    public static void destroyInstance() {
        INSTANCE = null;
    }


/*

    private static void populateWithTestData(UserHabitDao mUserHabitDao
                                                , UserHabitDao2 mUserHabitDao2
                                                , CelebHabit mCelebModel
                                                , HabitDao mHabitModel
                                                )
    {
        Log.d(TAG, "초기화 시작");
        // 설정 값 있을때는 그냥 종료
        Log.d(TAG, "초기화 " + Integer.toString(mUserHabitDao.getMaxSeqUserHabitDetail()));
        if(mUserHabitDao.getMaxSeqUserHabitDetail()>1){
            Log.d(TAG, "값 있으니까 초기화 안함  ");
            return;
        }

        mUserHabitDao.deleteAllUserHabitDetail();
        mUserHabitDao.deleteAllUserHabitState();


        Habit drinkwater = new Habit(mHabitModel.getMaxCode()+1, "물마시기", "drinkwater", 1, 0, 10, 1, 6, "water.jpg", "blue");
        Habit prestudy = new Habit(mHabitModel.getMaxCode()+1, "예습하기", "prestudy", 1, 0, 10, 1, 12, "study.jpg", "red");
        Habit skiprope = new Habit(mHabitModel.getMaxCode()+1, "줄넘기 하기", "skiprope", 1, 0, 10, 1, 12,"rope.jpg", "black");


        mHabitModel.insertHabit(drinkwater);
        mHabitModel.insertHabit(prestudy);
        mHabitModel.insertHabit(skiprope);

        CelebHabitDetail celebHabitd1 = new CelebHabitDetail(1, 0, 1, 1, "물마시기", "하루에 6L 물마시기", 30, 6, 2, "L", "aaaa");
        CelebHabitDetail celebHabitd2 = new CelebHabitDetail(1, 3, 1, 2, "예습하기", "다음날 예습하기", 124, 1, 1, "번", "aaaa");

        mCelebModel.insertCelebDetail(celebHabitd1);
        mCelebModel.insertCelebDetail(celebHabitd2);

//            List<CelebHabitDetail> celebHabitDetailList = mCelebModel.selectCelebDetail(1);

        Log.d(TAG, "초기화 종료 ");
    }
*/

}
