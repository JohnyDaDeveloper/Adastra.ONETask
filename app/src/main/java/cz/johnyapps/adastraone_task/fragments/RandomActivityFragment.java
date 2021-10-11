package cz.johnyapps.adastraone_task.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import cz.johnyapps.adastraone_task.viewmodels.MainViewModel;
import cz.johnyapps.adastraone_task.R;
import cz.johnyapps.adastraone_task.databinding.FragmnetRandomActivityBinding;
import cz.johnyapps.adastraone_task.entities.Activity;
import cz.johnyapps.adastraone_task.tools.Logger;

public class RandomActivityFragment extends Fragment {
    private static final String TAG = "RandomActivityFragment";

    private MainViewModel viewModel;

    @Nullable
    private FragmnetRandomActivityBinding binding;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        setupViewModel();
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmnetRandomActivityBinding.inflate(inflater);
        binding.randomActivityButton.setOnClickListener(v -> viewModel.getActivityService().fetchRandomActivity());
        setupObservers();
        return binding.getRoot();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        binding = null;
    }

    private void setupViewModel() {
        ViewModelProvider provider = new ViewModelProvider(requireActivity());
        viewModel = provider.get(MainViewModel.class);
    }

    private void setupObservers() {
        viewModel.getRandomActivity().observe(getViewLifecycleOwner(), this::fillActivity);
        viewModel.getFetchingActivity().observe(getViewLifecycleOwner(), aBoolean -> {
            if (binding == null) {
                Logger.w(TAG, "setupObservers: Binding is null");
            }

            binding.randomActivityButton.setEnabled(aBoolean != null && !aBoolean);
        });
    }

    private void fillActivity(@Nullable Activity activity) {
        if (binding == null) {
            Logger.w(TAG, "fillActivity: Binding is null");
        }

        if (activity != null) {
            binding.activityNameTextView.setText(activity.getActivity());
            binding.accessibilityTextView.setText(String.valueOf(activity.getAccessibility()));
            binding.typeTextView.setText(activity.getType());
            binding.participantsTextView.setText(String.valueOf(activity.getParticipants()));
            binding.priceTextView.setText(String.valueOf(activity.getPrice()));
            binding.linkTextView.setText(activity.getLink());
            binding.keyTextView.setText(activity.getKey());
        } else {
            binding.activityNameTextView.setText(R.string.activityNameTextView_default);
            binding.accessibilityTextView.setText(null);
            binding.typeTextView.setText(null);
            binding.participantsTextView.setText(null);
            binding.priceTextView.setText(null);
            binding.linkTextView.setText(null);
            binding.keyTextView.setText(null);
        }
    }
}
