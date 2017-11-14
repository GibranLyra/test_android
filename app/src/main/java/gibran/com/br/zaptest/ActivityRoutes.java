package gibran.com.br.zaptest;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityOptionsCompat;
import android.view.View;

import gibran.com.br.zapservice.model.Imovel;
import gibran.com.br.zaptest.imoveldetails.ImovelDetailsActivity;

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

    public void openImovelDetailsActivity(Context context, Imovel imovel, @Nullable View transitionView) {
        Intent intent = ImovelDetailsActivity.createIntent(context, imovel);
        if (transitionView != null) {
            ActivityOptionsCompat options = ActivityOptionsCompat.
                    makeSceneTransitionAnimation((Activity) context, transitionView, "imovel_image_transition");
            context.startActivity(intent, options.toBundle());
        } else {
            context.startActivity(intent);
        }
    }
}
