package com.toocms.tab.push.activity;

import android.content.Intent;
import android.os.Bundle;

import com.blankj.utilcode.util.ActivityUtils;
import com.blankj.utilcode.util.IntentUtils;
import com.umeng.message.UmengNotifyClickActivity;

import org.android.agoo.common.AgooConstants;

import static com.qmuiteam.qmui.arch.QMUIFragmentActivity.QMUI_INTENT_FRAGMENT_ARG;

/**
 * Author：Zero
 * Date：2021/4/30
 */
public class TooCMSNotifyClickActivity extends UmengNotifyClickActivity {

    @Override
    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
    }

    @Override
    public void onMessage(Intent intent) {
        super.onMessage(intent);
        Bundle bundle = new Bundle();
        bundle.putString(AgooConstants.MESSAGE_BODY, intent.getStringExtra(AgooConstants.MESSAGE_BODY));
        Intent newIntent = IntentUtils.getComponentIntent(getPackageName(), ActivityUtils.getLauncherActivity());
        newIntent.putExtra(QMUI_INTENT_FRAGMENT_ARG, bundle);
        ActivityUtils.startActivity(newIntent);
        finish();
    }
}
