package com.example.sandarumk.bakingapp;

import android.support.test.espresso.contrib.RecyclerViewActions;
import android.support.test.espresso.matcher.ViewMatchers;
import android.support.test.filters.LargeTest;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.example.sandarumk.bakingapp.activities.MainActivity;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withSubstring;

@RunWith(AndroidJUnit4.class)
@LargeTest
public class MainActivityTest {

    @Rule
    public ActivityTestRule<MainActivity> mActivityRule = new ActivityTestRule<>(MainActivity.class);

    @Test
    public void testRecipesClick() {
        int position = 0;

        onView(ViewMatchers.withId(R.id.rv_recipe_view))
                .perform(RecyclerViewActions.actionOnItemAtPosition(position,
                        click()));

        String itemElementText = mActivityRule.getActivity().getResources()
                .getString(R.string.title_recipestep_list);
        onView(withSubstring(itemElementText)).check(matches(isDisplayed()));

    }

}