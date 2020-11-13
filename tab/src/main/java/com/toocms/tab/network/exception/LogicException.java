package com.toocms.tab.network.exception;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.io.IOException;

import okhttp3.Response;

/**
 * 逻辑异常类
 * 用于flag为error时的逻辑判断
 * <p>
 * Author：Zero
 * Date：2020/9/22 16:53
 */
public class LogicException extends IOException {

    private String flag;
    private String message;

    private Response response;

    public LogicException(@NonNull String flag, String message, Response response) {
        super(message);
        this.flag = flag;
        this.message = message;
        this.response = response;
    }

    public String getFlag() {
        return flag;
    }

    @Nullable
    @Override
    public String getMessage() {
        return message;
    }

    public Response getResponse() {
        return response;
    }
}
