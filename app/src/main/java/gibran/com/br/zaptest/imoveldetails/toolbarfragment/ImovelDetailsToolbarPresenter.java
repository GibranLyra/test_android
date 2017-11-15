package gibran.com.br.zaptest.imoveldetails.toolbarfragment;

import gibran.com.br.zapservice.imovel.ImovelDataSource;
import gibran.com.br.zaptest.helpers.ObserverHelper;
import gibran.com.br.zaptest.helpers.schedulers.BaseSchedulerProvider;
import io.reactivex.disposables.Disposable;

/**
 * Created by gibranlyra on 25/08/17.
 */

public class ImovelDetailsToolbarPresenter implements ImovelDetailsToolbarContract.Presenter {

    private ImovelDataSource imovelsRepository;
    private ImovelDetailsToolbarContract.ContractView view;
    private BaseSchedulerProvider schedulerProvider;
    private Disposable getImovelDisposable;

    public ImovelDetailsToolbarPresenter(ImovelDataSource imovelDatasource, ImovelDetailsToolbarContract.ContractView view,
                                         BaseSchedulerProvider schedulerProvider) {
        this.imovelsRepository = imovelDatasource;
        this.view = view;
        this.schedulerProvider = schedulerProvider;
        this.view.setPresenter(this);
    }

    @Override
    public void subscribe() {

    }

    @Override
    public void unsubscribe() {
        ObserverHelper.safelyDispose(getImovelDisposable);
    }
}
