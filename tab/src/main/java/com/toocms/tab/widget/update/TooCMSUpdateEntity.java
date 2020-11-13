package com.toocms.tab.widget.update;

/**
 * 类描述：版本信息实体类
 * 创建人：Zero
 * 创建时间：2017/2/15 12:47
 * 修改人：Zero
 * 修改时间：2017/3/13 17:02
 * 修改备注：
 */
public class TooCMSUpdateEntity {

    private int update_status;
    private int version_code;
    private String version_name;
    private String description;
    private String url;
    private int apk_size;
    private String apk_md5;

    public int getUpdate_status() {
        return update_status;
    }

    public void setUpdate_status(int update_status) {
        this.update_status = update_status;
    }

    public int getVersion_code() {
        return version_code;
    }

    public void setVersion_code(int version_code) {
        this.version_code = version_code;
    }

    public String getVersion_name() {
        return version_name;
    }

    public void setVersion_name(String version_name) {
        this.version_name = version_name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public int getApk_size() {
        return apk_size;
    }

    public void setApk_size(int apk_size) {
        this.apk_size = apk_size;
    }

    public String getApk_md5() {
        return apk_md5;
    }

    public void setApk_md5(String apk_md5) {
        this.apk_md5 = apk_md5;
    }
}
