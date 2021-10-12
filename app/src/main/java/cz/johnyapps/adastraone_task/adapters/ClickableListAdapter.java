package cz.johnyapps.adastraone_task.adapters;

import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

public abstract class ClickableListAdapter<T, VH extends ClickableListAdapter<T, VH>.ClickableViewHolder> extends ListAdapter<T, VH> {
    @Nullable
    private OnItemClickListener<T> onItemClickListener;

    protected ClickableListAdapter(@NonNull DiffUtil.ItemCallback<T> diffCallback) {
        super(diffCallback);
    }

    @Override
    public void onBindViewHolder(@NonNull VH holder, int position) {
        holder.bindTo(getItem(position));
    }

    public abstract class ClickableViewHolder extends RecyclerView.ViewHolder {
        @Nullable
        private T item;

        public ClickableViewHolder(@NonNull View itemView) {
            super(itemView);
            itemView.setOnClickListener(v -> {
                if (onItemClickListener != null && item != null) {
                    onItemClickListener.onClick(item);
                }
            });
        }

        public void bindTo(@NonNull T item) {
            this.item = item;
            onBind(item);
        }

        public abstract void onBind(@NonNull T item);
    }

    public interface OnItemClickListener<T> {
        void onClick(@NonNull T item);
    }

    public void setOnItemClickListener(@Nullable OnItemClickListener<T> onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }
}
