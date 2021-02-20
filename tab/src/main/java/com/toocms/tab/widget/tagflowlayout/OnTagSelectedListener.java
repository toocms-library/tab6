package com.toocms.tab.widget.tagflowlayout;

import android.view.View;

import com.toocms.tab.widget.tagflowlayout.bean.TagBean;

import java.util.List;

/**
 * 标签被选中或取消选中监听
 */
public interface OnTagSelectedListener {

    void selected(View view, int position, List<TagBean> selected);

    void unSelected(View view, int position, List<TagBean> selected);
}
