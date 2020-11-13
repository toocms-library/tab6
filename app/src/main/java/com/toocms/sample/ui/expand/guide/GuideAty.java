package com.toocms.sample.ui.expand.guide;

import android.app.Activity;

import com.toocms.sample.R;
import com.toocms.sample.ui.MainActivity;
import com.toocms.tab.expand.guide.BaseGuideActivity;

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
    protected Class<? extends Activity> getSkipClass() {
        return MainActivity.class;
    }
}
