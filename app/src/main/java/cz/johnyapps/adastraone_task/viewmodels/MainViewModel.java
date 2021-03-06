package cz.johnyapps.adastraone_task.viewmodels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.util.List;

import cz.johnyapps.adastraone_task.entities.Activity;
import cz.johnyapps.adastraone_task.entities.Type;
import cz.johnyapps.adastraone_task.services.ActivityService;

public class MainViewModel extends AndroidViewModel {
    @NonNull
    private final ActivityService activityService;

    @NonNull
    private final MutableLiveData<Activity> selectedActivity = new MutableLiveData<>();
    @NonNull
    private final MutableLiveData<Boolean> fetchingActivity = new MutableLiveData<>();
    @NonNull
    private final MutableLiveData<LiveData<List<Activity>>> allActivitiesLiveData = new MutableLiveData<>();
    @NonNull
    private final MutableLiveData<LiveData<List<Activity>>> likedActivitiesLiveData = new MutableLiveData<>();

    public MainViewModel(@NonNull Application application) {
        super(application);
        activityService = new ActivityService(this);

        if (activityService.getLastActivityFromDatabase()) {
            activityService.getRandomActivityFromAPI();
        }

        activityService.getAllAndAllLikedActivitiesFromDatabase();
    }

    public void getRandomActivityWithSameTypeAsSelected() {
        Activity selected = selectedActivity.getValue();

        if (selected != null && !Type.fromString(selected.getType()).isCustom()) {
            activityService.getRandomActivityFromAPI(selected.getType());
        } else {
            activityService.getRandomActivityFromAPI();
        }
    }

    @NonNull
    public LiveData<Activity> getSelectedActivity() {
        return selectedActivity;
    }

    public void setSelectedActivity(@Nullable Activity selectedActivity) {
        this.selectedActivity.setValue(selectedActivity);
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

    @NonNull
    public LiveData<LiveData<List<Activity>>> getAllActivitiesLiveData() {
        return allActivitiesLiveData;
    }

    public void setAllActivitiesLiveData(@Nullable LiveData<List<Activity>> allActivitiesLiveData) {
        this.allActivitiesLiveData.setValue(allActivitiesLiveData);
    }

    @NonNull
    public LiveData<LiveData<List<Activity>>> getLikedActivitiesLiveData() {
        return likedActivitiesLiveData;
    }

    public void setLikedActivitiesLiveData(@Nullable LiveData<List<Activity>> likedActivitiesLiveData) {
        this.likedActivitiesLiveData.setValue(likedActivitiesLiveData);
    }
}
