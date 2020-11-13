package com.toocms.tab.network.exception;

import android.text.TextUtils;

import com.blankj.utilcode.util.NetworkUtils;
import com.google.gson.JsonSyntaxException;

import java.net.ConnectException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;
import java.util.concurrent.TimeoutException;

import rxhttp.wrapper.exception.HttpStatusCodeException;

/**
 * Author：Zero
 * Date：2020/9/22 17:49
 */
public class ErrorInfo {

    private String flag;
    private String message;
    private Throwable throwable;
    private boolean isLogicException;

    public ErrorInfo(Throwable throwable) {
        this.throwable = throwable;
        String errorMsg;
        if (throwable instanceof UnknownHostException) {
            if (!NetworkUtils.isConnected()) {
                errorMsg = "当前无网络，请检查你的网络设置";
            } else {
                errorMsg = "网络连接不可用，请稍后重试！";
            }
        } else if (throwable instanceof SocketTimeoutException || throwable instanceof TimeoutException) {
            //前者是通过OkHttpClient设置的超时引发的异常，后者是对单个请求调用timeout方法引发的超时异常
            errorMsg = "连接超时,请稍后再试";
        } else if (throwable instanceof ConnectException) {
            errorMsg = "网络不给力，请稍候重试！";
        } else if (throwable instanceof HttpStatusCodeException) {  //请求失败异常
            String code = throwable.getLocalizedMessage();
            if ("416".equals(code)) {
                errorMsg = "请求范围不符合要求";
            } else {
                errorMsg = throwable.getMessage();
            }
        } else if (throwable instanceof JsonSyntaxException) {  // 请求成功，但Json语法异常，导致解析失败
            errorMsg = "数据解析失败,请稍后再试";
        } else if (throwable instanceof LogicException) {   // LogicException异常表明请求成功，但是flag为error
            isLogicException = true;
            String errorCode = throwable.getLocalizedMessage();
            this.flag = errorCode;
            errorMsg = throwable.getMessage();
            if (TextUtils.isEmpty(errorMsg)) errorMsg = errorCode;  //errorMsg为空，显示errorCode
        } else {
            errorMsg = throwable.getMessage();
        }
        this.message = errorMsg;
    }

    public String getFlag() {
        return flag;
    }

    public String getMessage() {
        return message;
    }

    public Throwable getThrowable() {
        return throwable;
    }

    public boolean isLogicException() {
        return isLogicException;
    }
}
