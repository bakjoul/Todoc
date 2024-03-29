package com.bakjoul.todoc.ui.task;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;

import com.bakjoul.todoc.R;
import com.bakjoul.todoc.databinding.TaskFragmentBinding;
import com.bakjoul.todoc.ui.ViewEvent;
import com.bakjoul.todoc.ui.add.AddTaskDialogFragment;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class TaskFragment extends Fragment implements TaskOnDeleteClickedListener {

    private final String TAG_ADD_TASK_DIALOG = "ADD";

    public static TaskFragment newInstance() {
        return new TaskFragment();
    }

    private TaskViewModel viewModel;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        TaskFragmentBinding binding = TaskFragmentBinding.inflate(inflater, container, false);
        viewModel = new ViewModelProvider(this).get(TaskViewModel.class);

        TaskAdapter adapter = new TaskAdapter(this);
        binding.taskRecyclerView.setAdapter(adapter);

        binding.fabAdd.setOnClickListener(view -> viewModel.onAddButtonClicked());

        viewModel.getTaskViewStateLiveData().observe(getViewLifecycleOwner(), taskViewState -> {
                adapter.submitList(taskViewState.getTaskViewStateItems());
                if (taskViewState.isEmptyStateVisible()) {
                    binding.noTask.setVisibility(View.VISIBLE);
                } else {
                    binding.noTask.setVisibility(View.GONE);
                }
            }
        );

        viewModel.getTaskViewEvent().observe(getViewLifecycleOwner(), viewEvent -> {
            FragmentTransaction ft = getParentFragmentManager().beginTransaction();
            if (viewEvent == ViewEvent.DISPLAY_ADD_TASK_DIALOG) {
                AddTaskDialogFragment.newInstance().show(ft, TAG_ADD_TASK_DIALOG);
            }
        });

        return binding.getRoot();
    }

    @Override
    public void onDeleteTaskButtonClick(long taskId) {
        viewModel.onDeleteTaskButtonClicked(taskId);
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.actions, menu);
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.filter_alphabetical:
                viewModel.onSortingTypeChanged(TaskSortingType.AZ);
                return true;
            case R.id.filter_alphabetical_inverted:
                viewModel.onSortingTypeChanged(TaskSortingType.ZA);
                return true;
            case R.id.filter_oldest_first:
                viewModel.onSortingTypeChanged(TaskSortingType.OLDEST_FIRST);
                return true;
            case R.id.filter_recent_first:
                viewModel.onSortingTypeChanged(TaskSortingType.NEWEST_FIRST);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
