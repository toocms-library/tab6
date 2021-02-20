package com.toocms.tab.widget.tagflowlayout.tags;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;

import java.util.Random;

public class ColorfulTagView extends DefaultTagView {

    public ColorfulTagView(Context context) {
        super(context);
    }

    public ColorfulTagView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ColorfulTagView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected int getNormalBackgroundColor() {
        Random random = new Random();
        int red = random.nextInt(200) + 50;
        int green = random.nextInt(200) + 50;
        int blue = random.nextInt(200) + 50;
        return Color.rgb(red, green, blue);
    }
}
