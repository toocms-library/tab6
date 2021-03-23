package com.toocms.tab;

import android.view.Gravity;

import androidx.multidex.MultiDexApplication;

import com.blankj.utilcode.util.ColorUtils;
import com.blankj.utilcode.util.CrashUtils;
import com.blankj.utilcode.util.EncryptUtils;
import com.blankj.utilcode.util.ReflectUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.qmuiteam.qmui.arch.QMUISwipeBackActivityManager;
import com.qmuiteam.qmui.util.QMUIResHelper;
import com.scwang.smart.refresh.footer.BallPulseFooter;
import com.scwang.smart.refresh.header.MaterialHeader;
import com.scwang.smart.refresh.layout.SmartRefreshLayout;
import com.toocms.tab.configs.FileManager;
import com.toocms.tab.configs.IAppConfig;
import com.toocms.tab.crash.CrashConfig;
import com.toocms.tab.network.RxHttp;
import com.toocms.tab.network.interceptor.Level;
import com.toocms.tab.network.interceptor.LoggingInterceptor;
import com.toocms.tab.verification.VerificationService;
import com.toocms.tab.widget.update.XUpdate;
import com.toocms.tab.widget.update.service.TooCMSUpdateHttpService;
import com.toocms.tab.widget.update.utils.UpdateUtils;
import com.umeng.analytics.MobclickAgent;
import com.umeng.commonsdk.UMConfigure;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.internal.platform.Platform;

import static com.toocms.tab.widget.update.entity.UpdateError.ERROR.CHECK_NO_NEW_VERSION;

/**
 * Application类初始化配置
 * <p>
 * Author Zero
 * Version 6.0
 * Date 2020-10-23
 */
public class TooCMSApplication extends MultiDexApplication {

    private final long CONNECT_TIMEOUT = 15;      //网络请求超时时间

    private IAppConfig appConfig;

    /**
     * Application的实例
     */
    private volatile static TooCMSApplication instance;

    static {
        SmartRefreshLayout.setDefaultRefreshHeaderCreator((context, layout) -> {
            layout.setEnableHeaderTranslationContent(false);
            MaterialHeader materialHeader = new MaterialHeader(context);
            materialHeader.setColorSchemeColors(QMUIResHelper.getAttrColor(layout.getLayout().getContext(), R.attr.app_primary_color),
                    ColorUtils.getColor(R.color.qmui_config_color_red),
                    ColorUtils.getColor(R.color.qmui_config_color_black),
                    ColorUtils.getColor(R.color.qmui_config_color_blue));
            return materialHeader;
        });
        SmartRefreshLayout.setDefaultRefreshFooterCreator((context, layout) -> {
            int app_primary_color = QMUIResHelper.getAttrColor(layout.getLayout().getContext(), R.attr.app_primary_color);
            layout.setPrimaryColors(app_primary_color, app_primary_color);
            return new BallPulseFooter(context);
        });
    }

    /**
     * 该方法因为是整个程序的入口，所以主要就是初始化数据
     * 1、{@link #instance}的初始化
     * 1、{@link #appConfig}的初始化
     * 3、网络框架{@link RxHttp}的初始化
     * 4、验证框架可用性
     * 5、崩溃异常捕捉器{@link CrashUtils}的初始化
     * 6、友盟初始化以及设置为自动采集模式
     * 7、{@link XUpdate}初始化
     */
    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        appConfig = ReflectUtils.reflect(getPackageName() + ".config.AppConfig").newInstance().get();  // 初始化DataSet
        // 初始化网络请求
        RxHttp.init(getClient(), false);
        // 初始化崩溃异常捕捉器
        initCrash();
        // 验证使用权限
        VerificationService.getInstance().verification();
        //
        QMUISwipeBackActivityManager.init(this);
        // 初始化Umeng
        UMConfigure.init(this,
                appConfig.getUmengAppkey(),
                "Umeng",
                UMConfigure.DEVICE_TYPE_PHONE,
                appConfig.getUmengPushSecret());
        UMConfigure.setLogEnabled(true);
        // 设置自动页面采集模式
        MobclickAgent.setPageCollectionMode(MobclickAgent.PageMode.AUTO);
        // 初始化App更新
        initUpdate();
        // 设置Toast在中间显示
        ToastUtils.make().setGravity(Gravity.CENTER, 0, 0);
        // 初始化第三方Jar包
        appConfig.initJarForWeApplication(this);
    }

    private OkHttpClient getClient() {
        return new OkHttpClient.Builder()
                .connectTimeout(CONNECT_TIMEOUT, TimeUnit.SECONDS)
                .readTimeout(CONNECT_TIMEOUT, TimeUnit.SECONDS)
                .writeTimeout(CONNECT_TIMEOUT, TimeUnit.SECONDS)
                .addInterceptor(getInterceptor())
                .build();
    }

    private LoggingInterceptor getInterceptor() {
        return new LoggingInterceptor.Builder()
                .loggable(true)
                .setLevel(Level.BODY)
                .log(Platform.INFO)
                .request("Request")
                .response("Response")
                .build();
    }

    private void initCrash() {
        CrashConfig.Builder.create()
                .backgroundMode(CrashConfig.BACKGROUND_MODE_SILENT)
                .enabled(true)
                .showErrorDetails(true)
                .showRestartButton(true)
                .minTimeBetweenCrashesMs(2000)
                .apply();
    }

    private void initUpdate() {
        XUpdate.get()
                .debug(true)
                .isWifiOnly(false)
                .setApkCacheDir(FileManager.getDownloadPath())
                .param("package", EncryptUtils.encryptMD5ToString(getPackageName()))
                .param("version_code", UpdateUtils.getVersionCode(this))
                .setOnUpdateFailureListener(error -> {
                    if (error.getCode() != CHECK_NO_NEW_VERSION) {
                        ToastUtils.showShort(error.getDetailMsg());
                    }
                })
                .supportSilentInstall(false)
                .setIUpdateHttpService(new TooCMSUpdateHttpService())
                .init(this);
    }

    public static TooCMSApplication getInstance() {
        return instance;
    }

    public IAppConfig getAppConfig() {
        return appConfig;
    }
}
