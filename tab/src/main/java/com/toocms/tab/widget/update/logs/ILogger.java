package com.toocms.tab.widget.update.logs;

/**
 * 简易的日志接口
 *
 * @author xuexiang
 * @since 2018/6/29 下午7:57
 */
public interface ILogger {

    /**
     * 打印信息
     *
     * @param priority 优先级
     * @param tag      标签
     * @param message  信息
     * @param t        出错信息
     */
    void log(int priority, String tag, String message, Throwable t);
}
