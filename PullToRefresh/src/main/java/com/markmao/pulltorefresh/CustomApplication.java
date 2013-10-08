package com.markmao.pulltorefresh;

import android.app.Application;
import android.content.Context;

/**
 * 自定义的Application，用于存储一些参数
 *
 * @author MarkMjw
 * @date 2013-10-08
 */
public class CustomApplication extends Application {
    public static Context gContext;

    @Override
    public void onCreate() {
        super.onCreate();

        gContext = this;
    }
}
