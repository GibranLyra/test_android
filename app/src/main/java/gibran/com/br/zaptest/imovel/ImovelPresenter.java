package gibran.com.br.zaptest.imovel;

import android.support.annotation.Nullable;
import android.view.View;

import gibran.com.br.zapservice.imovel.ImovelDataSource;
import gibran.com.br.zapservice.model.Imovel;
import gibran.com.br.zaptest.helpers.EspressoIdlingResource;
import gibran.com.br.zaptest.helpers.ObserverHelper;
import gibran.com.br.zaptest.helpers.schedulers.BaseSchedulerProvider;
import io.reactivex.disposables.Disposable;
import timber.log.Timber;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Created by gibranlyra on 24/08/17.
 */

public class ImovelPresenter implements ImovelContract.Presenter {

    private ImovelDataSource imovelRepository;
    private ImovelContract.ContractView view;
    private BaseSchedulerProvider schedulerProvider;
    private Disposable getImovelsDisposable;


    public ImovelPresenter(ImovelDataSource imovelRepository,
                         ImovelContract.ContractView view,
                         BaseSchedulerProvider schedulerProvider) {
        this.imovelRepository = imovelRepository;
        this.view = view;
        this.schedulerProvider = schedulerProvider;
        this.view.setPresenter(this);
    }

    @Override
    public void subscribe() {
        //UNUSED
    }

    @Override
    public void unsubscribe() {
        ObserverHelper.safelyDispose(getImovelsDisposable);
    }


    @Override
    public void loadImoveis() {
        view.showLoading(true);
        // The network request might be handled in a different thread so make sure Espresso knows
        // that the app is busy until the response is handled.
        EspressoIdlingResource.increment(); // App is busy until further notice
        getImovelsDisposable = imovelRepository.getImoveis()
                .subscribeOn(schedulerProvider.io())
                .observeOn(schedulerProvider.ui())
                .doOnTerminate(() -> {
                    if (!EspressoIdlingResource.getIdlingResource().isIdleNow()) {
                        EspressoIdlingResource.decrement(); // Set app as idle.
                    }
                })
                .subscribe(imoveis -> {
                            view.showLoading(false);
                            view.showImoveis(imoveis);
                        },
                        e -> {
                            Timber.e(e, "loadImoveis: %s", e.getMessage());
                            view.showLoading(false);
                            view.showImovelError();
                        });

    }

    @Override
    public void openImovelDetails(Imovel imovel, @Nullable View v) {
        checkNotNull(imovel, "Imovel cannot be null!");
        view.showImovelDetailsUi(imovel, v);
    }
}
