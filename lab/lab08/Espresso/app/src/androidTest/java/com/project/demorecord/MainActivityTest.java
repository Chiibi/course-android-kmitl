package com.project.demorecord;


import android.support.test.espresso.ViewInteraction;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.test.suitebuilder.annotation.LargeTest;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import org.hamcrest.core.IsInstanceOf;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.closeSoftKeyboard;
import static android.support.test.espresso.action.ViewActions.replaceText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static com.project.demorecord.TestUtils.withRecyclerView;
import static org.hamcrest.Matchers.allOf;

@LargeTest
@RunWith(AndroidJUnit4.class)
public class MainActivityTest {

    @Rule
    public ActivityTestRule<MainActivity> mActivityTestRule = new ActivityTestRule<>(MainActivity.class);

    @Test
    public void NullUserAndAgeTest() {
        onView(allOf(withId(R.id.editTExtName),isDisplayed())).check(matches(withText("")));
        onView(allOf(withId(R.id.editTextAge),isDisplayed())).check(matches(withText("")));
        onView(allOf(withId(R.id.buttonAdded), withText("ADDED"), isDisplayed())).perform(click());
        onView(allOf(withId(android.R.id.message), isDisplayed())).check(matches(withText("Please Enter user info")));
    }

    @Test
    public void NullUserTest(){
        onView(allOf(withId(R.id.editTExtName),isDisplayed())).check(matches(withText("")));
        onView(allOf(withId(R.id.editTextAge), isDisplayed())).perform(replaceText("20"), closeSoftKeyboard());
        onView(allOf(withId(R.id.buttonAdded), withText("ADDED"), isDisplayed())).perform(click());
        onView(allOf(withId(android.R.id.message), isDisplayed())).check(matches(withText("Please Enter user info")));
    }

    @Test
    public void NullAgeTest(){
        onView(allOf(withId(R.id.editTExtName), isDisplayed())).perform(replaceText("Ying"), closeSoftKeyboard());
        onView(allOf(withId(R.id.editTextAge),isDisplayed())).check(matches(withText("")));
        onView(allOf(withId(R.id.buttonAdded), withText("ADDED"), isDisplayed())).perform(click());
        onView(allOf(withId(android.R.id.message), isDisplayed())).check(matches(withText("Please Enter user info")));
    }

    @Test
    public void UserInfoNotFoundTest(){
        onView(allOf(withId(R.id.buttonGotoList), withText("GO TO LIST"), isDisplayed())).perform(click());
        onView(allOf(withId(R.id.textNotFound), isDisplayed())).check(matches(withText("Not Found")));
    }

    @Test
    public void AddUserYingTest(){
        AddUser("Ying", "20");
        onView(withId(R.id.buttonGotoList)).perform(click());
        CheckMatchAtPosition(0, "Ying", "20");
        onView(withId(R.id.clearList)).perform(click());
        onView(allOf(withId(R.id.textNotFound), isDisplayed())).check(matches(withText("Not Found")));
    }

    @Test
    public void AddUserLadarat(){
        AddUser("Ying", "20");
        AddUser("Ladarat", "20");
        onView(withId(R.id.buttonGotoList)).perform(click());
        CheckMatchAtPosition(0, "Ying", "20");
        CheckMatchAtPosition(1, "Ladarat", "20");
        onView(withId(R.id.clearList)).perform(click());
        onView(allOf(withId(R.id.textNotFound), isDisplayed())).check(matches(withText("Not Found")));
    }

    @Test
    public void AddUserSomkaitTest(){
        AddUser("Ying", "20");
        AddUser("Ladarat", "20");
        AddUser("Somkait", "80");
        onView(withId(R.id.buttonGotoList)).perform(click());
        CheckMatchAtPosition(0, "Ying", "20");
        CheckMatchAtPosition(1, "Ladarat", "20");
        CheckMatchAtPosition(2, "Somkait", "80");
        onView(withId(R.id.clearList)).perform(click());
        onView(allOf(withId(R.id.textNotFound), isDisplayed())).check(matches(withText("Not Found")));
    }

    @Test
    public void AddUserPrayochAge60Test(){
        AddUser("Ying", "20");
        AddUser("Ladarat", "20");
        AddUser("Somkait", "80");
        AddUser("Prayoch", "60");
        onView(withId(R.id.buttonGotoList)).perform(click());
        CheckMatchAtPosition(0, "Ying", "20");
        CheckMatchAtPosition(1, "Ladarat", "20");
        CheckMatchAtPosition(2, "Somkait", "80");
        CheckMatchAtPosition(3, "Prayoch", "60");
        onView(withId(R.id.clearList)).perform(click());
        onView(allOf(withId(R.id.textNotFound), isDisplayed())).check(matches(withText("Not Found")));
    }

    @Test
    public void AddUserPrayochAge50Test(){
        AddUser("Ying", "20");
        AddUser("Ladarat", "20");
        AddUser("Somkait", "80");
        AddUser("Prayoch", "60");
        AddUser("Prayoch", "50");
        onView(withId(R.id.buttonGotoList)).perform(click());
        CheckMatchAtPosition(0, "Ying", "20");
        CheckMatchAtPosition(1, "Ladarat", "20");
        CheckMatchAtPosition(2, "Somkait", "80");
        CheckMatchAtPosition(3, "Prayoch", "60");
        CheckMatchAtPosition(4, "Prayoch", "50");
        onView(withId(R.id.clearList)).perform(click());
        onView(allOf(withId(R.id.textNotFound), isDisplayed())).check(matches(withText("Not Found")));
    }

    private void AddUser(String name, String age){
        onView(allOf(withId(R.id.editTExtName), isDisplayed())).perform(replaceText(name), closeSoftKeyboard());
        onView(allOf(withId(R.id.editTextAge),isDisplayed())).perform(replaceText(age), closeSoftKeyboard());
        onView(allOf(withId(R.id.buttonAdded), isDisplayed())).perform(click());
    }


    private void CheckMatchAtPosition(int position, String name, String age){
        onView(withRecyclerView(R.id.list).atPositionOnView(position, R.id.textName)).check(matches(withText(name)));
        onView(withRecyclerView(R.id.list).atPositionOnView(position, R.id.textAge)).check(matches(withText(age)));
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
