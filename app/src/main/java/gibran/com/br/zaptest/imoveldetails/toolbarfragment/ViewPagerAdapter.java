package gibran.com.br.zaptest.imoveldetails.toolbarfragment;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

import gibran.com.br.zaptest.R;

/**
 * Created by gibranlyra on 15/11/17.
 */

public class ViewPagerAdapter extends PagerAdapter {
    private ArrayList<String> images;
    private LayoutInflater inflater;
    private Context context;

    public ViewPagerAdapter(Context context, ArrayList<String> images) {
        this.context = context;
        this.images = images;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }

    @Override
    public int getCount() {
        return images.size();
    }

    @Override
    public Object instantiateItem(ViewGroup view, int position) {
        View imageLayout = inflater.inflate(R.layout.pager_layout, view, false);
        ImageView imageView = imageLayout.findViewById(R.id.pager_layout_image);
        if (!images.isEmpty()) {
            for (String image : images) {
                if (!TextUtils.isEmpty(image)) {
                    Glide.with(context)
                            .load(image)
                            .into(imageView);
                } else {
                    Glide.with(context)
                            .load(R.drawable.placeholder)
                            .into(imageView);
                }
            }
        } else {
            Glide.with(context)
                    .load(R.drawable.placeholder)
                    .into(imageView);
        }
        view.addView(imageLayout, 0);
        return imageLayout;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view.equals(object);
    }
}
