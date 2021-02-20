package com.toocms.tab.widget.tagflowlayout;

import android.content.Context;
import android.content.res.TypedArray;
import android.database.DataSetObserver;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.ColorInt;
import androidx.annotation.IntDef;

import com.blankj.utilcode.util.ConvertUtils;
import com.toocms.tab.R;
import com.toocms.tab.widget.adapter.DefaultTagAdapter;
import com.toocms.tab.widget.tagflowlayout.tags.DefaultTagView;
import com.toocms.tab.widget.tagflowlayout.tags.MutSelectedTagView;
import com.toocms.tab.widget.tagflowlayout.view.FlowLayout;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * 可折叠和展开的标签控件
 */
public class TagFlowLayout extends androidx.core.widget.NestedScrollView {

    // 默认标签之间的间距
    private static final float DEFAULT_TAGS_SPACE = 15;
    // 默认控件背景颜色
    private static final int DEFAULT_LAYOUT_BACKGROUND_COLOR = 0Xdef2f2f2;
//    // 默认标签字号
//    private static final int DEFAULT_TAGS_TEXT_SIZE = 15;
//    // 默认标签文字颜色
//    private static final int DEFAULT_TAGS_TEXT_COLOR = 0XFF333333;
//    // 默认标签背景正常颜色
//    private static final int DEFAULT_TAGS_BACKGROUND_COLOR = 0XFFF5F5F5;
//    // 默认标签背景按下颜色
//    private static final int DEFAULT_TAGS_BACKGROUND_PRESSED_COLOR = 0XFF97445C;
//    // 默认标签圆角大小
//    private static final int DEFAULT_TAGS_BACKGROUND_CORNERS = 8;

    @IntDef({ITEM_MODEL_CLICK, ITEM_MODEL_SELECT})
    @Retention(RetentionPolicy.SOURCE)
    public @interface ItemMode {
    }

    public static final int ITEM_MODEL_CLICK = 100000;
    public static final int ITEM_MODEL_SELECT = 100001;
    private Context mContext;

    //控件背景颜色
    private int backGroundColor;
    //标签之间的横向间距
    private int tagsHorizontalSpace;
    //标签之间的纵向间距
    private int tagsVerticalSpace;
//    // 标签字号
//    private int tagsTextSize;
//    // 标签文字颜色
//    private int tagsTextColor;
//    // 标签背景正常颜色
//    private int tagsBackgroundColor;
//    // 标签背景按下颜色
//    private int tagsBackgroundPressedColor;
//    // 标签圆角大小
//    private int tagsBackgroundCorners;
    //标签模式，点击模式（默认），选中模式
    @ItemMode
    private int itemModel;

    //点击监听事件
    private OnTagClickListener mListener;
    private OnTagSelectedListener mSelectedListener;
    private AdapterDataSetObserver mDataSetObserver;
    private TagAdapter mTagAdapter;
    private FlowLayout mLayout;

    public TagFlowLayout(Context context) {
        this(context, null);
    }

    public TagFlowLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public TagFlowLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.mContext = context;
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.TagFlowLayout);
        backGroundColor = a.getColor(R.styleable.TagFlowLayout_backGroundColor, DEFAULT_LAYOUT_BACKGROUND_COLOR);
        tagsHorizontalSpace = (int) a.getDimension(R.styleable.TagFlowLayout_tagsHorizontalSpace, ConvertUtils.dp2px(DEFAULT_TAGS_SPACE));
        tagsVerticalSpace = (int) a.getDimension(R.styleable.TagFlowLayout_tagsVerticalSpace, ConvertUtils.dp2px(DEFAULT_TAGS_SPACE));
