package gibran.com.br.zaptest.imoveldetails;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.ContentLoadingProgressBar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
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

public class ImovelDetailsFragment extends BaseFragment<ImovelDetailsContract.Presenter>
        implements ImovelDetailsContract.ContractView {

    private static final String LOADED_IMOVEL = "loadedImovel";
    @BindView(R.id.fragment_imovel_details_progress_bar)
    ContentLoadingProgressBar progressBar;
    @BindView(R.id.fragment_imovel_details_author)
    TextView authorView;
    @BindView(R.id.fragment_imovel_details_image)
    ImageView imageView;
    @BindView(R.id.fragment_imovel_details_avatar)
    ImageView avatarView;
    @BindView(R.id.fragment_imovel_details_likes)
    TextView likesView;
    @BindView(R.id.fragment_imovel_details_buckets_count)
    TextView bucketsCountView;
    @BindView(R.id.fragment_imovel_details_description)
    TextView descriptionView;
    @BindView(R.id.fragment_imovel_details_views_count)
    TextView countsView;
    @BindView(R.id.fragment_imovel_details_created_at)
    TextView createdAtView;

    private static final String EXTRA_IMOVEL = "Imovel";
    private Unbinder unbinder;
    private ImovelDetailsContract.Presenter presenter;
    private Imovel imovel;

    public static ImovelDetailsFragment newInstance(Imovel imovel) {
        ImovelDetailsFragment fragment = new ImovelDetailsFragment();
        Bundle bundle = new Bundle();
        bundle.putParcelable(EXTRA_IMOVEL, imovel);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_shot_details, container, false);
        unbinder = ButterKnife.bind(this, view);
        if (savedInstanceState == null) {
            presenter.loadImovel(getImovelFromBundle().getCodImovel());
        } else {
            imovel = savedInstanceState.getParcelable(LOADED_IMOVEL);
            if (imovel == null) {
                //If we are restoring the state but dont have a imovel, we load it
                presenter.loadImovel(getImovelFromBundle().getCodImovel());
            } else {
                //If we already have the imovels we simply add them to the list
                showImovel(imovel);
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
        outState.putParcelable(LOADED_IMOVEL, imovel);
    }

    @Override
    public void showImovel(Imovel imovel) {
        this.imovel = imovel;
        setupView(imovel);
    }

    private Imovel getImovelFromBundle() {
        Bundle bundle = this.getArguments();
        Imovel imovel = bundle.getParcelable(EXTRA_IMOVEL);
        if (imovel != null) {
            return imovel;
        } else {
            throw new RuntimeException("Imovel cannot be null");
        }
    }

    private void setupView(Imovel imovel) {
        // TODO: 13/11/17 fotos
//        if (!TextUtils.isEmpty(imovel.getFotos().getNormal())) {
//            Glide.with(getContext())
//                    .load(imovel.getImages().getNormal())
//                    .into(imageView);
//        } else {
//            Glide.with(getContext())
//                    .load(R.drawable.placeholder)
//                    .into(imageView);
//        }
//        if (!TextUtils.isEmpty(imovel.getUser().getAvatarUrl())) {
//            Glide.with(getContext())
//                    .load(imovel.getUser().getAvatarUrl())
//                    .into(avatarView);
//        }
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
    protected void reloadFragment() {
        presenter.loadImovel(getImovelFromBundle().getCodImovel());
    }

    @Override
    public boolean isActive() {
        return isAdded();
    }

    @Override
    public void setPresenter(ImovelDetailsContract.Presenter presenter) {
        this.presenter = presenter;
    }
}
