package com.toocms.tab.verification;

import com.blankj.utilcode.util.EncryptUtils;
import com.blankj.utilcode.util.NetworkUtils;
import com.toocms.tab.TooCMSApplication;
import com.toocms.tab.network.ApiTool;
import com.toocms.tab.network.HttpParams;
import com.toocms.tab.network.exception.OnError;

import io.reactivex.rxjava3.internal.functions.Functions;

/**
 * 验证使用框架的项目
 * <p>
 * Author：Zero
 * Date：2020/10/27
 */
public class VerificationService {

    private static VerificationService instance = null;

    public static VerificationService getInstance() {
        if (instance == null)
            synchronized (VerificationService.class) {
                if (instance == null)
                    instance = new VerificationService();
            }
        return instance;
    }

    private VerificationService() {
    }

    public final void verification() {
        if (!NetworkUtils.isConnected()) return;
        HttpParams params = new HttpParams();
        params.put("pack", EncryptUtils.encryptMD5ToString(TooCMSApplication.getInstance().getPackageName()));
        params.put("type", "1");
        ApiTool.post("http://twp.toocms.com/PaCheck/Docheck")
                .params(params)
                .asTooCMSResponse(String.class)
//                .observeOn(AndroidSchedulers.mainThread())
                .request(Functions.emptyConsumer(), (OnError) errorInfo -> {
                    if (errorInfo.isLogicException()) {
                        Thread.getDefaultUncaughtExceptionHandler().uncaughtException(Thread.currentThread(), errorInfo.getThrowable());
                    }
                });
    }
}
