package gibran.com.br.zaptest;


import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.lang.reflect.Type;
import java.util.ArrayList;

import gibran.com.br.dribbleservice.DribbleApiModule;
import gibran.com.br.dribbleservice.model.Shot;
import gibran.com.br.dribbleservice.shots.ShotsDataSource;
import gibran.com.br.zaptest.helpers.schedulers.ImmediateSchedulerProvider;
import gibran.com.br.zaptest.shot.ShotContract;
import gibran.com.br.zaptest.shot.ShotPresenter;
import io.reactivex.Observable;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Created by gibranlyra on 12/09/17.
 */

public class ShotPresenterTest {

    @Mock
    private ShotsDataSource shotsDataSource;

    @Mock
    private ShotContract.ContractView contractView;

    private ShotContract.Presenter contractPresenter;
    private ArrayList<Shot> SHOTS;
    private ImmediateSchedulerProvider schedulerProvider;

    @Before
    public void setupShotPresenter() {
        // Mockito has a very convenient way to inject mocks by using the @Mock annotation. To
        // inject the mocks in the test the initMocks method needs to be called.
        MockitoAnnotations.initMocks(this);
        schedulerProvider = new ImmediateSchedulerProvider();
        // Get a reference to the class under test
        contractPresenter = new ShotPresenter(shotsDataSource, contractView, schedulerProvider);

        // The presenter won't update the view unless it's active.
        when(contractView.isActive()).thenReturn(true);

        Gson gson = DribbleApiModule.getDefaultGsonBuilder();
        InputStream shotsRaw = getClass().getClassLoader().getResourceAsStream("shotsResponse.json");
        Reader shotsResponseJson = new BufferedReader(new InputStreamReader(shotsRaw));
        SHOTS = gson.fromJson(shotsResponseJson, getShotsType());
    }

    @Test
    public void loadShotPageFromServiceAndLoadIntoView() throws Exception {
        when(shotsDataSource.getShots(0)).thenReturn(Observable.just(SHOTS));
        contractPresenter.loadPage(0);
        verify(contractView).setPresenter(contractPresenter);
        verify(contractView, never()).addMoreShotsError();
    }

    @Test
    public void loadShotPageException() throws Exception {
        when(shotsDataSource.getShots(0)).thenReturn(Observable.error(new Exception()));
        contractPresenter.loadPage(0);
        verify(contractView).setPresenter(contractPresenter);
        verify(contractView).addMoreShotsError();
        verify(contractView, never()).setPresenter(contractPresenter);
    }

    @Test
    public void loadShotException() throws Exception {
        when(shotsDataSource.getShot(any(Integer.class))).thenReturn(Observable.error(new Exception()));
        contractPresenter.loadShots();
        verify(contractView).setPresenter(contractPresenter);
        verify(contractView).showLoading(true);
        verify(contractView).showLoading(false);
        verify(contractView).showShotsError();
        verify(contractView, never()).showShots(any(ArrayList.class));
    }

    @Test
    public void clickOnShot_ShowDetailUi() {
        Shot shot = SHOTS.get(0);
        contractPresenter.openShotDetails(shot, null);
        // Then shot detail UI is shown
        verify(contractView).showShotDetailsUi(any(Shot.class), eq(null));
    }

    public Type getShotsType() {
        return new TypeToken<ArrayList<Shot>>() {}.getType();
    }
}
