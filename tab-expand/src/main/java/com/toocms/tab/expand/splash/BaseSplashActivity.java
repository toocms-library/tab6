package com.toocms.tab.expand.splash;

import android.os.Bundle;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.FrameLayout;
import android.widget.FrameLayout.LayoutParams;

import com.blankj.utilcode.util.SPUtils;
import com.toocms.tab.base.BaseActivity;

/**
 * Author：Zero
 * Date：2020/10/31 16:49
 */
public abstract class BaseSplashActivity extends BaseActivity {

    private static final String IS_FIRST_OPEN = "IS_FIRST_OPEN";
    /**
     * 默认启动页过渡时间
     */
    private static final int DEFAULT_SPLASH_DURATION_MILLIS = 1000;

    protected FrameLayout mWelcomeLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
        initSplashView(getSplashImageResId());
        onActivityCreated();
    }

    private void initView() {
        mWelcomeLayout = new FrameLayout(this);
        LayoutParams params = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
        mWelcomeLayout.setLayoutParams(params);
        setContentView(mWelcomeLayout);
    }

    /**
     * 初始化启动界面
     *
     * @param splashImgResId 背景资源图片资源ID
     */
    protected void initSplashView(int splashImgResId) {
        if (splashImgResId != 0) {
            mWelcomeLayout.setBackgroundResource(splashImgResId);
        }
    }

    /**
     * activity启动后的初始化
     */
    protected abstract void onActivityCreated();

    /**
     * 启动页结束后的动作
     */
    protected abstract void onSplashFinished();

    /**
     * 初始化启动界面背景图片
     *
     * @return 背景图片资源ID
     */
    protected abstract int getSplashImageResId();

    /**
     * @return 启动页持续的时间
     */
    protected long getSplashDurationMillis() {
        return DEFAULT_SPLASH_DURATION_MILLIS;
    }

    /**
     * 是否是第一次启动
     */
    public boolean isFirstOpen() {
        return SPUtils.getInstance().getBoolean(IS_FIRST_OPEN, true);
    }

    /**
     * 设置是否是第一次启动
     */
    public void setIsFirstOpen(boolean isFirstOpen) {
        SPUtils.getInstance().put(IS_FIRST_OPEN, isFirstOpen);
    }

    /**
     * 开启过渡
     *
     * @param enableAlphaAnim 是否启用渐近动画
     */
    protected void startSplash(boolean enableAlphaAnim) {
        if (enableAlphaAnim) {
            startSplashAnim(new AlphaAnimation(0.2F, 1.0F));
        } else {
            startSplashAnim(new AlphaAnimation(1.0F, 1.0F));
        }
    }

    /**
     * 开启引导过渡动画
     *
     * @param anim
     */
    private void startSplashAnim(Animation anim) {
        anim.setDuration(getSplashDurationMillis());
        anim.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
            }

            @Override
            public void onAnimationRepeat(Animation animation) {
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                onSplashFinished();
            }
        });
        mWelcomeLayout.startAnimation(anim);
    }
}
