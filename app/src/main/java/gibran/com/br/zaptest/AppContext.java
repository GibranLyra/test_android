package gibran.com.br.zaptest;

import android.app.Application;
import android.support.multidex.MultiDex;

import com.bumptech.glide.request.RequestOptions;
import com.crashlytics.android.Crashlytics;
import com.jakewharton.threetenabp.AndroidThreeTen;

import gibran.com.br.zapservice.ZapApiModule;
import gibran.com.br.zapservice.LoggingInterceptor;
import io.fabric.sdk.android.Fabric;
import timber.log.Timber;

/**
 * Created by gibranlyra on 24/08/17.
 */

public class AppContext extends Application {

    private static AppContext instance;
    private RequestOptions requestOptions;

    public static AppContext getInstance() {
        return instance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        MultiDex.install(this);
        initializeTimezone();
        initializeTimber();
        initializeApiModules();
        initializeGlideRequestOptions();
        Fabric.with(this, new Crashlytics());
    }

    private void initializeTimezone() {
        AndroidThreeTen.init(this);
    }

    private void initializeTimber() {
        if (BuildConfig.DEBUG) {
            Timber.plant(new Timber.DebugTree());
        }
    }

    private void initializeApiModules() {
        ZapApiModule.setRetrofit(LoggingInterceptor.Level.HEADERS);
    }


    private void initializeGlideRequestOptions() {
        requestOptions = new RequestOptions();
        requestOptions.placeholder(R.drawable.placeholder);
        requestOptions.error(R.drawable.placeholder);
    }

    public RequestOptions getGlideRequestOptions() {
        return requestOptions;
    }
}
