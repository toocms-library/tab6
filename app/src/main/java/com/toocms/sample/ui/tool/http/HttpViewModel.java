package com.toocms.sample.ui.tool.http;

import android.app.Application;
import android.os.Build;

import androidx.annotation.NonNull;

import com.blankj.utilcode.util.ToastUtils;
import com.luck.picture.lib.entity.LocalMedia;
import com.luck.picture.lib.listener.OnResultCallbackListener;
import com.toocms.tab.base.BaseModel;
import com.toocms.tab.base.BaseViewModel;
import com.toocms.tab.binding.command.BindingCommand;
import com.toocms.tab.bus.event.SingleLiveEvent;
import com.toocms.tab.network.ApiTool;
import com.toocms.tab.network.HttpParams;
import com.toocms.tab.configs.FileManager;

import java.io.File;
import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;

/**
 * Author：Zero
 * Date：2020/10/11 18:29
 */
public class HttpViewModel extends BaseViewModel<BaseModel> {

    private UIChangeObservable uc = new UIChangeObservable();

    public HttpViewModel(@NonNull Application application) {
        super(application);
    }

    public UIChangeObservable getUc() {
        return uc;
    }

    /**
     * Get请求方式（回调在主线程执行）按钮点击命令
     * 注：需在{@link #onDestroy()}中进行手动关闭请求
     * <p>
     *
     * @<code>if (! disposable.isDisposed ()) {  //判断请求有没有结束
     * disposable.dispose();  //没有结束，则关闭请求
     * }</code>
     */
    public BindingCommand getObjectOnMain = new BindingCommand(() ->
            ApiTool.get("Index/index")
                    .asTooCMSResponse(Index.class)
                    .observeOn(AndroidSchedulers.mainThread())
                    .request(index -> {
                        uc.setText.setValue(index.toString());
                    }));

    public BindingCommand getListOnMain = new BindingCommand(() ->
            ApiTool.get("http://api.qunyan.uuudoo.com/Member/getMemberList")
                    .asTooCMSResponseList(User.class)
                    .observeOn(AndroidSchedulers.mainThread())
                    .request(users -> {
                        uc.setText.setValue(users.toString());
                    }));

    //  Post请求方式（回调在主线程执行）按钮点击命令
    public BindingCommand postOnMain = new BindingCommand(() -> {
        ApiTool.post("http://hotpotshop-api.uuudoo.com/Center/setDefault")
                .add("m_id", 7)
                .add("adr_id", 5)
                .asTooCMSResponse(String.class)
                .observeOn(AndroidSchedulers.mainThread())
                .request(s -> {
                    uc.setText.setValue(s);
                });
    });

    //  Get请求方式（回调不在主线程执行）按钮点击命令
    public BindingCommand getNonMain = new BindingCommand(() ->
            ApiTool.get("http://api.qunyan.uuudoo.com/Member/getMemberList")
                    .asTooCMSResponseList(User.class)
                    .request(users -> {
                        ToastUtils.showShort(users.toString());
                    }));

    //  Post请求方式（回调不在主线程执行）按钮点击命令
    public BindingCommand postNonMain = new BindingCommand(() -> {
        HttpParams params = new HttpParams();
        params.put("m_id", 7);
        params.put("adr_id", 5);
        ApiTool.post("http://hotpotshop-api.uuudoo.com/Center/setDefault")
                .params(params)
                .asTooCMSResponse(String.class)
                .request(s -> {
                    ToastUtils.showShort(s);
                });
    });

    //  Get请求方式（页面销毁自动关闭&自动显示/隐藏加载条）按钮点击命令
    public BindingCommand autoDispose = new BindingCommand(() ->
            ApiTool.get("http://api.qunyan.uuudoo.com/Member/getMemberList")
                    .asTooCMSResponseList(User.class)
                    .withViewModel(this)
                    .request(users -> {
                        uc.setText.setValue(users.toString());
                    }));

    //  上传
    public BindingCommand upload = new BindingCommand(() ->
            startSelectSignImageAty(new OnResultCallbackListener<LocalMedia>() {
                @Override
                public void onResult(List<LocalMedia> result) {
                    HttpParams param = new HttpParams();
                    param.put("folder", "1");
                    param.putFile("head", Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q ? result.get(0).getAndroidQToPath() : result.get(0).getCutPath());
                    ApiTool.post("http://xlg-api.uuudoo.com/System/upload")
                            .params(param)
                            .asUpload(progress -> {
                                uc.setText.setValue(progress.getProgress() + "%");
                            })
                            .asTooCMSResponse(ImageUrl.class)
                            .request(imageUrl -> {
                                uc.setText.setValue(imageUrl.toString());
                            });
                }

                @Override
                public void onCancel() {

                }
            }));

    //  下载
    public BindingCommand download = new BindingCommand(() ->
            ApiTool.get("http://update.9158.com/miaolive/Miaolive.apk")
                    .asDownload(FileManager.getDownloadPath() + File.separator + System.currentTimeMillis() + ".apk",
                            progress -> {
                                uc.setText.setValue(progress.getProgress() + "%");
                            })
                    .request(fileName -> {
                        uc.setText.setValue(fileName);
                    }));

    public class UIChangeObservable {
        public SingleLiveEvent<String> setText = new SingleLiveEvent<>();
    }
}
