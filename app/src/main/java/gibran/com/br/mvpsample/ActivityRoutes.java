package gibran.com.br.mvpsample;

import android.content.Context;
import android.content.Intent;

import gibran.com.br.dribbleservice.model.Shot;
import gibran.com.br.mvpsample.shotdetails.ShotDetailsActivity;

/**
 * Created by gibranlyra on 25/08/17.
 */

public class ActivityRoutes {

    private static ActivityRoutes instance;

    public static ActivityRoutes getInstance() {
        if (instance == null) {
            instance = new ActivityRoutes();
        }
        return instance;
    }

    public void openShotDetailsActivity(Context context, Shot shot) {
        Intent intent = ShotDetailsActivity.createIntent(context, shot);
        context.startActivity(intent);
    }

}
