package study.sky.studyvolley;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.ImageRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.NetworkImageView;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private Button btn_get;
    private Button btn_post;
    private Button btn_json;
    private Button btn_ImageRequest;
    private Button btn_ImageLoader;
    private Button btn_NetworkImageView;
    private ImageView iv_volley_image;
    private NetworkImageView iv_networkimageview;
    private TextView tv_volley_text;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
    }

    private void initView() {
        btn_get = findViewById(R.id.btn_get);
        btn_post = findViewById(R.id.btn_post);
        btn_json = findViewById(R.id.btn_json);
        btn_ImageRequest = findViewById(R.id.btn_ImageRequest);
        btn_ImageLoader = findViewById(R.id.btn_ImageLoader);
        btn_NetworkImageView = findViewById(R.id.btn_NetworkImageView);

        iv_volley_image = findViewById(R.id.tv_volley_image);
        iv_networkimageview = findViewById(R.id.iv_networkimageview);
        tv_volley_text = findViewById(R.id.tv_volley_text);

        btn_get.setOnClickListener(this);
        btn_post.setOnClickListener(this);
        btn_json.setOnClickListener(this);
        btn_ImageRequest.setOnClickListener(this);
        btn_ImageLoader.setOnClickListener(this);
        btn_NetworkImageView.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_get:
                volleyGet();
                break;
            case R.id.btn_post:
                volleyPost();
                break;
            case R.id.btn_json:
                volleyJson();
                break;
            case R.id.btn_ImageRequest:
                volleyImageRequest();
                break;
            case R.id.btn_ImageLoader:
                volleyImageLoader();
                break;
            case R.id.btn_NetworkImageView:
                volleyNetworkImageView();
                break;
        }
    }

    private void volleyNetworkImageView() {
        iv_networkimageview.setVisibility(View.VISIBLE);
        //创建一个请求队列
        RequestQueue requestQueue = Volley.newRequestQueue(MainActivity.this);
        //创建一个imageloader (此时要求带缓存)
        ImageLoader imageLoader = new ImageLoader(requestQueue, new BitmapCache());
        //设置默认图片和错误图片
        iv_networkimageview.setDefaultImageResId(R.mipmap.ic_launcher);
        iv_networkimageview.setErrorImageResId(R.mipmap.ic_launcher_round);

        //加载图片
        String url = "http://images.csdn.net/20150817/1.jpg";
        iv_networkimageview.setImageUrl(url, imageLoader);

    }

    private void volleyImageLoader() {
        RequestQueue requestQueue = Volley.newRequestQueue(MainActivity.this);
        //创建一个imageloader
        //这是没有做缓存处理
        ImageLoader imageLoader = new ImageLoader(requestQueue, new ImageLoader.ImageCache() {
            //这里是对获取到的图片进行缓存处理
            @Override
            public Bitmap getBitmap(String s) {
                return null;
            }

            @Override
            public void putBitmap(String s, Bitmap bitmap) {

            }
        });
        //ImageLoader imageLoader = new ImageLoader(requestQueue, new BitmapCache());
        //加载图片
        String url = "http://images.csdn.net/20150817/1.jpg";
        iv_volley_image.setVisibility(View.VISIBLE);
        ImageLoader.ImageListener imageListener = imageLoader.getImageListener(iv_volley_image, R.mipmap.ic_launcher, R.mipmap.ic_launcher_round);
        imageLoader.get(url, imageListener);
    }

    private void volleyImageRequest() {
        RequestQueue requestQueue = Volley.newRequestQueue(MainActivity.this);
        //创建图片请求
        String url = "http://images.csdn.net/20150817/1.jpg";
        ImageRequest imageRequest = new ImageRequest(url, new Response.Listener<Bitmap>() {
            @Override
            public void onResponse(Bitmap bitmap) {
                iv_volley_image.setVisibility(View.VISIBLE);
                iv_volley_image.setImageBitmap(bitmap);
            }
        }, 0, 0, Bitmap.Config.RGB_565, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                tv_volley_text.setText("加载错误:" + volleyError);
            }
        });
        requestQueue.add(imageRequest);
    }

    private void volleyJson() {
        //1、创建一个请求队列
        RequestQueue requestQueue = Volley.newRequestQueue(MainActivity.this);
        //2、创建一个请求(普通请求即可）
        String url = "http://v.juhe.cn/weather/index?cityname=%E8%A5%BF%E5%AE%89&dtype=&format=&key=065059c0265c98676f6755602f7c2c8e";
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject jsonObject) {
                tv_volley_text.setText(jsonObject.toString());
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                tv_volley_text.setText("加载错误:" + volleyError);
            }
        });
        //3、将请求加入队列
        requestQueue.add(jsonObjectRequest);
    }

    private void volleyPost() {
        //1、创建一个请求队列
        RequestQueue requestQueue = Volley.newRequestQueue(MainActivity.this);
        //2、创建一个post请求
        String url = "http://v.juhe.cn/weather/index?cityname=%E8%A5%BF%E5%AE%89&dtype=&format=";
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String s) {
                tv_volley_text.setText(s);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                tv_volley_text.setText("加载错误:" + volleyError);
            }
        }) { //在这个方法中添加请求参数
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> map = new HashMap<String, String>();
                map.put("key", "065059c0265c98676f6755602f7c2c8e");
                return map;
            }
        };
        //3、将创建的的请求添加到队列中
        requestQueue.add(stringRequest);
    }

    private void volleyGet() {
        //1、创建一个请求队列
        RequestQueue requestQueue = Volley.newRequestQueue(MainActivity.this);
        //2、创建一个请求
        String url = "http://v.juhe.cn/weather/index?cityname=%E8%A5%BF%E5%AE%89&dtype=&format=&key=065059c0265c98676f6755602f7c2c8e";
        StringRequest stringRequest = new StringRequest(url, new Response.Listener<String>() {
            //正确接受数据回调
            @Override
            public void onResponse(String s) {
                tv_volley_text.setText(s);
            }
        }, new Response.ErrorListener() {// 发生异常后的监听回调
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                tv_volley_text.setText("加载错误:" + volleyError);
            }
        });
        //3、将创建的请求添加到请求队列中
        requestQueue.add(stringRequest);
    }
}
