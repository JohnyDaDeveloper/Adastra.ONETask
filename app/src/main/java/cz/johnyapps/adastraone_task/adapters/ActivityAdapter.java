package cz.johnyapps.adastraone_task.adapters;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;

import cz.johnyapps.adastraone_task.databinding.ItemActivityBinding;
import cz.johnyapps.adastraone_task.entities.Activity;
import cz.johnyapps.adastraone_task.entities.Type;

public class ActivityAdapter extends ClickableListAdapter<Activity, ActivityAdapter.ActivityViewHolder> {
    public ActivityAdapter() {
        super(DIFF_CALLBACK);
    }

    @NonNull
    @Override
    public ActivityViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ActivityViewHolder(ItemActivityBinding.inflate(LayoutInflater.from(parent.getContext()),
                parent,
                false));
    }

    public class ActivityViewHolder extends ClickableListAdapter<Activity, ActivityViewHolder>.ClickableViewHolder {
        @NonNull
        private final ItemActivityBinding binding;

        public ActivityViewHolder(@NonNull ItemActivityBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        @Override
        public void onBind(@NonNull Activity item) {
            Type type = Type.fromString(item.getType());
            binding.activityTypeImageView.setImageResource(type.getDrawableId());
            binding.activityNameTextView.setText(item.getActivity());
        }
    }

    private static final DiffUtil.ItemCallback<Activity> DIFF_CALLBACK = new DiffUtil.ItemCallback<Activity>() {
        @Override
        public boolean areItemsTheSame(@NonNull Activity oldItem, @NonNull Activity newItem) {
            return oldItem.getKey().equals(newItem.getKey());
        }

        @Override
        public boolean areContentsTheSame(@NonNull Activity oldItem, @NonNull Activity newItem) {
            return oldItem.equals(newItem);
        }
    };
}
