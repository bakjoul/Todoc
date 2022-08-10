package com.bakjoul.todoc.ui.add;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.bakjoul.todoc.R;
import com.bakjoul.todoc.databinding.AddTaskProjectSpinnerItemBinding;

public class AddTaskProjectSpinnerAdapter extends ArrayAdapter<AddTaskProjectItemViewState> {
    public AddTaskProjectSpinnerAdapter(@NonNull Context context) {
        super(context, R.layout.add_task_project_spinner_item);
    }

    @Override
    public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        return getCustomView(position, parent);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        return getCustomView(position, parent);
    }

    public View getCustomView(int position, @NonNull ViewGroup parent) {
        AddTaskProjectSpinnerItemBinding binding = AddTaskProjectSpinnerItemBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);

        AddTaskProjectItemViewState item = getItem(position);

        binding.addTaskSpinnerItemProjectColor.setColorFilter(item.getProjectColor());
        binding.addTaskSpinnerProjectName.setText(item.getProjectName());

        return binding.getRoot();
    }

    @NonNull
    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                return null;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {

            }

            @Override
            public CharSequence convertResultToString(Object resultValue) {
                return ((AddTaskProjectItemViewState) resultValue).getProjectName();
            }
        };
    }
}
