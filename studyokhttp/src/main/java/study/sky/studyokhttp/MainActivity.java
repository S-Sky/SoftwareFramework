package study.sky.studyokhttp;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.BitmapCallback;
import com.zhy.http.okhttp.callback.FileCallBack;
import com.zhy.http.okhttp.callback.StringCallback;

import java.io.File;
import java.io.IOException;

import okhttp3.Call;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private static final int GET = 1;
    private static final int POST = 2;
    private Button btn_okhttp;
    private Button btn_okhttputils;
    private Button btn_downloadfile;
    private Button btn_downloadimage;
    private Button btn_image_list;
    private TextView textView;
    private ProgressBar mProgressBar;
    private ImageView imageView;

    OkHttpClient client = new OkHttpClient();
    public static final MediaType JSON
            = MediaType.parse("application/json; charset=utf-8");
    private static final String TAG = MainActivity.class.getSimpleName();

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case GET:
                    if (msg.obj != null) {
                        String result = (String) msg.obj;
                        textView.setText(result);
                    }
                    break;
                case POST:
                    if (msg.obj != null) {
                        String result = (String) msg.obj;
                        textView.setText(result);
                    }
                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btn_okhttp = findViewById(R.id.btn);
        btn_okhttputils = findViewById(R.id.btn_get_okhttputils);
        btn_downloadfile = findViewById(R.id.btn_downloadfile);
        btn_downloadimage = findViewById(R.id.btn_downloadimage);
        btn_image_list = findViewById(R.id.btn_image_list);
        textView = findViewById(R.id.tv_result);
        mProgressBar = findViewById(R.id.progressBar);
        imageView = findViewById(R.id.image);

        btn_okhttp.setOnClickListener(this);
        btn_okhttputils.setOnClickListener(this);
        btn_downloadfile.setOnClickListener(this);
        btn_downloadimage.setOnClickListener(this);
        btn_image_list.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn: //使用原生的OKHttp请求网络数据,get和post
                textView.setText("");
                //getDataFromPost();//post请求
                getDataFromGet(); //get请求
                break;
            case R.id.btn_get_okhttputils: //使用okhttp-utils请求数据
                getDataGetByOKHttpUtils();
                break;
            case R.id.btn_downloadfile: //下载文件
                downloadFile();
                break;
            case R.id.btn_downloadimage: //请求单张图片
                getImage();
                break;
            case R.id.btn_image_list: //请求列表图片
                startActivity(new Intent(MainActivity.this, OKHttpListActivity.class));
                break;
        }
    }

    /**
     * get请求网络数据
     */
    private void getDataFromGet() {
        new Thread() {
            @Override
            public void run() {
                super.run();
                try {
                    String result = get("http://v.juhe.cn/weather/index?cityname=%E8%A5%BF%E5%AE%89&dtype=&format=&key=065059c0265c98676f6755602f7c2c8e");
                    Log.e("TAG", result);
                    Message msg = Message.obtain(); //obtain从全局池返回一个新的消息实例。允许我们在很多情况下避免分配新对象。
                    msg.what = GET;
                    msg.obj = result;
                    mHandler.sendMessage(msg);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }.start();
    }

    /**
     * OKHttp3的get请求
     * 直接使用okhttp进行网络请求需要在子线程中进行
     *
     * @param url 网络连接
     * @return
     * @throws IOException
     */
    private String get(String url) throws IOException {
        Request request = new Request.Builder()
                .url(url)
                .build();
        Response response = client.newCall(request).execute();
        return response.body().string();
    }

    /**
     * post请求网络数据
     */
    private void getDataFromPost() {
        new Thread() {
            @Override
            public void run() {
                super.run();
                try {
                    String result = post("http://v.juhe.cn/weather/index?cityname=%E8%A5%BF%E5%AE%89&dtype=&format=&key=065059c0265c98676f6755602f7c2c8e", "");
                    Log.e("TAG", result);
                    Message msg = Message.obtain(); //obtain从全局池返回一个新的消息实例。允许我们在很多情况下避免分配新对象。
                    msg.what = POST;
                    msg.obj = result;
                    mHandler.sendMessage(msg);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }.start();
    }

    /**
     * OKHttp3的post请求
     * 需要在子线程中请求
     *
     * @param url
     * @param json
     * @return
     * @throws IOException
     */
    private String post(String url, String json) throws IOException {
        RequestBody body = RequestBody.create(JSON, json);
        Request request = new Request.Builder()
                .url(url)
                .post(body)
                .build();
        Response response = client.newCall(request).execute();
        return response.body().string();
    }

    /**
     * 使用OKhttp-utils的get请求文本
     * post请求文本类似get
     */
    private void getDataGetByOKHttpUtils() {
        String url = "http://v.juhe.cn/weather/index?cityname=%E8%A5%BF%E5%AE%89&dtype=&format=&key=065059c0265c98676f6755602f7c2c8e";
        OkHttpUtils.get()
                .url(url)
                .id(100)
                .build()
                .execute(new MyStringCallback());
    }

    public class MyStringCallback extends StringCallback {
        @Override
        public void onBefore(Request request, int id) {
            setTitle("loading...");
        }

        @Override
        public void onAfter(int id) {
            setTitle("Sample-okHttp");
        }

        @Override
        public void onError(Call call, Exception e, int id) {
            e.printStackTrace();
            textView.setText("onError:" + e.getMessage());
        }

        @Override
        public void onResponse(String response, int id) {
            Log.e(TAG, "onResponse：complete");
            textView.setText("onResponse:" + response);

            switch (id) {
                case 100:
                    Toast.makeText(MainActivity.this, "http", Toast.LENGTH_SHORT).show();
                    break;
                case 101:
                    Toast.makeText(MainActivity.this, "https", Toast.LENGTH_SHORT).show();
                    break;
            }
        }

        @Override
        public void inProgress(float progress, long total, int id) {
            Log.e(TAG, "inProgress:" + progress);
            //       mProgressBar.setProgress((int) (100 * progress));
        }
    }

    /**
     * 使用okhttp-utils下载大文件
     */
    public void downloadFile() {
        String url = "https://github.com/hongyangAndroid/okhttp-utils/blob/master/okhttputils-2_6_2.jar?raw=true";
        OkHttpUtils//
                .get()//
                .url(url)//
                .build()//
                .execute(new FileCallBack(Environment.getExternalStorageDirectory().getAbsolutePath(), "okhttputils-2_6_2.jar")//
                {

                    @Override
                    public void onBefore(Request request, int id) {
                    }

                    @Override
                    public void inProgress(float progress, long total, int id) {
                        mProgressBar.setProgress((int) (100 * progress));
                        Log.e(TAG, "inProgress :" + (int) (100 * progress));
                    }

                    @Override
                    public void onError(Call call, Exception e, int id) {
                        Log.e(TAG, "onError :" + e.getMessage());
                    }

                    @Override
                    public void onResponse(File file, int id) {
                        Log.e(TAG, "onResponse :" + file.getAbsolutePath());
                    }
                });
    }

    public void getImage() {
        textView.setText("");
        String url = "http://images.csdn.net/20150817/1.jpg";
        OkHttpUtils
                .get()//
                .url(url)//
                .tag(this)//
                .build()//
                .connTimeOut(20000) //连接超时
                .readTimeOut(20000) //读取超时
                .writeTimeOut(20000)
                .execute(new BitmapCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        textView.setText("onError:" + e.getMessage());
                    }

                    @Override
                    public void onResponse(Bitmap bitmap, int id) {
                        Log.e("TAG", "onResponse：complete");
                        imageView.setImageBitmap(bitmap);
                    }
                });
    }
}
