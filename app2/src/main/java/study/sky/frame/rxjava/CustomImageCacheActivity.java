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
import study.sky.frame.rxjava.imageLoader.CleanMessageUtil;
import study.sky.frame.rxjava.imageLoader.RxImageLoader;

public class CustomImageCacheActivity extends Activity {

    private Unbinder unbinder;

    @BindView(R.id.image_cache)
    ImageView imageView;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.text_show)
    TextView tvShow;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_custom_image_cache);
        unbinder = ButterKnife.bind(this);

        tvTitle.setText("自定义图片缓存框架");
        showCacheSize();

    }

    private void showCacheSize() {
        try {
            tvShow.setText(CleanMessageUtil.getTotalCacheSize(getApplicationContext()));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @OnClick({R.id.btn_show, R.id.btn_clear})
    public void onClick(View view) {

        switch (view.getId()) {
            case R.id.btn_show:
                imageView.setImageResource(R.mipmap.ic_launcher);
                RxImageLoader.with(this)
                        .load("http://dynamic-image.yesky.com/740x-/uploadImages/2014/289/01/IGS09651F94M.jpg")
                        .into(imageView);
                break;
            case R.id.btn_clear:
                CleanMessageUtil.clearAllCache(getApplicationContext());
                imageView.setImageResource(R.mipmap.ic_launcher);
                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
    }
}
