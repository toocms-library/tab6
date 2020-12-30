package com.toocms.sample.ui.expand.share;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.databinding.ObservableField;

import com.toocms.sample.R;
import com.toocms.tab.base.BaseViewModel;
import com.toocms.tab.binding.command.BindingCommand;
import com.toocms.tab.share.TabShare;
import com.toocms.tab.share.listener.OnAuthListener;
import com.toocms.tab.share.listener.OnShareListener;
import com.toocms.tab.share.login.PlatformUser;
import com.umeng.socialize.bean.SHARE_MEDIA;

/**
 * Author：Zero
 * Date：2020/12/10 15:57
 */
public class ShareViewModel extends BaseViewModel {

    public ObservableField<String> qqLoginStatus = new ObservableField<>();
    public ObservableField<String> wechatLoginStatus = new ObservableField<>();
    public ObservableField<String> sinaLoginStatus = new ObservableField<>();

    public ShareViewModel(@NonNull Application application) {
        super(application);
    }

    @Override
    public void onStart() {
        super.onStart();
        refreshAuthorizeStatus();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        TabShare.release();
    }

    // 分享面板
    public BindingCommand doShare = new BindingCommand(() -> doShare(SHARE_MEDIA.WEIXIN, SHARE_MEDIA.QQ, SHARE_MEDIA.SINA));
    // QQ分享
    public BindingCommand qqShare = new BindingCommand(() -> doShare(SHARE_MEDIA.QQ));
    // 微信分享
    public BindingCommand wechatShare = new BindingCommand(() -> doShare(SHARE_MEDIA.WEIXIN));
    // 自定义菜单
    public BindingCommand customMenu = new BindingCommand(() -> showCustomMenu(SHARE_MEDIA.WEIXIN, SHARE_MEDIA.QQ));
    // QQ登录
    public BindingCommand qqLogin = new BindingCommand(() -> authorizeOrRevoke(SHARE_MEDIA.QQ));
    // 微信登录
    public BindingCommand wechatLogin = new BindingCommand(() -> authorizeOrRevoke(SHARE_MEDIA.WEIXIN));
    // 微博登录
    public BindingCommand sinaLogin = new BindingCommand(() -> authorizeOrRevoke(SHARE_MEDIA.SINA));

    /**
     * 调起分享
     *
     * @param share_media 分享到的平台（根据个数决定是否弹出分享面板，单个不弹出，多个弹出）
     */
    private void doShare(SHARE_MEDIA... share_media) {
        TabShare.getOneKeyShare()
                // 设置要分享的平台列表，枚举类为SHARE_MEDIA，例如SHARE_MEDIA.QQ
                .setPlatform(share_media)
                // 设置要分享的url、标题、描述、图片（分享内容4选1）
                .setUrl("http://www.toocms.com",
                        "晟轩科技",
                        "晟轩科技成立于2007年，12年时间让我们从默默无闻做到了行业优质服务商，主要从事网站建设，APP开发，微信开发，品牌设计等服务，能为您提供个性化的互联网全套解决方案",
                        R.raw.shengxuan)    // 也可设置网络图片地址："http://xsd-img.toocms.com//Uploads/Config/2016-12-07/5847d181ebeaa.png"
                // 设置要分享的view/layoutId（分享内容4选1）
                // .setLayout(R.id.share_view)
                // 设置要分享的imageUrl/resId（分享内容4选1）
                // .setImage(R.raw.shengxuan)
                // 设置要分享的小程序，实测描述貌似没什么用（分享内容4选1）
                // .setMin("http://www.toocms.com", "易商综合商城", "买啥都便宜", "pages/index/index", "gh_723a21db6d3a", R.raw.shengxuan)
                // 设置分享回调监听
                .setShareCallback(new OnShareListener() {
                    @Override
                    public void onResult(SHARE_MEDIA share_media) {
                        showToast("成功");
                    }

                    @Override
                    public void onError(SHARE_MEDIA share_media, Throwable throwable) {
                        showToast("失败");
                    }

                    @Override
                    public void onCancel(SHARE_MEDIA share_media) {
                        showToast("取消");
                    }
                })
                // 执行分享操作
                .share();
    }

