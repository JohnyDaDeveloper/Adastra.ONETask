package cz.johnyapps.adastraone_task.viewmodels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import cz.johnyapps.adastraone_task.entities.Activity;
import cz.johnyapps.adastraone_task.services.ActivityService;

public class MainViewModel extends AndroidViewModel {
    @NonNull
    private final ActivityService activityService;

    @NonNull
    private final MutableLiveData<Activity> randomActivity = new MutableLiveData<>();
    @NonNull
    private final MutableLiveData<Boolean> fetchingActivity = new MutableLiveData<>();

    public MainViewModel(@NonNull Application application) {
        super(application);
        activityService = new ActivityService(this);
        activityService.getLastActivityFromDatabase();
    }

    @NonNull
    public LiveData<Activity> getRandomActivity() {
        return randomActivity;
    }

    public void setRandomActivity(@Nullable Activity randomActivity) {
        this.randomActivity.setValue(randomActivity);
    }

    @NonNull
    public LiveData<Boolean> getFetchingActivity() {
        return fetchingActivity;
    }

    public void setFetchingActivity(@NonNull Boolean fetchingActivity) {
        this.fetchingActivity.setValue(fetchingActivity);
    }

    @NonNull
    public ActivityService getActivityService() {
        return activityService;
    }
}
