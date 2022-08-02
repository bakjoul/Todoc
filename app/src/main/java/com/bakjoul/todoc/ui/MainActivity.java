package com.bakjoul.todoc.ui;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.bakjoul.todoc.R;
import com.bakjoul.todoc.ui.task.TaskFragment;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.main_layout, TaskFragment.newInstance())
                .commitNow();
    }
}