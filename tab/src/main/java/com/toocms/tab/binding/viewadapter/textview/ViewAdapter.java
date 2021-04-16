package com.toocms.tab.binding.viewadapter.textview;

import android.graphics.Paint;
import android.widget.TextView;

import androidx.databinding.BindingAdapter;

/**
 * Author：Zero
 * Date：2021/4/16
 */
public class ViewAdapter {

    @BindingAdapter(value = {"isStrikethrough"}, requireAll = false)
    public static void textEffect(TextView textView, Boolean isStrikethrough) {
        if (isStrikethrough)
            textView.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG | Paint.ANTI_ALIAS_FLAG);
    }
}
