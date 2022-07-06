package com.bakjoul.todoc.ui.task;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.bakjoul.todoc.databinding.TaskFragmentBinding;

public class TaskFragment extends Fragment {

    public static TaskFragment newInstance() {
        return new TaskFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        TaskFragmentBinding b = TaskFragmentBinding.inflate(inflater, container, false);

        return b.getRoot();
    }
}
