package com.toocms.tab.widget.tagflowlayout.tags;

import android.content.Context;
import android.util.AttributeSet;

public class ColorfulStrokeTagView extends ColorfulTagView {

    public ColorfulStrokeTagView(Context context) {
        super(context);
    }

    public ColorfulStrokeTagView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ColorfulStrokeTagView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected boolean isSolid() {
        return false;
    }

    @Override
    protected float getStrokeWidth() {
        return 1;
    }
}
