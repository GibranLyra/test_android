package gibran.com.br.zaptest.imovel;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.VisibleForTesting;
import android.support.design.widget.CoordinatorLayout;
import android.support.test.espresso.IdlingResource;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.mikepenz.materialdrawer.AccountHeader;
import com.mikepenz.materialdrawer.AccountHeaderBuilder;
import com.mikepenz.materialdrawer.DrawerBuilder;
import com.mikepenz.materialdrawer.model.ProfileDrawerItem;

import butterknife.BindView;
import butterknife.ButterKnife;
import gibran.com.br.zapservice.imovel.ImovelApi;
import gibran.com.br.zaptest.R;
import gibran.com.br.zaptest.helpers.ActivityHelper;
import gibran.com.br.zaptest.helpers.EspressoIdlingResource;
import gibran.com.br.zaptest.helpers.schedulers.SchedulerProvider;

public class ImovelActivity extends AppCompatActivity {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.rootLayout)
    CoordinatorLayout coordinatorLayout;

    private ImovelContract.Presenter presenter;

    public static Intent createIntent(Context context) {
        Intent intent = new Intent(context, ImovelActivity.class);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shot);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setTitle(R.string.app_name);
        }
        createDrawer();
        setupViews();
    }

    private void createDrawer() {
        AccountHeader headerResult = new AccountHeaderBuilder()
                .withActivity(this)
                .withHeaderBackground(R.drawable.header)
                .addProfiles(
                        new ProfileDrawerItem().withName("Gibran Lyra").withEmail("lyra.gibran@gmail.com")
                                .withIcon(getResources().getDrawable(R.drawable.profile))
                )
                .withOnAccountHeaderListener((view, profile, currentProfile) -> false)
                .build();
        //Now create your drawer and pass the AccountHeader.Result
        new DrawerBuilder()
                .withActivity(this)
                .withToolbar(toolbar)
                .withActionBarDrawerToggle(true)
                .withAccountHeader(headerResult)
                .withActionBarDrawerToggleAnimated(true)
                .build();
    }

    private void setupViews() {
        ImovelFragment imovelFragment =
                (ImovelFragment) getSupportFragmentManager().findFragmentById(R.id.view_container);
        if (imovelFragment == null) {
            imovelFragment = ImovelFragment.newInstance();
            ActivityHelper.addFragmentToActivity(getSupportFragmentManager(), imovelFragment, R.id.view_container);
        }
        presenter = new ImovelPresenter(ImovelApi.getInstance(), imovelFragment, SchedulerProvider.getInstance());
    }

    @VisibleForTesting
    public IdlingResource getCountingIdlingResource() {
        return EspressoIdlingResource.getIdlingResource();
    }
}
