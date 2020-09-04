package ru.getof.ytvvkarmane.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.ImageView;
import android.widget.RelativeLayout;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import java.util.List;

import ru.getof.ytvvkarmane.Components.AdsData.AdsHomeList;
import ru.getof.ytvvkarmane.R;


public class AdsHomeAdapter extends PagerAdapter {

    private Context aContext;
    private List<AdsHomeList> adsHomeLists;

    public AdsHomeAdapter(Context aContext, List<AdsHomeList> adsHomeLists) {
        this.aContext = aContext;
        this.adsHomeLists = adsHomeLists;
    }

    @Override
    public int getCount() {
        return adsHomeLists.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {

        ImageView imageView;

        LayoutInflater layoutInflater = (LayoutInflater) aContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View itemView = layoutInflater.inflate(R.layout.item_page_ads, container, false);
        imageView = itemView.findViewById(R.id.img_ads);

        Glide
                .with(aContext)
                .load(adsHomeLists.get(position).getImageUrl())
                .skipMemoryCache(true)
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .into(imageView);

        container.addView(itemView);

        return itemView;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((RelativeLayout) object);
    }
}
