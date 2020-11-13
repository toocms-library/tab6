package com.toocms.tab.expand.tabsegment;

import android.view.View;

import com.blankj.utilcode.util.CollectionUtils;
import com.blankj.utilcode.util.ReflectUtils;
import com.blankj.utilcode.util.ResourceUtils;
import com.qmuiteam.qmui.arch.QMUIFragment;
import com.qmuiteam.qmui.arch.QMUIFragmentPagerAdapter;
import com.qmuiteam.qmui.widget.tab.QMUITab;
import com.qmuiteam.qmui.widget.tab.QMUITabBuilder;
import com.toocms.tab.base.BaseFragment;
import com.toocms.tab.base.BaseViewModel;
import com.toocms.tab.expand.R;
import com.toocms.tab.expand.databinding.BaseBottomTabSegmentBinding;

/**
 * Author：Zero
 * Date：2020/10/12 13:21
 */
public abstract class BaseBottomTabSegmentFragment extends BaseFragment<BaseBottomTabSegmentBinding, BaseViewModel> {

    private TabSegmentItem[] tabSegmentItems;

    @Override
    protected void onFragmentCreated() {
        topBar.setVisibility(View.GONE);
        tabSegmentItems = getTabSegmentItems();
        initTab();
        initPagers();
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.base_bottom_tab_segment;
    }

    @Override
    public int getVariableId() {
        return 0;
    }

    @Override
    protected void viewObserver() {
    }

    /**
     * 配置标签项
     *
     * @return
     */
    protected abstract TabSegmentItem[] getTabSegmentItems();

    /**
     * 是否可左右滑动
     *
     * @return
     */
    protected abstract boolean isSwipeable();

    private void initTab() {
        QMUITabBuilder builder = binding.tabSegment.tabBuilder();
        for (TabSegmentItem item : tabSegmentItems) {
            QMUITab tab = builder.setNormalDrawable(ResourceUtils.getDrawable(item.getNormalResId()))
                    .setSelectedDrawable(ResourceUtils.getDrawable(item.getSelectedResId()))
                    .setText(item.getText())
                    .build(getContext());
            binding.tabSegment.addTab(tab);
        }
    }

    private void initPagers() {
        QMUIFragmentPagerAdapter adapter = new QMUIFragmentPagerAdapter(getChildFragmentManager()) {

            @Override
            public QMUIFragment createFragment(int position) {
                return ReflectUtils.reflect(tabSegmentItems[position].getCls()).newInstance().get();
            }

            @Override
            public int getCount() {
                return CollectionUtils.size(tabSegmentItems);
            }
        };
        binding.viewPager.setSwipeable(isSwipeable());
        binding.viewPager.setAdapter(adapter);
        binding.tabSegment.setupWithViewPager(binding.viewPager, false);
    }
}
