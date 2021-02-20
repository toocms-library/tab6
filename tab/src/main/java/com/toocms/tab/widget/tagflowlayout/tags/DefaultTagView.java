package com.toocms.tab.widget.tagflowlayout.tags;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.StateListDrawable;
import android.util.AttributeSet;
import android.view.Gravity;

import androidx.annotation.ColorInt;
import androidx.appcompat.widget.AppCompatTextView;

import com.blankj.utilcode.util.ConvertUtils;
import com.toocms.tab.R;

/**
 * Author   :hymanme
 * Email    :hymanme@163.com
 * Create at 2016/9/5
 * Description: 默认标签视图，自定义标签样式时可继承该类
 */
public class DefaultTagView extends AppCompatTextView {
    //默认标签横向内间距
    private static final float DEFAULT_TAG_HORIZONTAL_PADDING = 15;
    //默认标签竖向内间距
    private static final float DEFAULT_TAG_VERTICAL_PADDING = 8;
    //默认标签背景圆角大小
    private static final float DEFAULT_TAG_CORNER = 8;
    //默认边框宽度(仅在非实心背景有效)，单位dp
    private static final float DEFAULT_TAG_STROKE = 1;
    //默认标签背景正常颜色
    private static final int DEFAULT_TAG_BACKGROUND_COLOR = 0XFFF5F5F5;
    //默认标签背景按下颜色
    private static final int DEFAULT_TAG_BACKGROUND_PRESSED_COLOR = 0XFFE0E0E0;
    protected Context mContext;


    public DefaultTagView(Context context) {
        this(context, null);
    }

    public DefaultTagView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public DefaultTagView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.mContext = context;
        init();
    }

    private void init() {
        int horizontalPadding = ConvertUtils.dp2px(getTagHorizontalPadding());
        int verticalPadding = ConvertUtils.dp2px(getTagVerticalPadding());
        setPadding(horizontalPadding, verticalPadding, horizontalPadding, verticalPadding);
        setGravity(Gravity.CENTER);
        setBackgroundDrawable(getBackgroundDrawable());
    }

    //完全自定义tag的样式请重写此方法
    //返回一个Drawable背景样式
    protected Drawable getBackgroundDrawable() {
        //设置字体颜色的选择器
        ColorStateList colorSateList = mContext.getResources().getColorStateList(R.color.tag_flow_layout_default_secondary_text);
        setTextColor(colorSateList);

        GradientDrawable normal = new GradientDrawable();
        normal.setShape(GradientDrawable.RECTANGLE);
        normal.setCornerRadius(ConvertUtils.dp2px(getTagRadius()));
        if (isSolid()) {
            normal.setColor(getNormalBackgroundColor());
        } else {
            normal.setStroke(ConvertUtils.dp2px(getStrokeWidth()), getNormalBackgroundColor());
            normal.setColor(getBackgroundColor());
        }

        GradientDrawable pressed = new GradientDrawable();
        pressed.setShape(GradientDrawable.RECTANGLE);
        pressed.setCornerRadius(ConvertUtils.dp2px(getTagRadius()));
        if (isSolid()) {
            pressed.setColor(getPressedBackgroundColor());
        } else {
            pressed.setStroke(ConvertUtils.dp2px(getStrokeWidth()), getPressedBackgroundColor());
            pressed.setColor(getPressedBackgroundColor());
        }

        StateListDrawable selector = new StateListDrawable();
        selector.addState(new int[]{android.R.attr.state_pressed}, pressed);
        selector.addState(new int[]{}, normal);
        return selector;
    }

    //默认tag背景颜色
    protected
    @ColorInt
    int getNormalBackgroundColor() {
        return DEFAULT_TAG_BACKGROUND_COLOR;
    }

    //按下tag时背景颜色
    protected
    @ColorInt
    int getPressedBackgroundColor() {
        return DEFAULT_TAG_BACKGROUND_PRESSED_COLOR;
    }

    //tag的圆角角度
    protected float getTagRadius() {
        return DEFAULT_TAG_CORNER;
    }

    //tag横向内间距
    protected float getTagHorizontalPadding() {
        return DEFAULT_TAG_HORIZONTAL_PADDING;
    }

    // tag竖向内间距
    protected float getTagVerticalPadding() {
        return DEFAULT_TAG_VERTICAL_PADDING;
    }

    /***
     * 是否为实心背景
     *
     * @return true:实心的，false：空心，可通过{@link #getStrokeWidth()}设置边框宽度
     */
    protected boolean isSolid() {
        return true;
    }

    //设置边框的宽度，单位dp
    protected float getStrokeWidth() {
        return DEFAULT_TAG_STROKE;
    }

    //当设置了空心加边框的tag时给tag加一个背景，
    //防止在透明背景上无法显示点击颜色
    protected
    @ColorInt
    int getBackgroundColor() {
        return DEFAULT_TAG_BACKGROUND_COLOR;
    }
}
