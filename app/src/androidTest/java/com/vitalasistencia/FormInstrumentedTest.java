package com.vitalasistencia;


import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;

import androidx.test.espresso.Espresso;
import androidx.test.espresso.ViewInteraction;
import androidx.test.espresso.action.ViewActions;
import androidx.test.espresso.matcher.ViewMatchers;
import androidx.test.filters.LargeTest;
import androidx.test.rule.ActivityTestRule;
import androidx.test.runner.AndroidJUnit4;

import com.vitalasistencia.R;
import com.vitalasistencia.presenters.PList;
import com.vitalasistencia.views.MainActivity;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import io.realm.Realm;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.replaceText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.contrib.RecyclerViewActions.actionOnItemAtPosition;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withClassName;
import static androidx.test.espresso.matcher.ViewMatchers.withContentDescription;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.is;

@LargeTest
@RunWith(AndroidJUnit4.class)
public class FormInstrumentedTest {

    @Rule
    public ActivityTestRule<MainActivity> mActivityTestRule = new ActivityTestRule<>(MainActivity.class);

    @Before
    public void removePreviusDB(){
        Realm realm = Realm.getDefaultInstance();
        realm.beginTransaction();
        realm.commitTransaction();
        realm.close();
        Realm.deleteRealm(realm.getConfiguration());
    }

    @Test
    public void instrumentedTest() {
        try {
            Thread.sleep(6000);
        } catch (InterruptedException e) {
            System.out.println("Hubo un error en la carga de OnCreate");
        }
        ViewInteraction floatingActionButton = onView(
                allOf(withId(R.id.fab), withContentDescription("Vital Asistencia"),
                        childAtPosition(
                                childAtPosition(
                                        withId(android.R.id.content),
                                        0),
                                2),
                        isDisplayed()));
        floatingActionButton.perform(click());

        ViewInteraction appCompatButton = onView(
                allOf(withId(R.id.date_picker),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(is("android.widget.LinearLayout")),
                                        2),
                                2),
                        isDisplayed()));
        appCompatButton.perform(click());

        ViewInteraction appCompatButton2 = onView(
                allOf(withId(android.R.id.button1), withText("OK"),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(is("android.widget.ScrollView")),
                                        0),
                                3),
                        isDisplayed()));
        appCompatButton2.perform(click());

        ViewInteraction textInputEditText = onView(
                allOf(withId(R.id.TEI_phone),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.phone_form),
                                        0),
                                1),
                        isDisplayed()));
        textInputEditText.perform(replaceText("5556667778"), closeSoftKeyboard());

        ViewInteraction textInputEditText2 = onView(
                allOf(withId(R.id.TEI_email),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.email_form),
                                        0),
                                1),
                        isDisplayed()));
        textInputEditText2.perform(replaceText("a@1"), closeSoftKeyboard());

        ViewInteraction textInputEditText3 = onView(
                allOf(withId(R.id.TEI_address),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.address_form),
                                        0),
                                1),
                        isDisplayed()));
        textInputEditText3.perform(replaceText("C"), closeSoftKeyboard());

        ViewInteraction textInputEditText4 = onView(
                allOf(withId(R.id.TEI_affiliate_number),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.affiliate_number_form),
                                        0),
                                1),
                        isDisplayed()));
        textInputEditText4.perform(replaceText("112AAAAA"), closeSoftKeyboard());

        ViewInteraction appCompatButton3 = onView(
                allOf(withId(R.id.add_spinner),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(is("android.widget.LinearLayout")),
                                        1),
                                2),
                        isDisplayed()));
        appCompatButton3.perform(click());

        ViewInteraction appCompatEditText = onView(
                allOf(withId(R.id.dialogInput),
                        childAtPosition(
                                allOf(withId(R.id.custom_dialog_layout_design_user_input),
                                        childAtPosition(
                                                withId(android.R.id.custom),
                                                0)),
                                1),
                        isDisplayed()));
        appCompatEditText.perform(replaceText("Lunes"), closeSoftKeyboard());

        ViewInteraction appCompatButton4 = onView(
                allOf(withId(android.R.id.button1), withText("Add"),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(is("android.widget.ScrollView")),
                                        0),
                                3),
                        isDisplayed()));
        appCompatButton4.perform(click());
        Espresso.onView(ViewMatchers.withId(R.id.CL_FormActivity)).perform(ViewActions.swipeUp());
        ViewInteraction appCompatButton5 = onView(
                allOf(withId(R.id.Save_Form), withText("Save"),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(is("android.widget.LinearLayout")),
                                        8),
                                1),
                        isDisplayed()));
        appCompatButton5.perform(click());

        ViewInteraction recyclerView = onView(
                allOf(withId(R.id.recyclerView_List),
                        childAtPosition(
                                withId(R.id.swipeRefresh),
                                1)));
        recyclerView.perform(actionOnItemAtPosition(0, click()));

        ViewInteraction textView = onView(
                allOf(withId(android.R.id.text1), withText("Lunes"), isDisplayed()));
        textView.check(matches(withText("Lunes")));

        ViewInteraction editText = onView(
                allOf(withId(R.id.date_search_tei), withText("19/02/2021"), isDisplayed()));
        editText.check(matches(withText("19/02/2021")));

        ViewInteraction editText2 = onView(
                allOf(withId(R.id.TEI_phone), withText("5556667778"), isDisplayed()));
        editText2.check(matches(withText("5556667778")));

        ViewInteraction editText3 = onView(
                allOf(withId(R.id.TEI_email), withText("a@1"), isDisplayed()));
        editText3.check(matches(withText("a@1")));

        ViewInteraction editText4 = onView(
                allOf(withId(R.id.TEI_address), withText("C"), isDisplayed()));
        editText4.check(matches(withText("C")));

        ViewInteraction editText5 = onView(
                allOf(withId(R.id.TEI_affiliate_number), withText("112AAAAA"), isDisplayed()));
        editText5.check(matches(withText("112AAAAA")));

        ViewInteraction editText6 = onView(
                allOf(withId(R.id.TEI_affiliate_number), withText("112AAAAA"), isDisplayed()));
        editText6.check(matches(withText("112AAAAA")));
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

    @After
    public void reloadTenUsers(){
        PList a=new PList();
        a.tenUsersForFirstTime();
    }
}
