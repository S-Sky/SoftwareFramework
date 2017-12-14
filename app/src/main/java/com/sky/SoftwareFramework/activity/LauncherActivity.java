package com.sky.SoftwareFramework.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;

import com.sky.SoftwareFramework.R;

/**
 * Created by Administrator on 2017/12/14 0014.
 */

public class LauncherActivity extends Activity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_launcher);
        //两秒钟后启动主页面,关闭启动页
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                //在主线程中执行
                startActivity(new Intent(LauncherActivity.this, MainActivity.class));
                finish();
            }
        }, 2000);
    }
}
