package cz.johnyapps.adastraone_task.services;

import cz.johnyapps.adastraone_task.entities.Activity;
import retrofit2.Call;
import retrofit2.http.GET;

public interface BoredAPIService {
    @GET("api/activity")
    Call<Activity> getRandomActivity();
}
