package gibran.com.br.mvpsample.helpers;


import java.io.EOFException;
import java.net.SocketTimeoutException;
import java.util.concurrent.TimeUnit;

import br.com.net.nowkids.service.Constants;
import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.ObservableTransformer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import timber.log.Timber;

/**
 * Created by gibran.lyra on 22/02/2016.
 */
public class ObserverHelper {

    private static ObserverHelper instance;

    public static ObserverHelper getInstance() {
        if (instance == null) {
            instance = new ObserverHelper();
        }
        return instance;
    }
    public <T> ObservableTransformer<T, T> retryOnEOFException() {
        return observable -> observable.retryWhen(new RetryWithDelay(7, 100, EOFException.class));
    }

    public <T> ObservableTransformer<T, T> applySchedulers() {
        return observable -> observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public <T> ObservableTransformer<T, T> applyTimeOutRetry() {
        return observable -> observable.retry((attempts, error) -> error
                instanceof SocketTimeoutException && attempts < Constants.MAX_RETRY_ATTEMPS);
    }

    public void safelyDispose(Disposable... disposables) {
        for (Disposable subscription : disposables) {
            if (subscription != null && !subscription.isDisposed()) {
                subscription.dispose();
            }
        }
    }

    class RetryWithDelay implements
            Function<Observable<? extends Throwable>, Observable<?>> {

        private final int maxRetries;
        private final int retryDelayMillis;
        private Class exceptionType;
        private int retryCount;

        public RetryWithDelay(final int maxRetries, final int retryDelayMillis) {
            this(maxRetries, retryDelayMillis, null);
        }


        public RetryWithDelay(final int maxRetries, final int retryDelayMillis, Class exceptionType) {
            this.maxRetries = maxRetries;
            this.retryDelayMillis = retryDelayMillis;
            this.exceptionType = exceptionType;
            this.retryCount = 0;
        }

        @Override
        public Observable<?> apply(@NonNull Observable<? extends Throwable> attempts) throws Exception {
            return attempts
                    .flatMap((Function<Throwable, ObservableSource<?>>) throwable -> {
                        if (++retryCount < maxRetries && (exceptionType == null ||
                                throwable.getClass().isAssignableFrom(exceptionType))) {
                            long retryDelay = (long) (retryDelayMillis * Math.pow(retryCount, 2));
                            Timber.w("%s - Retrying %s with delay of %s", throwable, retryCount, retryDelay);
                            return Observable.timer(retryDelay, TimeUnit.MILLISECONDS);
                        }
                        // Max retries hit or it's not a exception type been handled.
                        // Just passing the error along.
                        return Observable.error(throwable);
                    });
        }
    }
}
