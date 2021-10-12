package cz.johnyapps.adastraone_task.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.ViewModelProvider;
import androidx.viewbinding.ViewBinding;

public abstract class BaseFragment<VB extends ViewBinding, VM extends AndroidViewModel> extends Fragment {
    @Nullable
    private VB binding;
    @Nullable
    private VM viewModel;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel = setupViewModel(new ViewModelProvider(requireActivity()));
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = inflateBinding(inflater, container);
        onCreateView(binding);
        return binding.getRoot();
    }

    @NonNull
    protected abstract VM setupViewModel(@NonNull ViewModelProvider provider);

    @NonNull
    protected VM requireViewModel() {
        if (viewModel == null) {
            throw new IllegalStateException("Fragment " + this + " is not attached to a view model.");
        }

        return viewModel;
    }

    public abstract void onCreateView(@NonNull VB binding);

    @Override
    public void onDestroy() {
        super.onDestroy();
        binding = null;
        viewModel = null;
    }

    @Nullable
    protected VB getBinding() {
        return binding;
    }

    @NonNull
    protected abstract VB inflateBinding(@NonNull LayoutInflater inflater, @Nullable ViewGroup container);
}
