package cz.johnyapps.adastraone_task.adapters;

import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

public abstract class ExpandableListAdapter<T, VH extends ExpandableListAdapter<T, VH>.ExpandableViewHolder> extends ListAdapter<T, VH> {

    @Nullable
    private ExpandedItem<T> expandedItem = null;

    protected ExpandableListAdapter(@NonNull DiffUtil.ItemCallback<T> diffCallback) {
        super(diffCallback);
    }

    @Override
    public void onBindViewHolder(@NonNull VH holder, int position) {
        onBindViewHolder(holder,
                position,
                expandedItem != null && expandedItem.getPos() == position);
    }

    public abstract void onBindViewHolder(@NonNull VH holder, int position, boolean expanded);

    public void expandItem(int pos) {
        ExpandedItem<T> prevItem = this.expandedItem;

        if (prevItem != null && prevItem.getPos() == pos) {
            this.expandedItem = null;
            notifyItemChanged(pos);
        } else {
            if (pos >= 0) {
                this.expandedItem = new ExpandedItem<>(pos, getItem(pos));
            } else {
                this.expandedItem = null;
            }

            if (prevItem != null) {
                notifyItemChanged(prevItem.getPos());
            }

            if (this.expandedItem != null) {
                notifyItemChanged(this.expandedItem.getPos());
            }
        }
    }

    public class ExpandableViewHolder extends RecyclerView.ViewHolder {
        public ExpandableViewHolder(@NonNull View itemView) {
            super(itemView);
            itemView.setOnClickListener(v -> expandItem(getAdapterPosition()));
        }
    }

    public static class ExpandedItem<T> {
        private final int pos;
        @NonNull
        private final T item;

        public ExpandedItem(int pos, @NonNull T item) {
            this.pos = pos;
            this.item = item;
        }

        public int getPos() {
            return pos;
        }

        @NonNull
        public T getItem() {
            return item;
        }
    }
}
