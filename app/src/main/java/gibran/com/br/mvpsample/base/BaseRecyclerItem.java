package gibran.com.br.mvpsample.base;

import android.support.v7.widget.RecyclerView;

import com.mikepenz.fastadapter.items.AbstractItem;

/**
 * Created by gibran.lyra on 24/08/2016.
 */
public abstract class BaseRecyclerItem<T, item extends AbstractItem<?, ?>, viewHolder
        extends RecyclerView.ViewHolder> extends AbstractItem<item, viewHolder> {

    private T item;

    public BaseRecyclerItem(T item) {
        this.item = item;
    }

    public T getModel() {
        return item;
    }
}

