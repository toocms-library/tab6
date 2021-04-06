package com.toocms.tab.widget.wheelview.listener;

import android.view.GestureDetector;
import android.view.MotionEvent;

import com.toocms.tab.widget.wheelview.WheelView;

/**
 * 手势监听
 */
public final class LoopViewGestureListener extends GestureDetector.SimpleOnGestureListener {

    private final WheelView wheelView;

    public LoopViewGestureListener(WheelView wheelView) {
        this.wheelView = wheelView;
    }

    @Override
    public final boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
        wheelView.scrollBy(velocityY);
        return true;
    }
}
