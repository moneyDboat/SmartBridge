package com.captain.smartbridge.Common;

/**
 * Created by fish on 17-4-24.
 */

public class CommonUtils {
    public static boolean isEmpty(String str) {
        if (str == null || str.length() == 0 || str.equalsIgnoreCase("null") || str.isEmpty() || str.equals("")) {
            return true;
        } else {
            return false;
        }
    }

    public static String[] types = {"","系统管理员","桥梁管理单位","桥梁检测录入单位","科研机构","学生","游客","施工方"};

    public static String getStatus(String status){
        switch (status){
            case ("0"):
                return "待接收";
            case ("1"):
                return "检测中";
            case ("2"):
                return "已完成";
        }
        return "";
    }

}
