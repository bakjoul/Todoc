package com.bakjoul.todoc.ui.task;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.bakjoul.todoc.databinding.TaskItemBinding;

public class TaskAdapter extends ListAdapter<TaskViewStateItem, TaskAdapter.ViewHolder> {

    @NonNull
    private final TaskOnDeleteClickedListener listener;

    public TaskAdapter(@NonNull TaskOnDeleteClickedListener listener) {
        super(new TaskAdapterDiffCallback());
        this.listener = listener;
    }

    @NonNull
    @Override
    public TaskAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        TaskItemBinding b = TaskItemBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new ViewHolder(b);
    }

    @Override
    public void onBindViewHolder(@NonNull TaskAdapter.ViewHolder holder, int position) {
        holder.bind(getItem(position), listener);
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        private final TaskItemBinding b;

        public ViewHolder(@NonNull TaskItemBinding binding) {
            super(binding.getRoot());
            this.b = binding;
        }

        public void bind(@NonNull TaskViewStateItem taskViewStateItem, @NonNull TaskOnDeleteClickedListener listener) {
            b.taskItemProjectColor.setColorFilter(taskViewStateItem.getProjectColor());
            b.taskItemDescription.setText(taskViewStateItem.getTaskDescription());
            b.taskItemProject.setText(taskViewStateItem.getProject());

            b.taskItemDelete.setOnClickListener(view -> listener.onDeleteTaskButtonClick(taskViewStateItem.getTaskId()));
        }
    }

    private static class TaskAdapterDiffCallback extends DiffUtil.ItemCallback<TaskViewStateItem> {

        @Override
        public boolean areItemsTheSame(@NonNull TaskViewStateItem oldItem, @NonNull TaskViewStateItem newItem) {
            return oldItem.getTaskDescription().equals(newItem.getTaskDescription());
        }

        @Override
        public boolean areContentsTheSame(@NonNull TaskViewStateItem oldItem, @NonNull TaskViewStateItem newItem) {
            return oldItem.equals(newItem);
        }
    }
}