    /**
     * 调起自定义选项分享面板
     *
     * @param share_media 分享平台
     */
    private void showCustomMenu(SHARE_MEDIA... share_media) {
        TabShare.getOneKeyShare()
                .setPlatform(share_media)
                .addButton("复制链接", "copyurl", "umeng_socialize_copyurl", "umeng_socialize_copyurl")
                .addButton("屏蔽", "delete", "umeng_socialize_delete", "umeng_socialize_delete")
                .addButton("更多", "more", "umeng_socialize_more", "umeng_socialize_more")
                .addButton("音乐", "music", "umeng_socialize_share_music", "umeng_socialize_share_music")
                .addButton("视频", "video", "umeng_socialize_share_video", "umeng_socialize_share_video")
                .addButton("链接", "web", "umeng_socialize_share_web", "umeng_socialize_share_web")
                .setShareboardclickCallback((snsPlatform, share_media1) -> {
                    // 通过判断share_media1是否为空来决定点击的选项是自定义按钮还是三方平台
                    if (share_media1 == null) {  // 自定义按钮
                        switch (snsPlatform.mKeyword) {
                            case "copyurl":
                                showToast("复制成功");
                                break;
                            case "delete":
                                showToast("屏蔽成功");
                                break;
                            case "more":
                                showToast("敬请期待");
                                break;
                            case "music":
                                showToast("分享音乐");
                                break;
                            case "video":
                                showToast("分享视频");
                                break;
                            case "web":
                                showToast("分享链接");
                                break;
                        }
                    } else {    // 三方平台分享
                        doShare(share_media1);
                    }
                })
                .share();
    }

    /**
     * 根据平台进行授权登录
     *
     * @param share_media 要授权的平台
     */
    private void authorizeOrRevoke(final SHARE_MEDIA share_media) {
        // platform参数为要获取授权的平台
        if (TabShare.getOneKeyLogin().isAuthorize(share_media)) {
            TabShare.getOneKeyLogin().revokeAuthorize(share_media, onAuthListener);
        } else {
            TabShare.getOneKeyLogin().showUser(true, share_media, onAuthListener);
        }
    }

    private OnAuthListener onAuthListener = new OnAuthListener() {
        @Override
        public void onComplete(SHARE_MEDIA share_media, int action, PlatformUser platformUser) {
            switch (action) {
                case ACTION_GET_PROFILE:  // 获取资料
                    showSingleActionDialog(share_media.name() + "授权登录成功",
                            "用户资料：\n" +
                                    "OpenId：" + platformUser.getOpenId() + "\n" +   // openid
                                    "Name：" + platformUser.getName() + "\n" +          // 昵称
                                    "Gender：" + platformUser.getGender() + "\n" +    // 性别
                                    "Head：" + platformUser.getHead() + "\n" +          // 头像url
                                    "Token：" + platformUser.getToken(),                  // token
                            "确定",
                            (dialog, index) -> dialog.dismiss()
                    );
                    break;
                case ACTION_DELETE: // 撤销授权
                    showToast("已撤销" + share_media.name() + "授权");
                    break;
            }
            refreshAuthorizeStatus();
        }

        @Override
        public void onError(SHARE_MEDIA share_media, int action, Throwable throwable) {
            switch (action) {
                case ACTION_AUTHORIZE:  // 授权
                    showToast(share_media.name() + "授权失败");
                    break;
                case ACTION_DELETE: // 撤销授权
                    showToast("撤销" + share_media.name() + "授权出错");
                    break;
            }
        }

        @Override
        public void onCancel(SHARE_MEDIA share_media, int action) {
            showToast("取消" + share_media.name() + "授权");
        }
    };

    private void refreshAuthorizeStatus() {
        if (TabShare.getOneKeyLogin().isAuthorize(SHARE_MEDIA.QQ)) {
            qqLoginStatus.set("QQ退出登录");
        } else {
            qqLoginStatus.set("QQ登录");
        }
        if (TabShare.getOneKeyLogin().isAuthorize(SHARE_MEDIA.WEIXIN)) {
            wechatLoginStatus.set("微信退出登录");
        } else {
            wechatLoginStatus.set("微信登录");
        }
        if (TabShare.getOneKeyLogin().isAuthorize(SHARE_MEDIA.SINA)) {
            sinaLoginStatus.set("微博退出登录");
        } else {
            sinaLoginStatus.set("微博登录");
        }
    }
}
