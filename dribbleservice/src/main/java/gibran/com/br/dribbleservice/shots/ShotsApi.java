package gibran.com.br.dribbleservice.shots;

import java.util.ArrayList;

import gibran.com.br.dribbleservice.DribbleApiModule;
import gibran.com.br.dribbleservice.model.Shot;
import io.reactivex.Observable;
import retrofit2.Retrofit;
import timber.log.Timber;

/**
 * Created by gibran.lyra on 23/08/2017.
 */
public class ShotsApi implements ShotsDataSource {
    private static ShotsApi instance;
    private final ShotsService shotsService;

    private ShotsApi() {
        Retrofit retrofit = DribbleApiModule.getRetrofit();
        shotsService = retrofit.create(ShotsService.class);
    }

    public static ShotsApi getInstance() {
        if (instance == null) {
            instance = new ShotsApi();
        }
        return instance;
    }

    public static void renewInstance() {
        instance = new ShotsApi();
    }


    public Observable<ArrayList<Shot>> getShots() {
        /* For the Sample purposes we always get only the first page */
        return shotsService.getShots(1)
                .doOnError(e -> Timber.e(e, "getShots: %s", e.getMessage()));
    }

    public Observable<Shot> getShot(int id) {
        return shotsService.getShot(id)
                .doOnError(e -> Timber.e(e, "getShots: %s", e.getMessage()));
    }
}