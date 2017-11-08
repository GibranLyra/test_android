package gibran.com.br.mvpsample;

import com.google.gson.Gson;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;

import gibran.com.br.dribbleservice.DribbleApiModule;
import gibran.com.br.dribbleservice.model.Shot;
import gibran.com.br.dribbleservice.shots.ShotsDataSource;
import gibran.com.br.mvpsample.helpers.schedulers.ImmediateSchedulerProvider;
import gibran.com.br.mvpsample.shotdetails.ShotDetailsContract;
import gibran.com.br.mvpsample.shotdetails.ShotDetailsPresenter;
import io.reactivex.Observable;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Created by gibranlyra on 12/09/17.
 */
public class ShotDetailsPresenterTest {

    @Mock
    private ShotsDataSource shotsDataSource;

    @Mock
    ShotDetailsContract.ContractView contractView;
    private ShotDetailsContract.Presenter contractPresenter;

    private ImmediateSchedulerProvider schedulerProvider;
    private Shot SHOT;

    @Before
    public void setupShotDetailsPresenter() {
        // Mockito has a very convenient way to inject mocks by using the @Mock annotation. To
        // inject the mocks in the test the initMocks method needs to be called.
        MockitoAnnotations.initMocks(this);
        schedulerProvider = new ImmediateSchedulerProvider();
        // Get a reference to the class under test
        contractPresenter = new ShotDetailsPresenter(shotsDataSource, contractView, schedulerProvider);

        // The presenter won't update the view unless it's active.
        when(contractView.isActive()).thenReturn(true);
        Gson gson = DribbleApiModule.getDefaultGsonBuilder();
        InputStream shotRaw = getClass().getClassLoader().getResourceAsStream("shotsDetailsResponse.json");
        Reader shotResponseJson = new BufferedReader(new InputStreamReader(shotRaw));
        SHOT = gson.fromJson(shotResponseJson, Shot.class);
    }

    @Test
    public void loadShotFromServiceAndLoadIntoView() throws Exception {
        // When open shot details is requested
        when(shotsDataSource.getShot(SHOT.getId())).thenReturn(Observable.just(SHOT));
        contractPresenter.loadShot(SHOT.getId());
        verify(contractView).setPresenter(contractPresenter);
        verify(contractView).showLoading(true);
        verify(contractView).showShot(eq(SHOT));
        verify(contractView).showLoading(false);
        verify(contractView, never()).showShotError();
    }

    @Test
    public void loadChannelException() throws Exception {
        when(shotsDataSource.getShot(any(Integer.class))).thenReturn(Observable.error(new Exception()));
        contractPresenter.loadShot(SHOT.getId());
        verify(contractView).setPresenter(contractPresenter);
        verify(contractView).showLoading(true);
        verify(contractView).showLoading(false);
        verify(contractView).showShotError();
        verify(contractView, never()).showShot(any(Shot.class));
    }
}