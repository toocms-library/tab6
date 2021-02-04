package com.toocms.tab.binding.viewadapter.recyclerview;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.blankj.utilcode.util.ConvertUtils;
import com.blankj.utilcode.util.SizeUtils;
import com.toocms.tab.R;

import java.util.Objects;

public class DividerLine extends RecyclerView.ItemDecoration {

    //默认分隔线厚度为1dp
    private static final int DEFAULT_DIVIDER_SIZE = 1;
    //默认分隔线颜色的属性,值为一个color
    private static final int[] ATTRS = {R.attr.app_list_divider};
    private Paint paint;
    //divider的颜色
    private int dividerColor;
    private int dividerSize;
    //默认为null
    private LineDrawMode mMode = null;

    /**
     * 分隔线绘制模式,水平，垂直，两者都绘制
     */
    public enum LineDrawMode {
        HORIZONTAL, VERTICAL, BOTH
    }

    public DividerLine(Context context) {
        //获取样式中对应的属性值
        TypedArray attrArray = context.obtainStyledAttributes(ATTRS);
        dividerColor = attrArray.getColor(0, Color.parseColor("#F6F4F4"));
        dividerSize = ConvertUtils.dp2px(DEFAULT_DIVIDER_SIZE);
        attrArray.recycle();

        paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setAntiAlias(true);
        paint.setStyle(Paint.Style.FILL);
        paint.setColor(dividerColor);
    }

    public DividerLine(Context context, LineDrawMode mode) {
        this(context);
        mMode = mode;
    }

    public DividerLine(Context context, int dividerSize, LineDrawMode mode) {
        this(context, mode);
        this.dividerSize = ConvertUtils.dp2px(dividerSize);
    }

    public DividerLine(Context context, int dividerSize, int dividerColor, LineDrawMode mode) {
        this(context, mode);
        this.dividerSize = ConvertUtils.dp2px(dividerSize);
        this.dividerColor = dividerColor;
        paint.setColor(dividerColor);
    }

    /**
     * Item绘制完毕之后绘制分隔线
     * 根据不同的模式绘制不同的分隔线
     *
     * @param c
     * @param parent
     * @param state
     */
    @Override
    public void onDraw(@NonNull Canvas c, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
        super.onDraw(c, parent, state);
        if (mMode == null) {
            throw new IllegalStateException("assign LineDrawMode,please!");
        }
        switch (mMode) {
            case VERTICAL:
                drawVertical(c, parent);
                break;
            case HORIZONTAL:
                drawHorizontal(c, parent);
                break;
            case BOTH:
                drawHorizontal(c, parent);
                drawVertical(c, parent);
                break;
        }
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        super.getItemOffsets(outRect, view, parent, state);
        int right = dividerSize;
        int bottom = dividerSize;
        int itemPosition = parent.getChildAdapterPosition(view);
        if (isLastSpan(itemPosition, parent)) {
            right = 0;
        }
        if (isLastRow(itemPosition, parent)) {
            bottom = 0;
        }
        outRect.set(0, 0, right, bottom);
    }

    public boolean isLastRow(int itemPosition, RecyclerView parent) {
        RecyclerView.LayoutManager layoutManager = parent.getLayoutManager();
        if (layoutManager instanceof GridLayoutManager) {
            int spanCount = ((GridLayoutManager) layoutManager).getSpanCount();
            int itemCount = Objects.requireNonNull(parent.getAdapter()).getItemCount();
            return (itemCount - itemPosition - 1) < spanCount;
        }
        return false;
    }

    public boolean isLastSpan(int itemPosition, RecyclerView parent) {
        RecyclerView.LayoutManager layoutManager = parent.getLayoutManager();
        if (layoutManager instanceof GridLayoutManager) {
            int spanCount = ((GridLayoutManager) layoutManager).getSpanCount();
            return (itemPosition + 1) % spanCount == 0;
        }
        return false;
    }

    /**
     * 绘制垂直分隔线
     *
     * @param c
     * @param parent
     */
    private void drawVertical(Canvas c, RecyclerView parent) {
        final int childCount = parent.getChildCount();
        for (int i = 0; i < childCount; i++) {
            View child = parent.getChildAt(i);
            RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) child.getLayoutParams();
            int top = child.getTop() - params.topMargin;
            int bottom = child.getBottom() + params.bottomMargin;
            int left = child.getRight() + params.rightMargin;
            int right = left + dividerSize;
            c.drawRect(left, top, right, bottom, paint);
        }
    }

    /**
     * 绘制水平分隔线
     *
     * @param c
     * @param parent
     */
    private void drawHorizontal(Canvas c, RecyclerView parent) {
        int childCount = parent.getChildCount();
        for (int i = 0; i < childCount; i++) {
            View child = parent.getChildAt(i);
            RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) child.getLayoutParams();
            int left = child.getLeft() - params.leftMargin;
            int top = child.getBottom() + params.bottomMargin;
            int right = child.getRight() + params.rightMargin;
            int bottom = top + dividerSize;
            c.drawRect(left, top, right, bottom, paint);
        }
    }

    public int getDividerSize() {
        return dividerSize;
    }

    public void setDividerSize(int dividerSize) {
        this.dividerSize = ConvertUtils.dp2px(dividerSize);
    }

    public int getDividerColor() {
        return dividerColor;
    }

    public void setDividerColor(int dividerColor) {
        this.dividerColor = dividerColor;
    }

    public LineDrawMode getMode() {
        return mMode;
    }

    public void setMode(LineDrawMode mode) {
        mMode = mode;
    }
}
