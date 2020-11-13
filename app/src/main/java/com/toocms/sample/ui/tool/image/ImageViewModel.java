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
