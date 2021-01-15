package ru.shtrm.familyfinder.ui.login.view;


import android.app.Activity;
import android.support.test.espresso.ViewInteraction;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.support.test.runner.lifecycle.ActivityLifecycleMonitorRegistry;
import android.support.test.runner.lifecycle.Stage;
import android.test.suitebuilder.annotation.LargeTest;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.Collection;
import java.util.Iterator;

import ru.shtrm.familyfinder.R;

import static android.support.test.InstrumentationRegistry.getInstrumentation;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.closeSoftKeyboard;
import static android.support.test.espresso.action.ViewActions.pressImeActionButton;
import static android.support.test.espresso.action.ViewActions.replaceText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.contrib.RecyclerViewActions.actionOnItemAtPosition;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withClassName;
import static android.support.test.espresso.matcher.ViewMatchers.withContentDescription;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.is;

@LargeTest
@RunWith(AndroidJUnit4.class)
public class AllActivityTest {

    @Rule
    public ActivityTestRule<LoginActivity> mActivityTestRule = new ActivityTestRule<>(LoginActivity.class);

    @Test
    public void allActivityTest() throws Exception {
        try {
            onView(withId(R.id.et_email)).check(matches(isDisplayed())).perform(click());
            onView(allOf(withId(R.id.et_email),
                    childAtPosition(
                            childAtPosition(
                                    withClassName(is("android.support.design.widget.TextInputLayout")),
                                    0),
                            0),
                    isDisplayed()))
                    .perform(replaceText("olejek8@yandex.ru"), closeSoftKeyboard());

            onView(allOf(withId(R.id.et_password),
                    childAtPosition(
                            childAtPosition(
                                    withClassName(is("android.support.design.widget.TextInputLayout")),
                                    0),
                            0),
                    isDisplayed())).perform(click());

            onView(allOf(withId(R.id.et_password),
                    childAtPosition(
                            childAtPosition(
                                    withClassName(is("android.support.design.widget.TextInputLayout")),
                                    0),
                            0), isDisplayed()))
                    .perform(replaceText("123456"), closeSoftKeyboard());

            onView(
                    allOf(withId(R.id.btnServerLogin), withText("Login"),
                            childAtPosition(
                                    childAtPosition(
                                            withClassName(is("android.support.v7.widget.CardView")),
                                            1),
                                    2), isDisplayed())).perform(click());
        }
        catch (Exception e) {

        }

        // Added a sleep statement to match the app's execution delay.
        // The recommended way to handle such scenarios is to use Espresso idling resources:
        // https://google.github.io/android-testing-support-library/docs/espresso/idling-resource/index.html
        try {
            Thread.sleep(20000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        ViewInteraction bottomBarTab = onView(
                allOf(withId(R.id.menu_family),
                        childAtPosition(
                                allOf(withId(R.id.bb_bottom_bar_item_container),
                                        childAtPosition(
                                                withId(R.id.bb_bottom_bar_outer_container),
                                                1)),
                                1),
                        isDisplayed()));
        bottomBarTab.perform(click());

        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        ViewInteraction bottomBarTab2 = onView(
                allOf(withId(R.id.menu_profile),
                        childAtPosition(
                                allOf(withId(R.id.bb_bottom_bar_item_container),
                                        childAtPosition(
                                                withId(R.id.bb_bottom_bar_outer_container),
                                                1)),
                                2),
                        isDisplayed()));
        bottomBarTab2.perform(click());

        ViewInteraction bottomBarTab4 = onView(
                allOf(withId(R.id.menu_map),
                        childAtPosition(
                                allOf(withId(R.id.bb_bottom_bar_item_container),
                                        childAtPosition(
                                                withId(R.id.bb_bottom_bar_outer_container),
                                                1)),
                                0),
                        isDisplayed()));
        bottomBarTab4.perform(click());

        ViewInteraction bottomBarTab5 = onView(
                allOf(withId(R.id.menu_family),
                        childAtPosition(
                                allOf(withId(R.id.bb_bottom_bar_item_container),
                                        childAtPosition(
                                                withId(R.id.bb_bottom_bar_outer_container),
                                                1)),
                                1),
                        isDisplayed()));
        bottomBarTab5.perform(click());

        ViewInteraction bottomBarTab6 = onView(
                allOf(withId(R.id.menu_profile),
                        childAtPosition(
                                allOf(withId(R.id.bb_bottom_bar_item_container),
                                        childAtPosition(
                                                withId(R.id.bb_bottom_bar_outer_container),
                                                1)),
                                2),
                        isDisplayed()));
        bottomBarTab6.perform(click());

        // Added a sleep statement to match the app's execution delay.
        // The recommended way to handle such scenarios is to use Espresso idling resources:
        // https://google.github.io/android-testing-support-library/docs/espresso/idling-resource/index.html
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        onView(withId(R.id.user_text_name)).perform(click());

        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        onView(withId(R.id.user_text_name)).perform(click());

        try {
            Thread.sleep(4000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        onView(withId(R.id.user_text_name)).perform(replaceText("Olejek2"));
        onView(withId(R.id.user_text_name)).perform(closeSoftKeyboard());

        // Added a sleep statement to match the app's execution delay.
        // The recommended way to handle such scenarios is to use Espresso idling resources:
        // https://google.github.io/android-testing-support-library/docs/espresso/idling-resource/index.html
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        ViewInteraction appCompatEditText5 = onView(
                allOf(withId(R.id.user_text_name), withText("Olejek2"),
                        childAtPosition(
                                allOf(withId(R.id.user_info_common),
                                        childAtPosition(
                                                withId(R.id.coordinatorLayout),
                                                0)),
                                1),
                        isDisplayed()));
        appCompatEditText5.perform(replaceText("Olejek"));

        ViewInteraction appCompatEditText6 = onView(
                allOf(withId(R.id.user_text_name), withText("Olejek"),
                        childAtPosition(
                                allOf(withId(R.id.user_info_common),
                                        childAtPosition(
                                                withId(R.id.coordinatorLayout),
                                                0)),
                                1),
                        isDisplayed()));
        appCompatEditText6.perform(closeSoftKeyboard());

        ViewInteraction appCompatEditText7 = onView(
                allOf(withId(R.id.user_text_name), withText("Olejek"),
                        childAtPosition(
                                allOf(withId(R.id.user_info_common),
                                        childAtPosition(
                                                withId(R.id.coordinatorLayout),
                                                0)),
                                1),
                        isDisplayed()));
        appCompatEditText7.perform(pressImeActionButton());

        ViewInteraction appCompatImageButton = onView(
                allOf(withContentDescription("Open navigation drawer"),
                        childAtPosition(
                                allOf(withId(R.id.toolbar),
                                        childAtPosition(
                                                withId(R.id.appbar_layout),
                                                0)),
                                1),
                        isDisplayed()));
        appCompatImageButton.perform(click());

        ViewInteraction navigationMenuItemView = onView(
                allOf(childAtPosition(
                        allOf(withId(R.id.design_navigation_view),
                                childAtPosition(
                                        withId(R.id.navView),
                                        0)),
                        1),
                        isDisplayed()));
        navigationMenuItemView.perform(click());

        // Added a sleep statement to match the app's execution delay.
        // The recommended way to handle such scenarios is to use Espresso idling resources:
        // https://google.github.io/android-testing-support-library/docs/espresso/idling-resource/index.html
        try {
            Thread.sleep(4000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        ViewInteraction appCompatImageButton2 = onView(
                allOf(withContentDescription("Open navigation drawer"),
                        childAtPosition(
                                allOf(withId(R.id.toolbar),
                                        childAtPosition(
                                                withId(R.id.appbar_layout),
                                                0)),
                                1),
                        isDisplayed()));
        appCompatImageButton2.perform(click());

        // Added a sleep statement to match the app's execution delay.
        // The recommended way to handle such scenarios is to use Espresso idling resources:
        // https://google.github.io/android-testing-support-library/docs/espresso/idling-resource/index.html
        try {
            Thread.sleep(4000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        ViewInteraction navigationMenuItemView2 = onView(
                allOf(childAtPosition(
                        allOf(withId(R.id.design_navigation_view),
                                childAtPosition(
                                        withId(R.id.navView),
                                        0)),
                        2),
                        isDisplayed()));
        navigationMenuItemView2.perform(click());

        // Added a sleep statement to match the app's execution delay.
        // The recommended way to handle such scenarios is to use Espresso idling resources:
        // https://google.github.io/android-testing-support-library/docs/espresso/idling-resource/index.html
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        ViewInteraction appCompatImageButton3 = onView(
                allOf(withContentDescription("Open navigation drawer"),
                        childAtPosition(
                                allOf(withId(R.id.toolbar),
                                        childAtPosition(
                                                withId(R.id.appbar_layout),
                                                0)),
                                1),
                        isDisplayed()));
        appCompatImageButton3.perform(click());

        ViewInteraction navigationMenuItemView3 = onView(
                allOf(childAtPosition(
                        allOf(withId(R.id.design_navigation_view),
                                childAtPosition(
                                        withId(R.id.navView),
                                        0)),
                        3),
                        isDisplayed()));
        navigationMenuItemView3.perform(click());

        onView(allOf(withContentDescription("Open navigation drawer"),
                        childAtPosition(
                                allOf(withId(R.id.toolbar),
                                        childAtPosition(
                                                withId(R.id.appbar_layout),
                                                0)),
                                1),
                        isDisplayed())).perform(click());

        onView(allOf(childAtPosition(
                        allOf(withId(R.id.design_navigation_view),
                                childAtPosition(
                                        withId(R.id.navView),
                                        0)),
                        6),
                        isDisplayed())).perform(click());

        onView(withContentDescription("Open navigation drawer")).perform(click());

        onView(allOf(withId(R.id.menu_family),
                        childAtPosition(
                                allOf(withId(R.id.bb_bottom_bar_item_container),
                                        childAtPosition(
                                                withId(R.id.bb_bottom_bar_outer_container),
                                                1)),
                                1),
                        isDisplayed())).perform(click());

        // Added a sleep statement to match the app's execution delay.
        // The recommended way to handle such scenarios is to use Espresso idling resources:
        // https://google.github.io/android-testing-support-library/docs/espresso/idling-resource/index.html
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        ViewInteraction recyclerView2 = onView(
                allOf(withId(R.id.recyclerView),
                        childAtPosition(
                                withClassName(is("android.widget.LinearLayout")),
                                0)));
        recyclerView2.perform(actionOnItemAtPosition(2, click()));

        ViewInteraction bottomBarTab8 = onView(
                allOf(withId(R.id.menu_profile),
                        childAtPosition(
                                allOf(withId(R.id.bb_bottom_bar_item_container),
                                        childAtPosition(
                                                withId(R.id.bb_bottom_bar_outer_container),
                                                1)),
                                2),
                        isDisplayed()));
        bottomBarTab8.perform(click());

        // Added a sleep statement to match the app's execution delay.
        // The recommended way to handle such scenarios is to use Espresso idling resources:
        // https://google.github.io/android-testing-support-library/docs/espresso/idling-resource/index.html
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        ViewInteraction bottomBarTab9 = onView(
                allOf(withId(R.id.menu_profile),
                        childAtPosition(
                                allOf(withId(R.id.bb_bottom_bar_item_container),
                                        childAtPosition(
                                                withId(R.id.bb_bottom_bar_outer_container),
                                                1)),
                                2),
                        isDisplayed()));
        bottomBarTab9.perform(click());

        // Added a sleep statement to match the app's execution delay.
        // The recommended way to handle such scenarios is to use Espresso idling resources:
        // https://google.github.io/android-testing-support-library/docs/espresso/idling-resource/index.html
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        ViewInteraction appCompatImageButton6 = onView(
                allOf(withContentDescription("Open navigation drawer"),
                        childAtPosition(
                                allOf(withId(R.id.toolbar),
                                        childAtPosition(
                                                withId(R.id.appbar_layout),
                                                0)),
                                1),
                        isDisplayed()));
        appCompatImageButton6.perform(click());

        ViewInteraction navigationMenuItemView5 = onView(
                allOf(childAtPosition(
                        allOf(withId(R.id.design_navigation_view),
                                childAtPosition(
                                        withId(R.id.navView),
                                        0)),
                        5),
                        isDisplayed()));
        navigationMenuItemView5.perform(click());

    }

    private Activity getActivityInstance(){
        final Activity[] currentActivity = {null};

        getInstrumentation().runOnMainSync(new Runnable(){
            public void run(){
                Collection<Activity> resumedActivity = ActivityLifecycleMonitorRegistry.getInstance().getActivitiesInStage(Stage.RESUMED);
                Iterator<Activity> it = resumedActivity.iterator();
                currentActivity[0] = it.next();
            }
        });

        return currentActivity[0];
    }

    private static Matcher<View> childAtPosition(
            final Matcher<View> parentMatcher, final int position) {

        return new TypeSafeMatcher<View>() {
            @Override
            public void describeTo(Description description) {
                description.appendText("Child at position " + position + " in parent ");
                parentMatcher.describeTo(description);
            }

            @Override
            public boolean matchesSafely(View view) {
                ViewParent parent = view.getParent();
                return parent instanceof ViewGroup && parentMatcher.matches(parent)
                        && view.equals(((ViewGroup) parent).getChildAt(position));
            }
        };
    }
}
