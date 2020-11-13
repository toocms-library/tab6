package com.toocms.tab.expand.tabsegment;

import androidx.annotation.DrawableRes;

import com.toocms.tab.base.BaseFragment;

/**
 * BaseTabSegmentFragment配置实体类
 * <p>
 * Author：Zero
 * Date：2020/10/15 14:45
 */
public class TabSegmentItem {

    public TabSegmentItem(int normalResId, int selectedResId, String text, Class<? extends BaseFragment> cls) {
        this.normalResId = normalResId;
        this.selectedResId = selectedResId;
        this.text = text;
        this.cls = cls;
    }

    private @DrawableRes
    int normalResId;

    private @DrawableRes
    int selectedResId;

    private String text;

    private Class<? extends BaseFragment> cls;

    public int getNormalResId() {
        return normalResId;
    }

    public void setNormalResId(int normalResId) {
        this.normalResId = normalResId;
    }

    public int getSelectedResId() {
        return selectedResId;
    }

    public void setSelectedResId(int selectedResId) {
        this.selectedResId = selectedResId;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Class<? extends BaseFragment> getCls() {
        return cls;
    }

    public void setCls(Class<? extends BaseFragment> cls) {
        this.cls = cls;
    }
}
