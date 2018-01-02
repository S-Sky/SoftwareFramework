package study.sky.studyfresco;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.backends.pipeline.PipelineDraweeController;
import com.facebook.drawee.view.SimpleDraweeView;
import com.facebook.imagepipeline.request.BasePostprocessor;
import com.facebook.imagepipeline.request.ImageRequest;
import com.facebook.imagepipeline.request.ImageRequestBuilder;
import com.facebook.imagepipeline.request.Postprocessor;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Administrator on 2018/1/2 0002.
 */

public class FrescoModifyActivity extends Activity {

    @BindView(R.id.fresco_image)
    SimpleDraweeView simpleDraweeView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fresco_modify);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.btn_fresco_modify)
    public void onClick(View view) {
        Uri uri = Uri.parse("http://pic1.sc.chinaz.com/files/pic/pic9/201712/bpic4641.jpg");

        Postprocessor postprocessor = new BasePostprocessor() {
            @Override
            public String getName() {
                return "postprocessor";
            }

            @Override
            public void process(Bitmap bitmap) {
                //super.process(bitmap);
                for (int i = 0; i < bitmap.getWidth(); i += 2) { //间隔一个像素
                    for (int j = 0; j < bitmap.getHeight(); j += 2) {
                        bitmap.setPixel(i, j, Color.RED);
                    }
                }
            }
        };

        ImageRequest request = ImageRequestBuilder.newBuilderWithSource(uri)
                .setPostprocessor(postprocessor)
                .build();
        PipelineDraweeController control = (PipelineDraweeController) Fresco.newDraweeControllerBuilder()
                .setOldController(simpleDraweeView.getController())
                .setImageRequest(request)
                .build();
        simpleDraweeView.setController(control);
    }
}
