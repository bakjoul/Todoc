package com.bakjoul.todoc.ui.add;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.bakjoul.todoc.R;
import com.bakjoul.todoc.databinding.AddTaskDialogBinding;

public class AddTaskDialogFragment extends DialogFragment {

    @NonNull
    public static AddTaskDialogFragment newInstance() {
        return new AddTaskDialogFragment();
    }

    private AddTaskViewModel viewModel;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        AddTaskDialogBinding b = AddTaskDialogBinding.inflate(inflater, container, false);

        final AddTaskProjectSpinnerAdapter adapter = new AddTaskProjectSpinnerAdapter(requireContext(), R.layout.add_task_project_spinner_item);
        b.addTaskProjectSpinnerActv.setAdapter(adapter);
        b.addTaskProjectSpinnerActv.setOnClickListener(view -> viewModel.onProjectSelected());

        return b.getRoot();
    }
}
