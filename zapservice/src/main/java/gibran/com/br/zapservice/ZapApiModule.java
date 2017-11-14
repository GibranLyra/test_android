package gibran.com.br.zapservice;


import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.threeten.bp.Clock;

import io.reactivex.annotations.Nullable;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by gibran.lyra on 23/08/2017.
 */
public class ZapApiModule {

    private static final String BASE_URL = "http://demo4573903.mockable.io/";
    private static Retrofit retrofit;

    public static void setRetrofit(@Nullable LoggingInterceptor.Level logLevel) {
        if (logLevel == null) {
            logLevel = LoggingInterceptor.Level.BASIC;
        }
        LoggingInterceptor interceptor = new LoggingInterceptor(Clock.systemDefaultZone());
        interceptor.setLogLevel(logLevel);
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        builder.addInterceptor(interceptor);
        builder.addInterceptor(chain -> chain.proceed(chain.request()));
        OkHttpClient okClient = builder.build();
        retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(okClient)
                .addConverterFactory(GsonConverterFactory.create(getDefaultGsonBuilder()))
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
    }

    public static Gson getDefaultGsonBuilder() {
        return new GsonBuilder()
                .setFieldNamingPolicy(FieldNamingPolicy.UPPER_CAMEL_CASE)
                .create();
    }

    public static Retrofit getRetrofit() {
        return retrofit;
    }
}
