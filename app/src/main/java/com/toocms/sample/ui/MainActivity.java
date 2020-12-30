package com.toocms.sample.ui;

import android.content.Intent;

import com.qmuiteam.qmui.arch.annotation.DefaultFirstFragment;
import com.toocms.tab.base.BaseActivity;
import com.umeng.socialize.UMShareAPI;

@DefaultFirstFragment(MainFgt.class)
public class MainActivity extends BaseActivity {

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // 分享回调
        UMShareAPI.get(this).onActivityResult(requestCode, resultCode, data);
    }
}
