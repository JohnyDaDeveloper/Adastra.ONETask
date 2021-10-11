package cz.johnyapps.adastraone_task.services;

import androidx.annotation.NonNull;

import cz.johnyapps.adastraone_task.tools.Logger;
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

    public void fetchRandomActivity() {
        Call<Activity> call = boredAPIService.getRandomActivity();
        call.enqueue(new Callback<Activity>() {
            @Override
            public void onResponse(@NonNull Call<Activity> call, @NonNull Response<Activity> response) {
                if (response.isSuccessful() && response.body() != null) {
                    Activity activity = response.body();
                    mainViewModel.setRandomActivity(activity);
                } else if (response.body() == null) {
                    Logger.w(TAG, "onResponse: Body is null");
                    mainViewModel.setRandomActivity(null);
                } else {
                    Logger.w(TAG, "onResponse: Error: %s", response.message());
                    mainViewModel.setRandomActivity(null);
                }
            }

            @Override
            public void onFailure(@NonNull Call<Activity> call, @NonNull Throwable t) {
                Logger.e(TAG, "onFailure: ", t);
                mainViewModel.setRandomActivity(null);
            }
        });
    }
}
