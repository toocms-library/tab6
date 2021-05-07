package com.toocms.sample.ui.expand.splash;

import com.qmuiteam.qmui.util.QMUIStatusBarHelper;
import com.toocms.sample.R;
import com.toocms.sample.ui.MainActivity;
import com.toocms.sample.ui.expand.guide.GuideAty;
import com.toocms.tab.expand.splash.BaseSplashActivity;
import com.toocms.tab.push.TabPush;

/**
 * 启动页
 * Author：Zero
 * Date：2020/11/9 15:25
 */
public class SplashAty extends BaseSplashActivity {

    @Override
    protected void onActivityCreated() {
        QMUIStatusBarHelper.setStatusBarLightMode(this);
        startSplash(true);
    }

    @Override
    protected void onSplashFinished() {
//        // 判断是否为首次打开
//        if (isFirstOpen()) {
//            setIsFirstOpen(false);
//            TabPush.getInstance().startActivity(this, GuideAty.class);
//        } else {
//            TabPush.getInstance().startActivity(this, MainActivity.class);
//        }
        finish();
    }

    @Override
    protected int getSplashImageResId() {
        return R.drawable.qidong;
    }
}
