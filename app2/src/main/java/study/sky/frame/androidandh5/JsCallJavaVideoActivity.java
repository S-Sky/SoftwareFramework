package study.sky.frame.androidandh5;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.webkit.JavascriptInterface;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import study.sky.frame.R;

public class JsCallJavaVideoActivity extends Activity {

    private Unbinder unbinder;
    @BindView(R.id.web_view)
    WebView webView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_js_call_java_video);
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
        //以后js通过Android 字段调用AndroidAndJSInterface中的任何方法
        webView.addJavascriptInterface(new AndroidAndJSInterface(), "android");

        //设置客户端-不跳转到默认浏览器中
        webView.setWebViewClient(new WebViewClient());


        //加载网页
        webView.loadUrl("file:///android_asset/h5/html/RealNetJSCallJavaActivity.html");
        //setContentView(webView);
    }

    class AndroidAndJSInterface {
        @JavascriptInterface
        public void playVideo(int id, String videoUrl, String title) {

            //把所有的播放调起来
            Intent intent = new Intent();
            intent.setDataAndType(Uri.parse(videoUrl), "video/*");
            startActivity(intent);

            Toast.makeText(JsCallJavaVideoActivity.this, "videoUrl" + videoUrl, Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
    }
}
