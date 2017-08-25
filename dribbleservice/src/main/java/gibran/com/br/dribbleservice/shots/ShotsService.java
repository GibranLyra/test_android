package gibran.com.br.dribbleservice.shots;

import java.util.ArrayList;

import gibran.com.br.dribbleservice.model.Shot;
import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by gibranlyra on 23/08/17.
 */

interface ShotsService {
    @GET("shots")
    Observable<ArrayList<Shot>> getShots(@Query("page") int page);

    @GET("shots/{id}")
    Observable<Shot> getShot(@Path("id") int id);

}
