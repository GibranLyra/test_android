package gibran.com.br.mvpsample;

import android.app.Application;
import android.support.multidex.MultiDex;

import com.jakewharton.threetenabp.AndroidThreeTen;

import gibran.com.br.dribbleservice.DribbleApiModule;
import gibran.com.br.dribbleservice.LoggingInterceptor;
import timber.log.Timber;

/**
 * Created by gibranlyra on 24/08/17.
 */

public class AppContext extends Application {

    private static AppContext instance;

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
        DribbleApiModule.setRetrofit(LoggingInterceptor.Level.HEADERS);
    }
}
