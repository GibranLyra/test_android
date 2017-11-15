package gibran.com.br.zaptest.imoveldetails.bottomfragment;

import gibran.com.br.zapservice.imovel.ImovelDataSource;
import gibran.com.br.zaptest.helpers.ObserverHelper;
import gibran.com.br.zaptest.helpers.schedulers.BaseSchedulerProvider;
import io.reactivex.disposables.Disposable;

/**
 * Created by gibranlyra on 25/08/17.
 */

public class ImovelDetailsBottomPresenter implements ImovelDetailsBottomContract.Presenter {

    private ImovelDataSource imovelsRepository;
    private ImovelDetailsBottomContract.ContractView view;
    private BaseSchedulerProvider schedulerProvider;
    private Disposable getImovelDisposable;

    public ImovelDetailsBottomPresenter(ImovelDataSource imovelDatasource, ImovelDetailsBottomContract.ContractView view,
                                        BaseSchedulerProvider schedulerProvider) {
        this.imovelsRepository = imovelDatasource;
        this.view = view;
        this.schedulerProvider = schedulerProvider;
        this.view.setPresenter(this);
    }

    @Override
    public void loadImovel(int imovelId) {
        view.showLoading(true);
        getImovelDisposable = imovelsRepository.getImovel(imovelId)
                .subscribeOn(schedulerProvider.io())
                .observeOn(schedulerProvider.ui())
                .subscribe(imovel -> {
                    view.showImovel(imovel);
                    view.showLoading(false);
                }, e -> {
                    view.showImovelError();
                    view.showLoading(false);
                });
    }

    @Override
    public void subscribe() {

    }

    @Override
    public void unsubscribe() {
        ObserverHelper.safelyDispose(getImovelDisposable);
    }
}
