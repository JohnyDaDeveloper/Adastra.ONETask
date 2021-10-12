package cz.johnyapps.adastraone_task.fragments;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import java.util.List;

import cz.johnyapps.adastraone_task.adapters.ActivityAdapter;
import cz.johnyapps.adastraone_task.databinding.FragmentAllActivitiesBinding;
import cz.johnyapps.adastraone_task.entities.Activity;
import cz.johnyapps.adastraone_task.viewmodels.MainViewModel;

public class AllActivitiesFragment extends Fragment {
    @Nullable
    private FragmentAllActivitiesBinding binding;
    private MainViewModel viewModel;
    @Nullable
    private ActivityAdapter adapter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setupViewModel();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentAllActivitiesBinding.inflate(inflater, container, false);
        setupActivitiesRecycler(binding);
        setupObservers();
        return binding.getRoot();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        adapter = null;
    }

    private void setupActivitiesRecycler(@NonNull FragmentAllActivitiesBinding binding) {
        Context context = binding.getRoot().getContext();

        if (adapter == null) {
            adapter = new ActivityAdapter();
        }

        binding.allActivitiesRecyclerView.setLayoutManager(new LinearLayoutManager(context));
        binding.allActivitiesRecyclerView.setAdapter(adapter);
    }

    private void setupViewModel() {
        ViewModelProvider provider = new ViewModelProvider(requireActivity());
        viewModel = provider.get(MainViewModel.class);
    }

    private void setupObservers() {
        viewModel.getAllActivitiesLiveData().observe(getViewLifecycleOwner(), listLiveData ->
                listLiveData.observe(getViewLifecycleOwner(), activitiesObserver));
    }

    private final Observer<List<Activity>> activitiesObserver = activities -> {
        if (adapter != null) {
            adapter.submitList(activities);
        }
    };
}
