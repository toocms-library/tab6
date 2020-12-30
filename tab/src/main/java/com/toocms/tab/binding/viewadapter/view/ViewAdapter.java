package com.toocms.tab.binding.viewadapter.view;

import android.annotation.SuppressLint;
import android.view.View;

import androidx.databinding.BindingAdapter;

import com.jakewharton.rxbinding4.view.RxView;
import com.toocms.tab.binding.command.BindingCommand;

import java.util.concurrent.TimeUnit;

public class ViewAdapter {

    //防重复点击间隔(秒)
    public static final int CLICK_INTERVAL = 1;

    /**
     * 点击事件绑定
     *
     * @param view
     * @param clickCommand    绑定的命令
     * @param isThrottleFirst 是否开启防重复点击
     */
    @SuppressLint("CheckResult")
    @BindingAdapter(value = {"onClickCommand", "isThrottleFirst"}, requireAll = false)
    public static void onClickCommand(View view, final BindingCommand clickCommand, final boolean isThrottleFirst) {
        if (isThrottleFirst) {
            RxView.clicks(view)
                    .subscribe(object -> {
                        if (clickCommand != null) {
                            clickCommand.execute();
                        }
                    });
        } else {
            RxView.clicks(view)
                    .throttleFirst(CLICK_INTERVAL, TimeUnit.SECONDS)//1秒钟内只允许点击1次
                    .subscribe(object -> {
                        if (clickCommand != null) {
                            clickCommand.execute();
                        }
                    });
        }
    }

    /**
     * 长按事件绑定
     *
     * @param view
     * @param clickCommand
     */
    @SuppressLint("CheckResult")
    @BindingAdapter(value = {"onLongClickCommand"}, requireAll = false)
    public static void onLongClickCommand(View view, final BindingCommand clickCommand) {
        RxView.longClicks(view)
                .subscribe(object -> {
                    if (clickCommand != null) {
                        clickCommand.execute();
                    }
                });
    }

    /**
     * 回调控件本身
     *
     * @param currentView
     * @param bindingCommand
     */
    @BindingAdapter(value = {"currentView"}, requireAll = false)
    public static void replyCurrentView(View currentView, BindingCommand bindingCommand) {
        if (bindingCommand != null) {
            bindingCommand.execute(currentView);
        }
    }

    /**
     * view是否需要获取焦点
     *
     * @param view
     * @param needRequestFocus true or false
     */
    @BindingAdapter({"requestFocus"})
    public static void requestFocusCommand(View view, final Boolean needRequestFocus) {
        if (needRequestFocus) {
            view.setFocusableInTouchMode(true);
            view.requestFocus();
        } else {
            view.clearFocus();
        }
    }

    /**
     * view的焦点发生变化的事件绑定
     *
     * @param view
     * @param onFocusChangeCommand
     */
    @BindingAdapter({"onFocusChangeCommand"})
    public static void onFocusChangeCommand(View view, final BindingCommand<Boolean> onFocusChangeCommand) {
        view.setOnFocusChangeListener((v, hasFocus) -> {
            if (onFocusChangeCommand != null) {
                onFocusChangeCommand.execute(hasFocus);
            }
        });
    }

    /**
     * view的显示隐藏
     *
     * @param view
     * @param visibility
     */
    @BindingAdapter(value = {"isVisible"}, requireAll = false)
    public static void isVisible(View view, final Boolean visibility) {
        if (visibility) {
            view.setVisibility(View.VISIBLE);
        } else {
            view.setVisibility(View.GONE);
        }
    }

//    @BindingAdapter({"onTouchCommand"})
//    public static void onTouchCommand(View view, final ResponseCommand<MotionEvent, Boolean> onTouchCommand) {
//        view.setOnTouchListener(new View.OnTouchListener() {
//            @Override
//            public boolean onTouch(View v, MotionEvent event) {
//                if (onTouchCommand != null) {
//                    return onTouchCommand.execute(event);
//                }
//                return false;
//            }
//        });
//    }
}
