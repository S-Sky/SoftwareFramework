package study.sky.frame.androidandh5;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.webkit.JavascriptInterface;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import study.sky.frame.R;

public class JsCallJavaPhoneActivity extends Activity {


    private Unbinder unbinder;

    @BindView(R.id.web_view)
    WebView webView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_js_call_java_phone);
        unbinder = ButterKnife.bind(this);
        initWebView();
    }

    private void initWebView() {
        //加载网页--H5，Html  自定义浏览器  可以直接在webView中播放视频
        // webView = new WebView(this);
        WebSettings webSettings = webView.getSettings();

        //设置支持javascript
        webSettings.setJavaScriptEnabled(true);
        //支持双击-前提是页面要支持才显示
        //webSettings.setUseWideViewPort(true);
        //支持缩放按钮-前提是月面要支持才显示
        //webSettings.setBuiltInZoomControls(true);

        //设置客户端-不跳转到默认浏览器中
//        webView.setWebViewClient(new WebViewClient());

        //以后js通过Android 字段调用AndroidAndJSInterface中的任何方法
        webView.addJavascriptInterface(new AndroidAndJSInterface(), "Android");

        //加载网页
        webView.loadUrl("file:///android_asset/h5/html/JsCallJavaCallPhone.html");
        //setContentView(webView);
    }

    class AndroidAndJSInterface {
        /**
         * 被js调用,加载联系人数据
         */
        @JavascriptInterface
        public void showcontacts() {
            // 下面的代码建议在子线程中调用
//            String json = "[{\"name\":\"阿福\", \"phone\":\"18600012345\"}]";
//            // 调用JS中的方法
//            webView.loadUrl("javascript:show('" + json + "')");

            webView.post(new Runnable() {
                @Override
                public void run() {
                    String json = "[{\"name\":\"阿福\", \"phone\":\"18600012345\"}]";
                    // 调用JS中的方法
                    webView.loadUrl("javascript:show('" + json + "')");
                }
            });
        }

        @JavascriptInterface
        public void call(String phone) {
            Intent intent = new Intent();
            intent.setAction(Intent.ACTION_CALL);
            intent.setData(Uri.parse("tel:"+phone));
            if (ActivityCompat.checkSelfPermission(JsCallJavaPhoneActivity.this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                return;
            }
            startActivity(intent);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
    }
}
