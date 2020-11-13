package com.toocms.tab.widget.banner.base;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.toocms.tab.imageload.GlideLoader;
import com.toocms.tab.widget.banner.BannerItem;

import java.lang.ref.WeakReference;

/**
 * @author xuexiang
 * @since 2019-09-24 9:04
 */
public abstract class BaseImageBanner<T extends BaseImageBanner<T>> extends BaseIndicatorBanner<BannerItem, T> {

    /**
     * 默认加载图片
     */
    protected Drawable mPlaceHolder;
    /**
     * 是否允许进行缓存
     */
    protected boolean mEnableCache;
    /**
     * 加载图片的高／宽比率
     */
    protected double mScale;

    public BaseImageBanner(Context context) {
        super(context);
        initImageBanner();
    }

    public BaseImageBanner(Context context, AttributeSet attrs) {
        super(context, attrs);
        initImageBanner();
    }

    public BaseImageBanner(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        initImageBanner();
    }

    /**
     * 初始化ImageBanner
     */
    protected void initImageBanner() {
        mPlaceHolder = new ColorDrawable(Color.parseColor("#555555"));
        mEnableCache = true;
        mScale = getContainerScale();
    }

    @Override
    public void onTitleSelect(TextView tv, int position) {
        final BannerItem item = getItem(position);
        if (item != null) {
            tv.setText(item.title);
        }
    }

    /**
     * @return 轮播布局的ID
     */
    protected abstract int getItemLayoutId();

    /**
     * @return 图片控件的ID
     */
    protected abstract int getImageViewId();

    /**
     * 创建ViewPager的Item布局
     *
     * @param position
     */
    @Override
    public View onCreateItemView(int position) {
        View inflate = View.inflate(getContext(), getItemLayoutId(), null);
        ImageView iv = inflate.findViewById(getImageViewId());

        //解决Glide资源释放的问题，详细见http://blog.csdn.net/shangmingchao/article/details/51125554
        WeakReference<ImageView> imageViewWeakReference = new WeakReference<>(iv);
        ImageView target = imageViewWeakReference.get();

        BannerItem item = getItem(position);
        if (item != null && target != null) {
            loadingImageView(target, item);
        }
        return inflate;
    }

    /**
     * 默认加载图片的方法，可以重写
     *
     * @param iv
     * @param item
     */
    protected void loadingImageView(ImageView iv, BannerItem item) {
        String imgUrl = item.imgUrl;
        if (!TextUtils.isEmpty(imgUrl)) {
            int itemWidth = getItemWidth();
            int itemHeight = getItemHeight();
            if (mScale > 0) {
                itemHeight = (int) (itemWidth * mScale);
            }
            iv.setScaleType(ImageView.ScaleType.CENTER_CROP);
            iv.setLayoutParams(new LinearLayout.LayoutParams(itemWidth, itemHeight));
            GlideLoader.create(iv).load(imgUrl, mPlaceHolder, null);
        } else {
            iv.setImageDrawable(mPlaceHolder);
        }
    }

    /**
     * 设置是否允许缓存
     *
     * @param enableCache
     * @return
     */
    public T enableCache(boolean enableCache) {
        mEnableCache = enableCache;
        return (T) this;
    }

    /**
     * 获取是否允许缓存
     *
     * @return
     */
    public boolean getEnableCache() {
        return mEnableCache;
    }

    public Drawable getPlaceHolderDrawable() {
        return mPlaceHolder;
    }

    public T setPlaceHolderDrawable(Drawable placeHolder) {
        mPlaceHolder = placeHolder;
        return (T) this;
    }

    public double getScale() {
        return mScale;
    }

    public T setScale(double scale) {
        mScale = scale;
        return (T) this;
    }

    @Override
    protected void onDetachedFromWindow() {
        //解决内存泄漏的问题
        pauseScroll();
        super.onDetachedFromWindow();
    }
}
