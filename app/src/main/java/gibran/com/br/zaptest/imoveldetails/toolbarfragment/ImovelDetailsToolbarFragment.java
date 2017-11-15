package gibran.com.br.zaptest.imoveldetails.toolbarfragment;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import gibran.com.br.zaptest.R;
import gibran.com.br.zaptest.base.BaseFragment;

/**
 * Created by gibranlyra on 15/11/17.
 */

public class ImovelDetailsToolbarFragment extends BaseFragment<ImovelDetailsToolbarContract.Presenter>
        implements ImovelDetailsToolbarContract.ContractView {

    private static final String EXTRA_IMAGES = "images";
    private Unbinder unbinder;

    @BindView(R.id.fragment_imovel_details_top_image)
    ImageView imageView;
    private ArrayList<String> images;

    public static ImovelDetailsToolbarFragment newInstance(ArrayList<String> images) {
        ImovelDetailsToolbarFragment fragment = new ImovelDetailsToolbarFragment();
        Bundle bundle = new Bundle();
        bundle.putStringArrayList(EXTRA_IMAGES, images);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_imovel_details_toolbar, container, false);
        unbinder = ButterKnife.bind(this, view);
        images = getArguments().getStringArrayList(EXTRA_IMAGES);
        if (!images.isEmpty()) {
            for (String image : images) {
                if (!TextUtils.isEmpty(image)) {
                    Glide.with(getContext())
                            .load(image)
                            .into(imageView);
                } else {
                    Glide.with(getContext())
                            .load(R.drawable.placeholder)
                            .into(imageView);
                }
            }
        } else {
            Glide.with(getContext())
                    .load(R.drawable.placeholder)
                    .into(imageView);
        }
        return view;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
    }

    @Override
    protected void reloadFragment() {

    }

    @Override
    public void setPresenter(ImovelDetailsToolbarContract.Presenter presenter) {

    }

    @Override
    public void showLoading(boolean show) {

    }

    public void addImage(String image) {
        images.add(image);
    }

    public void addImage(ArrayList<String> images) {
        images.addAll(images);
    }
}
