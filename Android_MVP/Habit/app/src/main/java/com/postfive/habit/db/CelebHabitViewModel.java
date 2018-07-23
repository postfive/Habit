package com.postfive.habit.db;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;

import java.util.List;

public class CelebHabitViewModel extends AndroidViewModel {

    private HabitRespository mHabitRespository;
    private LiveData<List<CelebHabitMaster>> mCelebHabitMasterList ;
    public CelebHabitViewModel(@NonNull Application application) {
        super(application);
        mHabitRespository = new HabitRespository(application);

        mCelebHabitMasterList = mHabitRespository.getAllCelebLive();
    }

    public LiveData<List<CelebHabitMaster>> getCelebHabitList(){
        return mCelebHabitMasterList;
    }
}