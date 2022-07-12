package com.bakjoul.todoc.ui.task;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.bakjoul.todoc.databinding.TaskFragmentBinding;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class TaskFragment extends Fragment {

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

        viewModel.getTaskViewStateMediatorLiveData().observe(getViewLifecycleOwner(), taskViewStates ->
                adapter.submitList(taskViewStates));

        return b.getRoot();
    }
}
