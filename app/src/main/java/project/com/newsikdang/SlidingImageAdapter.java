package project.com.newsikdang;

import android.content.Context;
import android.os.Parcelable;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import java.util.List;

public class SlidingImageAdapter extends PagerAdapter {

    private List<String> photoList;
    private LayoutInflater inflater;
    private Context context;


    public SlidingImageAdapter(Context context, List<String> photoList) {
        this.context = context;
        this.photoList = photoList;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }

    @Override
    public int getCount() {
        return photoList.size();
    }

    @Override
    public Object instantiateItem(ViewGroup view, int position) {
        View imageLayout = inflater.inflate(R.layout.slidingimageslayout, view, false);

        assert imageLayout != null;
        final ImageView imageView = imageLayout.findViewById(R.id.review_image);

        Glide.with(context).load(photoList.get(position)).into(imageView);
        view.addView(imageLayout);

        return imageLayout;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view.equals(object);
    }

    @Override
    public void restoreState(Parcelable state, ClassLoader loader) {
    }

    @Override
    public Parcelable saveState() {
        return null;
    }


}
