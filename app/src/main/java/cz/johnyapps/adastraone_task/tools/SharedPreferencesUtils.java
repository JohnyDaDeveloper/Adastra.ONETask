package cz.johnyapps.adastraone_task.tools;

import android.content.Context;
import android.content.SharedPreferences;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class SharedPreferencesUtils {
    @NonNull
    private final static String GENERAL = "general";

    @NonNull
    private final static String LAST_ACTIVITY_KEY = "last_activity";

    @NonNull
    public static SharedPreferences getGeneral(@NonNull Context context) {
        return context.getSharedPreferences(GENERAL, Context.MODE_PRIVATE);
    }

    @Nullable
    public static String getLastActivityKey(@NonNull Context context) {
        return getGeneral(context).getString(LAST_ACTIVITY_KEY, null);
    }

    public static void setLastActivityKey(@NonNull Context context, @Nullable String key) {
        getGeneral(context).edit().putString(LAST_ACTIVITY_KEY, key).apply();
    }
}
