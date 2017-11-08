package gibran.com.br.mvpsample.shotdetails;

import gibran.com.br.dribbleservice.shots.ShotsDataSource;
import gibran.com.br.mvpsample.helpers.ObserverHelper;
import gibran.com.br.mvpsample.helpers.schedulers.BaseSchedulerProvider;
import io.reactivex.disposables.Disposable;
import timber.log.Timber;

/**
 * Created by gibranlyra on 25/08/17.
 */

public class ShotDetailsPresenter implements ShotDetailsContract.Presenter {

    private ShotsDataSource shotsRepository;
    private ShotDetailsContract.ContractView view;
    private BaseSchedulerProvider schedulerProvider;
    private Disposable getShotDisposable;

    public ShotDetailsPresenter(ShotsDataSource shotsRepository, ShotDetailsContract.ContractView view,
                                BaseSchedulerProvider schedulerProvider) {
        this.shotsRepository = shotsRepository;
        this.view = view;
        this.schedulerProvider = schedulerProvider;
        this.view.setPresenter(this);
    }

    @Override
    public void loadShot(int id) {
        view.showLoading(true);
        getShotDisposable = shotsRepository.getShot(id)
                .subscribeOn(schedulerProvider.io())
                .observeOn(schedulerProvider.ui())
                .subscribe(shot -> {
                    view.showShot(shot);
                    view.showLoading(false);
                }, e -> {
                    Timber.e(e, "loadShot: %s", e.getMessage());
                    view.showShotError();
                    view.showLoading(false);
                });
    }

    @Override
    public void subscribe() {

    }

    @Override
    public void unsubscribe() {
        ObserverHelper.safelyDispose(getShotDisposable);
    }
}
