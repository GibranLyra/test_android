package gibran.com.br.mvpsample;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityOptionsCompat;
import android.view.View;

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

    public void openShotDetailsActivity(Context context, Shot shot, @Nullable View transitionView) {
        Intent intent = ShotDetailsActivity.createIntent(context, shot);
        if (transitionView != null) {
            ActivityOptionsCompat options = ActivityOptionsCompat.
                    makeSceneTransitionAnimation((Activity) context, transitionView, "shot_image_transition");
            context.startActivity(intent, options.toBundle());
        } else {
            context.startActivity(intent);
        }
    }

}
