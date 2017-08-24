package gibran.com.br.mvpsample.helpers;


import io.reactivex.ObservableTransformer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by gibran.lyra on 24/08/2017.
 */
public class ObserverHelper {

    private static ObserverHelper instance;

    public static ObserverHelper getInstance() {
        if (instance == null) {
            instance = new ObserverHelper();
        }
        return instance;
    }

    public <T> ObservableTransformer<T, T> applySchedulers() {
        return observable -> observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public void safelyDispose(Disposable... disposables) {
        for (Disposable subscription : disposables) {
            if (subscription != null && !subscription.isDisposed()) {
                subscription.dispose();
            }
        }
    }
}
