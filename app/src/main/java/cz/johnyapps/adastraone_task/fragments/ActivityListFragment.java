package cz.johnyapps.adastraone_task.fragments;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.navigation.fragment.NavHostFragment;
import androidx.viewbinding.ViewBinding;

import java.util.List;

import cz.johnyapps.adastraone_task.adapters.ActivityAdapter;
import cz.johnyapps.adastraone_task.entities.Activity;
import cz.johnyapps.adastraone_task.viewmodels.MainViewModel;

public abstract class ActivityListFragment<VB extends ViewBinding> extends BaseFragment<VB, MainViewModel> {
    @Nullable
    private ActivityAdapter adapter;

    @Override
    public void onDestroy() {
        super.onDestroy();
        adapter = null;
    }

    @Override
    public void onCreateView(@NonNull VB binding) {
        setupActivitiesRecycler(binding);
    }

    private void setupActivitiesRecycler(@NonNull VB binding) {
        Context context = binding.getRoot().getContext();

        if (adapter == null) {
            adapter = new ActivityAdapter();
            adapter.setOnItemClickListener(this::activityClicked);
        }

        setupActivitiesRecycler(binding, adapter, context);
    }

    private void activityClicked(@NonNull Activity activity) {
        requireViewModel().setSelectedActivity(activity);
        NavHostFragment.findNavController(this).navigateUp();
    }

    protected abstract void setupActivitiesRecycler(@NonNull VB binding, @NonNull ActivityAdapter adapter, @NonNull Context context);

    @NonNull
    private final Observer<List<Activity>> activitiesObserver = activities -> {
        if (adapter != null) {
            adapter.submitList(activities);
        }
    };

    protected void onNewLiveData(@Nullable LiveData<List<Activity>> listLiveData) {
        if (listLiveData != null) {
            listLiveData.observe(getViewLifecycleOwner(), activitiesObserver);
        } else if (adapter != null) {
            adapter.submitList(null);
        }
    }
}
