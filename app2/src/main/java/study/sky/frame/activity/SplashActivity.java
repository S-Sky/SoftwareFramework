package study.sky.frame.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import study.sky.frame.R;
import study.sky.frame.utils.ChannelUtil;

/**
 * Created by Administrator on 2017/12/14 0014.
 */

public class SplashActivity extends AppCompatActivity {


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        String channel = ChannelUtil.getChannel(this, "defaultChannel");
        Toast.makeText(this, "channel==" + channel, Toast.LENGTH_SHORT).show();
        init();
    }

    private void init() {
        mHandler.sendEmptyMessageDelayed(1, 2000);
    }

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            //如果当前activity已经退出,不处理mHandler中的消息
            if (isFinishing()) {
                return;
            }
            startActivity(new Intent(SplashActivity.this, MainActivity.class));
            finish();
        }
    };

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //页面销毁时销毁handler
        mHandler.removeCallbacksAndMessages(null);
    }
}
