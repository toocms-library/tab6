package com.toocms.tab.widget.navigation;

import android.content.Context;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.blankj.utilcode.util.CollectionUtils;
import com.blankj.utilcode.util.SizeUtils;
import com.toocms.tab.R;
import com.toocms.tab.imageload.ImageLoader;
import com.toocms.tab.widget.banner.base.BaseIndicatorBanner;

import java.util.List;

/**
 * Author：Zero
 * Date：2020/11/10 17:34
 */
public class FlipNavigationView extends BaseIndicatorBanner<NavigationItem[][], FlipNavigationView> {

    private static final int ITEM_HEIGHT = SizeUtils.dp2px(80);
    private static final int VERTICAL_SPACING = SizeUtils.dp2px(8);

    private OnItemClickListener listener;
    private List<NavigationItem> items;

    private int height;
    private int columnNum = 5;

    public FlipNavigationView(Context context) {
        super(context);
        onCreateNavigationView();
    }

    public FlipNavigationView(Context context, AttributeSet attrs) {
        super(context, attrs);
        onCreateNavigationView();
    }

    public FlipNavigationView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        onCreateNavigationView();
    }

    protected void onCreateNavigationView() {
        setBarShowWhenLast(true);
        setAutoScrollEnable(false);
    }

    @Override
    public View onCreateItemView(int position) {
        return createPageLayout(getItem(position));
    }

    public FlipNavigationView setColumnNum(int columnNum) {
        this.columnNum = columnNum;
        return this;
    }

    public FlipNavigationView setData(List<NavigationItem> items) {
        this.items = items;
        return this;
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    @Override
    public void startScroll() {
        resetData();
        setSource(formatPageData(items));
        getLayoutParams().height = height;
        super.startScroll();
    }

    private void resetData() {
        mDatas.clear();
        height = ITEM_HEIGHT + VERTICAL_SPACING * 2;
    }

    private List<NavigationItem[][]> formatPageData(List<NavigationItem> items) {
        int pageSize = columnNum * 2;
        int counts = CollectionUtils.size(items);
        int childPosition;
        if (counts < columnNum) {
            NavigationItem[][] navigationItems = new NavigationItem[1][counts];
            for (childPosition = 0; childPosition < counts; ++childPosition) {
                navigationItems[0][childPosition] = items.get(childPosition);
            }
            mDatas.add(navigationItems);
            setIndicatorShow(false);
        } else {
            height += ITEM_HEIGHT + VERTICAL_SPACING * 2;
            int pageCount = counts / pageSize;
            childPosition = -1;
            int remainder = counts % pageSize;
            if (remainder != 0) {
                childPosition = remainder / columnNum + (remainder % columnNum != 0 ? 1 : 0);
                ++pageCount;
            }
            if (pageCount <= 1) {
                setIndicatorShow(false);
            } else {
                setIndicatorShow(true);
                height += SizeUtils.dp2px(10);  // 指示器高度
            }
            int tempPosition;
            for (tempPosition = 0; tempPosition < pageCount; ++tempPosition) {
                int parentPosition;
                if (tempPosition == pageCount - 1 && childPosition != -1) {
                    parentPosition = childPosition;
                } else {
                    parentPosition = 2;
                }
                NavigationItem[][] navigationItems = new NavigationItem[parentPosition][columnNum];
                mDatas.add(navigationItems);
            }
            tempPosition = 0;
            for (int i = 0; i < CollectionUtils.size(mDatas); ++i) {
                NavigationItem[][] navigationItems = mDatas.get(i);
                int size = navigationItems.length;
                for (int j = 0; j < size; ++j) {
                    NavigationItem[] navigationItems1 = navigationItems[j];
                    for (int k = 0; k < navigationItems1.length; ++k) {
                        if (tempPosition < counts) {
                            navigationItems1[k] = items.get(tempPosition);
                        }
                        ++tempPosition;
                    }
                }
            }
        }
        return mDatas;
    }

    private View createPageLayout(NavigationItem[][] navigationItems) {
        LinearLayout layout = new LinearLayout(getContext());
        layout.setOrientation(LinearLayout.VERTICAL);
        layout.setGravity(Gravity.TOP);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        layout.setLayoutParams(params);
        for (int i = 0; i < navigationItems.length; ++i) {
            NavigationItem[] navigationItems1 = navigationItems[i];
            View view = createRowLayout(navigationItems1);
            layout.addView(view);
        }
        return layout;
    }

    private View createRowLayout(NavigationItem[] navigationItems) {
        LinearLayout layout = new LinearLayout(getContext());
        layout.setOrientation(LinearLayout.HORIZONTAL);
        layout.setGravity(Gravity.AXIS_SPECIFIED);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        params.topMargin = params.bottomMargin = VERTICAL_SPACING;
        layout.setLayoutParams(params);
        for (int i = 0; i < navigationItems.length; ++i) {
            View view = createNavigationItem(navigationItems[i]);
            layout.addView(view);
        }
        return layout;
    }

    private View createNavigationItem(NavigationItem navigationItem) {
        LinearLayout layout = new LinearLayout(getContext());
        layout.setOrientation(LinearLayout.VERTICAL);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT);
        params.weight = 1.0f;
        layout.setLayoutParams(params);
        layout.setGravity(Gravity.CENTER);
        if (navigationItem != null) {
            View view = LayoutInflater.from(getContext()).inflate(R.layout.item_navigation, null);
            ImageView imageView = view.findViewById(R.id.navigation_image_view);
            TextView textView = view.findViewById(R.id.navigation_text_view);
            textView.setText(navigationItem.getName());
            ImageLoader.loadUrl2Image(navigationItem.getIcon(), imageView, 0);
            view.setOnClickListener(v -> {
                if (listener != null) listener.onItemClick(navigationItem);
            });
            layout.addView(view);
        }
        return layout;
    }

    public interface OnItemClickListener {
        void onItemClick(NavigationItem item);
    }
}
