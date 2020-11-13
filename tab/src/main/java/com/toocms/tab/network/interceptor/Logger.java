package com.toocms.tab.network.interceptor;

import okhttp3.internal.platform.Platform;

@SuppressWarnings({"WeakerAccess", "unused"})
public interface Logger {

    void log(int level, String tag, String msg);

    Logger DEFAULT = (level, tag, message) -> {
        Platform.get().log(message, level, null);
    };
}
