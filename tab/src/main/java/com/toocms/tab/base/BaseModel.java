package com.toocms.tab.base;

/**
 * M基类
 * <p>
 * Author：Zero
 * Date：2020/10/16 16:08
 */
public class BaseModel {

    public BaseModel() {
    }

    /**
     * ViewModel销毁时清除Model，与ViewModel共消亡。Model层同样不能持有长生命周期对象
     */
    public void onCleared() {
    }
}
