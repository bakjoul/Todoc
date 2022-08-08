package com.bakjoul.todoc.ui;

import static androidx.test.espresso.Espresso.closeSoftKeyboard;
import static androidx.test.espresso.Espresso.onData;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.replaceText;
import static androidx.test.espresso.action.ViewActions.scrollTo;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.RootMatchers.isPlatformPopup;
import static androidx.test.espresso.matcher.ViewMatchers.isCompletelyDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withChild;
import static androidx.test.espresso.matcher.ViewMatchers.withEffectiveVisibility;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.isA;

import androidx.annotation.NonNull;
import androidx.annotation.StringRes;
import androidx.test.core.app.ActivityScenario;
import androidx.test.espresso.contrib.RecyclerViewActions;
import androidx.test.espresso.matcher.ViewMatchers;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.bakjoul.todoc.R;
import com.bakjoul.todoc.ui.add.AddTaskProjectItemViewState;
import com.bakjoul.todoc.utils.ClickChildViewWithId;
import com.bakjoul.todoc.utils.DrawableMatcher;
import com.bakjoul.todoc.utils.RecyclerViewItemAssertion;
import com.bakjoul.todoc.utils.RecyclerViewItemCountAssertion;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4.class)
public class MainActivityTest {

    private static final long DELAY = 2_000;

    private static final String TEST_TASK_DESCRIPTION = "Test task description";

    @Before
    public void setUp() {
        ActivityScenario.launch(MainActivity.class);
    }

    @Test
    public void addAndDeleteTask() throws InterruptedException {
        assertNoTask();
        addAndDeleteTask(TEST_TASK_DESCRIPTION, Project.TARTAMPION);

        // Check that task was added
        onView(withId(R.id.task_RecyclerView))
                .check(new RecyclerViewItemAssertion(0, R.id.task_item_description, withText(TEST_TASK_DESCRIPTION)));

        // Check that recycler view contains only one element
        onView(withId(R.id.task_RecyclerView))
                .check(new RecyclerViewItemCountAssertion(1));

        // Check that no task TextView is not displayed
        onView(withId(R.id.no_task))
                .check(matches(withEffectiveVisibility(ViewMatchers.Visibility.GONE)));

        // Delete previously added task
        onView(withId(R.id.task_RecyclerView))
                .perform(RecyclerViewActions.actionOnItemAtPosition(0, new ClickChildViewWithId(R.id.task_item_delete)));
        Thread.sleep(DELAY);

        assertNoTask();
    }

    private void assertNoTask() {
        onView(allOf(withId(R.id.no_task), isCompletelyDisplayed()))
                .check(matches(withText(R.string.no_task)))
                .check(matches(new DrawableMatcher(R.drawable.ic_baseline_work_off_24, DrawableMatcher.TextDrawablePosition.TOP)));
        onView(allOf(withId(R.id.task_RecyclerView), isCompletelyDisplayed()))
                .check(new RecyclerViewItemCountAssertion(0));
    }

    private void addAndDeleteTask(@NonNull String taskDescription, @NonNull Project project) throws InterruptedException {
        onView(withId(R.id.fab_add)).perform(click());
        onView(withId(R.id.add_task_description_edit)).perform(replaceText(taskDescription));
        closeSoftKeyboard();

        onView(withId(R.id.add_task_project_spinner_actv)).perform(click());
        onData(isA(AddTaskProjectItemViewState.class))
                .inRoot(isPlatformPopup())
                .atPosition(project.spinnerIndex)
                .check(matches(withChild(withText(project.projectNameStringRes))))
                .perform(scrollTo(), click());
        onView(withId(R.id.add_task_add_button)).perform(click());

        Thread.sleep(DELAY);
    }

    private enum Project {
        TARTAMPION(0, R.string.project_tartampion),
        LUCIDIA(1, R.string.project_lucidia),
        CIRCUS(2, R.string.project_circus);

        private final int spinnerIndex;
        private final int projectNameStringRes;

        Project(int spinnerIndex, @StringRes int projectNameStringRes) {
            this.spinnerIndex = spinnerIndex;
            this.projectNameStringRes = projectNameStringRes;
        }
    }

}