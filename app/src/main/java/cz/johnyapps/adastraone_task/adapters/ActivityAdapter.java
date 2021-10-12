package cz.johnyapps.adastraone_task.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;

import cz.johnyapps.adastraone_task.databinding.ItemActivityBinding;
import cz.johnyapps.adastraone_task.entities.Activity;
import cz.johnyapps.adastraone_task.entities.Type;

public class ActivityAdapter extends ExpandableListAdapter<Activity, ActivityAdapter.ActivityViewHolder> {

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

    @Override
    public void onBindViewHolder(@NonNull ActivityViewHolder holder, int position, boolean expanded) {
        holder.bindTo(getItem(position));
        holder.setExpanded(expanded);
    }

    public class ActivityViewHolder extends ExpandableListAdapter<Activity, ActivityViewHolder>.ExpandableViewHolder {
        @NonNull
        private final ItemActivityBinding binding;

        public ActivityViewHolder(@NonNull ItemActivityBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void bindTo(@NonNull Activity activity) {
            Type type = Type.fromString(activity.getType());
            binding.activityTypeImageView.setImageResource(type.getDrawableId());
            binding.activityNameTextView.setText(activity.getActivity());
            binding.topPartLayout.setOnClickListener(v -> binding.getRoot().performClick());
        }

        public void setExpanded(boolean expanded) {
            if (expanded) {
                binding.expandableLayout.getRoot().setVisibility(View.VISIBLE);
            } else {
                binding.expandableLayout.getRoot().setVisibility(View.GONE);
            }
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
