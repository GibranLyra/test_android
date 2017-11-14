package gibran.com.br.zaptest.imoveldetails;

import gibran.com.br.zapservice.imovel.ImovelDataSource;
import gibran.com.br.zapservice.model.Cliente;
import gibran.com.br.zaptest.helpers.ObserverHelper;
import gibran.com.br.zaptest.helpers.schedulers.BaseSchedulerProvider;
import io.reactivex.disposables.Disposable;
import timber.log.Timber;

/**
 * Created by gibranlyra on 25/08/17.
 */

public class ImovelDetailsPresenter implements ImovelDetailsContract.Presenter {

    private ImovelDataSource imovelsRepository;
    private ImovelDetailsContract.ContractView view;
    private BaseSchedulerProvider schedulerProvider;
    private Disposable getImovelDisposable;

    public ImovelDetailsPresenter(ImovelDataSource imovelDatasource, ImovelDetailsContract.ContractView view,
                                  BaseSchedulerProvider schedulerProvider) {
        this.imovelsRepository = imovelDatasource;
        this.view = view;
        this.schedulerProvider = schedulerProvider;
        this.view.setPresenter(this);
    }

    @Override
    public void loadImovel(int imovelId) {
        view.showLoading(true);
        Cliente cliente = new Cliente();
        cliente.setCodCliente(1);
        cliente.setEmail("teste@teste.com");
        cliente.setTelefone("9 7777-7777");
        cliente.setNomeFantasia("Gibran");
        imovelsRepository.postMessage(cliente)
                .subscribeOn(schedulerProvider.io())
                .observeOn(schedulerProvider.ui())
                .subscribe(o -> {
                    Timber.d("loadImovel: ");
                }, e -> {
                    Timber.e(e, "loadImovel: %s", e.getMessage());
                });
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
