package study.sky.studypicasso;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.image_picasso_1)
    ImageView imageView1;
    @BindView(R.id.image_picasso_2)
    ImageView imageView2;
    @BindView(R.id.image_picasso_3)
    ImageView imageView3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
    }

    @OnClick({R.id.btn_picasso, R.id.btn_picasso_listview})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_picasso:
                //基本用法
                //普通加载图片
                Picasso.with(MainActivity.this)
                        .load("http://images.csdn.net/20150817/1.jpg")
                        .into(imageView1);
                //裁剪的方式加载图片
                Picasso.with(MainActivity.this)
                        .load("http://images.csdn.net/20150817/1.jpg")
                        .resize(100, 100)
                        .into(imageView2);
                //旋转180度
                Picasso.with(MainActivity.this)
                        .load("http://images.csdn.net/20150817/1.jpg")
                        .rotate(180)
                        .into(imageView3);
                break;
            case R.id.btn_picasso_listview:
                startActivity(new Intent(this, PicassoListViewActivity.class));
                break;
        }
    }
}
