package com.example.john.lifeclock10;

/**
 * Created by john on 2017/3/14.
 */

public class UtilsFinalArguments {

    public static long clickTime = 0; // 退出应用时第一次点击的时间

    //权限控制
    public static final int REQUEST_USUAL = 100;
    public static final int REQUEST_CAMERO = 101;   //调用相机权限
    public static final int REQUEST_WRITE_EXTERNAL_STORAGE = 102;
    public static final int REQUEST_READ_EXTERNAL_STORAGE = 103;
    public static final int REQUEST_ACCESS_COARSE_LOCATION = 104;    //获取大概位置的权限
    public static final int REQUEST_ACCESS_FINE_LOCATION = 105;    //获取精确位置的权限
    public static final int REQUEST_CHANGE_WIFI_STATE = 106;
    public static final int REQUEST_READ_PHONE_STATE = 107;

    //头像path
    public static String imagePathStatic;

    public static int IS_ADAPTED_ORIENTATION = 1; //是否地图自适应方向
    public static boolean isFirstLoc = true;  //标识，用于判断是否只显示一次定位信息和用户重新定位
    public static Boolean ifAnonymous = false;  //保存ifAnonymousCBox的值，表示是否匿名
}
