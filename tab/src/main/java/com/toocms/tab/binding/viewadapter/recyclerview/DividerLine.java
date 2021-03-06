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
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

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
        int itemPosition = ((RecyclerView.LayoutParams) view.getLayoutParams()).getViewLayoutPosition();
        int spanCount = getSpanCount(parent);
        int childCount = parent.getAdapter().getItemCount();
        boolean isLastRow = isLastRow(parent, itemPosition, spanCount, childCount);
        boolean isLastColumn = isLastColumn(parent, itemPosition, spanCount, childCount);
        int eachWidth = (spanCount - 1) * dividerSize / spanCount;
        int dl = dividerSize - eachWidth;
        int left = itemPosition % spanCount * dl;
        int right = eachWidth - left;
        int bottom = dividerSize;

        switch (mMode) {
            case VERTICAL:
                if (isLastColumn) {
                    right = 0;
                }
                outRect.set(0, 0, right, 0);
                break;
            case HORIZONTAL:
                if (isLastRow) {
                    bottom = 0;
                }
                outRect.set(0, 0, 0, bottom);
                break;
            case BOTH:
                if (isLastColumn) {
                    right = 0;
                }
                if (isLastRow) {
                    bottom = 0;
                }
                outRect.set(left, 0, right, bottom);
                break;
        }
    }

    private boolean isLastRow(RecyclerView parent, int pos, int spanCount, int childCount) {
        RecyclerView.LayoutManager layoutManager = parent.getLayoutManager();
        if (layoutManager instanceof GridLayoutManager) {
            // childCount = childCount - childCount % spanCount;
            int lines = childCount % spanCount == 0 ? childCount / spanCount : childCount / spanCount + 1;
            return lines == pos / spanCount + 1;
        } else if (layoutManager instanceof StaggeredGridLayoutManager) {
            int orientation = ((StaggeredGridLayoutManager) layoutManager).getOrientation();
            // StaggeredGridLayoutManager 且纵向滚动
            if (orientation == StaggeredGridLayoutManager.VERTICAL) {
                childCount = childCount - childCount % spanCount;
                // 如果是最后一行，则不需要绘制底部
                if (pos >= childCount) return true;
            } else {
                // 如果是最后一行，则不需要绘制底部
                if ((pos + 1) % spanCount == 0) return true;
            }
        }
        return false;
    }

    private boolean isLastColumn(RecyclerView parent, int pos, int spanCount, int childCount) {
        RecyclerView.LayoutManager layoutManager = parent.getLayoutManager();
        if (layoutManager instanceof GridLayoutManager) {
            // 如果是最后一列，则不需要绘制右边
            if ((pos + 1) % spanCount == 0) return true;
        } else if (layoutManager instanceof StaggeredGridLayoutManager) {
            int orientation = ((StaggeredGridLayoutManager) layoutManager).getOrientation();
            if (orientation == StaggeredGridLayoutManager.VERTICAL) {
                // 如果是最后一列，则不需要绘制右边
                if ((pos + 1) % spanCount == 0) return true;
            } else {
                childCount = childCount - childCount % spanCount;
                // 如果是最后一列，则不需要绘制右边
                if (pos >= childCount) return true;
            }
        }
        return false;
    }

    private int getSpanCount(RecyclerView parent) {
        // 列数
        int spanCount = -1;
        RecyclerView.LayoutManager layoutManager = parent.getLayoutManager();
        if (layoutManager instanceof GridLayoutManager) {
            spanCount = ((GridLayoutManager) layoutManager).getSpanCount();
        } else if (layoutManager instanceof StaggeredGridLayoutManager) {
            spanCount = ((StaggeredGridLayoutManager) layoutManager).getSpanCount();
        }
        return spanCount;
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
