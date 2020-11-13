package com.toocms.sample.data;

import com.blankj.utilcode.util.ReflectUtils;
import com.blankj.utilcode.util.SPUtils;
import com.toocms.tab.expand.data.LoginStatusRepository;

import java.lang.reflect.Field;

/**
 * Author：Zero
 * Date：2020/11/5 15:23
 */
public class UserRepository extends LoginStatusRepository {

    private static final String SP_NAME_USERDATA = "TOOCMS_USER_DATA";

    private volatile static UserRepository instance;
    private SPUtils sp;
    private User user;

    private UserRepository() {
        sp = SPUtils.getInstance(SP_NAME_USERDATA);
        readUserInfo();
    }

    public static UserRepository getInstance() {
        if (instance == null)
            synchronized (UserRepository.class) {
                if (instance == null)
                    instance = new UserRepository();
            }
        return instance;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
        for (Field field : user.getClass().getDeclaredFields()) {
            try {
                sp.put(field.getName(), String.valueOf(field.get(user)));
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
    }

    public void setUserInfo(String name, String value) {
        ReflectUtils.reflect(user).field(name, value);
        sp.put(name, value);
    }

    public void clearUserInfo() {
        user = null;
        sp.clear();
    }

    private void readUserInfo() {
        User user = new User();
        for (Field field : user.getClass().getDeclaredFields()) {
            ReflectUtils.reflect(user).field(field.getName(), sp.getString(field.getName()));
        }
    }
}
