package com.taykangsheng.www.singaporepowerpsitracker;

import android.app.Instrumentation;
import android.support.test.InstrumentationRegistry;
import android.support.test.espresso.Espresso;
import android.support.test.espresso.IdlingResource;
import android.support.test.espresso.contrib.DrawerActions;
import android.support.test.espresso.contrib.NavigationViewActions;
import android.support.test.filters.LargeTest;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.contrib.DrawerMatchers.isClosed;
import static android.support.test.espresso.contrib.DrawerMatchers.isOpen;
import static android.support.test.espresso.matcher.ViewMatchers.isAssignableFrom;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withParent;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.CoreMatchers.allOf;


@RunWith(AndroidJUnit4.class)
@LargeTest
public class ChangeFragmentBehaviorTest {

    private navigationDrawerIdlingResource mIdlingResource;
    private MainActivity mActivity;

    @Rule
    public ActivityTestRule<MainActivity> mActivityRule = new ActivityTestRule<>(MainActivity.class);

    @Before
    public void runBeforeTest() throws Exception {

        Instrumentation instrumentation = InstrumentationRegistry.getInstrumentation();
        mActivity = mActivityRule.getActivity();

        mIdlingResource = new navigationDrawerIdlingResource(mActivity);
        Espresso.registerIdlingResources(mIdlingResource);
    }

    @After
    public void tearDown() {
        Espresso.unregisterIdlingResources(mIdlingResource);
    }

    private void checkToolbarTitle(String title){
        onView(allOf(isAssignableFrom(TextView.class), withParent(isAssignableFrom(Toolbar.class))))
                .check(matches(withText(title)));
    }

    @Test
    public void open3hrPSITest(){
        onView(withId(R.id.drawer_layout)).perform(DrawerActions.open());
        onView(withId(R.id.drawer_layout)).check(matches(isOpen()));

        String title = "3hr PSI";
        onView(withId(R.id.nav_view)).perform(NavigationViewActions.navigateTo(R.id.nav_3psi));
        onView(withId(R.id.drawer_layout)).check(matches(isClosed()));

        checkToolbarTitle(title);
        onView(withId(R.id.chart)).check(matches(isDisplayed()));
    }


    @Test
    public void open24hrPSITest(){
        onView(withId(R.id.drawer_layout)).perform(DrawerActions.open());
        onView(withId(R.id.drawer_layout)).check(matches(isOpen()));

        String title = "24hr PSI";
        onView(withId(R.id.nav_view)).perform(NavigationViewActions.navigateTo(R.id.nav_24psi));
        checkToolbarTitle(title);
        onView(withId(R.id.chart)).check(matches(isDisplayed()));
    }

    @Test
    public void openP_SubIndTest(){
        onView(withId(R.id.drawer_layout)).perform(DrawerActions.open());
        onView(withId(R.id.drawer_layout)).check(matches(isOpen()));

        String title = "Pollutant Sub-indicies";
        onView(withId(R.id.nav_view)).perform(NavigationViewActions.navigateTo(R.id.nav_psubindicies));
        checkToolbarTitle(title);
        onView(withId(R.id.chart)).check(matches(isDisplayed()));
    }

    @Test
    public void openMapTest(){
        onView(withId(R.id.drawer_layout)).perform(DrawerActions.open());
        onView(withId(R.id.drawer_layout)).check(matches(isOpen()));

        String title = "Map";
        onView(withId(R.id.nav_view)).perform(NavigationViewActions.navigateTo(R.id.nav_map));
        checkToolbarTitle(title);
        onView(withId(R.id.map_frame)).check(matches(isDisplayed()));
        onView(withId(R.id.map_readings)).check(matches(isDisplayed()));
    }

    @Test
    public void openAllTest(){
        openP_SubIndTest();
        open24hrPSITest();
        open3hrPSITest();
        openMapTest();
        openP_SubIndTest();
        open24hrPSITest();
        open3hrPSITest();
        openMapTest();
    }


    public class navigationDrawerIdlingResource implements IdlingResource{
        private MainActivity mActivity;
        private ResourceCallback mResourceCallback;

        public navigationDrawerIdlingResource(MainActivity activity){
            mActivity = activity;
        }

        @Override
        public String getName() {
            return "NavigationDrawerIdlingResource";
        }

        @Override
        public boolean isIdleNow() {
            boolean idle = (mActivity.selectedNavigationItem == mActivity.currentNavigationItem);
            if (idle) mResourceCallback.onTransitionToIdle();
            return idle;
        }

        @Override
        public void registerIdleTransitionCallback(ResourceCallback callback) {
            mResourceCallback = callback;
        }
    }
}
