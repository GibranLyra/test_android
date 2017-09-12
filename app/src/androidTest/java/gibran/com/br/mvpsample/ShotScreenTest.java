package gibran.com.br.mvpsample;

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

import gibran.com.br.mvpsample.shot.ShotActivity;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.matcher.ViewMatchers.withId;

/**
 * Created by gibranlyra on 12/09/17.
 */

@RunWith(AndroidJUnit4.class)
@LargeTest
public class ShotScreenTest {
    /**
     * {@link IntentsTestRule} is an {@link ActivityTestRule} which inits and releases Espresso
     * Intents before and after each test run.
     * <p>
     * <p>
     * Rules are interceptors which are executed for each test method and are important building
     * blocks of Junit tests.
     */
    @Rule
    public IntentsTestRule<ShotActivity> shotIntentsTestRule =
            new IntentsTestRule<>(ShotActivity.class);

    /**
     * Prepare your test fixture for this test. In this case we register an IdlingResources with
     * Espresso. IdlingResource resource is a great way to tell Espresso when your app is in an
     * idle state. This helps Espresso to synchronize your test actions, which makes tests significantly
     * more reliable.
     */
    @Before
    public void registerIdlingResource() {
        Espresso.registerIdlingResources(
                shotIntentsTestRule.getActivity().getCountingIdlingResource());
    }

    @Test
    public void showAllShots() {
        // Add 2 active tasks
        onView(withId(R.id.fragment_shot_recycler))
                .perform(RecyclerViewActions.actionOnItemAtPosition(2, click()));
    }

    /**
     * Unregister your Idling Resource so it can be garbage collected and does not leak any memory.
     */
    @After
    public void unregisterIdlingResource() {
        Espresso.unregisterIdlingResources(
                shotIntentsTestRule.getActivity().getCountingIdlingResource());
    }
}
