package com.bakjoul.todoc.ui.add;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

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
        AddTaskDialogBinding binding = AddTaskDialogBinding.inflate(inflater, container, false);

        AddTaskViewModel viewModel = new ViewModelProvider(this).get(AddTaskViewModel.class);

        AddTaskProjectSpinnerAdapter adapter = new AddTaskProjectSpinnerAdapter(requireContext());
        binding.addTaskProjectSpinnerActv.setAdapter(adapter);
        binding.addTaskProjectSpinnerActv.setOnItemClickListener((adapterView, view, i, l) ->
            viewModel.onProjectSelected(adapter.getItem(i).getProjectId()));

        binding.addTaskDescriptionEdit.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                viewModel.onTaskDescriptionChanged(editable.toString());
            }
        });

        binding.addTaskAddButton.setOnClickListener(view -> viewModel.onAddButtonClicked());

        viewModel.getAddTaskViewStateLiveData().observe(this, addTaskViewState -> {
            binding.addTaskDescription.setError(addTaskViewState.getTaskDescriptionError());
            binding.addTaskProjectSpinnerLayout.setError(addTaskViewState.getProjectError());
        });

        viewModel.getProjectItemsViewState().observe(this, projects -> {
            adapter.clear();
            adapter.addAll(projects);
        });

        viewModel.getAddTaskViewEvent().observe(this, viewEvent -> {
            switch (viewEvent) {
                case DISMISS_ADD_TASK_DIALOG:
                    dismiss();
                    break;
                case TOAST_ADD_TASK_SQL_EXCEPTION:
                    Toast.makeText(requireContext(), R.string.toast_cannot_add_task, Toast.LENGTH_LONG).show();
                    break;
            }
        });

        return binding.getRoot();
    }
}
