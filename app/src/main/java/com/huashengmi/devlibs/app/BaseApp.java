package com.huashengmi.devlibs.app;

import android.app.Application;

import com.facebook.drawee.backends.pipeline.Fresco;

/**
 * User: huangsanm@foxmail.com
 * Date: 2016-03-16
 * Time: 15:50
 */
public class BaseApp extends Application {

    @Override
    public void onCreate() {
        Fresco.initialize(this);
        super.onCreate();

    }
}
