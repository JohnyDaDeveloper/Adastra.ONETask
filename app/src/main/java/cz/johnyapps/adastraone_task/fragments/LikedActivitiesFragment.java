package cz.johnyapps.adastraone_task.fragments;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;


import cz.johnyapps.adastraone_task.adapters.ActivityAdapter;
import cz.johnyapps.adastraone_task.databinding.FragmentLikedActivitiesBinding;
import cz.johnyapps.adastraone_task.viewmodels.MainViewModel;

public class LikedActivitiesFragment extends ActivityListFragment<FragmentLikedActivitiesBinding> {

    @NonNull
    @Override
    protected MainViewModel setupViewModel(@NonNull ViewModelProvider provider) {
        return provider.get(MainViewModel.class);
    }

    @Override
    public void onCreateView(@NonNull FragmentLikedActivitiesBinding binding) {
        super.onCreateView(binding);
        setupObservers();
    }

    @NonNull
    @Override
    protected FragmentLikedActivitiesBinding inflateBinding(@NonNull LayoutInflater inflater, @Nullable ViewGroup container) {
        return FragmentLikedActivitiesBinding.inflate(inflater, container, false);
    }

    @Override
    protected void setupActivitiesRecycler(@NonNull FragmentLikedActivitiesBinding binding, @NonNull ActivityAdapter adapter, @NonNull Context context) {
        binding.likedActivitiesRecyclerView.setLayoutManager(new LinearLayoutManager(context));
        binding.likedActivitiesRecyclerView.setAdapter(adapter);
    }

    private void setupObservers() {
        requireViewModel().getLikedActivitiesLiveData().observe(getViewLifecycleOwner(), this::onNewLiveData);
    }
}
