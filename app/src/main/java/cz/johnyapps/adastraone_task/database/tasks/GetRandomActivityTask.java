package cz.johnyapps.adastraone_task.database.tasks;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import cz.johnyapps.adastraone_task.entities.Activity;

public class GetRandomActivityTask extends BaseDatabaseTask<Activity, Void, Activity> {
    @Nullable
    private final String type;

    public GetRandomActivityTask(@NonNull Context context, @Nullable String type) {
        super(context);
        this.type = type;
    }

    @Nullable
    @Override
    protected Activity doInBackground(@Nullable Activity activity) throws Exception {
        if (activity == null) {
            if (type == null) {
                return getDatabase().activityDao().getRandom();
            } else {
                return getDatabase().activityDao().getRandomWithType(type);
            }
        }

        if (type == null) {
            return getDatabase().activityDao().getRandomButNotThis(activity.getKey());
        } else {
            return getDatabase().activityDao().getRandomWithTypeButNotThis(activity.getKey(), type);
        }
    }
}
