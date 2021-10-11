package cz.johnyapps.adastraone_task.database.tasks;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import cz.johnyapps.adastraone_task.entities.Activity;

public class InsertActivityTask extends BaseDatabaseTask<Activity, Void, Activity> {
    public InsertActivityTask(@NonNull Context context) {
        super(context);
    }

    @Nullable
    @Override
    protected Activity doInBackground(@Nullable Activity activity) throws Exception {
        if (activity != null) {
            getDatabase().activityDao().insert(activity);
        }

        return activity;
    }
}