//        tagsTextSize = (int) a.getDimension(R.styleable.TagFlowLayout_tagsTextSize, ConvertUtils.dp2px(DEFAULT_TAGS_TEXT_SIZE));
//        tagsTextColor = (int) a.getColor(R.styleable.TagFlowLayout_tagsTextColor, DEFAULT_TAGS_TEXT_COLOR);
//        tagsBackgroundColor = (int) a.getColor(R.styleable.TagFlowLayout_tagsBackgroundColor, DEFAULT_TAGS_BACKGROUND_COLOR);
//        tagsBackgroundPressedColor = (int) a.getColor(R.styleable.TagFlowLayout_tagsBackgroundPressedColor, DEFAULT_TAGS_BACKGROUND_PRESSED_COLOR);
//        tagsBackgroundCorners = (int) a.getDimension(R.styleable.TagFlowLayout_tagsBackgroundCorners, ConvertUtils.dp2px(DEFAULT_TAGS_BACKGROUND_CORNERS));
        itemModel = ITEM_MODEL_CLICK;
        a.recycle();
        init();
    }

    private void init() {
        mLayout = new FlowLayout(mContext);
        removeAllViews();
        addView(mLayout);
        mLayout.setSpace(tagsHorizontalSpace, tagsVerticalSpace);
    }

    private void reloadData() {
        if (mTagAdapter == null || mTagAdapter.getCount() == 0) {
            return;
        }
        mLayout.clearAllView();
        for (int i = 0; i < mTagAdapter.getCount(); i++) {
            final View view = mTagAdapter.getView(i, null, mLayout);
            final int j = i;
            if (itemModel == ITEM_MODEL_CLICK && mListener != null) {
                view.setOnClickListener(v -> mListener.onClick(v, j));
                view.setOnLongClickListener(v -> {
                    mListener.onLongClick(v, j);
                    return true;
                });
            } else if (itemModel == ITEM_MODEL_SELECT && mSelectedListener != null) {
                view.setOnClickListener(v -> {
                    if (v instanceof MutSelectedTagView) {
                        if (!v.isSelected()) {
                            mTagAdapter.select(j);
                            mSelectedListener.selected(v, j, mTagAdapter.getTagBeans());
                        } else {
                            mTagAdapter.unSelect(j);
                            mSelectedListener.unSelected(v, j, mTagAdapter.getTagBeans());
                        }
                        ((MutSelectedTagView) v).toggle();
                    }
                });
            }
            mLayout.addView(view);
        }
    }

    public TagAdapter getTagAdapter() {
        return mTagAdapter;
    }

    public void setTagAdapter(TagAdapter mTagAdapter) {
        if (mTagAdapter != null && mDataSetObserver != null) {
            this.mTagAdapter.unregisterDataSetObserver(mDataSetObserver);
        }
        this.mTagAdapter = mTagAdapter;
        if (this.mTagAdapter != null) {
            mDataSetObserver = new AdapterDataSetObserver();
            this.mTagAdapter.registerDataSetObserver(mDataSetObserver);
            reloadData();
        }
    }

    public int getBackGroundColor() {
        return backGroundColor;
    }

    public void setBackGroundColor(@ColorInt int backGroundColor) {
        this.backGroundColor = backGroundColor;
    }

    public int getTagsHorizontalSpace() {
        return tagsHorizontalSpace;
    }

    public void setTagsHorizontalSpace(int tagsHorizontalSpace) {
        this.tagsHorizontalSpace = tagsHorizontalSpace;
    }

    public int getTagsVerticalSpace() {
        return tagsVerticalSpace;
    }

    public void setTagsVerticalSpace(int tagsVerticalSpace) {
        this.tagsVerticalSpace = tagsVerticalSpace;
    }

    public int getItemModel() {
        return itemModel;
    }

    public void setItemModel(@ItemMode int itemModel) {
        this.itemModel = itemModel;
        reloadData();
    }

    public OnTagClickListener getTagListener() {
        return mListener;
    }

    public void setTagListener(OnTagClickListener mListener) {
        this.mListener = mListener;
        if (mTagAdapter != null) {
            reloadData();
        }
    }

    public OnTagSelectedListener getSelectedListener() {
        return mSelectedListener;
    }

    public void setSelectedListener(OnTagSelectedListener mSelectedListener) {
        this.mSelectedListener = mSelectedListener;
        if (mTagAdapter != null) {
            reloadData();
        }
    }

    class AdapterDataSetObserver extends DataSetObserver {
        @Override
        public void onChanged() {
            super.onChanged();
            reloadData();
        }

        @Override
        public void onInvalidated() {
            super.onInvalidated();
        }
    }
}
