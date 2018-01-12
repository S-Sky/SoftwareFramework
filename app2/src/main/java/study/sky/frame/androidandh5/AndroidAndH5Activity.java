package study.sky.frame.androidandh5;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import study.sky.frame.R;

/**
 * Created by Administrator on 2018/1/11 0011.
 */

public class AndroidAndH5Activity extends Activity {

    private Unbinder unbinder;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_android_and_h5);
        unbinder = ButterKnife.bind(this);
    }

    @OnClick({R.id.btn_java_and_js, R.id.btn_js_call_java, R.id.btn_js_call_phone})
    public void onCklik(View view) {
        switch (view.getId()) {
            case R.id.btn_java_and_js: //Java代码和H5代码互调
                startActivity(new Intent(this, JavaAndJsCallActivity.class));
                break;
            case R.id.btn_js_call_java: //H5调用Android播放视频
                startActivity(new Intent(this, JsCallJavaVideoActivity.class));
                break;
            case R.id.btn_js_call_phone: //H5调用Android拨打电话
                startActivity(new Intent(this, JsCallJavaPhoneActivity.class));
                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
    }
}
