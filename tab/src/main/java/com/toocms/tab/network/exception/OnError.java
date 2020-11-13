package com.toocms.tab.network.exception;

import io.reactivex.rxjava3.functions.Consumer;

/**
 * RxJava 错误回调 ,已处理网络异常
 * Author：Zero
 * Date：2020/9/22 17:34
 */
public interface OnError extends Consumer<Throwable> {

    @Override
    default void accept(Throwable throwable) throws Exception {
        onError(new ErrorInfo(throwable));
    }

    void onError(ErrorInfo error) throws Exception;
}