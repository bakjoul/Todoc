package com.bakjoul.todoc.ui;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isCompletelyDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;

import androidx.test.core.app.ActivityScenario;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.bakjoul.todoc.R;
import com.bakjoul.todoc.utils.DrawableMatcher;
import com.bakjoul.todoc.utils.RecyclerViewItemCountAssertion;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4.class)
public class MainActivityTest {

    @Before
    public void setUp() {
        ActivityScenario.launch(MainActivity.class);
    }

    @Test
    public void assertNoTask() {
        onView(allOf(withId(R.id.no_task), isCompletelyDisplayed()))
                .check(matches(withText(R.string.no_task)))
                .check(matches(new DrawableMatcher(R.drawable.ic_baseline_work_off_24, DrawableMatcher.TextDrawablePosition.TOP)));
        onView(allOf(withId(R.id.task_RecyclerView), isCompletelyDisplayed()))
                .check(new RecyclerViewItemCountAssertion(0));

    }

}