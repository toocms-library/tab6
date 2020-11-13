package com.toocms.tab.widget.update.proxy;

import com.toocms.tab.widget.update.entity.UpdateEntity;
import com.toocms.tab.widget.update.listener.IUpdateParseCallback;

/**
 * 版本更新解析器[异步解析和同步解析方法只需要实现一个就行了，当isAsyncParser为true时需要实现异步解析方法，否则实现同步解析方法]
 *
 * @author xuexiang
 * @since 2018/6/29 下午8:30
 */
public interface IUpdateParser {

    /**
     * [同步解析方法]
     * <p>
     * 将请求的json结果解析为版本更新信息实体
     *
     * @param json
     * @return
     */
    UpdateEntity parseJson(String json) throws Exception;

    /**
     * [异步解析方法]
     * <p>
     * 将请求的json结果解析为版本更新信息实体
     *
     * @param json
     * @param callback
     * @return
     */
    void parseJson(String json, final IUpdateParseCallback callback) throws Exception;

    /**
     * @return 是否是异步解析
     */
    boolean isAsyncParser();
}
