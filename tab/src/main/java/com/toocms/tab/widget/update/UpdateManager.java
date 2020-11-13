package com.toocms.tab.widget.update;

import com.blankj.utilcode.util.ActivityUtils;
import com.blankj.utilcode.util.GsonUtils;
import com.qmuiteam.qmui.util.QMUIResHelper;
import com.toocms.tab.R;
import com.toocms.tab.TooCMSApplication;
import com.toocms.tab.widget.update.entity.CheckVersionResult;
import com.toocms.tab.widget.update.entity.UpdateEntity;
import com.toocms.tab.widget.update.listener.IUpdateParseCallback;
import com.toocms.tab.widget.update.proxy.IUpdateParser;

/**
 * App版本更新
 * <p>
 * Author：Zero
 * Date：2020/4/17 21:20
 */
public class UpdateManager {

    /**
     * 检查更新
     */
    public static void checkUpdate() {
        XUpdate.newBuild(ActivityUtils.getTopActivity())
                .updateUrl(TooCMSApplication.getInstance().getAppConfig().getUpdateUrl())
                .updateParser(new TooCMSUpdateParser())
                .supportBackgroundUpdate(true)
                .promptThemeColor(QMUIResHelper.getAttrColor(ActivityUtils.getTopActivity(), R.attr.app_primary_color))
                .promptTopResId(R.drawable.xupdate_icon_app_rocket)
                .update();
    }

    /**
     * 自定义json解析器
     */
    static class TooCMSUpdateParser implements IUpdateParser {

        @Override
        public UpdateEntity parseJson(String json) {
            TooCMSUpdateEntity result = GsonUtils.fromJson(json, TooCMSUpdateEntity.class);
            if (result != null) {
                UpdateEntity entity = new UpdateEntity();
                entity
                        .setHasUpdate(result.getUpdate_status() != CheckVersionResult.NO_NEW_VERSION)
                        .setForce(result.getUpdate_status() == CheckVersionResult.HAVE_NEW_VERSION_FORCED_UPLOAD)
                        .setVersionCode(result.getVersion_code())
                        .setVersionName(result.getVersion_name())
                        .setUpdateContent(result.getDescription().replaceAll("\\\\r\\\\n", "\r\n"))
                        .setDownloadUrl(result.getUrl())
                        .setMd5(result.getApk_md5())
                        .setSize(result.getApk_size());
                return entity;
            }
            return null;
        }

        @Override
        public void parseJson(String json, IUpdateParseCallback callback) {
        }

        @Override
        public boolean isAsyncParser() {
            return false;
        }
    }
}
