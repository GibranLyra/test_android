package gibran.com.br.zaptest.imoveldetails;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import butterknife.BindView;
import butterknife.ButterKnife;
import gibran.com.br.zapservice.imovel.ImovelApi;
import gibran.com.br.zapservice.model.Imovel;
import gibran.com.br.zaptest.R;
import gibran.com.br.zaptest.helpers.ActivityHelper;
import gibran.com.br.zaptest.helpers.schedulers.SchedulerProvider;

/**
 * Created by gibranlyra on 25/08/17.
 */

public class ImovelDetailsActivity extends AppCompatActivity {

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    private static final String EXTRA_IMOVEL = "Imovel";

    private ImovelDetailsContract.Presenter presenter;

    public static Intent createIntent(Context context, Imovel imovel) {
        Intent intent = new Intent(context, ImovelDetailsActivity.class);
        intent.putExtra(EXTRA_IMOVEL, imovel);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_imovel_details);
        ButterKnife.bind(this);

        Bundle data = getIntent().getExtras();
        Imovel imovel = data.getParcelable(EXTRA_IMOVEL);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setTitle(imovel.getTipoImovel());
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        setupViews(imovel);
    }

    private void setupViews(Imovel imovel) {
        ImovelDetailsFragment fragment =
                (ImovelDetailsFragment) getSupportFragmentManager().findFragmentById(R.id.view_container);
        if (fragment == null) {
            fragment = ImovelDetailsFragment.newInstance(imovel.getCodImovel());
            ActivityHelper.addFragmentToActivity(getSupportFragmentManager(), fragment, R.id.view_container);
        }
        presenter = new ImovelDetailsPresenter(ImovelApi.getInstance(), fragment, SchedulerProvider.getInstance());
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

}
