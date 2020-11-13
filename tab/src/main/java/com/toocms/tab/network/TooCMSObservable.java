package com.toocms.tab.network;

import com.blankj.utilcode.util.ObjectUtils;
import com.toocms.tab.base.BaseViewModel;
import com.toocms.tab.crash.CrashStore;
import com.toocms.tab.network.exception.OnError;

import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.Scheduler;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.functions.Action;
import io.reactivex.rxjava3.functions.Consumer;
import io.reactivex.rxjava3.internal.functions.Functions;

/**
 * Author：Zero
 * Date：2020/9/30 15:54
 */
public class TooCMSObservable<T> {

    private Scheduler scheduler;
    private Consumer<? super Disposable> onStart;
    private Action onFinally;

    private RxHttp rxHttp;
    private Observable<T> observable;

    public static TooCMSObservable create(RxHttp rxHttp) {
        return new TooCMSObservable(rxHttp);
    }

    private TooCMSObservable(RxHttp rxHttp) {
        this.rxHttp = rxHttp;
    }

    public TooCMSObservable<T> asTooCMSResponse(Class<T> cls) {
        observable = rxHttp.asTooCMSResponse(cls);
        return this;
    }

    public TooCMSObservable<T> asTooCMSResponseList(Class<T> cls) {
        observable = rxHttp.asTooCMSResponseList(cls);
        return this;
    }

    public TooCMSObservable<T> observeOn(Scheduler scheduler) {
        this.scheduler = scheduler;
        return this;
    }

    public TooCMSObservable<T> onStart(Consumer<? super Disposable> onStart) {
        this.onStart = onStart;
        return this;
    }

    public TooCMSObservable<T> onFinally(Action onFinally) {
        this.onFinally = onFinally;
        return this;
    }

    public TooCMSObservableLife<T> withViewModel(BaseViewModel viewModel) {
        return TooCMSObservableLife.create(rxHttp, onStart, onFinally, observable, viewModel);
    }

    public Disposable request() {
        return request(Functions.emptyConsumer());
    }

    public Disposable request(Consumer<? super T> onNext) {
        return request(onNext, (OnError) error -> {
            if (!error.isLogicException()) {
                error.getThrowable().printStackTrace();
                // 处理错误日志
                CrashStore.uploadCrashLog(error.getThrowable(), rxHttp.getUrl());
            }
        });
    }

    public Disposable request(Consumer<? super T> onNext, Consumer<? super Throwable> onError) {
        observable = ObjectUtils.isNotEmpty(scheduler) ? observable.observeOn(scheduler) : observable;
        observable = ObjectUtils.isNotEmpty(onStart) ? observable.doOnSubscribe(onStart) : observable;
        observable = ObjectUtils.isNotEmpty(onFinally) ? observable.doFinally(onFinally) : observable;
        return observable.subscribe(onNext, onError);
    }
}
