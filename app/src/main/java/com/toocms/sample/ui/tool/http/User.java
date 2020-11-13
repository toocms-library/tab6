package com.toocms.sample.ui.tool.http;

import java.util.List;

/**
 * Author：Zero
 * Date：2020/11/06
 */
public class User {

    private String id;
    private String nickname;
    private String c_and_p;
    private Object personal_profile;
    private String avatar;
    private List<?> tags;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getC_and_p() {
        return c_and_p;
    }

    public void setC_and_p(String c_and_p) {
        this.c_and_p = c_and_p;
    }

    public Object getPersonal_profile() {
        return personal_profile;
    }

    public void setPersonal_profile(Object personal_profile) {
        this.personal_profile = personal_profile;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public List<?> getTags() {
        return tags;
    }

    public void setTags(List<?> tags) {
        this.tags = tags;
    }

    @Override
    public String toString() {
        return "User{" +
                "id='" + id + '\'' +
                ", nickname='" + nickname + '\'' +
                ", c_and_p='" + c_and_p + '\'' +
                ", personal_profile=" + personal_profile +
                ", avatar='" + avatar + '\'' +
                ", tags=" + tags +
                '}';
    }
}
