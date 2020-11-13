package com.toocms.tab.network;

import com.blankj.utilcode.util.ObjectUtils;
import com.toocms.tab.crash.CrashStore;
import com.toocms.tab.network.exception.OnError;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.Scheduler;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.functions.Action;
import io.reactivex.rxjava3.functions.Consumer;
import rxhttp.wrapper.entity.Progress;

/**
 * Author：Zero
 * Date：2020/10/21 18:04
 */
public class DownloadObservable {

    private RxHttp rxHttp;
    private Observable<String> observable;

    private Scheduler scheduler;
    private Consumer<? super Disposable> onStart;
    private Action onFinally;

    public static DownloadObservable create(RxHttp rxHttp) {
        return new DownloadObservable(rxHttp);
    }

    private DownloadObservable(RxHttp rxHttp) {
        this.rxHttp = rxHttp;
    }

    public DownloadObservable observeOn(Scheduler scheduler) {
        this.scheduler = scheduler;
        return this;
    }

    public DownloadObservable onStart(Consumer<? super Disposable> onStart) {
        this.onStart = onStart;
        return this;
    }

    public DownloadObservable onFinally(Action onFinally) {
        this.onFinally = onFinally;
        return this;
    }

    public DownloadObservable asDownload(String destPath, Consumer<Progress> progressConsumer) {
        observable = rxHttp.asDownload(destPath, AndroidSchedulers.mainThread(), progressConsumer);
        return this;
    }

    public Disposable request(Consumer<? super String> onNext) {
        return request(onNext, (OnError) error -> {
            if (!error.isLogicException()) {
                error.getThrowable().printStackTrace();
                // 处理错误日志
                CrashStore.uploadCrashLog(error.getThrowable(), rxHttp.getUrl());
            }
        });
    }

    public Disposable request(Consumer<? super String> onNext, Consumer<? super Throwable> onError) {
        observable = ObjectUtils.isNotEmpty(scheduler) ? observable.observeOn(scheduler) : observable;
        observable = ObjectUtils.isNotEmpty(onStart) ? observable.doOnSubscribe(onStart) : observable;
        observable = ObjectUtils.isNotEmpty(onFinally) ? observable.doFinally(onFinally) : observable;
        return observable.subscribe(onNext, onError);
    }
}
