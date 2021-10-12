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
import cz.johnyapps.adastraone_task.databinding.FragmentLikedActivitiesBinding;
import cz.johnyapps.adastraone_task.entities.Activity;
import cz.johnyapps.adastraone_task.viewmodels.MainViewModel;

public class LikedActivitiesFragment extends BaseFragment<FragmentLikedActivitiesBinding, MainViewModel> {
    @Nullable
    private ActivityAdapter adapter;

    @NonNull
    @Override
    protected MainViewModel setupViewModel(@NonNull ViewModelProvider provider) {
        return provider.get(MainViewModel.class);
    }

    @Override
    public void onCreateView(@NonNull FragmentLikedActivitiesBinding binding) {
        setupActivitiesRecycler(binding);
        setupObservers();
    }

    @NonNull
    @Override
    protected FragmentLikedActivitiesBinding inflateBinding(@NonNull LayoutInflater inflater, @Nullable ViewGroup container) {
        return FragmentLikedActivitiesBinding.inflate(inflater, container, false);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        adapter = null;
    }

    private void setupActivitiesRecycler(@NonNull FragmentLikedActivitiesBinding binding) {
        Context context = binding.getRoot().getContext();

        if (adapter == null) {
            adapter = new ActivityAdapter();
        }

        binding.likedActivitiesRecyclerView.setLayoutManager(new LinearLayoutManager(context));
        binding.likedActivitiesRecyclerView.setAdapter(adapter);
    }

    private void setupObservers() {
        requireViewModel().getLikedActivitiesLiveData().observe(getViewLifecycleOwner(), listLiveData -> {
            if (listLiveData != null) {
                listLiveData.observe(getViewLifecycleOwner(), activitiesObserver);
            } else if (adapter != null) {
                adapter.submitList(null);
            }
        });
    }

    @NonNull
    private final Observer<List<Activity>> activitiesObserver = activities -> {
        if (adapter != null) {
            adapter.submitList(activities);
        }
    };
}
