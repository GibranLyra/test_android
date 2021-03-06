package gibran.com.br.zaptest;

import com.google.gson.Gson;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;

import gibran.com.br.zapservice.ZapApiModule;
import gibran.com.br.zapservice.imovel.ImovelDataSource;
import gibran.com.br.zapservice.model.Imovel;
import gibran.com.br.zaptest.helpers.schedulers.ImmediateSchedulerProvider;
import gibran.com.br.zaptest.imoveldetails.bottomfragment.ImovelDetailsBottomContract;
import gibran.com.br.zaptest.imoveldetails.bottomfragment.ImovelDetailsBottomPresenter;
import io.reactivex.Observable;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Created by gibranlyra on 12/09/17.
 */
public class ImovelDetailsBottomPresenterTest {

    @Mock
    private ImovelDataSource imovelDataSource;

    @Mock
    ImovelDetailsBottomContract.ContractView contractView;
    private ImovelDetailsBottomContract.Presenter contractPresenter;

    private ImmediateSchedulerProvider schedulerProvider;
    private Imovel IMOVEL;

    @Before
    public void setupImovelDetailsPresenter() {
        // Mockito has a very convenient way to inject mocks by using the @Mock annotation. To
        // inject the mocks in the test the initMocks method needs to be called.
        MockitoAnnotations.initMocks(this);
        schedulerProvider = new ImmediateSchedulerProvider();
        // Get a reference to the class under test
        contractPresenter = new ImovelDetailsBottomPresenter(imovelDataSource, contractView, schedulerProvider);

        // The presenter won't update the view unless it's active.
        when(contractView.isActive()).thenReturn(true);
        Gson gson = ZapApiModule.getDefaultGsonBuilder();
        InputStream imovelRaw = getClass().getClassLoader().getResourceAsStream("imovelDetailsResponse.json");
        Reader imovelResponseJson = new BufferedReader(new InputStreamReader(imovelRaw));
        IMOVEL = gson.fromJson(imovelResponseJson, Imovel.class);
    }

    @Test
    public void loadImovelFromServiceAndLoadIntoView() throws Exception {
         //When open imovel details is requested
        when(imovelDataSource.getImovel(IMOVEL.getCodImovel())).thenReturn(Observable.just(IMOVEL));
        contractPresenter.loadImovel(IMOVEL.getCodImovel());
        verify(contractView).setPresenter(contractPresenter);
        verify(contractView).showLoading(true);
        verify(contractView).showImovel(eq(IMOVEL));
        verify(contractView).showLoading(false);
        verify(contractView, never()).showImovelError();
    }

    @Test
    public void loadChannelException() throws Exception {
        when(imovelDataSource.getImovel(any(Integer.class))).thenReturn(Observable.error(new Exception()));
        contractPresenter.loadImovel(IMOVEL.getCodImovel());
        verify(contractView).setPresenter(contractPresenter);
        verify(contractView).showLoading(true);
        verify(contractView).showLoading(false);
        verify(contractView).showImovelError();
        verify(contractView, never()).showImovel(any(Imovel.class));
    }
}