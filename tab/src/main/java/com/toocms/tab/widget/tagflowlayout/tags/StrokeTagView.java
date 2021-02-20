package com.toocms.tab.widget.tagflowlayout.tags;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;

public class StrokeTagView extends DefaultTagView {

    public StrokeTagView(Context context) {
        super(context);
    }

    public StrokeTagView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public StrokeTagView(Context context, AttributeSet attrs, int defStyleAttr) {
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

    @Override
    protected int getNormalBackgroundColor() {
        return Color.parseColor("#000000");
    }

    @Override
    protected int getPressedBackgroundColor() {
        return Color.parseColor("#aa666666");
    }
}
