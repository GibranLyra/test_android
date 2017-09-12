package gibran.com.br.mvpsample.shotdetails;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import gibran.com.br.dribbleservice.model.Shot;
import gibran.com.br.dribbleservice.shots.ShotsDataSource;
import io.reactivex.Observable;

import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Created by gibranlyra on 12/09/17.
 */
public class ShotDetailsPresenterTest {

    @Mock
    private ShotsDataSource shotsService;

    @Mock
    ShotDetailsContract.View shotDetailsView;

    private ShotDetailsPresenter shotDetailsPresenter;
    private Shot SHOT;

    @Before
    public void setupShotDetailsPresenter() {
        // Mockito has a very convenient way to inject mocks by using the @Mock annotation. To
        // inject the mocks in the test the initMocks method needs to be called.
        MockitoAnnotations.initMocks(this);

        // Get a reference to the class under test
        shotDetailsPresenter = new ShotDetailsPresenter(shotsService, shotDetailsView);

        // The presenter won't update the view unless it's active.
        when(shotDetailsView.isActive()).thenReturn(true);

        SHOT = new Shot(3800291, "Title 1");
    }

    @Test
    public void loadShotFromServiceAndLoadIntoView() throws Exception {
        // When open task details is requested
        when(shotsService.getShot(SHOT.getId())).thenReturn(Observable.just(SHOT));

        shotDetailsPresenter.loadShot(SHOT.getId());

        verify(shotDetailsView).showLoading(true);
        verify(shotDetailsView).showShot(eq(SHOT));
        verify(shotDetailsView).showLoading(false);
    }

}