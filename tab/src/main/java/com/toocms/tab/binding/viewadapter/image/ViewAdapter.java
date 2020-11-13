package com.toocms.tab.binding.viewadapter.image;

import android.text.TextUtils;
import android.widget.ImageView;

import androidx.annotation.DrawableRes;
import androidx.databinding.BindingAdapter;

import com.toocms.tab.imageload.ImageLoader;

public final class ViewAdapter {

    @BindingAdapter("android:src")
    public static void setImageRes(ImageView imageView, @DrawableRes int resId) {
        imageView.setImageResource(resId);
    }

    @BindingAdapter(value = {"url", "placeholderRes"}, requireAll = false)
    public static void setImageUrl(ImageView imageView, String url, int placeholderRes) {
        if (!TextUtils.isEmpty(url)) {
            ImageLoader.loadUrl2Image(url, imageView, placeholderRes);
        }
    }
}

