package study.sky.studyfresco;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.backends.pipeline.PipelineDraweeController;
import com.facebook.drawee.interfaces.DraweeController;
import com.facebook.drawee.view.SimpleDraweeView;
import com.facebook.imagepipeline.common.ResizeOptions;
import com.facebook.imagepipeline.common.RotationOptions;
import com.facebook.imagepipeline.request.ImageRequest;
import com.facebook.imagepipeline.request.ImageRequestBuilder;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Administrator on 2018/1/2 0002.
 */

public class FrescoResizeActivity extends Activity {

    @BindView(R.id.fresco_image)
    SimpleDraweeView simpleDraweeView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resize);
        ButterKnife.bind(this);
    }

    @OnClick({R.id.btn_fresco_resize, R.id.btn_fresco_rotate})
    public void onCkilc(View view) {
        switch (view.getId()) {
            case R.id.btn_fresco_resize: //修改内存中图片的大小
                Uri uri = Uri.parse("http://pic1.sc.chinaz.com/files/pic/pic9/201712/bpic4641.jpg");
                //图片的请求
                ImageRequest request = ImageRequestBuilder.newBuilderWithSource(uri)
                        .setResizeOptions(new ResizeOptions(50, 50))
                        .build();
                //控制图片的加载
                PipelineDraweeController controller = (PipelineDraweeController) Fresco.newDraweeControllerBuilder()
                        .setOldController(simpleDraweeView.getController())
                        .setImageRequest(request)
                        .build();
                simpleDraweeView.setController(controller);

                break;
            case R.id.btn_fresco_rotate: //旋转图片
                Uri uri1 = Uri.parse("http://pic1.sc.chinaz.com/files/pic/pic9/201712/bpic4641.jpg");
                ImageRequest request1 = ImageRequestBuilder.newBuilderWithSource(uri1)
                        .setRotationOptions(RotationOptions.forceRotation(RotationOptions.ROTATE_90))
                        .build();
                DraweeController controller1 = Fresco.newDraweeControllerBuilder()
                        .setOldController(simpleDraweeView.getController())
                        .setImageRequest(request1)
                        .build();
                simpleDraweeView.setController(controller1);

                break;
        }
    }
}
