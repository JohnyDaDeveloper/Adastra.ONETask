package cz.johnyapps.adastraone_task.database.tasks;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import cz.johnyapps.adastraone_task.entities.Activity;

public class UpdateActivityTask extends BaseDatabaseTask<Activity, Void, Activity> {
    public UpdateActivityTask(@NonNull Context context) {
        super(context);
    }

    @Nullable
    @Override
    protected Activity doInBackground(@Nullable Activity activity) throws Exception {
        if (activity != null) {
            getDatabase().activityDao().update(activity);
        }

        return activity;
    }
}
