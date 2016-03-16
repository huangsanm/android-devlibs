package com.huashengmi.devlibs.app;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

/**
 * User: huangsanm@foxmail.com
 * Date: 2016-03-16
 * Time: 15:51
 */
public class BaseActivity extends AppCompatActivity {

    public BaseActivity mActivity;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mActivity = this;
    }
}
