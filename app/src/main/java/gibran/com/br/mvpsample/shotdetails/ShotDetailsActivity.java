package gibran.com.br.mvpsample.shotdetails;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import butterknife.BindView;
import butterknife.ButterKnife;
import gibran.com.br.dribbleservice.model.Shot;
import gibran.com.br.dribbleservice.shots.ShotsApi;
import gibran.com.br.mvpsample.R;
import gibran.com.br.mvpsample.helpers.ActivityHelper;
import gibran.com.br.mvpsample.helpers.schedulers.SchedulerProvider;

/**
 * Created by gibranlyra on 25/08/17.
 */

public class ShotDetailsActivity extends AppCompatActivity {

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    private static final String EXTRA_SHOT = "ShotId";

    private ShotDetailsContract.Presenter presenter;

    public static Intent createIntent(Context context, Shot shot) {
        Intent intent = new Intent(context, ShotDetailsActivity.class);
        intent.putExtra(EXTRA_SHOT, shot);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shot_details);
        ButterKnife.bind(this);

        Bundle data = getIntent().getExtras();
        Shot shot = data.getParcelable(EXTRA_SHOT);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setTitle(shot.getTitle());
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        setupViews(shot);
    }

    private void setupViews(Shot shot) {
        ShotDetailsFragment fragment =
                (ShotDetailsFragment) getSupportFragmentManager().findFragmentById(R.id.view_container);
        if (fragment == null) {
            fragment = ShotDetailsFragment.newInstance(shot);
            ActivityHelper.addFragmentToActivity(getSupportFragmentManager(), fragment, R.id.view_container);
        }
        presenter = new ShotDetailsPresenter(ShotsApi.getInstance(), fragment, SchedulerProvider.getInstance());
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

}
