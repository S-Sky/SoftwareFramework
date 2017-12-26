package com.sky.studyglide;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.image)
    ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
    }

    @OnClick({R.id.btn_glide_base, R.id.btn_glide_list})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_glide_base:
                //加载网络图片
                /*Glide.with(this).load("http://images.csdn.net/20150817/1.jpg").into(imageView);*/

                //加载资源图片
                /*Glide.with(this).load(R.drawable.jiqiren).into(imageView);*/

                //加载本地图片
                /*String path = Environment.getExternalStorageDirectory().getAbsolutePath() + "/DCIM/Camera/IMG_20161002_115016.jpg";
                File file = new File(path);
                Uri uri = Uri.fromFile(file);
                Glide.with(this).load(uri).into(imageView);*/

                //加载网络gif
               /* String uri = "http://a.hiphotos.baidu.com/image/pic/item/0824ab18972bd407b564b0fd70899e510fb3090a.jpg";
                Glide.with(this)
                        .load(uri)
                        .placeholder(R.drawable.ic_launcher_background)
                        .into(imageView);*/

                //加载资源Gif
                Glide.with(this)
                        .load(R.drawable.image_gif)
                        .asGif()
                        .placeholder(R.mipmap.ic_launcher) //占位符(gif还没显示出来的时候显示)
                        .into(imageView);

                //加载本地gif与加载本地图片类型,只是以加载gif的方式加载

                //加载本地的小视频和快照:显示出来的是视屏的首张图片
               /* String videoPath = Environment.getExternalStorageDirectory().getAbsolutePath() + "/DCIM/Camera/VID_20171001_211247.mp4";
                File file = new File(videoPath);
                Glide.with(this)
                        .load(Uri.fromFile(file))
                        .placeholder(R.mipmap.ic_launcher)
                        .into(imageView);*/

                //设置缩略比例,然后在加载缩略图,在加载原图
                /*String path = Environment.getExternalStorageDirectory().getAbsolutePath() + "/DCIM/Camera/IMG_20161002_115016.jpg";
                Glide.with(this)
                        .load(new File(path))
                        .thumbnail(0.1f)
                        .centerCrop()
                        .placeholder(R.mipmap.ic_launcher_round)
                        .into(imageView);*/

                //先建立一个缩略图对象,然后先加载缩略图,在加载原图
               /* String path = Environment.getExternalStorageDirectory().getAbsolutePath() + "/DCIM/Camera/IMG_20161002_115016.jpg";
                String videoPath = Environment.getExternalStorageDirectory().getAbsolutePath() + "/DCIM/Camera/VID_20171001_211247.mp4";
                DrawableRequestBuilder thumbnailRequest = Glide.with(this).load(new File(path));
                Glide.with(this)
                        .load(Uri.fromFile(new File(videoPath)))
                        .thumbnail(thumbnailRequest)
                        .centerCrop()
                        .placeholder(R.mipmap.ic_launcher_round)
                        .into(imageView);*/

                break;
            case R.id.btn_glide_list:
                startActivity(new Intent(this, GlideRecyclerViewActivity.class));
                break;
        }
    }
}
