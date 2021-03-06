package gibran.com.br.zaptest.imoveldetails.bottomfragment;

import android.os.Bundle;
import android.support.v4.widget.ContentLoadingProgressBar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import gibran.com.br.zapservice.model.Imovel;
import gibran.com.br.zaptest.R;
import gibran.com.br.zaptest.base.BaseFragment;

/**
 * Created by gibranlyra on 25/08/17.
 */

public class ImovelDetailsBottomFragment extends BaseFragment<ImovelDetailsBottomContract.Presenter>
        implements ImovelDetailsBottomContract.ContractView {

    private static final String LOADED_IMOVEL = "loadedImovel";

    @BindView(R.id.no_error_view)
    View errorView;
    @BindView(R.id.fragment_imovel_details_bottom_progress_bar)
    ContentLoadingProgressBar progressBar;
    @BindView(R.id.fragment_imovel_details_bottom_info_container)
    View infoContainer;
    @BindView(R.id.fragment_imovel_details_bottom_sale_price)
    TextView salePrice;
    @BindView(R.id.fragment_imovel_details_bottom_type)
    TextView typeView;
    @BindView(R.id.fragment_imovel_details_bottom_address)
    TextView addressView;
    @BindView(R.id.fragment_imovel_details_bottom_description)
    TextView descriptionView;

    private static final String EXTRA_IMOVEL = "ImovelId";
    private Unbinder unbinder;
    private ImovelDetailsBottomContract.Presenter presenter;
    private Imovel imovel;

    public static ImovelDetailsBottomFragment newInstance(int imovelId) {
        ImovelDetailsBottomFragment fragment = new ImovelDetailsBottomFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(EXTRA_IMOVEL, imovelId);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_imovel_details_bottom, container, false);
        unbinder = ButterKnife.bind(this, view);
        if (savedInstanceState == null) {
            presenter.loadImovel(getArguments().getInt(EXTRA_IMOVEL));
        } else {
            imovel = savedInstanceState.getParcelable(LOADED_IMOVEL);
            if (imovel == null) {
                //If we are restoring the state but dont have a imovel, we load it
                presenter.loadImovel(getArguments().getInt(EXTRA_IMOVEL));
            } else {
                //If we already have the imovels we simply add them to the list
                showImovel(imovel);
                showLoading(false);
            }
        }
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        presenter.subscribe();
    }

    @Override
    public void onPause() {
        super.onPause();
        presenter.unsubscribe();
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelable(LOADED_IMOVEL, imovel);
    }

    @Override
    public void showImovel(Imovel imovel) {
        this.imovel = imovel;
        infoContainer.setVisibility(View.VISIBLE);
        if (getContext() instanceof ImovelLoadedListener) {
            ((ImovelLoadedListener) getContext()).imovelLoaded(imovel);
        }
        setupView(imovel);
    }

    @Override
    public void showLoading(boolean show) {
        if (show) {
            progressBar.setVisibility(View.VISIBLE);
            errorView.setVisibility(View.GONE);
            infoContainer.setVisibility(View.GONE);
        } else {
            progressBar.setVisibility(View.GONE);
        }
    }


    @Override
    public void showImovelError() {
        super.showImovelError();
        errorView.setVisibility(View.VISIBLE);
    }

    @Override
    protected void reloadFragment() {
        presenter.loadImovel(getArguments().getInt(EXTRA_IMOVEL));
    }

    @Override
    public boolean isActive() {
        return isAdded();
    }

    @Override
    public void setPresenter(ImovelDetailsBottomContract.Presenter presenter) {
        this.presenter = presenter;
    }

    private void setupView(Imovel imovel) {
        typeView.setText(imovel.getTipoImovel());
        salePrice.setText(getContext().getResources().getString(R.string.imovel_price,
                String.valueOf(imovel.getPrecoVenda())));
        addressView.setText(getContext().getResources().getString(R.string.imovel_address,
                imovel.getEndereco().getBairro(),
                imovel.getEndereco().getCidade()));
        descriptionView.setText(imovel.getObservacao());
    }
}
