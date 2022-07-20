package com.bakjoul.todoc.ui.task;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
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
        TaskFragmentBinding b = TaskFragmentBinding.inflate(inflater, container, false);
        viewModel = new ViewModelProvider(this).get(TaskViewModel.class);

        TaskAdapter adapter = new TaskAdapter(this);
        b.taskRecyclerView.setAdapter(adapter);

        b.fabAdd.setOnClickListener(view -> viewModel.onAddButtonClicked());

        viewModel.getTaskViewStateLiveData().observe(getViewLifecycleOwner(), taskViewState -> {
                    adapter.submitList(taskViewState.getTaskViewStateItems());
                    if (taskViewState.isEmptyStateVisible()) {
                        b.noTask.setVisibility(View.VISIBLE);
                    } else {
                        b.noTask.setVisibility(View.GONE);
                    }
                }
        );

        viewModel.getTaskViewEvent().observe(getViewLifecycleOwner(), viewEvent -> {
            FragmentTransaction ft = getParentFragmentManager().beginTransaction();
            Fragment prev;
            if (viewEvent == ViewEvent.DISPLAY_ADD_TASK_DIALOG) {
                prev = getParentFragmentManager().findFragmentByTag(TAG_ADD_TASK_DIALOG);
                if (prev == null) {
                    ft.addToBackStack(null);
                    AddTaskDialogFragment.newInstance().show(ft, TAG_ADD_TASK_DIALOG);
                }
            }
        });

        return b.getRoot();
    }

    @Override
    public void onDeleteTaskButtonClick(long taskId) {
        viewModel.onDeleteTaskButtonClicked(taskId);
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.actions, menu);
    }
}
