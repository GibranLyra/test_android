package gibran.com.br.mvpsample.shot;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
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
import gibran.com.br.dribbleservice.model.Shot;
import gibran.com.br.mvpsample.ActivityRoutes;
import gibran.com.br.mvpsample.R;

/**
 * Created by gibranlyra on 24/08/17.
 */

public class ShotFragment extends Fragment implements ShotContract.View {

    private static final String LOADED_SHOTS = "loadedShots";
    @BindView(R.id.fragment_shot_progress_bar)
    protected ProgressBar progressBar;
    @BindView(R.id.fragment_shot_recycler)
    protected RecyclerView recyclerView;
    @BindView(R.id.fragment_shot_swipe)
    protected SwipeRefreshLayout swipeToRefresh;

    private Unbinder unbinder;
    private ShotContract.Presenter presenter;
    private FastItemAdapter<ShotItem> fastAdapter;
    protected boolean isViewLoaded;
    @Nullable
    private Bundle savedInstanceState;
    private ArrayList<Shot> shots;

    public static ShotFragment newInstance() {
        ShotFragment fragment = new ShotFragment();
        Bundle bundle = new Bundle();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_shot, container, false);
        unbinder = ButterKnife.bind(this, view);
        swipeToRefresh.setOnRefreshListener(() -> presenter.loadShots());
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
            presenter.loadShots();
        } else {
            shots = savedInstanceState.getParcelableArrayList(LOADED_SHOTS);
            if (shots == null) {
                //If we are restoring the state but dont have shots, we load it.
                presenter.loadShots();
            } else {
                //If we already have the shots we simply add them to the list
                showShots(shots);
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
        outState.putParcelableArrayList(LOADED_SHOTS, shots);
        super.onSaveInstanceState(outState);
    }

    @Override
    public void showShots(ArrayList<Shot> shots) {
        swipeToRefresh.setRefreshing(false);
        this.shots = shots;
        fastAdapter = new FastItemAdapter<>();
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        if (recyclerView.getAdapter() == null) {
            recyclerView.setAdapter(fastAdapter);
        } else {
            fastAdapter.clear();
        }
        for (Shot shot : shots) {
            ShotItem shotItem = new ShotItem(shot);
            fastAdapter.add(shotItem);
        }
        fastAdapter.withOnClickListener((v, adapter, item, position) -> {
            ActivityRoutes.getInstance().openShotDetailsActivity(getContext(), item.getModel(), v);
            return false;
        });
        //restore selections (this has to be done after the items were added
        fastAdapter.withSavedInstanceState(savedInstanceState);
    }

    @Override
    public void showShotsError() {
        swipeToRefresh.setRefreshing(false);
        Snackbar.make(getActivity().findViewById(R.id.rootLayout), R.string.generic_error, Snackbar.LENGTH_LONG).show();
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
    public boolean isActive() {
        return isAdded();
    }

    @Override
    public void setPresenter(ShotContract.Presenter presenter) {
        this.presenter = presenter;
    }
}
