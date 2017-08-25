package gibran.com.br.mvpsample.shotdetails;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import gibran.com.br.dribbleservice.model.Shot;
import gibran.com.br.mvpsample.R;

/**
 * Created by gibranlyra on 25/08/17.
 */

public class ShotDetailsFragment extends Fragment implements ShotDetailsContract.View {

    @BindView(R.id.fragment_shot_details_progress_bar)
    TextView progressBar;
    @BindView(R.id.fragment_shot_details_title)
    TextView titleView;
    @BindView(R.id.fragment_shot_details_image)
    ImageView imageView;
    @BindView(R.id.fragment_shot_details_description)
    TextView descriptionView;
    @BindView(R.id.fragment_shot_details_views_count)
    TextView viewsCountView;
    @BindView(R.id.fragment_shot_details_comments_count)
    TextView commentsCountView;
    @BindView(R.id.fragment_shot_details_created_at)
    TextView createdAtView;

    private static final String EXTRA_SHOT = "ShotId";
    private Unbinder unbinder;
    private ShotDetailsContract.Presenter presenter;

    public static ShotDetailsFragment newInstance(Shot shot) {
        ShotDetailsFragment fragment = new ShotDetailsFragment();
        Bundle bundle = new Bundle();
        bundle.putParcelable(EXTRA_SHOT, shot);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_shot_details, container, false);
        if (savedInstanceState == null) {
            Bundle bundle = this.getArguments();
            Shot shot = bundle.getParcelable(EXTRA_SHOT);
            if (shot != null) {
                presenter.loadShot(shot.getId());
            } else {
                throw new RuntimeException("Shot cannot be null");
            }
        }
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
    }

    @Override
    public void showShot(Shot shot) {
        setupView(shot);
    }

    private void setupView(Shot shot) {
        titleView.setText(shot.getTitle());
        if (!TextUtils.isEmpty(shot.getImages().getNormal())) {
            Glide.with(getContext())
                    .load(shot.getImages().getNormal())
                    .into(imageView);
        }

        descriptionView.setText(shot.getDescription());
        viewsCountView.setText(String.format(getResources().getString(R.string.shot_item_view_count_text),
                String.valueOf(shot.getViewsCount())));
        commentsCountView.setText(shot.getCommentsCount());
        createdAtView.setText(String.format(getResources().getString(R.string.shot_item_created_at_text),
                shot.getCreatedAt()));
    }

    @Override
    public void showShotError() {
        Snackbar.make(getActivity().findViewById(R.id.rootLayout), R.string.generic_error, Snackbar.LENGTH_LONG).show();
    }


    @Override
    public void showLoading(boolean show) {
        if (show) {
            progressBar.setVisibility(View.VISIBLE);
        } else {
            progressBar.setVisibility(View.GONE);
        }
    }

    @Override
    public void setPresenter(ShotDetailsContract.Presenter presenter) {
        this.presenter = presenter;
    }
}
