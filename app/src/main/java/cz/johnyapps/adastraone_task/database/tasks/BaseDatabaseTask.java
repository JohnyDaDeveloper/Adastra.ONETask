package cz.johnyapps.adastraone_task.database.tasks;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import cz.johnyapps.adastraone_task.async.AsyncTask;
import cz.johnyapps.adastraone_task.database.BoredDatabase;
import cz.johnyapps.adastraone_task.tools.Logger;

public abstract class BaseDatabaseTask<INPUT, PROGRESS, OUTPUT> extends AsyncTask<INPUT, PROGRESS, OUTPUT> {
    private static final String TAG = "BaseDatabaseTask";

    @Nullable
    private OnCompleteListener<OUTPUT> onCompleteListener;
    @NonNull
    private final BoredDatabase database;

    public BaseDatabaseTask(@NonNull Context context) {
        this.database = BoredDatabase.getDatabase(context);
    }

    @Override
    protected void onBackgroundError(@Nullable Exception e) {
        Logger.e(TAG, "onBackgroundError: unexpected error occurred while working in background", e);

        if (onCompleteListener != null) {
            onCompleteListener.onFailure(e);
            onCompleteListener.onComplete();
        }
    }

    @Override
    protected void onPostExecute(@Nullable OUTPUT output) {
        super.onPostExecute(output);

        if (onCompleteListener != null) {
            onCompleteListener.onSuccess(output);
            onCompleteListener.onComplete();
        }
    }

    @NonNull
    public BoredDatabase getDatabase() {
        return database;
    }

    public void setOnCompleteListener(@Nullable OnCompleteListener<OUTPUT> onCompleteListener) {
        this.onCompleteListener = onCompleteListener;
        setOnCancelledListener(() -> {
            if (BaseDatabaseTask.this.onCompleteListener != null) {
                BaseDatabaseTask.this.onCompleteListener.onComplete();
            }
        });
    }

    public interface OnCompleteListener<OUTPUT> {
        void onSuccess(@Nullable OUTPUT output);
        void onFailure(@Nullable Exception e);
        void onComplete();
    }
}
