package com.toocms.sample.ui.tool.user;

import androidx.lifecycle.ViewModelProvider;

import com.toocms.sample.BR;
import com.toocms.sample.R;
import com.toocms.sample.data.AppViewModelFactory;
import com.toocms.sample.databinding.FgtUserBinding;
import com.toocms.sample.ui.base.BaseFgt;
import com.toocms.tab.TooCMSApplication;

/**
 * Author：Zero
 * Date：2020/11/5 17:27
 */
public class UserFgt extends BaseFgt<FgtUserBinding, UserViewModel> {

    @Override
    protected void onFragmentCreated() {
        topBar.setTitle("用户信息");
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.fgt_user;
    }

    @Override
    public int getVariableId() {
        return BR.userViewModel;
    }

    @Override
    protected UserViewModel getViewModel() {
        AppViewModelFactory factory = AppViewModelFactory.getInstance(TooCMSApplication.getInstance());
        return new ViewModelProvider(this, factory).get(UserViewModel.class);
    }

    @Override
    protected void viewObserver() {
    }
}
