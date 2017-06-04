package com.captain.smartbridge.model;

/**
 * Created by Captain on 17/6/5.
 */

public class Info {
    /**
     * username : system
     * roleType : 1
     * registerDate : 2017-01-01
     * headPortrait : 2ad5d1a1-5505-4747-95cb-87214beaf97d.png
     * sf : 江苏省
     * userId : 1
     * inspectionDepartmentDM : null
     * phoneNumber : 15751867155
     * cs : 南京市
     * lastLoginTime : 2017-06-05 00:49:05
     * password : 123456
     * nickname : 系统管理员
     * email : 123@qq.com
     */

    private String username;
    private int roleType;
    private String registerDate;
    private String headPortrait;
    private String sf;
    private int userId;
    private Object inspectionDepartmentDM;
    private String phoneNumber;
    private String cs;
    private String lastLoginTime;
    private String password;
    private String nickname;
    private String email;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public int getRoleType() {
        return roleType;
    }

    public void setRoleType(int roleType) {
        this.roleType = roleType;
    }

    public String getRegisterDate() {
        return registerDate;
    }

    public void setRegisterDate(String registerDate) {
        this.registerDate = registerDate;
    }

    public String getHeadPortrait() {
        return headPortrait;
    }

    public void setHeadPortrait(String headPortrait) {
        this.headPortrait = headPortrait;
    }

    public String getSf() {
        return sf;
    }

    public void setSf(String sf) {
        this.sf = sf;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public Object getInspectionDepartmentDM() {
        return inspectionDepartmentDM;
    }

    public void setInspectionDepartmentDM(Object inspectionDepartmentDM) {
        this.inspectionDepartmentDM = inspectionDepartmentDM;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getCs() {
        return cs;
    }

    public void setCs(String cs) {
        this.cs = cs;
    }

    public String getLastLoginTime() {
        return lastLoginTime;
    }

    public void setLastLoginTime(String lastLoginTime) {
        this.lastLoginTime = lastLoginTime;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
