package gibran.com.br.mvpsample.shot;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
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

    @BindView(R.id.fragment_shot_progress_bar)
    protected ProgressBar progressBar;
    @BindView(R.id.fragment_shot_recycler)
    protected RecyclerView recyclerView;

    private Unbinder unbinder;
    private ShotContract.Presenter presenter;

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
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        presenter.loadShots();
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
    public void showShots(ArrayList<Shot> shots) {
        FastItemAdapter<ShotItem> fastAdapter = new FastItemAdapter<>();
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(fastAdapter);
        for (Shot shot : shots) {
            ShotItem shotItem = new ShotItem(shot);
            fastAdapter.add(shotItem);
        }
        fastAdapter.withOnClickListener((v, adapter, item, position) -> {
            ActivityRoutes.getInstance().openShotDetailsActivity(getContext(), item.getModel());
            return false;
        });
    }

    @Override
    public void showShotsError() {
        Snackbar.make(getActivity().findViewById(R.id.rootLayout), R.string.generic_error, Snackbar.LENGTH_LONG).show();
    }

    @Override
    public void showLoading(boolean show) {
        if (show) {
            progressBar.setVisibility(View.VISIBLE);
            recyclerView.setVisibility(View.GONE);
        } else {
            progressBar.setVisibility(View.GONE);
            recyclerView.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void setPresenter(ShotContract.Presenter presenter) {
        this.presenter = presenter;
    }
}
