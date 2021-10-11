package cz.johnyapps.adastraone_task.fragments;

import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import cz.johnyapps.adastraone_task.entities.Type;
import cz.johnyapps.adastraone_task.viewmodels.MainViewModel;
import cz.johnyapps.adastraone_task.R;
import cz.johnyapps.adastraone_task.databinding.FragmnetRandomActivityBinding;
import cz.johnyapps.adastraone_task.entities.Activity;
import cz.johnyapps.adastraone_task.tools.Logger;

public class RandomActivityFragment extends Fragment {
    @NonNull
    private static final String TAG = "RandomActivityFragment";
    private static final int MAX_PROGRESS = 100;

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
        binding.randomActivityButton.setOnClickListener(v -> viewModel.getActivityService().getRandomActivityFromAPI());
        binding.accessibilityProgress.setMax(MAX_PROGRESS);
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
            binding.typeImageView.setImageResource(Type.fromString(activity.getType()).getDrawableId());
            binding.typeImageView.setVisibility(View.VISIBLE);
            binding.participantsTextView.setText(String.valueOf(activity.getParticipants()));
            binding.linkTextView.setText(activity.getLink());
            binding.keyTextView.setText(activity.getKey());

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                binding.accessibilityProgress.setProgress(toProgress(activity.getAccessibility()), true);
                binding.priceProgress.setProgress(toProgress(activity.getPrice()), true);
            } else {
                binding.accessibilityProgress.setProgress(toProgress(activity.getAccessibility()));
                binding.priceProgress.setProgress(toProgress(activity.getPrice()));
            }
        } else {
            binding.activityNameTextView.setText(R.string.activityNameTextView_default);
            binding.typeImageView.setImageBitmap(null);
            binding.typeImageView.setVisibility(View.GONE);
            binding.participantsTextView.setText(null);
            binding.linkTextView.setText(null);
            binding.keyTextView.setText(null);

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                binding.accessibilityProgress.setProgress(0, true);
                binding.priceProgress.setProgress(0, true);
            } else {
                binding.accessibilityProgress.setProgress(0);
                binding.priceProgress.setProgress(0);
            }
        }
    }

    private int toProgress(@Nullable Float f) {
        if (f == null) {
            return 0;
        }

        return Math.round(MAX_PROGRESS * f);
    }
}
