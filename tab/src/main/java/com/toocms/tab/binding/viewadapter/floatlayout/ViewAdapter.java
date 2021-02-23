package com.toocms.tab.binding.viewadapter.floatlayout;

import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.StateListDrawable;
import android.util.TypedValue;
import android.widget.TextView;

import androidx.databinding.BindingAdapter;

import com.blankj.utilcode.util.ActivityUtils;
import com.blankj.utilcode.util.ConvertUtils;
import com.qmuiteam.qmui.widget.QMUIFloatLayout;

import java.util.List;

/**
 * Author：Zero
 * Date：2021/2/23
 */
public class ViewAdapter {

    @BindingAdapter(value = {"items", "custom", "onLineCountChangeListener", "onFloatItemClickListener"}, requireAll = false)
    public static <T> void setAdapter(QMUIFloatLayout floatLayout,
                                      List<T> items,
                                      BindingCustom<T> custom,
                                      QMUIFloatLayout.OnLineCountChangeListener onLineCountChangeListener,
                                      OnFloatItemClickListener<T> onFloatItemClickListener) {
        if (onLineCountChangeListener != null)
            floatLayout.setOnLineCountChangeListener(onLineCountChangeListener);
        floatLayout.removeAllViews();
        for (T t : items) {
            TextView textView = new TextView(ActivityUtils.getTopActivity());
            int horizontalPadding = ConvertUtils.dp2px(15);
            int verticalPadding = ConvertUtils.dp2px(8);
            textView.setPadding(horizontalPadding, verticalPadding, horizontalPadding, verticalPadding);
            textView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 14);
            textView.setTextColor(0xFF333333);
            textView.setBackground(getSolidBackgroundDrawable());
            if (custom != null) custom.assignment(textView, t);
            textView.setOnClickListener(v -> {
                if (onFloatItemClickListener != null)
                    onFloatItemClickListener.onFloatItemClick(textView, t);
            });
            floatLayout.addView(textView);
        }
    }

    public static Drawable getSolidBackgroundDrawable() {
        GradientDrawable normal = new GradientDrawable();
        normal.setShape(GradientDrawable.RECTANGLE);
        normal.setCornerRadius(ConvertUtils.dp2px(10));
        normal.setColor(0xFFF5F5F5);
        GradientDrawable pressed = new GradientDrawable();
        pressed.setShape(GradientDrawable.RECTANGLE);
        pressed.setCornerRadius(ConvertUtils.dp2px(10));
        pressed.setColor(0xFFE0E0E0);
        StateListDrawable selector = new StateListDrawable();
        selector.addState(new int[]{android.R.attr.state_pressed}, pressed);
        selector.addState(new int[]{}, normal);
        return selector;
    }

    public static Drawable getHollowBackgroundDrawable() {
        GradientDrawable normal = new GradientDrawable();
        normal.setShape(GradientDrawable.RECTANGLE);
        normal.setCornerRadius(ConvertUtils.dp2px(10));
        normal.setStroke(ConvertUtils.dp2px(1), 0xFFF5F5F5);
        normal.setColor(0x00000000);
        GradientDrawable pressed = new GradientDrawable();
        pressed.setShape(GradientDrawable.RECTANGLE);
        pressed.setCornerRadius(ConvertUtils.dp2px(10));
        pressed.setStroke(ConvertUtils.dp2px(1), 0xFFE0E0E0);
        pressed.setColor(0x00000000);
        StateListDrawable selector = new StateListDrawable();
        selector.addState(new int[]{android.R.attr.state_pressed}, pressed);
        selector.addState(new int[]{}, normal);
        return selector;
    }

    public interface BindingCustom<T> {
        void assignment(TextView textView, T t);
    }

    public interface OnFloatItemClickListener<T> {
        void onFloatItemClick(TextView textView, T t);
    }
}
