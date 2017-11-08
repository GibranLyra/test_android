package gibran.com.br.dribbleservice;


import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.threeten.bp.Clock;

import io.reactivex.annotations.Nullable;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by gibran.lyra on 23/08/2017.
 */
public class DribbleApiModule {

    private static final String BASE_URL = "https://api.dribbble.com/v1/";
    private static final String API_KEY = "6b33e0f447946266f3a936b31ab32531e314c833ce758250a0058d8c086c4f23";
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
        builder.addInterceptor(chain -> chain.proceed(chain.request()));
        builder.addInterceptor(chain -> {
            Request request = chain.request().newBuilder()
                    .addHeader("Authorization", String.format("Bearer %s", API_KEY)).build();
            return chain.proceed(request);
        });
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
