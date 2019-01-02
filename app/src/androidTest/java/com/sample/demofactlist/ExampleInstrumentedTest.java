package com.sample.demofactlist;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.annotation.UiThreadTest;
import android.support.test.espresso.Espresso;
import android.support.test.espresso.IdlingPolicies;
import android.support.test.espresso.IdlingRegistry;
import android.support.test.espresso.IdlingResource;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.support.v7.widget.RecyclerView;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.List;
import java.util.concurrent.TimeUnit;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static org.hamcrest.CoreMatchers.not;
import static org.junit.Assert.*;

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class ExampleInstrumentedTest {

    private IdlingResource mIdlingResource = EspressoIdlingResouce.getIdlingResource();

    @Rule
    public ActivityTestRule<MainActivity> mActivityRule = new ActivityTestRule(MainActivity.class);

    @Before
    public void init() {
        IdlingPolicies.setMasterPolicyTimeout(60, TimeUnit.SECONDS);
        IdlingPolicies.setIdlingResourceTimeout(60, TimeUnit.SECONDS);
    }

    @After
    public void unregisterIdlingResource() {
        List<IdlingResource> idlingResourceList = Espresso.getIdlingResources();
        if (idlingResourceList != null) {
            for (int i = 0; i < idlingResourceList.size(); i++) {
                IdlingRegistry.getInstance().unregister(idlingResourceList.get(i));
            }
        }
    }


    @Test
    public void listSuccessStatus() {
        // if item loaded successfully
        IdlingRegistry.getInstance().register(mIdlingResource);
        mActivityRule.getActivity().initList();
        onView(withId(R.id.recycler_list)).check(matches((isDisplayed())));

    }

    @Test
    public void listErrorStatus() {
        // if internet is switch off or item can not be fetched
        IdlingRegistry.getInstance().register(mIdlingResource);
        mActivityRule.getActivity().initList();
        onView(withId(R.id.recycler_list)).check(matches(not(isDisplayed())));

    }
}
