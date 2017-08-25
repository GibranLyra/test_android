package gibran.com.br.mvpsample.shot;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.v7.app.AppCompatActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import gibran.com.br.dribbleservice.shots.ShotsApi;
import gibran.com.br.mvpsample.R;
import gibran.com.br.mvpsample.helpers.ActivityHelper;

public class ShotActivity extends AppCompatActivity {

    @BindView(R.id.rootLayout)
    CoordinatorLayout coordinatorLayout;

    private ShotContract.Presenter presenter;

    public static Intent createIntent(Context context) {
        Intent intent = new Intent(context, ShotActivity.class);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shot_details);
        ButterKnife.bind(this);
        setupViews();
    }

    private void setupViews() {
        ShotFragment shotFragment =
                (ShotFragment) getSupportFragmentManager().findFragmentById(R.id.view_container);
        if (shotFragment == null) {
            shotFragment = ShotFragment.newInstance();
            ActivityHelper.addFragmentToActivity(getSupportFragmentManager(), shotFragment, R.id.view_container);
        }
        presenter = new ShotPresenter(ShotsApi.getInstance(), shotFragment);
    }
}
