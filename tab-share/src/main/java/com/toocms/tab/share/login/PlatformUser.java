package com.toocms.tab.share.login;

/**
 * 授权平台的用户信息
 * <p>
 * Author：Zero
 * Date：2020/5/26
 *
 * @version v3.0
 */
public class PlatformUser {

    private String openId;
    private String name;
    private String gender;
    private String head;
    private String token;

    public PlatformUser(String openId, String name, String gender, String head, String token) {
        this.openId = openId;
        this.name = name;
        this.gender = gender;
        this.head = head;
        this.token = token;
    }

    public String getOpenId() {
        return openId;
    }

    public void setOpenId(String openId) {
        this.openId = openId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getHead() {
        return head;
    }

    public void setHead(String head) {
        this.head = head;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
