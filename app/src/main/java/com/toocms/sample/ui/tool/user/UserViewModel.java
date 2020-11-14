package com.toocms.sample.ui.tool.user;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.databinding.ObservableField;

import com.toocms.sample.data.User;
import com.toocms.sample.data.UserRepository;
import com.toocms.tab.base.BaseViewModel;
import com.toocms.tab.binding.command.BindingCommand;
import com.toocms.tab.network.ApiTool;

/**
 * Author：Zero
 * Date：2020/11/5 17:32
 */
public class UserViewModel extends BaseViewModel<UserRepository> {

    public ObservableField<String> showText = new ObservableField<>();

    public UserViewModel(@NonNull Application application, UserRepository model) {
        super(application, model);
    }

    private void loadUserInfo() {
        ApiTool.post("http://xlg-api.uuudoo.com/Center/getInfo")
                .add("m_id", 6)
                .asTooCMSResponse(User.class)
                .withViewModel(this)
                .request(user -> {
                    model.setUser(user);
                    showText.set("登录成功");
                });
    }

    public BindingCommand login = new BindingCommand(() -> {
        model.setLogin(true);
        // 保存/获取账号密码
//        model.saveAccount("");
//        model.savePassword("");
//        model.getAccount();
//        model.getPassword();
        loadUserInfo();
    });

    public BindingCommand getUserInfo = new BindingCommand(() -> showText.set(model.getUser().toString()));

    public BindingCommand getUserId = new BindingCommand(() -> showText.set("用户ID：" + model.getUser().getM_id()));

    public BindingCommand setUserNickname = new BindingCommand(() -> {
        model.setUserInfo("nickname", "TooCMS");
        showText.set("用户昵称：" + model.getUser().getNickname());
    });

    public BindingCommand logout = new BindingCommand(() -> {
        model.setLogin(false, () -> showText.set("已注销"));
        model.clearUserInfo();
    });

    public BindingCommand checkStatus = new BindingCommand(() -> showText.set(String.valueOf(model.isLogin())));
}
