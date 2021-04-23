package com.toocms.tab.base;

import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;

import com.blankj.utilcode.util.KeyboardUtils;
import com.qmuiteam.qmui.arch.QMUIFragmentActivity;

public abstract class BaseActivity extends QMUIFragmentActivity {

    private boolean isEnableHideSoftInput;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        super.onCreate(savedInstanceState);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
    }

    /**
     * 点击空白处隐藏键盘
     */
    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        if (isEnableHideSoftInput())
            if (event.getAction() == MotionEvent.ACTION_DOWN) {
                View v = getCurrentFocus();
                if (isShouldHideKeyboard(v, event)) {
                    KeyboardUtils.hideSoftInput(this);
                }
            }
        return super.dispatchTouchEvent(event);
    }

    private boolean isShouldHideKeyboard(View v, MotionEvent event) {
        if ((v instanceof EditText)) {
            int[] l = {0, 0};
            v.getLocationOnScreen(l);
            int left = l[0],
                    top = l[1],
                    bottom = top + v.getHeight(),
                    right = left + v.getWidth();
            return !(event.getRawX() > left && event.getRawX() < right
                    && event.getRawY() > top && event.getRawY() < bottom);
        }
        return false;
    }

    public boolean isEnableHideSoftInput() {
        return isEnableHideSoftInput;
    }

    public void setEnableHideSoftInput(boolean enableHideSoftInput) {
        isEnableHideSoftInput = enableHideSoftInput;
    }
}
