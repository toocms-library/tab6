package com.toocms.tab.share.share;

import android.app.Activity;
import android.view.View;

import com.blankj.utilcode.util.ActivityUtils;
import com.blankj.utilcode.util.RegexUtils;
import com.toocms.tab.share.listener.OnShareListener;
import com.toocms.tab.share.util.TooCMSShareUtils;
import com.umeng.socialize.ShareAction;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.media.UMImage;
import com.umeng.socialize.media.UMMin;
import com.umeng.socialize.media.UMWeb;
import com.umeng.socialize.shareboard.ShareBoardConfig;
import com.umeng.socialize.utils.ShareBoardlistener;

import java.io.File;
import java.util.Arrays;
import java.util.List;

/**
 * 一键分享
 *
 * @author Zero
 * @version 3.0
 * @date 2020年5月26日
 */
public class OneKeyShare {

    private volatile static OneKeyShare instance;

    private Activity mActivity;
    private ShareAction shareView;
    private List<SHARE_MEDIA> platforms;

    private OneKeyShare() {
        mActivity = ActivityUtils.getTopActivity();
        shareView = new ShareAction(mActivity);
    }

    public static OneKeyShare getInstance() {
        if (instance == null)
            synchronized (OneKeyShare.class) {
                if (instance == null)
                    instance = new OneKeyShare();
            }
        return instance;
    }

    /**
     * 设置需要分享的平台
     *
     * @param share_media 分享的平台列表
     * @return
     */
    public OneKeyShare setPlatform(SHARE_MEDIA... share_media) {
        if (share_media == null || share_media.length == 0)
            throw new NullPointerException("请设置需要分享的平台");
        platforms = Arrays.asList(share_media);
        switch (share_media.length) {
            case 1:
                shareView.setPlatform(share_media[0]);
                return this;
            default:
                shareView.setDisplayList(share_media);
                return this;
        }
    }

    /**
     * 设置分享url（本地图片）
     *
     * @param url   分享的url
     * @param title 标题
     * @param desc  描述
     * @param resId 图片id
     * @return
     */
    public OneKeyShare setUrl(String url, String title, String desc, int resId) {
        if (!RegexUtils.isURL(url)) throw new ClassCastException("请设置正确的url");
        UMWeb web = new UMWeb(url, title, desc, new UMImage(mActivity, resId));
        shareView.withMedia(web);
        return this;
    }

    /**
     * 设置分享url（网络图片）
     *
     * @param url      分享的url
     * @param title    标题
     * @param desc     描述
     * @param imageUrl 图片url
     * @return
     */
    public OneKeyShare setUrl(String url, String title, String desc, String imageUrl) {
        if (!RegexUtils.isURL(url)) throw new ClassCastException("请设置正确的url");
        if (!RegexUtils.isURL(imageUrl)) throw new ClassCastException("请设置正确的imageUrl");
        UMWeb web = new UMWeb(url, title, desc, new UMImage(mActivity, imageUrl));
        shareView.withMedia(web);
        return this;
    }

    /**
     * 设置分享资源文件图片
     *
     * @param resId 资源id
     * @return
     */
    public OneKeyShare setImage(int resId) {
        UMImage image = new UMImage(mActivity, resId);
        shareView.withMedia(image);
        return this;
    }

    /**
     * 设置分享本地文件图片
     *
     * @param imagePath 本地路径
     * @return
     */
    public OneKeyShare setImage(File imagePath) {
        UMImage image = new UMImage(mActivity, imagePath);
        shareView.withMedia(image);
        return this;
    }

    /**
     * 设置分享网络图片
     *
     * @param imageUrl
     * @return
     */
    public OneKeyShare setImage(String imageUrl) {
        if (!RegexUtils.isURL(imageUrl)) throw new ClassCastException("请设置正确的imageUrl");
        UMImage image = new UMImage(mActivity, imageUrl);
        shareView.withMedia(image);
        return this;
    }

