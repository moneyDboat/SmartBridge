package com.captain.smartbridge.model;

import java.util.List;

/**
 * Created by Captain on 17/6/5.
 */

public class MapRes {

    /**
     * content : [{"qldm":"G00010008","wd":"31.216677","qx":"弋江区","cs":"芜湖市","fs":"安徽省","qlmc":"望东大桥","jd":"118.335970","lxh":"L00010008"},{"qldm":"G00010010","wd":"31.392480","qx":"鸠江区","cs":"芜湖市","fs":"安徽省","qlmc":"芜湖长江大桥","jd":"118.364716","lxh":"G00010010"}]
     * code : 200
     */

    private int code;
    private List<MapBridge> content;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public List<MapBridge> getContent() {
        return content;
    }

    public void setContent(List<MapBridge> content) {
        this.content = content;
    }

    public static class MapBridge {
        /**
         * qldm : G00010008
         * wd : 31.216677
         * qx : 弋江区
         * cs : 芜湖市
         * fs : 安徽省
         * qlmc : 望东大桥
         * jd : 118.335970
         * lxh : L00010008
         */

        private String qldm;
        private String wd;
        private String qx;
        private String cs;
        private String fs;
        private String qlmc;
        private String jd;
        private String lxh;

        public String getQldm() {
            return qldm;
        }

        public void setQldm(String qldm) {
            this.qldm = qldm;
        }

        public String getWd() {
            return wd;
        }

        public void setWd(String wd) {
            this.wd = wd;
        }

        public String getQx() {
            return qx;
        }

        public void setQx(String qx) {
            this.qx = qx;
        }

        public String getCs() {
            return cs;
        }

        public void setCs(String cs) {
            this.cs = cs;
        }

        public String getFs() {
            return fs;
        }

        public void setFs(String fs) {
            this.fs = fs;
        }

        public String getQlmc() {
            return qlmc;
        }

        public void setQlmc(String qlmc) {
            this.qlmc = qlmc;
        }

        public String getJd() {
            return jd;
        }

        public void setJd(String jd) {
            this.jd = jd;
        }

        public String getLxh() {
            return lxh;
        }

        public void setLxh(String lxh) {
            this.lxh = lxh;
        }
    }
}
