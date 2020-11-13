package com.toocms.tab.imageload;

import android.content.Context;

import androidx.annotation.NonNull;

import com.bumptech.glide.Glide;
import com.bumptech.glide.GlideBuilder;
import com.bumptech.glide.Registry;
import com.bumptech.glide.annotation.GlideModule;
import com.bumptech.glide.integration.okhttp3.OkHttpUrlLoader;
import com.bumptech.glide.load.engine.cache.DiskLruCacheFactory;
import com.bumptech.glide.load.engine.cache.LruResourceCache;
import com.bumptech.glide.load.model.GlideUrl;
import com.bumptech.glide.module.AppGlideModule;
import com.toocms.tab.imageload.progress.ProgressManager;
import com.toocms.tab.configs.FileManager;

import java.io.InputStream;

/**
 * 图片缓存配置
 * Author：Zero
 * Date：2017/3/18 15:52
 */
@GlideModule
public class TooCMSCache extends AppGlideModule {

    @Override
    public void registerComponents(@NonNull Context context, @NonNull Glide glide, @NonNull Registry registry) {
        super.registerComponents(context, glide, registry);
        registry.replace(GlideUrl.class, InputStream.class, new OkHttpUrlLoader.Factory(ProgressManager.getOkHttpClient()));
    }

    @Override
    public boolean isManifestParsingEnabled() {
        return false;
    }

    public void applyOptions(@NonNull Context context, @NonNull GlideBuilder builder) {
//        // 设置图片解码格式
//        builder.setDecodeFormat(DecodeFormat.PREFER_ARGB_8888);
        // 设置内存缓存容量
        long maxMemory = Runtime.getRuntime().maxMemory(); // 总容量
        long memoryCacheSize = maxMemory / 8; // 1/8大小
        builder.setMemoryCache(new LruResourceCache((int) memoryCacheSize));
        // 设置缓存目录以及容量
        String cachePath = FileManager.getCachePath();
        int cacheSize = 100 * 1024 * 1024;
        builder.setDiskCache(new DiskLruCacheFactory(cachePath, cacheSize));
    }
}
