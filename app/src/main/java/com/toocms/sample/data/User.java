package com.toocms.sample.data;

/**
 * Author：Zero
 * Date：2020/11/5 15:27
 */
public class User {

    private String m_id;
    private String account;
    private String nickname;
    private String avatar;
    private String balance;
    private String member_expiration_date;
    private String status;
    private String account_format;
    private String avatar_path;
    private String is_pay_pass;
    private String is_super_member;
    private String default_address;

    public String getM_id() {
        return m_id;
    }

    public void setM_id(String m_id) {
        this.m_id = m_id;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getBalance() {
        return balance;
    }

    public void setBalance(String balance) {
        this.balance = balance;
    }

    public String getMember_expiration_date() {
        return member_expiration_date;
    }

    public void setMember_expiration_date(String member_expiration_date) {
        this.member_expiration_date = member_expiration_date;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getAccount_format() {
        return account_format;
    }

    public void setAccount_format(String account_format) {
        this.account_format = account_format;
    }

    public String getAvatar_path() {
        return avatar_path;
    }

    public void setAvatar_path(String avatar_path) {
        this.avatar_path = avatar_path;
    }

    public String getIs_pay_pass() {
        return is_pay_pass;
    }

    public void setIs_pay_pass(String is_pay_pass) {
        this.is_pay_pass = is_pay_pass;
    }

    public String getIs_super_member() {
        return is_super_member;
    }

    public void setIs_super_member(String is_super_member) {
        this.is_super_member = is_super_member;
    }

    public String getDefault_address() {
        return default_address;
    }

    public void setDefault_address(String default_address) {
        this.default_address = default_address;
    }

    @Override
    public String toString() {
        return "User{" +
                "m_id='" + m_id + '\'' +
                ", account='" + account + '\'' +
                ", nickname='" + nickname + '\'' +
                ", avatar='" + avatar + '\'' +
                ", balance='" + balance + '\'' +
                ", member_expiration_date='" + member_expiration_date + '\'' +
                ", status='" + status + '\'' +
                ", account_format='" + account_format + '\'' +
                ", avatar_path='" + avatar_path + '\'' +
                ", is_pay_pass='" + is_pay_pass + '\'' +
                ", is_super_member='" + is_super_member + '\'' +
                ", default_address='" + default_address + '\'' +
                '}';
    }
}
