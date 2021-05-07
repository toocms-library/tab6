package com.toocms.sample.ui.expand.guide;

import com.toocms.sample.R;
import com.toocms.sample.ui.MainActivity;
import com.toocms.tab.expand.guide.BaseGuideActivity;
import com.toocms.tab.push.TabPush;

/**
 * 引导页
 * Author：Zero
 * Date：2020/11/9 16:06
 */
public class GuideAty extends BaseGuideActivity {

    @Override
    protected Object[] getGuideResourceList() {
        return new Object[]{
                R.drawable.guide1,
                R.drawable.guide2,
                R.drawable.guide3
        };
    }

    @Override
    public void onJumpClick() {
//        TabPush.getInstance().startActivity(this, MainActivity.class);
        finish();
    }
}
