package cz.johnyapps.adastraone_task.fragments;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import java.util.List;

import cz.johnyapps.adastraone_task.adapters.ActivityAdapter;
import cz.johnyapps.adastraone_task.databinding.FragmentAllActivitiesBinding;
import cz.johnyapps.adastraone_task.entities.Activity;
import cz.johnyapps.adastraone_task.viewmodels.MainViewModel;

public class AllActivitiesFragment extends BaseFragment<FragmentAllActivitiesBinding, MainViewModel> {
    @Nullable
    private ActivityAdapter adapter;

    @NonNull
    @Override
    protected MainViewModel setupViewModel(@NonNull ViewModelProvider provider) {
        return provider.get(MainViewModel.class);
    }

    @Override
    public void onCreateView(@NonNull FragmentAllActivitiesBinding binding) {
        setupActivitiesRecycler(binding);
        setupObservers();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        adapter = null;
    }

    @NonNull
    @Override
    protected FragmentAllActivitiesBinding inflateBinding(@NonNull LayoutInflater inflater, @Nullable ViewGroup container) {
        return FragmentAllActivitiesBinding.inflate(inflater, container, false);
    }

    private void setupActivitiesRecycler(@NonNull FragmentAllActivitiesBinding binding) {
        Context context = binding.getRoot().getContext();

        if (adapter == null) {
            adapter = new ActivityAdapter();
        }

        binding.allActivitiesRecyclerView.setLayoutManager(new LinearLayoutManager(context));
        binding.allActivitiesRecyclerView.setAdapter(adapter);
    }

    private void setupObservers() {
        requireViewModel().getAllActivitiesLiveData().observe(getViewLifecycleOwner(), listLiveData -> {
            if (listLiveData != null) {
                listLiveData.observe(getViewLifecycleOwner(), activitiesObserver);
            } else if (adapter != null) {
                adapter.submitList(null);
            }
        });
    }

    private final Observer<List<Activity>> activitiesObserver = activities -> {
        if (adapter != null) {
            adapter.submitList(activities);
        }
    };
}
