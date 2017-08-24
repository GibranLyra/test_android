package gibran.com.br.dribbleservice.shots;

import java.util.ArrayList;

import gibran.com.br.dribbleservice.model.Shot;
import io.reactivex.Observable;
import retrofit2.http.GET;

/**
 * Created by gibranlyra on 23/08/17.
 */

interface ShotsService {
    @GET("shots")
    Observable<ArrayList<Shot>> getShots();
}
