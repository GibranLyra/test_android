package gibran.com.br.dribbleservice.shots;

import java.util.ArrayList;

import gibran.com.br.dribbleservice.model.Shot;
import io.reactivex.Observable;

/**
 * Created by gibranlyra on 30/07/17.
 */

public interface ShotsDataSource {
    Observable<ArrayList<Shot>> getShots();
    Observable<Shot> getShot(String id);
}
