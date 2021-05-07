package com.toocms.sample.ui.expand.push;

import android.app.Application;

import androidx.annotation.NonNull;

import com.blankj.utilcode.util.LogUtils;
import com.toocms.tab.base.BaseViewModel;
import com.toocms.tab.binding.command.BindingCommand;
import com.toocms.tab.push.TabPush;
import com.umeng.message.IUmengCallback;

/**
 * Author：Zero
 * Date：2021/4/29
 */
public class PushViewModel extends BaseViewModel {

    public PushViewModel(@NonNull Application application) {
        super(application);
    }

    public BindingCommand enable = new BindingCommand(() -> TabPush.getInstance().enable(new IUmengCallback() {
        @Override
        public void onSuccess() {
            showToast("已打开推送");
        }

        @Override
        public void onFailure(String s, String s1) {

        }
    }));

    public BindingCommand disable = new BindingCommand(() -> TabPush.getInstance().disable(new IUmengCallback() {
        @Override
        public void onSuccess() {
            showToast("已关闭推送");
        }

        @Override
        public void onFailure(String s, String s1) {

        }
    }));

    public BindingCommand addAlias = new BindingCommand(() -> TabPush.getInstance().getAliasApi().addAlias("Zero", (b, s) -> LogUtils.e(b, s)));

    public BindingCommand setAlias = new BindingCommand(() -> TabPush.getInstance().getAliasApi().setAlias("Zero", (b, s) -> LogUtils.e(b, s)));

    public BindingCommand deleteAlias = new BindingCommand(() -> TabPush.getInstance().getAliasApi().deleteAlias("Zero", (b, s) -> LogUtils.e(b, s)));
}
