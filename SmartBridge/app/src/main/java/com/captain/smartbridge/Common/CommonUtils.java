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
}
