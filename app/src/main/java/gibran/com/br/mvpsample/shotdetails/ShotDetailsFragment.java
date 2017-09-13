package gibran.com.br.mvpsample.shotdetails;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.widget.ContentLoadingProgressBar;
import android.support.v7.widget.CardView;
import android.text.Html;
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
import gibran.com.br.mvpsample.helpers.ActivityHelper;

/**
 * Created by gibranlyra on 25/08/17.
 */

public class ShotDetailsFragment extends Fragment implements ShotDetailsContract.View {

    private static final String LOADED_SHOT = "loadedShot";
    @BindView(R.id.fragment_shot_details_progress_bar)
    ContentLoadingProgressBar progressBar;
    @BindView(R.id.fragment_shot_details_card_view)
    CardView cardView;
    @BindView(R.id.fragment_shot_details_title)
    TextView titleView;
    @BindView(R.id.fragment_shot_details_author)
    TextView authorView;
    @BindView(R.id.fragment_shot_details_image)
    ImageView imageView;
    @BindView(R.id.fragment_shot_details_likes)
    TextView likesView;
    @BindView(R.id.fragment_shot_details_buckets_count)
    TextView bucketsCountView;
    @BindView(R.id.fragment_shot_details_description)
    TextView descriptionView;
    @BindView(R.id.fragment_shot_details_views_count)
    TextView countsView;
    @BindView(R.id.fragment_shot_details_comments_count)
    TextView commentsCountView;
    @BindView(R.id.fragment_shot_details_created_at)
    TextView createdAtView;

    private static final String EXTRA_SHOT = "ShotId";
    private Unbinder unbinder;
    private ShotDetailsContract.Presenter presenter;
    private Shot shot;

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
        unbinder = ButterKnife.bind(this, view);
        if (savedInstanceState == null) {
            presenter.loadShot(getShotFromBundle().getId());
        } else {
            shot = savedInstanceState.getParcelable(LOADED_SHOT);
            if (shot == null) {
                //If we are restoring the state but dont have a shot, we load it
                presenter.loadShot(getShotFromBundle().getId());
            } else {
                //If we already have the shots we simply add them to the list
                showShot(shot);
                showLoading(false);
            }
        }
        return view;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelable(LOADED_SHOT, shot);
    }

    @Override
    public void showShot(Shot shot) {
        this.shot = shot;
        setupView(shot);
    }

    private Shot getShotFromBundle() {
        Bundle bundle = this.getArguments();
        Shot shot = bundle.getParcelable(EXTRA_SHOT);
        if (shot != null) {
            return shot;
        } else {
            throw new RuntimeException("Shot cannot be null");
        }
    }

    private void setupView(Shot shot) {
        titleView.setText(shot.getTitle());
        if (!TextUtils.isEmpty(shot.getImages().getNormal())) {
            Glide.with(getContext())
                    .load(shot.getImages().getNormal())
                    .placeholder(R.drawable.placeholder)
                    .into(imageView);
        } else {
            Glide.with(getContext())
                    .load(R.drawable.placeholder)
                    .into(imageView);
        }
        //Some Shots don`t have description, so we check if it have one
        if (!TextUtils.isEmpty(shot.getDescription())) {
            descriptionView.setText(Html.fromHtml(shot.getDescription()));
        }
        authorView.setText(String.format(getResources().getString(R.string.shot_item_view_author_text),
                String.valueOf(shot.getUser().getName())));
        likesView.setText(String.format(getResources().getString(R.string.shot_item_view_likes_text),
                String.valueOf(shot.getBucketsCount())));
        bucketsCountView.setText(String.format(getResources().getString(R.string.shot_item_view_buckets_text),
                String.valueOf(shot.getBucketsCount())));
        countsView.setText(String.format(getResources().getString(R.string.shot_item_view_count_text),
                String.valueOf(shot.getViewsCount())));
        commentsCountView.setText(String.format(getResources().getString(R.string.shot_item_comments_count),
                String.valueOf(shot.getCommentsCount())));
        String createdAt = ActivityHelper.getFormatedDate(shot.getCreatedAt());
        createdAtView.setText(String.format(getResources().getString(R.string.shot_item_created_at_text),
                createdAt));
    }

    @Override
    public void showShotError() {
        Snackbar.make(getActivity().findViewById(R.id.rootLayout), R.string.generic_error, Snackbar.LENGTH_LONG).show();
    }


    @Override
    public void showLoading(boolean show) {
        if (show) {
            progressBar.setVisibility(View.VISIBLE);
            cardView.setVisibility(View.GONE);
        } else {
            progressBar.setVisibility(View.GONE);
            cardView.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public boolean isActive() {
        return isAdded();
    }

    @Override
    public void setPresenter(ShotDetailsContract.Presenter presenter) {
        this.presenter = presenter;
    }
}
