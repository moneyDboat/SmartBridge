package com.captain.smartbridge.model;

/**
 * Created by fish on 17-6-5.
 */

public class InfoRes {
    /**
     * content : {"username":"system","roleType":1,"registerDate":"2017-01-01","headPortrait":"2ad5d1a1-5505-4747-95cb-87214beaf97d.png","sf":"江苏省","userId":1,"inspectionDepartmentDM":null,"phoneNumber":"15751867155","cs":"南京市","lastLoginTime":"2017-06-05 17:15:49","password":"123456","nickname":"系统管理员","email":"123@qq.com"}
     * code : 200
     */

    private Info content;
    private int code;

    public Info getContent() {
        return content;
    }

    public void setContent(Info content) {
        this.content = content;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }
}
