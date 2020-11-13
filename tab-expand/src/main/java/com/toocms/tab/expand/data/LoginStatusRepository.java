package com.toocms.tab.expand.data;

import com.blankj.utilcode.util.ArrayUtils;
import com.blankj.utilcode.util.SPUtils;
import com.toocms.tab.base.BaseModel;
import com.toocms.tab.binding.command.BindingAction;

/**
 * Author：Zero
 * Date：2020/11/5 16:37
 */
public class LoginStatusRepository extends BaseModel {

    private static final String SP_NAME_LOGIN = "TOOCMS_LOGIN_STATUS";
    private static final String SP_KEY_LOGIN_STATE = "LOGIN_STATE";
    private static final String SP_KEY_ACCOUNT = "ACCOUNT";
    private static final String SP_KEY_PASSWORD = "PASSWORD";

    private SPUtils sp;

    public LoginStatusRepository() {
        sp = SPUtils.getInstance(SP_NAME_LOGIN);
    }

    public void saveAccount(String account) {
        sp.put(SP_KEY_ACCOUNT, account);
    }

    public void savePassword(String password) {
        sp.put(SP_KEY_ACCOUNT, password);
    }

    public String getAccount() {
        return sp.getString(SP_KEY_ACCOUNT);
    }

    public String getPassword() {
        return sp.getString(SP_KEY_PASSWORD);
    }

    /**
     * 用户是否已经登陆
     */
    public boolean isLogin() {
        return sp.getBoolean(SP_KEY_LOGIN_STATE);
    }

    /**
     * 设置登录状态，并进行用户统计分析
     *
     * @param isLogin 登录-true ，退出-false
     */
    public void setLogin(boolean isLogin, BindingAction... actions) {
        SPUtils.getInstance(SP_NAME_LOGIN).put(SP_KEY_LOGIN_STATE, isLogin);
        // 退出登录回调
        if (!isLogin) {
            if (!ArrayUtils.isEmpty(actions))
                actions[0].call();
        }
    }
}
