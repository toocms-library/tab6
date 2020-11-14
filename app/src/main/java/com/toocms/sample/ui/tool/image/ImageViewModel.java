package com.toocms.sample.ui.tool.image;

import android.app.Application;

import androidx.annotation.NonNull;

import com.toocms.tab.base.BaseViewModel;
import com.toocms.tab.binding.command.BindingCommand;
import com.toocms.tab.configs.FileManager;

/**
 * Author：Zero
 * Date：2020/11/12 14:57
 */
public class ImageViewModel extends BaseViewModel {

    public String urlCenter = "https://ss0.bdstatic.com/70cFuHSh_Q1YnxGkpoWK1HF6hhy/it/u=383304109,937640631&fm=11&gp=0.jpg";
    public String urlLeftTop = "https://ss1.bdstatic.com/70cFvXSh_Q1YnxGkpoWK1HF6hhy/it/u=818918895,1070782834&fm=11&gp=0.jpg";
    public String urlRightTop = "https://ss0.bdstatic.com/70cFuHSh_Q1YnxGkpoWK1HF6hhy/it/u=4277751004,3595306162&fm=26&gp=0.jpg";
    public String urlLeftBottom = "https://ss3.bdstatic.com/70cFv8Sh_Q1YnxGkpoWK1HF6hhy/it/u=4204193996,2127491495&fm=26&gp=0.jpg";
    public String urlRightBottom = "https://ss1.bdstatic.com/70cFuXSh_Q1YnxGkpoWK1HF6hhy/it/u=2207936752,3352455107&fm=26&gp=0.jpg";

    public ImageViewModel(@NonNull Application application) {
        super(application);
    }

    public BindingCommand clearMemory = new BindingCommand(() -> {
        FileManager.clearMemCache();
        showToast("运存缓存清理完毕");
    });

    public BindingCommand clearCache = new BindingCommand(() -> {
        FileManager.clearCacheFiles();
        showToast("内存缓存清理完毕");
    });
}
