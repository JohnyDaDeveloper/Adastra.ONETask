package cz.johnyapps.adastraone_task.viewmodels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import cz.johnyapps.adastraone_task.entities.Activity;
import cz.johnyapps.adastraone_task.services.ActivityService;

public class MainViewModel extends AndroidViewModel {
    @NonNull
    private final ActivityService activityService;

    @NonNull
    private final MutableLiveData<Activity> randomActivity = new MutableLiveData<>();

    public MainViewModel(@NonNull Application application) {
        super(application);
        activityService = new ActivityService(this);
        activityService.fetchRandomActivity();
    }

    @NonNull
    public MutableLiveData<Activity> getRandomActivity() {
        return randomActivity;
    }

    public void setRandomActivity(@Nullable Activity randomActivity) {
        this.randomActivity.setValue(randomActivity);
    }
}
