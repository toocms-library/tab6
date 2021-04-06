package com.toocms.tab.widget.picker.utils;

import android.view.Gravity;

import com.toocms.tab.R;

/**
 * 选择器动画
 */
public class PickerViewAnimateUtils {

    private static final int INVALID = -1;

    private PickerViewAnimateUtils() {
        throw new UnsupportedOperationException("u can't instantiate me...");
    }

    /**
     * Get default animation resource when not defined by the user
     *
     * @param gravity       the animGravity of the dialog
     * @param isInAnimation determine if is in or out animation. true when is is
     * @return the id of the animation resource
     */
    public static int getAnimationResource(int gravity, boolean isInAnimation) {
        switch (gravity) {
            case Gravity.BOTTOM:
                return isInAnimation ? R.anim.picker_view_slide_in_bottom : R.anim.picker_view_slide_out_bottom;
            default:
                break;
        }
        return INVALID;
    }
}
