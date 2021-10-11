package cz.johnyapps.adastraone_task.database.tasks;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import cz.johnyapps.adastraone_task.entities.Activity;

public class GetActivityTask extends BaseDatabaseTask<String, Void, Activity> {
    public GetActivityTask(@NonNull Context context) {
        super(context);
    }

    @Nullable
    @Override
    protected Activity doInBackground(@Nullable String s) throws Exception {
        if (s != null) {
            return getDatabase().activityDao().get(s);
        }

        return null;
    }
}
