package gibran.com.br.mvpsample.main;

import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import gibran.com.br.dribbleservice.shots.ShotsApi;
import gibran.com.br.mvpsample.R;
import gibran.com.br.mvpsample.helpers.ObserverHelper;
import timber.log.Timber;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.rootLayout)
    CoordinatorLayout coordinatorLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        ShotsApi.getInstance().getShots()
                .compose(ObserverHelper.getInstance().applySchedulers())
                .subscribe(shots -> {
                    Timber.d("onCreate: ");
                }, e -> {
                    Snackbar.make(coordinatorLayout, "Ocorreu um erro", Snackbar.LENGTH_LONG);
                });
    }
}
