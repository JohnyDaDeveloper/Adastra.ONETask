package cz.johnyapps.adastraone_task.fragments;

import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProvider;

import cz.johnyapps.adastraone_task.databinding.FragmentActivityBinding;
import cz.johnyapps.adastraone_task.entities.Type;
import cz.johnyapps.adastraone_task.services.ActivityService;
import cz.johnyapps.adastraone_task.viewmodels.MainViewModel;
import cz.johnyapps.adastraone_task.R;
import cz.johnyapps.adastraone_task.entities.Activity;
import cz.johnyapps.adastraone_task.tools.Logger;

public class ActivityFragment extends BaseFragment<FragmentActivityBinding, MainViewModel> {
    @NonNull
    private static final String TAG = "ActivityFragment";
    private static final int MAX_PROGRESS = 100;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @NonNull
    @Override
    protected MainViewModel setupViewModel(@NonNull ViewModelProvider provider) {
        return provider.get(MainViewModel.class);
    }

    @Override
    public void onCreateView(@NonNull FragmentActivityBinding binding) {
        binding.randomActivityButton.setOnClickListener(v -> requireViewModel().getActivityService().getRandomActivityFromAPI());
        binding.accessibilityProgress.setMax(MAX_PROGRESS);
        setupObservers();
    }

    @NonNull
    @Override
    protected FragmentActivityBinding inflateBinding(@NonNull LayoutInflater inflater, @Nullable ViewGroup container) {
        return FragmentActivityBinding.inflate(inflater);
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.activity_fragment_options_menu, menu);

        Activity activity = requireViewModel().getSelectedActivity().getValue();
        MenuItem likeActivityMenuItem = menu.findItem(R.id.likeActivityMenuItem);
        MenuItem unlikeActivityMenuItem = menu.findItem(R.id.unlikeActivityMenuItem);

        if (activity != null && !activity.getKey().equals(ActivityService.NO_INTERNET_KEY)) {
            likeActivityMenuItem.setOnMenuItemClickListener(item -> {
                likeActivity(activity);
                return false;
            });

            unlikeActivityMenuItem.setOnMenuItemClickListener(item -> {
                unlikeActivity(activity);
                return false;
            });

            if (activity.isLiked()) {
                likeActivityMenuItem.setVisible(false);
                unlikeActivityMenuItem.setVisible(true);
            } else {
                likeActivityMenuItem.setVisible(true);
                unlikeActivityMenuItem.setVisible(false);
            }
        } else {
            likeActivityMenuItem.setVisible(false);
            unlikeActivityMenuItem.setVisible(false);
        }

        super.onCreateOptionsMenu(menu, inflater);
    }

    private void likeActivity(@NonNull Activity activity) {
        activity.setLiked(true);
        requireActivity().invalidateOptionsMenu();
        requireViewModel().getActivityService().updateActivity(activity);
    }

    private void unlikeActivity(@NonNull Activity activity) {
        activity.setLiked(false);
        requireActivity().invalidateOptionsMenu();
        requireViewModel().getActivityService().updateActivity(activity);
    }

    private void setupObservers() {
        requireViewModel().getSelectedActivity().observe(getViewLifecycleOwner(), activity -> {
            fillActivity(activity);
            requireActivity().invalidateOptionsMenu();
        });
        requireViewModel().getFetchingActivity().observe(getViewLifecycleOwner(), aBoolean -> {
            FragmentActivityBinding binding = getBinding();

            if (binding == null) {
                Logger.w(TAG, "setupObservers: Binding is null");
                return;
            }

            boolean b = aBoolean != null && !aBoolean;
            binding.randomActivityButton.setEnabled(b);
            binding.getActivityProgress.setVisibility(b ? View.GONE : View.VISIBLE);
        });
    }

    private void fillActivity(@Nullable Activity activity) {
        FragmentActivityBinding binding = getBinding();
        if (binding == null) {
            Logger.w(TAG, "fillActivity: Binding is null");
            return;
        }

        if (activity != null) {
            binding.activityNameTextView.setText(activity.getActivity());
            binding.typeImageView.setImageResource(Type.fromString(activity.getType()).getDrawableId());
            binding.typeImageView.setVisibility(View.VISIBLE);
            binding.participantsTextView.setText(requireContext().getString(R.string.participantsTextView,
                    activity.getParticipants()));
            binding.linkTextView.setText(activity.getLink());

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
