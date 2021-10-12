package cz.johnyapps.adastraone_task.services;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.LiveData;

import java.util.List;

import cz.johnyapps.adastraone_task.database.tasks.BaseDatabaseTask;
import cz.johnyapps.adastraone_task.database.tasks.GetActivityTask;
import cz.johnyapps.adastraone_task.database.tasks.GetAllActivitiesTask;
import cz.johnyapps.adastraone_task.database.tasks.InsertActivityTask;
import cz.johnyapps.adastraone_task.tools.Logger;
import cz.johnyapps.adastraone_task.tools.SharedPreferencesUtils;
import cz.johnyapps.adastraone_task.viewmodels.MainViewModel;
import cz.johnyapps.adastraone_task.entities.Activity;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ActivityService {
    @NonNull
    private static final String TAG = "ActivityService";

    @NonNull
    private final MainViewModel mainViewModel;
    @NonNull
    private final BoredAPIService boredAPIService;

    public ActivityService(@NonNull MainViewModel mainViewModel) {
        this.mainViewModel = mainViewModel;

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://www.boredapi.com")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        boredAPIService = retrofit.create(BoredAPIService.class);
    }

    public void getAllActivitiesFromDatabase() {
        GetAllActivitiesTask task = new GetAllActivitiesTask(mainViewModel.getApplication());
        task.setOnCompleteListener(new BaseDatabaseTask.OnCompleteListener<LiveData<List<Activity>>>() {
            @Override
            public void onSuccess(@Nullable LiveData<List<Activity>> listLiveData) {
                mainViewModel.setAllActivitiesLiveData(listLiveData);
            }

            @Override
            public void onFailure(@Nullable Exception e) {

            }

            @Override
            public void onComplete() {

            }
        });
        task.execute();
    }

    public boolean getLastActivityFromDatabase() {
        String lastKey = SharedPreferencesUtils.getLastActivityKey(mainViewModel.getApplication());

        if (lastKey != null) {
            GetActivityTask task = new GetActivityTask(mainViewModel.getApplication());
            task.setOnCompleteListener(new BaseDatabaseTask.OnCompleteListener<Activity>() {
                @Override
                public void onSuccess(@Nullable Activity activity) {
                    Activity loaded = mainViewModel.getRandomActivity().getValue();

                    if (activity != null) {
                        if (loaded == null) {
                            mainViewModel.setRandomActivity(activity);
                        }
                    }
                }

                @Override
                public void onFailure(@Nullable Exception e) {

                }

                @Override
                public void onComplete() {

                }
            });
            task.execute(lastKey);
            return false;
        }

        return true;
    }

    public void getRandomActivityFromAPI() {
        mainViewModel.setFetchingActivity(true);

        Call<Activity> call = boredAPIService.getRandomActivity();
        call.enqueue(new Callback<Activity>() {
            @Override
            public void onResponse(@NonNull Call<Activity> call, @NonNull Response<Activity> response) {
                if (response.isSuccessful() && response.body() != null) {
                    Activity activity = response.body();
                    mainViewModel.setRandomActivity(activity);
                    insertActivityToDatabase(activity);
                } else if (response.body() == null) {
                    Logger.w(TAG, "onResponse: Body is null");
                    mainViewModel.setRandomActivity(null);
                } else {
                    Logger.w(TAG, "onResponse: Error: %s", response.message());
                    mainViewModel.setRandomActivity(null);
                }

                mainViewModel.setFetchingActivity(false);
            }

            @Override
            public void onFailure(@NonNull Call<Activity> call, @NonNull Throwable t) {
                Logger.e(TAG, "onFailure: ", t);
                mainViewModel.setRandomActivity(null);
                mainViewModel.setFetchingActivity(false);
            }
        });
    }

    public void insertActivityToDatabase(@NonNull Activity activity) {
        InsertActivityTask task = new InsertActivityTask(mainViewModel.getApplication());
        task.setOnCompleteListener(new BaseDatabaseTask.OnCompleteListener<Activity>() {
            @Override
            public void onSuccess(@Nullable Activity activity) {
                if (activity != null) {
                    SharedPreferencesUtils.setLastActivityKey(mainViewModel.getApplication(),
                            activity.getKey());
                }
            }

            @Override
            public void onFailure(@Nullable Exception e) {

            }

            @Override
            public void onComplete() {

            }
        });
        task.execute(activity);
    }
}
