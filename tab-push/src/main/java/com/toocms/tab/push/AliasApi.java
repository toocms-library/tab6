package com.toocms.tab.push;

import com.blankj.utilcode.util.LogUtils;
import com.umeng.message.PushAgent;
import com.umeng.message.UTrack;

/**
 * 别名API
 * Author：Zero
 * Date：2020/9/7 17:56
 */
public class AliasApi {

    private final String ALIAS_TYPE = "m_id";

    private volatile static AliasApi instance;

    private PushAgent pushAgent;

    private AliasApi(PushAgent pushAgent) {
        this.pushAgent = pushAgent;
    }

    public static AliasApi getInstance(PushAgent pushAgent) {
        if (instance == null)
            synchronized (AliasApi.class) {
                if (instance == null)
                    instance = new AliasApi(pushAgent);
            }
        return instance;
    }

    public void addAlias(String alias, UTrack.ICallBack... iCallBack) {
        addAlias(alias, ALIAS_TYPE, iCallBack);
    }

    public void addAlias(String alias, String type, UTrack.ICallBack... iCallBack) {
        pushAgent.addAlias(alias, type, (iCallBack != null && iCallBack.length > 0) ? iCallBack[0] : emptyCallBack);
    }

    public void setAlias(String alias, UTrack.ICallBack... iCallBack) {
        setAlias(alias, ALIAS_TYPE, iCallBack);
    }

    public void setAlias(String alias, String type, UTrack.ICallBack... iCallBack) {
        pushAgent.setAlias(alias, type, (iCallBack != null && iCallBack.length > 0) ? iCallBack[0] : emptyCallBack);
    }

    public void deleteAlias(String alias, UTrack.ICallBack... iCallBack) {
        deleteAlias(alias, ALIAS_TYPE, iCallBack);
    }

    public void deleteAlias(String alias, String type, UTrack.ICallBack... iCallBack) {
        pushAgent.deleteAlias(alias, type, (iCallBack != null && iCallBack.length > 0) ? iCallBack[0] : emptyCallBack);
    }

    private UTrack.ICallBack emptyCallBack = (b, s) -> LogUtils.e(b, s);
}
