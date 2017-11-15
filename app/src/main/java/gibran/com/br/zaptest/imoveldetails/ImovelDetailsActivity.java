package gibran.com.br.zaptest.imoveldetails;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import gibran.com.br.zapservice.imovel.ImovelApi;
import gibran.com.br.zapservice.model.Imovel;
import gibran.com.br.zaptest.R;
import gibran.com.br.zaptest.helpers.ActivityHelper;
import gibran.com.br.zaptest.helpers.schedulers.SchedulerProvider;
import gibran.com.br.zaptest.imoveldetails.bottomfragment.ImovelDetailsBottomContract;
import gibran.com.br.zaptest.imoveldetails.bottomfragment.ImovelDetailsBottomFragment;
import gibran.com.br.zaptest.imoveldetails.bottomfragment.ImovelDetailsBottomPresenter;
import gibran.com.br.zaptest.imoveldetails.toolbarfragment.ImovelDetailsToolbarContract;
import gibran.com.br.zaptest.imoveldetails.toolbarfragment.ImovelDetailsToolbarFragment;
import gibran.com.br.zaptest.imoveldetails.toolbarfragment.ImovelDetailsToolbarPresenter;

/**
 * Created by gibranlyra on 25/08/17.
 */

public class ImovelDetailsActivity extends AppCompatActivity {

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    private static final String EXTRA_IMOVEL = "Imovel";

    private ImovelDetailsBottomContract.Presenter bottomFragmentPresenter;
    private ImovelDetailsToolbarContract.Presenter toolbarFragmentPresenter;

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

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    private void setupViews(Imovel imovel) {
        ArrayList<String> images = new ArrayList<>();
        images.add(imovel.getUrlImagem());
        setupToolbarFragment(images);
        setupBottomFragment(imovel);
    }

    private void setupToolbarFragment(ArrayList<String> images) {
        ImovelDetailsToolbarFragment toolbarFragment =
                (ImovelDetailsToolbarFragment) getSupportFragmentManager().findFragmentById(R.id.toolbar_view_container);
        if (toolbarFragment == null) {
            toolbarFragment = ImovelDetailsToolbarFragment.newInstance(images);
            ActivityHelper.addFragmentToActivity(getSupportFragmentManager(), toolbarFragment, R.id.toolbar_view_container);
        }
        toolbarFragmentPresenter = new ImovelDetailsToolbarPresenter(
                ImovelApi.getInstance(), toolbarFragment, SchedulerProvider.getInstance());
    }

    private void setupBottomFragment(Imovel imovel) {
        ImovelDetailsBottomFragment bottomFragment =
                (ImovelDetailsBottomFragment) getSupportFragmentManager().findFragmentById(R.id.view_container);
        if (bottomFragment == null) {
            bottomFragment = ImovelDetailsBottomFragment.newInstance(imovel.getCodImovel());
            ActivityHelper.addFragmentToActivity(getSupportFragmentManager(), bottomFragment, R.id.view_container);
        }
        bottomFragmentPresenter = new ImovelDetailsBottomPresenter(ImovelApi.getInstance(), bottomFragment,
                SchedulerProvider.getInstance());
    }
}
