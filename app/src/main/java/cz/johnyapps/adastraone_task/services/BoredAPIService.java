package cz.johnyapps.adastraone_task.services;

import androidx.annotation.NonNull;

import cz.johnyapps.adastraone_task.entities.Activity;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface BoredAPIService {
    @GET("api/activity")
    Call<Activity> getRandomActivity();

    @GET("api/activity")
    Call<Activity> getRandomActivity(@NonNull @Query("type") String type);
}
