package com.toocms.tab.network;

import com.blankj.utilcode.util.ObjectUtils;
import com.rxjava.rxlife.ObservableLife;
import com.rxjava.rxlife.RxLife;
import com.toocms.tab.base.BaseViewModel;
import com.toocms.tab.crash.CrashStore;
import com.toocms.tab.network.exception.OnError;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.functions.Action;
import io.reactivex.rxjava3.functions.Consumer;
import io.reactivex.rxjava3.internal.functions.Functions;

/**
 * Author：Zero
 * Date：2020/10/20 18:31
 */
public class TooCMSObservableLife<T> {

    private RxHttp rxHttp;
    private Consumer<? super Disposable> onStart;
    private Action onFinally;

    private Observable<T> observable;
    private ObservableLife<T> observableLife;

    private BaseViewModel viewModel;
    private boolean isShowLoading = true;

    public static <T> TooCMSObservableLife create(RxHttp rxHttp, Consumer<? super Disposable> onStart, Action onFinally, Observable<T> observable, BaseViewModel viewModel) {
        return new TooCMSObservableLife(rxHttp, onStart, onFinally, observable, viewModel);
    }

    private TooCMSObservableLife(RxHttp rxHttp, Consumer<? super Disposable> onStart, Action onFinally, Observable<T> observable, BaseViewModel viewModel) {
        this.rxHttp = rxHttp;
        this.onStart = onStart;
        this.onFinally = onFinally;
        this.observable = observable;
        this.viewModel = viewModel;
    }

    public TooCMSObservableLife<T> showLoading(boolean isShowLoading) {
        this.isShowLoading = isShowLoading;
        return this;
    }

    public Disposable request() {
        return request(Functions.emptyConsumer());
    }

    public Disposable request(Consumer<? super T> onNext) {
        return request(onNext, (OnError) error -> {
            if (!error.isLogicException()) {
                viewModel.showFailed(error.getMessage(), v -> {
                    request(onNext);
                });
                error.getThrowable().printStackTrace();
                // 处理错误日志
                CrashStore.uploadCrashLog(error.getThrowable(), rxHttp.getUrl());
            }
        });
    }

    public Disposable request(Consumer<? super T> onNext, Consumer<? super Throwable> onError) {
        observable = observable.observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe(disposable -> {
                    if (isShowLoading) viewModel.showProgress();
                    if (ObjectUtils.isNotEmpty(onStart)) onStart.accept(disposable);
                }).doFinally(() -> {
                    if (isShowLoading) viewModel.removeProgress();
                    if (ObjectUtils.isNotEmpty(onFinally)) onFinally.run();
                });
        observableLife = observable.to(RxLife.to(viewModel));
        return observableLife.subscribe(onNext, onError);
    }
}
