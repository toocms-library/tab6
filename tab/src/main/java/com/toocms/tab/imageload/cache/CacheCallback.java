package com.toocms.tab.imageload.cache;

import java.io.File;

/**
 * 获取图片缓存路径的回调接口
 * <p>
 * Author：Zero
 * Date：2017/5/15 17:12
 *
 * @version v4.0
 */
public interface CacheCallback {
    void onCache(File file);
}
