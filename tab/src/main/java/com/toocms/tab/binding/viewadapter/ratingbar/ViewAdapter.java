package com.toocms.tab.binding.viewadapter.ratingbar;

import android.widget.RatingBar;

import androidx.databinding.BindingAdapter;

import com.toocms.tab.binding.command.BindingCommand;

/**
 * Author：Zero
 * Date：2021/4/16
 */
public class ViewAdapter {

    @BindingAdapter(value = {"onRatingBarChange"}, requireAll = false)
    public static void ratingBarChange(RatingBar ratingBar, BindingCommand<Float> command) {
        ratingBar.setOnRatingBarChangeListener((rating, v, b) -> command.execute(v));
    }
}
