package com.postfive.habit.db;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;

import java.util.List;

public class HabitViewModel extends AndroidViewModel {
    private HabitRespository mHabitRespository;
    private LiveData<List<Habit>> mList;

    public HabitViewModel(@NonNull Application application) {
        super(application);
        mHabitRespository = new HabitRespository(application);

        mList = mHabitRespository.getAllHabitLive();
    }

    public LiveData<List<Habit>> getAllHabitLive() {
        return mList;
    }

    public List<Habit> getAllHabit() {
        return mHabitRespository.getAllHabit();
    }

    public Habit getHabit(int code) {
        return mHabitRespository.getHabit(code);
    }
}
