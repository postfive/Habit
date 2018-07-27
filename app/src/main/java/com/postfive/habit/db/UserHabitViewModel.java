package com.postfive.habit.db;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;

import java.util.List;

public class UserHabitViewModel extends AndroidViewModel {
    private UserHabitRespository mHabitRespository;
    private LiveData<List<UserHabitDetail>> mDetailList;
    private LiveData<List<UserHabitState>> mStatelList;

    public UserHabitViewModel(@NonNull Application application) {
        super(application);
        mHabitRespository = new UserHabitRespository(application);

//        mDetailList = mHabitRespository
        mDetailList = this.mHabitRespository.getUserAllHabitLive();
        mStatelList = this.mHabitRespository.getTodayHabitLive();
    }

    public LiveData<List<UserHabitDetail>> getAllUserHabitDetailLive() {
        return mDetailList;
    }
    public LiveData<List<UserHabitState>> getTodayUserHabitStateLive() {
        return mStatelList;
    }

    public List<UserHabitDetail> getAllUserHabitDetail() {
        return mHabitRespository.getAllUserHabitDetail();
    }
    public List<UserHabitState> getAllUserHabitState() {
        return mHabitRespository.getAllUserHabitState();
    }
}
