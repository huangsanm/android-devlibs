package com.huashengmi.devlibs;

import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;

import com.facebook.drawee.view.SimpleDraweeView;
import com.huashengmi.devlibs.app.BaseActivity;

public class MainActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        SimpleDraweeView imageView = (SimpleDraweeView) findViewById(R.id.my_image_view);
        imageView.setImageURI(Uri.parse("http://imgstore.cdn.sogou.com/app/a/100540002/834169.jpg"));
    }
}
