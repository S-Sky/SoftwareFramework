package study.sky.frame.androidandh5;

import android.app.Activity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.webkit.JavascriptInterface;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.EditText;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import study.sky.frame.R;

public class JavaAndJsCallActivity extends Activity {

    private Unbinder unBinder;

    @BindView(R.id.et_number)
    EditText et_number;
    @BindView(R.id.et_password)
    EditText et_password;
    private WebView webView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_java_and_js_call);
        unBinder = ButterKnife.bind(this);

        initWebView(); //初始化webView
    }

    @OnClick(R.id.btn_login)
    public void onClick(View view) {
        login();
    }

    /**
     * 登录
     */
    private void login() {
        //1、得到账号和密码
        String number = et_number.getText().toString().trim();
        String password = et_password.getText().toString().trim();
        //2、判断密码是否为空

        if (TextUtils.isEmpty(number) || TextUtils.isEmpty(password)) {
            Toast.makeText(this, "账号或密码不能为空", Toast.LENGTH_SHORT).show();
        } else {
//            initWebView();
            login(number);
        }
    }

    private void login(String number) {
        webView.loadUrl("javascript:javaCallJs(" + "'" + number + "'" + ")");
        setContentView(webView);
    }

    private void initWebView() {
        //加载网页--H5，Html  自定义浏览器  可以直接在webView中播放视频
        webView = new WebView(this);
        WebSettings webSettings = webView.getSettings();

        //设置支持javascript
        webSettings.setJavaScriptEnabled(true);
        //支持双击-前提是页面要支持才显示
        //webSettings.setUseWideViewPort(true);
        //支持缩放按钮-前提是月面要支持才显示
        //webSettings.setBuiltInZoomControls(true);
        //以后js通过Android 字段调用AndroidAndJSInterface中的任何方法
        webView.addJavascriptInterface(new AndroidAndJSInterface(), "Android");

        //设置客户端-不跳转到默认浏览器中
        webView.setWebViewClient(new WebViewClient());


        //加载网页
        webView.loadUrl("file:///android_asset/h5/html/JavaAndJavaScriptCall.html");
        //setContentView(webView);
    }

    class AndroidAndJSInterface {
        //将会被js调用
        @JavascriptInterface
        public void showToast() {
            Toast.makeText(JavaAndJsCallActivity.this, "JS调用Java代码", Toast.LENGTH_SHORT).show();
            webView.post(new Runnable() {
                @Override
                public void run() {
                    webView.loadUrl("javascript:showDialog()");
                    setContentView(webView);
                }
            });
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unBinder.unbind();
    }
}
