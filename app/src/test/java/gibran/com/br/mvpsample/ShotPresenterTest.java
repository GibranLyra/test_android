package gibran.com.br.mvpsample;


import com.google.common.collect.Lists;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;

import gibran.com.br.dribbleservice.model.Shot;
import gibran.com.br.dribbleservice.shots.ShotsDataSource;
import gibran.com.br.mvpsample.shot.ShotContract;
import gibran.com.br.mvpsample.shot.ShotPresenter;
import io.reactivex.Observable;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Created by gibranlyra on 12/09/17.
 */

public class ShotPresenterTest {

    @Mock
    private ShotsDataSource shotsService;

    @Mock
    private ShotContract.View shotsView;

    private ShotPresenter shotPresenter;
    private ArrayList<Shot> SHOTS;

    @Before
    public void setupShotPresenter() {
        // Mockito has a very convenient way to inject mocks by using the @Mock annotation. To
        // inject the mocks in the test the initMocks method needs to be called.
        MockitoAnnotations.initMocks(this);

        // Get a reference to the class under test
        shotPresenter = new ShotPresenter(shotsService, shotsView);

        // The presenter won't update the view unless it's active.
        when(shotsView.isActive()).thenReturn(true);
        // We subscribe the tasks to 3, with one active and two completed
        SHOTS = Lists.newArrayList(new Shot(1, "Title1"), new Shot(2, "Title2"),
                new Shot(3, "Title3"), new Shot(4, "Title4"), new Shot(5, "Title5"));
    }

    @Test
    public void loadAllShotsFromServiceAndLoadIntoView() {
        // Given an initialized ShotsPresenter with initialized tasks
        when(shotsService.getShots()).thenReturn(Observable.just(SHOTS));
        // When loading of Tasks is requested
        shotPresenter.loadShots();

        // Then progress indicator is shown
        verify(shotsView).showLoading(true);
        // Then progress indicator is hidden and all tasks are shown in UI
        verify(shotsView).showLoading(false);
        verify(shotsView).showShots(SHOTS);
    }

    @Test
    public void clickOnShot_ShowDetailUi() {
        // Given a stubbed active task
        Shot shot = new Shot(3800291, "Title 1");
        // When open task details is requested
        shotPresenter.openShotDetails(shot, null);

        // Then shot detail UI is shown
        verify(shotsView).showShotDetailsUi(any(Shot.class), eq(null));
    }
}
