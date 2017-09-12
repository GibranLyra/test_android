package gibran.com.br.mvpsample.shot;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;

import gibran.com.br.dribbleservice.model.Shot;
import gibran.com.br.dribbleservice.shots.ShotsDataSource;
import gibran.com.br.mvpsample.helpers.EspressoIdlingResource;
import gibran.com.br.mvpsample.helpers.ObserverHelper;
import io.reactivex.disposables.Disposable;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Created by gibranlyra on 24/08/17.
 */

public class ShotPresenter implements ShotContract.Presenter {

    private ShotsDataSource shotRepository;
    private ShotContract.View view;
    private Disposable getShotsDisposable;


    public ShotPresenter(@NonNull ShotsDataSource shotRepository,
                         @NonNull ShotContract.View view) {
        this.shotRepository = shotRepository;
        this.view = view;
        this.view.setPresenter(this);
    }

    @Override
    public void subscribe() {
        //UNUSED
    }

    @Override
    public void unsubscribe() {
        ObserverHelper.getInstance().safelyDispose(getShotsDisposable);
    }


    @Override
    public void loadShots() {
        view.showLoading(true);
        // The network request might be handled in a different thread so make sure Espresso knows
        // that the app is busy until the response is handled.
        EspressoIdlingResource.increment(); // App is busy until further notice
        getShotsDisposable = shotRepository.getShots()
                .compose(ObserverHelper.getInstance().applySchedulers())
                .doOnTerminate(() -> {
                    if (!EspressoIdlingResource.getIdlingResource().isIdleNow()) {
                        EspressoIdlingResource.decrement(); // Set app as idle.
                    }
                })
                .subscribe(
                        shots -> {
                            view.showLoading(false);
                            view.showShots(shots);
                        },
                        __ -> {
                            view.showLoading(false);
                            view.showShotsError();
                        });

    }

    @Override
    public void openShotDetails(Shot shot, @Nullable View v) {
        checkNotNull(shot, "Shot cannot be null!");
        view.showShotDetailsUi(shot, v);
    }
}
