package com.toocms.tab.binding.viewadapter.tagflowlayout;

import androidx.databinding.BindingAdapter;

import com.toocms.tab.widget.adapter.DefaultTagAdapter;
import com.toocms.tab.widget.tagflowlayout.OnTagClickListener;
import com.toocms.tab.widget.tagflowlayout.OnTagSelectedListener;
import com.toocms.tab.widget.tagflowlayout.TagFlowLayout;
import com.toocms.tab.widget.tagflowlayout.bean.TagBean;

import java.util.List;

public class ViewAdapter {

    @BindingAdapter(value = {"items", "adapter", "onTagClickListener", "onTagSelectedListener"}, requireAll = false)
    public static void setAdapter(TagFlowLayout tagFlowLayout,
                                  List<TagBean> items,
                                  DefaultTagAdapter adapter,
                                  OnTagClickListener onTagClickListener,
                                  OnTagSelectedListener onTagSelectedListener) {
        if (onTagClickListener != null)
            tagFlowLayout.setTagListener(onTagClickListener);
        if (onTagSelectedListener != null)
            tagFlowLayout.setSelectedListener(onTagSelectedListener);

        DefaultTagAdapter oldAdapter = (DefaultTagAdapter) tagFlowLayout.getTagAdapter();
        if (adapter == null) {
            if (oldAdapter == null) {
                adapter = new DefaultTagAdapter();
            } else {
                adapter = oldAdapter;
            }
        }
        adapter.addAllTags(items);

        if (oldAdapter != adapter) {
            tagFlowLayout.setTagAdapter(adapter);
        }
    }
}
