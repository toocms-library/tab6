package com.toocms.tab.push.activity;

import android.os.Bundle;

import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.ReflectUtils;
import com.qmuiteam.qmui.arch.QMUIFragment;
import com.toocms.tab.base.BaseActivity;

/**
 * Author：Zero
 * Date：2021/5/22
 */
public class TooCMSPushContainerActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getIntent().getExtras();
        if (bundle == null) throw new IllegalArgumentException("未设置要跳转的Fragment类");
        Class<QMUIFragment> cls = (Class<QMUIFragment>) bundle.getSerializable("fragment");
        QMUIFragment fragment = ReflectUtils.reflect(cls).newInstance().get();
        LogUtils.e(getIntent().getExtras());
        fragment.setArguments(getIntent().getExtras());
        startFragment(fragment);
    }
}
