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
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.blankj.utilcode.util.ConvertUtils;
import com.toocms.tab.R;

import java.util.ArrayList;
import java.util.List;

public class DividerLine extends RecyclerView.ItemDecoration {
    public static final String TAG = DividerLine.class.getSimpleName();


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
        int itemPosition = parent.getChildLayoutPosition(view);
        outRect.set(itemOffsets(parent, itemPosition));
//        Log.e(TAG, "position:" + itemPosition + "," + outRect.toString());
    }

    /**
     * 条目的偏移
     *
     * @param parent
     * @param itemPosition
     * @return
     */
    private Rect itemOffsets(RecyclerView parent, int itemPosition) {
        Rect result = new Rect();
        int spanCount = getSpanCount(parent);
        int childCount = parent.getAdapter().getItemCount();
        boolean isFirstRow = isFirstRow(parent, itemPosition, spanCount, childCount);
        boolean isLastRow = isLastRow(parent, itemPosition, spanCount, childCount);
        boolean isFirstColumn = isFirstColumn(parent, itemPosition, spanCount, childCount);
        boolean isLastColumn = isLastColumn(parent, itemPosition, spanCount, childCount);

//        Log.e(TAG,"position:"+itemPosition
//                +"\nisFirstRow:"+isFirstRow
//                +"\nisLastRow:"+isLastRow
//                +"\nisFirstColumn:"+isFirstColumn
//                +"\nisLastColumn:"+isLastColumn);

        int eachWidth = dividerSize / 2;
//        int dl = dividerSize - eachWidth;
        int left = RecyclerView.HORIZONTAL == orientation(parent) ? eachWidth * 2 : eachWidth;
        int top = RecyclerView.VERTICAL == orientation(parent) ? eachWidth * 2 : eachWidth;
        int right = RecyclerView.HORIZONTAL == orientation(parent) ? 0 : eachWidth;
        int bottom = RecyclerView.VERTICAL == orientation(parent) ? 0 : eachWidth;
        switch (mMode) {
            case VERTICAL:
                if (isFirstColumn) {
                    left = 0;
                }
                if (isLastColumn) {
                    right = 0;
                }
                result.set(left, 0, right, 0);
                break;
            case HORIZONTAL:
                if (isFirstRow) {
                    top = 0;
                }
                if (isLastRow) {
                    bottom = 0;
                }
                result.set(0, top, 0, bottom);
                break;
            case BOTH:
                if (isFirstColumn) {
                    left = 0;
                }
                if (isLastColumn) {
                    right = 0;
                }
                if (isFirstRow) {
                    top = 0;
                }
                if (isLastRow) {
                    bottom = 0;
                }
                result.set(left, top, right, bottom);
                break;
            default:
                result.set(0, 0, 0, 0);
        }

//        Log.e(TAG, "position:" + itemPosition + "," + result.toString());

        return result;
    }


    /**
     * 将列表item的位置装换为二维数组
     *
     * @param parent
     * @param spanCount
     * @param childCount
     * @return 每个元素为item的位置，之中这个元素上没有Item则值为-1
     */
    private List<List<Integer>> gridLayoutManagerItemPosition(RecyclerView parent, int spanCount, int childCount) {
        RecyclerView.LayoutManager layoutManager = parent.getLayoutManager();
        if (!(layoutManager instanceof GridLayoutManager))
            throw new IllegalArgumentException("recyclerView layoutManager no instanceof GridLayoutManager");
//        int lines = childCount % spanCount == 0 ? childCount / spanCount : childCount / spanCount + 1;
        List<List<Integer>> result = new ArrayList<>();
        GridLayoutManager.SpanSizeLookup spanSizeLookup = ((GridLayoutManager) layoutManager).getSpanSizeLookup();
        for (int i = 0; i < childCount; i++) {
            int spanSize = spanSizeLookup.getSpanSize(i);
            if (0 == result.size()) {
                result.add(new ArrayList<>());
            }
            if (result.get(result.size() - 1).size() >= spanCount) {
                result.add(new ArrayList<>());
            }
            if (result.get(result.size() - 1).size() + spanSize > spanCount) {
                List<Integer> integers = result.get(result.size() - 1);
                int size = integers.size();
                for (int j = 0; j < (spanCount - size); j++) {
                    integers.add(-1);
                }
                result.add(new ArrayList<>());
            }
            for (int j = 0; j < spanSize; j++) {
                if (result.get(result.size() - 1).size() >= spanCount) {
                    result.add(new ArrayList<>());
                }
                List<Integer> integers = result.get(result.size() - 1);
                integers.add(i);
            }
        }
        //将最后一行的数量补全
        if (result.get(result.size() - 1).size() < spanCount) {
            int addItemNumber = spanCount - result.get(result.size() - 1).size();
            for (int j = 0; j < (spanCount - addItemNumber); j++) {
                result.get(result.size() - 1).add(-1);
            }
        }

//        Log.e(TAG, result.toString());
        return result;
    }

    /**
     * 判断是否在第一行
     *
     * @param parent
     * @param pos
     * @param spanCount
     * @param childCount
     * @return
     */
    private boolean isFirstRow(RecyclerView parent, int pos, int spanCount, int childCount) {
        RecyclerView.LayoutManager layoutManager = parent.getLayoutManager();
        if (layoutManager instanceof GridLayoutManager) {
            List<List<Integer>> gridLayoutManagerItemPosition = gridLayoutManagerItemPosition(parent, spanCount, childCount);
            if (GridLayoutManager.VERTICAL == ((GridLayoutManager) layoutManager).getOrientation()) {
                return pos <= gridLayoutManagerItemPosition.get(0).get(gridLayoutManagerItemPosition.get(0).size() - 1);
            } else {
                List<Integer> locationItemRow = locationItemRow(gridLayoutManagerItemPosition, spanCount, pos);
                return !locationItemRow.isEmpty() && pos == locationItemRow.get(0);
            }
        } else if (layoutManager instanceof StaggeredGridLayoutManager) {
            int orientation = ((StaggeredGridLayoutManager) layoutManager).getOrientation();
            // StaggeredGridLayoutManager 且纵向滚动
            if (orientation == StaggeredGridLayoutManager.VERTICAL) {
                // 如果是第一行，则不需要绘制顶部
                return spanCount > pos;
            } else {
                // 如果是第一行，则不需要绘制顶部
                return 0 == pos % spanCount;
            }
        }
        return false;
    }

    /**
     * 判断是否是最后一行
     *
     * @param parent
     * @param pos
     * @param spanCount
     * @param childCount
     * @return
     */
    private boolean isLastRow(RecyclerView parent, int pos, int spanCount, int childCount) {
        RecyclerView.LayoutManager layoutManager = parent.getLayoutManager();
        if (layoutManager instanceof GridLayoutManager) {
            List<List<Integer>> gridLayoutManagerItemPosition = gridLayoutManagerItemPosition(parent, spanCount, childCount);
            if (GridLayoutManager.VERTICAL == ((GridLayoutManager) layoutManager).getOrientation()) {
//                int lines = childCount % spanCount == 0 ? childCount / spanCount : childCount / spanCount + 1;
                if (spanCount < childCount - pos) return false;
                for (int i = 0; i < gridLayoutManagerItemPosition.get(gridLayoutManagerItemPosition.size() - 1).size(); i++) {
                    if (pos == gridLayoutManagerItemPosition.get(gridLayoutManagerItemPosition.size() - 1).get(i)) {
                        return true;
                    }
                }
                return false;
            } else {
                List<Integer> locationItemRow = locationItemRow(gridLayoutManagerItemPosition, spanCount, pos);
                //判断是否是最后一行
                return spanCount == locationItemRow.size() && pos == locationItemRow.get(locationItemRow.size() - 1);
            }
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

    /**
     * 判断是否是第一列
     *
     * @param parent
     * @param pos
     * @param spanCount
     * @param childCount
     * @return
     */
    private boolean isFirstColumn(RecyclerView parent, int pos, int spanCount, int childCount) {
        RecyclerView.LayoutManager layoutManager = parent.getLayoutManager();
        if (layoutManager instanceof GridLayoutManager) {
            List<List<Integer>> gridLayoutManagerItemPosition = gridLayoutManagerItemPosition(parent, spanCount, childCount);
            if (GridLayoutManager.HORIZONTAL == ((GridLayoutManager) layoutManager).getOrientation()) {
                return pos <= gridLayoutManagerItemPosition.get(0).get(gridLayoutManagerItemPosition.get(0).size() - 1);
            } else {
                List<Integer> locationItemRow = locationItemRow(gridLayoutManagerItemPosition, spanCount, pos);
                return !locationItemRow.isEmpty() && pos == locationItemRow.get(0);
            }
        } else if (layoutManager instanceof StaggeredGridLayoutManager) {
            int orientation = ((StaggeredGridLayoutManager) layoutManager).getOrientation();
            if (orientation == StaggeredGridLayoutManager.VERTICAL) {
                return 0 == pos / spanCount;
            } else {
                return spanCount > pos;
            }
        }
        return false;
    }

    /**
     * 判断是否是最后一列
     *
     * @param parent
     * @param pos
     * @param spanCount
     * @param childCount
     * @return
     */
    private boolean isLastColumn(RecyclerView parent, int pos, int spanCount, int childCount) {
        RecyclerView.LayoutManager layoutManager = parent.getLayoutManager();
        if (layoutManager instanceof GridLayoutManager) {
            // 如果是最后一列，则不需要绘制右边
//            if ((pos + 1) % spanCount == 0) return true;
            List<List<Integer>> gridLayoutManagerItemPosition = gridLayoutManagerItemPosition(parent, spanCount, childCount);
            if (GridLayoutManager.HORIZONTAL == ((GridLayoutManager) layoutManager).getOrientation()) {
//                int lines = childCount % spanCount == 0 ? childCount / spanCount : childCount / spanCount + 1;
                if (spanCount < childCount - pos) return false;
                for (int i = 0; i < gridLayoutManagerItemPosition.get(gridLayoutManagerItemPosition.size() - 1).size(); i++) {
                    if (pos == gridLayoutManagerItemPosition.get(gridLayoutManagerItemPosition.size() - 1).get(i)) {
                        return true;
                    }
                }
                return false;
            } else {
                List<Integer> locationItemRow = locationItemRow(gridLayoutManagerItemPosition, spanCount, pos);
                //判断是否是最后一列
                return spanCount == locationItemRow.size() && pos == locationItemRow.get(locationItemRow.size() - 1);
            }

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

    /**
     * 获取item所在的行
     *
     * @param items     item布局转换成的数组
     * @param spanCount 一行Item的数量
     * @param position  item所在的位置
     * @return item所在的行, 没找到返回空数组
     */
    private List<Integer> locationItemRow(List<List<Integer>> items, int spanCount, int position) {
        int row = position / spanCount;
        if (items.size() <= row || 0 > row) return new ArrayList<>();
        do {
            List<Integer> lastRowItems = 0 < row ? items.get(row - 1) : new ArrayList<>();
            List<Integer> rowItems = items.get(row);
            List<Integer> nextRowItems = 1 < items.size() - row ? items.get(row + 1) : new ArrayList<>();
            if (rowItems.get(0) <= position) {
                if (!nextRowItems.isEmpty() && nextRowItems.get(0) <= position) {
                    row++;
                } else {
                    break;
                }
            } else {
                row--;
                if (!lastRowItems.isEmpty() && rowItems.get(0) > position) {
                    row--;
                } else {
                    break;
                }
            }
        } while (0 <= row && row < items.size());
        if (0 <= row && row < items.size()) {
            return items.get(row);
        } else {
            return new ArrayList<>();
        }
    }

    private int getSpanCount(RecyclerView parent) {
        // 列数
        int spanCount = 1;
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
//            RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) child.getLayoutParams();
            int itemPosition = parent.getChildLayoutPosition(child);
            Rect itemOffsets = itemOffsets(parent, itemPosition);
            int itemLeft = child.getLeft();
            int itemTop = child.getTop();
            int itemRight = child.getRight();
            int itemBottom = child.getBottom();
            //防止Item交界处出现空白,线的顶部位置为（itemTop-itemOffsets.top）,线的底部位置为（itemBottom+itemOffsets.bottom）
            //绘制Item左边的线
            if (0 != itemOffsets.left)
                c.drawRect(itemLeft - itemOffsets.left, itemTop - itemOffsets.top, itemLeft, itemBottom + itemOffsets.bottom, paint);
            //绘制Item右边的线
            if (0 != itemOffsets.right)
                c.drawRect(itemRight, itemTop - itemOffsets.top, itemRight + itemOffsets.right, itemBottom + itemOffsets.bottom, paint);
        }
    }

    private int orientation(RecyclerView recyclerView) {
        int result = RecyclerView.VERTICAL;
        RecyclerView.LayoutManager layoutManager = recyclerView.getLayoutManager();
        if (layoutManager instanceof LinearLayoutManager) {
            result = ((LinearLayoutManager) layoutManager).getOrientation();
        } else if (layoutManager instanceof StaggeredGridLayoutManager) {
            result = ((StaggeredGridLayoutManager) layoutManager).getOrientation();
        }
        return result;
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
//            RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) child.getLayoutParams();
            int itemPosition = parent.getChildLayoutPosition(child);
            Rect itemOffsets = itemOffsets(parent, itemPosition);
            int itemLeft = child.getLeft();
            int itemTop = child.getTop();
            int itemRight = child.getRight();
            int itemBottom = child.getBottom();

//            Log.e(TAG, "position:" + itemPosition + "," + itemOffsets.toString());
            //防止Item交界处出现空白,线的左边位置为（itemLeft-itemOffsets.left）,线的右边位置为（itemRight+itemOffsets.right）
            //绘制Items顶部的线
            if (0 != itemOffsets.top) {
//                Log.e(TAG, "position:" + itemPosition + ",顶部的线绘制了,高度为" + itemOffsets.top);
                c.drawRect(itemLeft - itemOffsets.left, itemTop - itemOffsets.top, itemRight + itemOffsets.right, itemTop, paint);
            }
            //绘制Item底部的线
            if (0 != itemOffsets.bottom) {
//                Log.e(TAG, "position:" + itemPosition + ",底部的线绘制了,高度为" + itemOffsets.bottom);
                c.drawRect(itemLeft - itemOffsets.left, itemBottom, itemRight + itemOffsets.right, itemBottom + itemOffsets.bottom, paint);
            }
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
