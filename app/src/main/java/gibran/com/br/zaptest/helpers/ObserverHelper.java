package gibran.com.br.zaptest.helpers;


import io.reactivex.disposables.Disposable;

/**
 * Created by gibran.lyra on 22/02/2016.
 */
public class ObserverHelper {

    public static void safelyDispose(Disposable... disposables) {
        for (Disposable subscription : disposables) {
            if (subscription != null && !subscription.isDisposed()) {
                subscription.dispose();
            }
        }
    }
}
