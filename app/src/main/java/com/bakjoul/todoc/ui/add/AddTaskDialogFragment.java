package com.bakjoul.todoc.ui.add;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.lifecycle.ViewModelProvider;

import com.bakjoul.todoc.R;
import com.bakjoul.todoc.databinding.AddTaskDialogBinding;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class AddTaskDialogFragment extends DialogFragment {

    @NonNull
    public static AddTaskDialogFragment newInstance() {
        return new AddTaskDialogFragment();
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        return new Dialog(getActivity(), getTheme()) {
            @Override
            public boolean dispatchTouchEvent(@NonNull MotionEvent motionEvent) {
                if (getCurrentFocus() != null) {
                    InputMethodManager inputMethodManager = (InputMethodManager) getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                    inputMethodManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
                }
                return super.dispatchTouchEvent(motionEvent);
            }
        };
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        AddTaskDialogBinding b = AddTaskDialogBinding.inflate(inflater, container, false);

        AddTaskViewModel viewModel = new ViewModelProvider(this).get(AddTaskViewModel.class);

        final AddTaskProjectSpinnerAdapter adapter = new AddTaskProjectSpinnerAdapter(requireContext(), R.layout.add_task_project_spinner_item);
        b.addTaskProjectSpinnerActv.setAdapter(adapter);
        b.addTaskProjectSpinnerActv.setOnClickListener(view -> viewModel.onProjectSelected());

        return b.getRoot();
    }
}
