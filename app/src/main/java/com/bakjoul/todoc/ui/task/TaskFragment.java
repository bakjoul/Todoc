package com.bakjoul.todoc.ui.task;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;

import com.bakjoul.todoc.databinding.TaskFragmentBinding;
import com.bakjoul.todoc.ui.add.AddTaskDialogFragment;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class TaskFragment extends Fragment {

    private final String TAG_ADD_TASK_DIALOG = "ADD";

    public static TaskFragment newInstance() {
        return new TaskFragment();
    }

    private TaskViewModel viewModel;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        TaskFragmentBinding b = TaskFragmentBinding.inflate(inflater, container, false);
        viewModel = new ViewModelProvider(this).get(TaskViewModel.class);

        TaskAdapter adapter = new TaskAdapter();
        b.taskList.setAdapter(adapter);

        b.fabAdd.setOnClickListener(view -> viewModel.onAddButtonClicked());

        viewModel.getTaskViewStateLiveData().observe(getViewLifecycleOwner(), taskViewState ->
                adapter.submitList(taskViewState.getTaskViewStateItems()));

        viewModel.getTaskSingleLiveEvent().observe(getViewLifecycleOwner(), taskViewEvent -> {
            FragmentTransaction ft = getParentFragmentManager().beginTransaction();
            Fragment prev;
            if (taskViewEvent == TaskViewEvent.DISPLAY_ADD_TASK_DIALOG) {
                prev = getParentFragmentManager().findFragmentByTag(TAG_ADD_TASK_DIALOG);
                if (prev == null) {
                    ft.addToBackStack(null);
                    AddTaskDialogFragment.newInstance().show(ft, TAG_ADD_TASK_DIALOG);
                }
            }
        });

        return b.getRoot();
    }
}
