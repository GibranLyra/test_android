package gibran.com.br.zaptest;

import android.support.test.espresso.Espresso;
import android.support.test.espresso.contrib.RecyclerViewActions;
import android.support.test.espresso.intent.rule.IntentsTestRule;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.test.suitebuilder.annotation.LargeTest;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import gibran.com.br.zaptest.imovel.ImovelActivity;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.replaceText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;

/**
 * Created by gibranlyra on 12/09/17.
 */

@RunWith(AndroidJUnit4.class)
@LargeTest
public class ImovelScreenTest {
    /**
     * {@link IntentsTestRule} is an {@link ActivityTestRule} which inits and releases Espresso
     * Intents before and after each test run.
     * <p>
     * <p>
     * Rules are interceptors which are executed for each test method and are important building
     * blocks of Junit tests.
     */
    @Rule
    public IntentsTestRule<ImovelActivity> imovelIntentsTestRule =
            new IntentsTestRule<>(ImovelActivity.class);

    /**
     * Prepare your test fixture for this test. In this case we register an IdlingResources with
     * Espresso. IdlingResource resource is a great way to tell Espresso when your app is in an
     * idle state. This helps Espresso to synchronize your test actions, which makes tests significantly
     * more reliable.
     */
    @Before
    public void registerIdlingResource() {
        Espresso.registerIdlingResources(
                imovelIntentsTestRule.getActivity().getCountingIdlingResource());
    }

    @Test
    public void showImovel() {
        onView(withId(R.id.fragment_imovel_recycler))
                .perform(RecyclerViewActions.actionOnItemAtPosition(2, click()));
        onView(withId(R.id.fragment_imovel_details_toolbar_pager)).check(matches(isDisplayed()));
    }

    @Test
    public void showContactsDialog() {
        onView(withId(R.id.fragment_imovel_recycler))
                .perform(RecyclerViewActions.actionOnItemAtPosition(2, click()));
        onView(withId(R.id.activity_imovel_details_contact_button))
                .perform(click());
        onView(withId(R.id.contact_dialog_name)).check(matches(isDisplayed()));
    }

    @Test
    public void sendContact() {
        onView(withId(R.id.fragment_imovel_recycler))
                .perform(RecyclerViewActions.actionOnItemAtPosition(2, click()));
        onView(withId(R.id.activity_imovel_details_contact_button)).perform(click());
        onView(withId(R.id.contact_dialog_name)).perform(click(), replaceText("Gibran Lyra"));
        onView(withId(R.id.contact_dialog_email)).perform(click(), replaceText("gibranlyra@gmail.com"));
        onView(withId(R.id.contact_dialog_phone)).perform(click(), replaceText("99999999"));
        onView(withText(R.string.dialog_send)).perform(click());

    }


    /**
     * Unregister your Idling Resource so it can be garbage collected and does not leak any memory.
     */
    @After
    public void unregisterIdlingResource() {
        Espresso.unregisterIdlingResources(
                imovelIntentsTestRule.getActivity().getCountingIdlingResource());
    }
}
