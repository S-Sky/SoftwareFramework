package study.sky.studyfresco;

import android.app.Activity;
import android.graphics.drawable.Animatable;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.interfaces.DraweeController;
import com.facebook.drawee.view.SimpleDraweeView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Administrator on 2017/12/28 0028.
 */

public class FrescoGifActivity extends Activity {

    @BindView(R.id.fresco_image)
    SimpleDraweeView simpleDraweeView;
    private Animatable animatable;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gif);
        ButterKnife.bind(this);
    }

    /**
     * 使用动画必须添加依赖:implementation 'com.facebook.fresco:animated-gif:1.7.1'
     *
     * @param view
     */
    @OnClick({R.id.btn_fresco_gif, R.id.btn_fresco_gif_start, R.id.btn_fresco_gif_stop})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_fresco_gif:
                Uri uri = Uri.parse("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1514465791862&di=419ed4c9c724fc5a84ef0ee84bde4261&imgtype=0&src=http%3A%2F%2Fww2.sinaimg.cn%2Flarge%2F85cccab3gw1eteni8dajkg20go0841kx.jpg");
                DraweeController controller = Fresco.newDraweeControllerBuilder()
                        .setUri(uri)
                        .setAutoPlayAnimations(false) //是否自动播放动画,false表示不会自动播放动画
                        .setOldController(simpleDraweeView.getController()) //缓存,减少内存,复用
                        .build();
                simpleDraweeView.setController(controller);
                break;
            case R.id.btn_fresco_gif_start:
                //拿到动画的对象
                animatable = simpleDraweeView.getController().getAnimatable();

                if (animatable != null && !animatable.isRunning()) {
                    animatable.start();
                }
                break;
            case R.id.btn_fresco_gif_stop:
                //拿到动画的对象
                animatable = simpleDraweeView.getController().getAnimatable();
                if (animatable != null && animatable.isRunning()) {
                    animatable.stop();
                }
                break;
        }
    }
}
