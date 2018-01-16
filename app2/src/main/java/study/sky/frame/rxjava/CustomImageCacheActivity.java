package study.sky.frame.rxjava;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import study.sky.frame.R;
import study.sky.frame.rxjava.imageLoader.RxImageLoader;

public class CustomImageCacheActivity extends Activity {

    private Unbinder unbinder;

    @BindView(R.id.image_cache)
    ImageView imageView;
    @BindView(R.id.tv_title)
    TextView tvTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_custom_image_cache);
        unbinder = ButterKnife.bind(this);

        tvTitle.setText("自定义图片缓存框架");

    }

    @OnClick(R.id.btn_show)
    public void onClick(View view) {
        imageView.setImageResource(R.mipmap.ic_launcher);
        RxImageLoader.with(this)
                .load("http://dynamic-image.yesky.com/740x-/uploadImages/2014/289/01/IGS09651F94M.jpg")
                .into(imageView);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
    }
}
