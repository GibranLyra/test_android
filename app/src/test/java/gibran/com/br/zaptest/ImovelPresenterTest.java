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

import gibran.com.br.zapservice.ZapApiModule;
import gibran.com.br.zapservice.imovel.ImovelDataSource;
import gibran.com.br.zapservice.model.BaseZapListApiResponse;
import gibran.com.br.zapservice.model.Imovel;
import gibran.com.br.zaptest.helpers.schedulers.ImmediateSchedulerProvider;
import gibran.com.br.zaptest.imovel.ImovelContract;
import gibran.com.br.zaptest.imovel.ImovelPresenter;
import io.reactivex.Observable;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Created by gibranlyra on 12/09/17.
 */

public class ImovelPresenterTest {

    @Mock
    private ImovelDataSource imovelDataSource;

    @Mock
    private ImovelContract.ContractView contractView;

    private ImovelContract.Presenter contractPresenter;
    private BaseZapListApiResponse<ArrayList<Imovel>> IMOVEIS;
    private ImmediateSchedulerProvider schedulerProvider;

    @Before
    public void setupImovelPresenter() {
        // Mockito has a very convenient way to inject mocks by using the @Mock annotation. To
        // inject the mocks in the test the initMocks method needs to be called.
        MockitoAnnotations.initMocks(this);
        schedulerProvider = new ImmediateSchedulerProvider();
        // Get a reference to the class under test
        contractPresenter = new ImovelPresenter(imovelDataSource, contractView, schedulerProvider);

        // The presenter won't update the view unless it's active.
        when(contractView.isActive()).thenReturn(true);

        Gson gson = ZapApiModule.getDefaultGsonBuilder();
        InputStream imovelsRaw = getClass().getClassLoader().getResourceAsStream("imoveisResponse.json");
        Reader imovelsResponseJson = new BufferedReader(new InputStreamReader(imovelsRaw));
        IMOVEIS = gson.fromJson(imovelsResponseJson, getImovelsType());
    }

    @Test
    public void loadImoveisFromServiceAndLoadIntoView() throws Exception {
        when(imovelDataSource.getImoveis()).thenReturn(Observable.just(IMOVEIS.getData()));
        contractPresenter.loadImoveis();
        verify(contractView).setPresenter(contractPresenter);
        verify(contractView, never()).showImovelError();
    }

    @Test
    public void loadImoveisException() throws Exception {
        when(imovelDataSource.getImoveis()).thenReturn(Observable.error(new Exception()));
        contractPresenter.loadImoveis();
        verify(contractView).setPresenter(contractPresenter);
        verify(contractView).showImovelError();
        verify(contractView, never()).showImoveis(any());
    }

    @Test
    public void clickOnImovel_ShowDetailUi() {
        Imovel imovel = IMOVEIS.getData().get(0);
        contractPresenter.openImovelDetails(imovel, null);
        // Then imovel detail UI is shown
        verify(contractView).showImovelDetailsUi(any(Imovel.class), eq(null));
    }

    public Type getImovelsType() {
        return new TypeToken<BaseZapListApiResponse<ArrayList<Imovel>>>() {}.getType();
    }
}
