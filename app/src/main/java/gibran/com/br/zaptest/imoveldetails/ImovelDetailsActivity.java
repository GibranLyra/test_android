package gibran.com.br.zaptest.imoveldetails;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.EditText;

import com.afollestad.materialdialogs.MaterialDialog;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import gibran.com.br.zapservice.imovel.ImovelApi;
import gibran.com.br.zapservice.model.Cliente;
import gibran.com.br.zapservice.model.Imovel;
import gibran.com.br.zaptest.R;
import gibran.com.br.zaptest.helpers.ActivityHelper;
import gibran.com.br.zaptest.helpers.schedulers.SchedulerProvider;
import gibran.com.br.zaptest.imoveldetails.bottomfragment.ImovelDetailsBottomContract;
import gibran.com.br.zaptest.imoveldetails.bottomfragment.ImovelDetailsBottomFragment;
import gibran.com.br.zaptest.imoveldetails.bottomfragment.ImovelDetailsBottomPresenter;
import gibran.com.br.zaptest.imoveldetails.bottomfragment.ImovelLoadedListener;
import gibran.com.br.zaptest.imoveldetails.toolbarfragment.ImovelDetailsToolbarContract;
import gibran.com.br.zaptest.imoveldetails.toolbarfragment.ImovelDetailsToolbarFragment;
import gibran.com.br.zaptest.imoveldetails.toolbarfragment.ImovelDetailsToolbarPresenter;

/**
 * Created by gibranlyra on 25/08/17.
 */

public class ImovelDetailsActivity extends AppCompatActivity implements ImovelLoadedListener {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.activity_imovel_details_contact_button)
    FloatingActionButton contactButton;

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
        setupContactButton();
        setupViews(imovel);
    }

    private void setupContactButton() {
        ImovelApi imovelApi = ImovelApi.getInstance();
        contactButton.setOnClickListener(view -> new MaterialDialog.Builder(this)
                .title("Contato")
                .customView(R.layout.contact_dialog, true)
                .positiveText(R.string.dialog_send)
                .negativeText(R.string.dialog_close)
                .negativeColor(ContextCompat.getColor(this, R.color.md_red_900))
                .onPositive((dialog, which) -> {
                    Cliente cliente = new Cliente();
                    cliente.setNomeFantasia(String.valueOf(((EditText) dialog.getCustomView()
                            .findViewById(R.id.contact_dialog_name)).getText()));
                    cliente.setEmail(String.valueOf(((EditText) dialog.getCustomView()
                            .findViewById(R.id.contact_dialog_email)).getText()));
                    cliente.setTelefone(String.valueOf(((EditText) dialog.getCustomView()
                            .findViewById(R.id.contact_dialog_phone)).getText()));
                    imovelApi.postMessage(cliente)
                            .subscribeOn(SchedulerProvider.getInstance().io())
                            .observeOn(SchedulerProvider.getInstance().ui())
                            .subscribe(o ->
                                    Snackbar.make(findViewById(R.id.rootLayout), R.string.message_sent,
                                            Snackbar.LENGTH_SHORT)
                                            .show(), e ->
                                    Snackbar.make(findViewById(R.id.rootLayout), R.string.message_not_sent,
                                            Snackbar.LENGTH_LONG));
                })
                .show());
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

    @Override
    public void imovelLoaded(Imovel imovel) {
        ImovelDetailsToolbarFragment imovelDetailsToolbarFragment =
                (ImovelDetailsToolbarFragment) getSupportFragmentManager().findFragmentById(R.id.toolbar_view_container);
        imovelDetailsToolbarFragment.addImage(imovel.getFotos());
    }
}