    /**
     * 设置分享的布局
     *
     * @param view 布局根控件
     * @return
     */
    public OneKeyShare setLayout(View view) {
        UMImage image = new UMImage(mActivity, TooCMSShareUtils.loadImageFromView(mActivity, view));
        shareView.withMedia(image);
        return this;
    }

    /**
     * 设置分享的布局
     *
     * @param layoutId 布局根控件id
     * @return
     */
    public OneKeyShare setLayout(int layoutId) {
        View view = mActivity.findViewById(layoutId);
        UMImage image = new UMImage(mActivity, TooCMSShareUtils.loadImageFromView(mActivity, view));
        shareView.withMedia(image);
        return this;
    }

    /**
     * 设置分享的小程序
     *
     * @param minUrl 兼容低版本的网页链接
     * @param title  小程序消息标题
     * @param desc   小程序消息描述
     * @param path   小程序页面路径
     * @param minId  小程序原始id
     * @param resId  小程序消息封面图片（资源id）
     * @return
     */
    public OneKeyShare setMin(String minUrl, String title, String desc, String path, String minId, int resId) {
        if (!RegexUtils.isURL(minUrl)) throw new ClassCastException("请设置正确的url");
        UMImage image = new UMImage(mActivity, resId);
        UMMin min = new UMMin(minUrl);
        min.setTitle(title);
        min.setThumb(image);
        min.setDescription(desc);
        min.setPath(path);
        min.setUserName(minId);
        shareView.withMedia(min);
        return this;
    }

    /**
     * 设置分享的小程序
     *
     * @param minUrl   兼容低版本的网页链接
     * @param title    小程序消息标题
     * @param desc     小程序消息描述
     * @param path     小程序页面路径
     * @param minId    小程序原始id
     * @param imageUrl 小程序消息封面（网络图片）
     * @return
     */
    public OneKeyShare setMin(String minUrl, String title, String desc, String path, String minId, String imageUrl) {
        if (!RegexUtils.isURL(minUrl)) throw new ClassCastException("请设置正确的url");
        if (!RegexUtils.isURL(imageUrl)) throw new ClassCastException("请设置正确的imageUrl");
        UMImage image = new UMImage(mActivity, imageUrl);
        UMMin min = new UMMin(minUrl);
        min.setTitle(title);
        min.setThumb(image);
        min.setDescription(desc);
        min.setPath(path);
        min.setUserName(minId);
        shareView.withMedia(min);
        return this;
    }

    /**
     * 面板中添加自定义选项
     *
     * @param showWord  显示文字
     * @param mediaWord
     * @param icon
     * @param grayIcon
     */
    public OneKeyShare addButton(String showWord, String mediaWord, String icon, String grayIcon) {
        shareView.addButton(showWord, mediaWord, icon, grayIcon);
        return this;
    }

    /**
     * 面板中的自定义选项点击事件
     *
     * @param listener
     */
    public OneKeyShare setShareboardclickCallback(ShareBoardlistener listener) {
        shareView.setShareboardclickCallback(listener);
        return this;
    }

    /**
     * 设置分享状态回调监听
     *
     * @param listener
     * @return
     */
    public OneKeyShare setShareCallback(OnShareListener listener) {
        shareView.setCallback(listener);
        return this;
    }

    /**
     * 分享   <br/>
     * 是否弹出分享面板：根据传入的平台个数区分，单个不弹出，多个弹出  <br/>
     * 是否需要自定义面板：根据是否传入面板配置参数决定  <br/>
     */
    public void share(ShareBoardConfig... config) {
        if (platforms == null || platforms.size() == 0)
            throw new NullPointerException("请设置需要分享的平台");
        switch (platforms.size()) {
            case 1:
                shareView.share();
                break;
            default:
                if (config != null && config.length != 0) {
                    shareView.open(config[0]);
                } else {
                    shareView.open();
                }
                break;
        }
    }

    /**
     * 释放
     */
    public void release() {
        instance = null;
    }
}
