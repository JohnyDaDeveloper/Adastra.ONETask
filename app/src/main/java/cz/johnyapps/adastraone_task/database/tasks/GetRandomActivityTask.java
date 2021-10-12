package cz.johnyapps.adastraone_task.database.tasks;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import cz.johnyapps.adastraone_task.entities.Activity;

public class GetRandomActivityTask extends BaseDatabaseTask<Activity, Void, Activity> {
    public GetRandomActivityTask(@NonNull Context context) {
        super(context);
    }

    @Nullable
    @Override
    protected Activity doInBackground(@Nullable Activity activity) throws Exception {
        if (activity == null) {
            return getDatabase().activityDao().getRandom();
        }

        return getDatabase().activityDao().getRandom(activity.getKey());
    }
}