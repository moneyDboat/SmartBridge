package com.captain.smartbridge.model;

import java.util.List;

/**
 * Created by Captain on 17/7/2.
 */

/**
 * gjdm : 200031
 * bhlx : [{"maxbhbd":5,"bhlxmc":"冲刷、掏空","bhlxdm":"bhlx_200031_1"},{"maxbhbd":5,"bhlxmc":"剥落、露筋","bhlxdm":"bhlx_200031_2"},{"maxbhbd":4,"bhlxmc":"冲蚀","bhlxdm":"bhlx_200031_3"},{"maxbhbd":4,"bhlxmc":"河底辅砌损坏","bhlxdm":"bhlx_200031_4"},{"maxbhbd":5,"bhlxmc":"沉降","bhlxdm":"bhlx_200031_5"},{"maxbhbd":5,"bhlxmc":"滑移和倾斜","bhlxdm":"bhlx_200031_6"},{"maxbhbd":5,"bhlxmc":"裂缝","bhlxdm":"bhlx_200031_7"}]
 */

public class BinghaiRes {
    private String gjdm;
    private List<BhlxBean> bhlx;

    public String getGjdm() {
        return gjdm;
    }

    public void setGjdm(String gjdm) {
        this.gjdm = gjdm;
    }

    public List<BhlxBean> getBhlx() {
        return bhlx;
    }

    public void setBhlx(List<BhlxBean> bhlx) {
        this.bhlx = bhlx;
    }

    public static class BhlxBean {
        /**
         * maxbhbd : 5
         * bhlxmc : 冲刷、掏空
         * bhlxdm : bhlx_200031_1
         */

        private int maxbhbd;
        private String bhlxmc;
        private String bhlxdm;

        public int getMaxbhbd() {
            return maxbhbd;
        }

        public void setMaxbhbd(int maxbhbd) {
            this.maxbhbd = maxbhbd;
        }

        public String getBhlxmc() {
            return bhlxmc;
        }

        public void setBhlxmc(String bhlxmc) {
            this.bhlxmc = bhlxmc;
        }

        public String getBhlxdm() {
            return bhlxdm;
        }

        public void setBhlxdm(String bhlxdm) {
            this.bhlxdm = bhlxdm;
        }
    }
}
