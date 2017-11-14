package gibran.com.br.zaptest.imovel;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import gibran.com.br.zapservice.model.Imovel;
import gibran.com.br.zaptest.AppContext;
import gibran.com.br.zaptest.R;
import gibran.com.br.zaptest.base.BaseRecyclerItem;

/**
 * Created by gibranlyra on 24/08/17.
 */

public class ImovelItem extends BaseRecyclerItem<Imovel, ImovelItem, ImovelItem.ViewHolder> {

    public ImovelItem(Imovel item) {
        super(item);
    }

    //The unique ID for this type of item
    @Override
    public int getType() {
        return R.id.imovel_item_id;
    }

    //The layout to be used for this type of item
    @Override
    public int getLayoutRes() {
        return R.layout.imovel_item;
    }

    //The logic to bind your data to the view
    @Override
    public void bindView(ViewHolder viewHolder, List<Object> payloads) {
        //call super so the selection is already handled for you
        super.bindView(viewHolder, payloads);

        //bind our data
        Context context = viewHolder.itemView.getContext();
        Imovel imovel = getModel();
        if (!TextUtils.isEmpty(imovel.getUrlImagem())) {
            Glide.with(context)
                    .setDefaultRequestOptions(AppContext.getInstance().getGlideRequestOptions())
                    .load(imovel.getUrlImagem())
                    .into(viewHolder.imageView);
        } else {
            Glide.with(context)
                    .load(R.drawable.placeholder)
                    .into(viewHolder.imageView);
        }
        viewHolder.priceView.setText(String.valueOf(getModel().getPrecoVenda()));
        viewHolder.addressView.setText(getModel().getEndereco().getCidade());
        viewHolder.informationView.setText(String.valueOf(getModel().getAreaTotal()));
    }

    //reset the view here for better performance
    @Override
    public void unbindView(ViewHolder holder) {
        super.unbindView(holder);
        holder.imageView.setImageDrawable(null);
        holder.priceView.setText(null);
        holder.addressView.setText(null);
        holder.informationView.setText(null);
    }

    //Init the viewHolder for this Item
    @Override
    public ViewHolder getViewHolder(View v) {
        return new ViewHolder(v);
    }

    //The viewHolder used for this item. This viewHolder is always reused by the RecyclerView so scrolling is blazing fast
    protected static class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.imovel_item_image)
        ImageView imageView;
        @BindView(R.id.imovel_item_price)
        TextView priceView;
        @BindView(R.id.imovel_item_view_address)
        TextView addressView;
        @BindView(R.id.imovel_image_information)
        TextView informationView;

        public ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}
