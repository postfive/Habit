package com.postfive.habit.db;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;

import com.postfive.habit.view.statistics.HabitStatistics;
import com.postfive.habit.view.statistics.HabitStatisticsCalendar;

import java.util.List;

public class UserHabitViewModel extends AndroidViewModel {
    private UserHabitRespository mHabitRespository;
    private LiveData<List<HabitStatistics>> mHabitStatistics;

    public UserHabitViewModel(@NonNull Application application) {
        super(application);
        mHabitRespository = new UserHabitRespository(application);

        mHabitStatistics = this.mHabitRespository.getHabitStatics();
    }
    public LiveData<List<HabitStatistics>> getHabitStatics() {
        return mHabitStatistics;
    }
}
