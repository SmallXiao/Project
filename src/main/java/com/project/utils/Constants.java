package com.project.utils;


import java.util.ArrayList;
import java.util.List;

/**
 * Created by 高航 on 2014/8/5.
 */
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
    public static enum Menu_Key {
        /** 协同待办 */
        V1001_COL_TODO,
        /** 待开会议 */
        V1002_MEET_TODO,
        /** 新闻 */
        V2001_NEWS,
        /** 公告 */
        V2002_BULLETIN,
        /** 扫码登录 */
        V2003_QRCODE,
        /** 找人 */
        V3001_SEARCH_MEM,
        /** 今日事项 */
        V1003_DATE,
        /**帮助*/
        V3004_HELP
    }
    public static enum WORKINDEX_TYPE {

        工作狂人,

        工作达人,

        工作能手,

        工作新兵
    }
}
