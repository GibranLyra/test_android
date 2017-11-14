package gibran.com.br.zaptest.imovel;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.mikepenz.fastadapter.commons.adapters.FastItemAdapter;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import gibran.com.br.zapservice.model.Imovel;
import gibran.com.br.zaptest.ActivityRoutes;
import gibran.com.br.zaptest.R;
import gibran.com.br.zaptest.base.BaseFragment;

/**
 * Created by gibranlyra on 24/08/17.
 */

public class ImovelFragment extends BaseFragment<ImovelContract.Presenter> implements ImovelContract.ContractView {

    private static final String LOADED_IMOVEIS = "loadedImoveis";
    @BindView(R.id.fragment_imovel_progress_bar)
    protected ProgressBar progressBar;
    @BindView(R.id.fragment_imovel_recycler)
    protected RecyclerView recyclerView;
    @BindView(R.id.fragment_imovel_swipe)
    protected SwipeRefreshLayout swipeToRefresh;

    private Unbinder unbinder;
    private ImovelContract.Presenter presenter;
    private FastItemAdapter<ImovelItem> fastAdapter;
    protected boolean isViewLoaded;
    @Nullable
    private Bundle savedInstanceState;
    private ArrayList<Imovel> imovels;

    public static ImovelFragment newInstance() {
        ImovelFragment fragment = new ImovelFragment();
        Bundle bundle = new Bundle();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_imovel, container, false);
        unbinder = ButterKnife.bind(this, view);
        swipeToRefresh.setOnRefreshListener(() -> reloadFragment());
        swipeToRefresh.setColorSchemeResources(
                R.color.accent,
                R.color.colorSecondary,
                R.color.primary);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        this.savedInstanceState = savedInstanceState;
        if (savedInstanceState == null) {
            presenter.loadImoveis();
        } else {
            imovels = savedInstanceState.getParcelableArrayList(LOADED_IMOVEIS);
            if (imovels == null) {
                //If we are restoring the state but dont have imovels, we load it.
                presenter.loadImoveis();
            } else {
                //If we already have the imovels we simply add them to the list
                showImoveis(imovels);
                showLoading(false);
            }
        }
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
        if (fastAdapter != null) {
            //add the values which need to be saved from the adapter to the bundle
            outState = fastAdapter.saveInstanceState(outState);
        }
        outState.putParcelableArrayList(LOADED_IMOVEIS, imovels);
        super.onSaveInstanceState(outState);
    }

    @Override
    public void showImoveis(ArrayList<Imovel> imoveis) {
        swipeToRefresh.setRefreshing(false);
        this.imovels = imoveis;
        fastAdapter = new FastItemAdapter<>();
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        if (recyclerView.getAdapter() == null) {
            recyclerView.setAdapter(fastAdapter);
        } else {
            fastAdapter.clear();
        }
        addRecyclerItems(imoveis);
        fastAdapter.withOnClickListener((v, adapter, item, position) -> {
            presenter.openImovelDetails(item.getModel(), v);
            return false;
        });
        fastAdapter.withSavedInstanceState(savedInstanceState);
    }

    @Override
    public void showImovelsError() {
        swipeToRefresh.setRefreshing(false);
        showImovelError();
    }

    @Override
    public void showLoading(boolean show) {
        isViewLoaded = !show;
        if (show) {
            progressBar.setVisibility(View.VISIBLE);
            recyclerView.setVisibility(View.GONE);
        } else {
            progressBar.setVisibility(View.GONE);
            recyclerView.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void showImovelDetailsUi(Imovel imovel, @Nullable View v) {
        ActivityRoutes.getInstance().openImovelDetailsActivity(getContext(), imovel, v);
    }

    @Override
    public void setPresenter(ImovelContract.Presenter presenter) {
        this.presenter = presenter;
    }

    private void addRecyclerItems(ArrayList<Imovel> imoveis) {
        for (Imovel imovel : imoveis) {
            ImovelItem imovelItem = new ImovelItem(imovel);
            fastAdapter.add(imovelItem);
        }
    }

    @Override
    protected void reloadFragment() {
        presenter.loadImoveis();
    }
}
