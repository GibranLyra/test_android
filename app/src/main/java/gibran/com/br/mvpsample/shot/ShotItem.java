package gibran.com.br.mvpsample.shot;

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
import gibran.com.br.dribbleservice.model.Shot;
import gibran.com.br.mvpsample.R;
import gibran.com.br.mvpsample.base.BaseRecyclerItem;

/**
 * Created by gibranlyra on 24/08/17.
 */

public class ShotItem extends BaseRecyclerItem<Shot, ShotItem, ShotItem.ViewHolder> {

    public ShotItem(Shot item) {
        super(item);
    }

    //The unique ID for this type of item
    @Override
    public int getType() {
        return R.id.shot_item_id;
    }

    //The layout to be used for this type of item
    @Override
    public int getLayoutRes() {
        return R.layout.shot_item;
    }

    //The logic to bind your data to the view
    @Override
    public void bindView(ViewHolder viewHolder, List<Object> payloads) {
        //call super so the selection is already handled for you
        super.bindView(viewHolder, payloads);

        //bind our data
        Context context = viewHolder.itemView.getContext();
        Shot shot = getModel();
        if (shot.getImages() != null && !TextUtils.isEmpty(shot.getImages().getNormal())) {
            Glide.with(context)
                    .load(shot.getImages().getNormal())
                    .into(viewHolder.imageView);
        }
        viewHolder.titleView.setText(getModel().getTitle());
        viewHolder.viewCountView.setText(String.valueOf(getModel().getViewsCount()));
        viewHolder.createdAtView.setText(getModel().getCreatedAt());
    }

    //reset the view here for better performance
    @Override
    public void unbindView(ViewHolder holder) {
        super.unbindView(holder);
        Glide.clear(holder.imageView);
        holder.imageView.setImageDrawable(null);
        holder.titleView.setText(null);
        holder.viewCountView.setText(null);
        holder.createdAtView.setText(null);
    }

    //Init the viewHolder for this Item
    @Override
    public ViewHolder getViewHolder(View v) {
        return new ViewHolder(v);
    }

    //The viewHolder used for this item. This viewHolder is always reused by the RecyclerView so scrolling is blazing fast
    protected static class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.shot_item_image)
        ImageView imageView;
        @BindView(R.id.shot_item_title)
        TextView titleView;
        @BindView(R.id.shot_item_view_count)
        TextView viewCountView;
        @BindView(R.id.shot_image_created_at)
        TextView createdAtView;

        public ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}
