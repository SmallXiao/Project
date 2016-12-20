package com.project.utils;


import java.util.ArrayList;
import java.util.List;


public final class Constants {

    public static String SEARCH_PER_KEY = "找";
    public static String REMOVE_BINDING = "JCBD";
    public static String CONTEXT_TEXT = "";
    public static String WX_TOKEN = "weixin";
    public static String SEND_MSG = "@";
    public static String FANKUI = "反馈";
    public static String BAIKE = "百科";
    public static boolean IS_ENTERPRISE = true;
    public static String LOCAL_MODEL = "local";
    public static String CLOUD_MODEL = "cloud";

    public static List<String> getPointlist() {
        return pointlist;
    }

    public static void setPointlist(List<String> pointlist) {
        Constants.pointlist = pointlist;
    }
    public static void addPointlist(List<String> pointlist) {
        Constants.pointlist.addAll(pointlist);
    }

    public static List<String> pointlist = new ArrayList<String>();
    
}
