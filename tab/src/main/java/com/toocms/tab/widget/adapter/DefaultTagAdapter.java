package com.toocms.tab.widget.adapter;

import android.view.View;
import android.view.ViewGroup;

import com.toocms.tab.widget.tagflowlayout.TagAdapter;
import com.toocms.tab.widget.tagflowlayout.bean.TagBean;
import com.toocms.tab.widget.tagflowlayout.tags.DefaultTagView;

public class DefaultTagAdapter extends TagAdapter<TagBean> {

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        DefaultTagView textView = new DefaultTagView(parent.getContext());
        textView.setText(getItem(position).getName());
        return textView;
    }
}