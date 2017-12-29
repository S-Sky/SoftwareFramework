package study.sky.studyfresco;

import android.app.Activity;
import android.graphics.drawable.Animatable;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.TextView;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.controller.ControllerListener;
import com.facebook.drawee.interfaces.DraweeController;
import com.facebook.drawee.view.SimpleDraweeView;
import com.facebook.imagepipeline.decoder.ProgressiveJpegConfig;
import com.facebook.imagepipeline.image.ImageInfo;
import com.facebook.imagepipeline.image.ImmutableQualityInfo;
import com.facebook.imagepipeline.image.QualityInfo;
import com.facebook.imagepipeline.request.ImageRequest;
import com.facebook.imagepipeline.request.ImageRequestBuilder;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Administrator on 2017/12/29 0029.
 */

public class FrescoImageListenerActivity extends Activity {

    @BindView(R.id.fresco_image)
    SimpleDraweeView simpleDraweeView;
    @BindView(R.id.tv_show_successful)
    TextView tvSuccessful;
    @BindView(R.id.tv_show_failed)
    TextView tvFailed;
    private ControllerListener<? super ImageInfo> controllerListener = new ControllerListener<ImageInfo>() {
        @Override
        public void onSubmit(String id, Object callerContext) {

        }

        @Override
        public void onFinalImageSet(String id, @Nullable ImageInfo imageInfo, @Nullable Animatable animatable) {

        }

        @Override
        public void onIntermediateImageSet(String id, @Nullable ImageInfo imageInfo) {

        }

        @Override
        public void onIntermediateImageFailed(String id, Throwable throwable) {

        }

        @Override
        public void onFailure(String id, Throwable throwable) {

        }

        @Override
        public void onRelease(String id) {

        }
    };

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listener);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.btn_fresco_start_image)
    public void onClick(View view) {

        //图片质量配置
        ProgressiveJpegConfig jpegConfig = new ProgressiveJpegConfig() {
            @Override
            public int getNextScanNumberToDecode(int scanNumber) {
                return scanNumber + 2;
            }

            @Override
            public QualityInfo getQualityInfo(int scanNumber) {
                boolean isGoodEnough = (scanNumber >= 5);
                return ImmutableQualityInfo.of(scanNumber, isGoodEnough, false);
            }
        };

        Uri uri = Uri.parse("http://pic1.sc.chinaz.com/files/pic/pic9/201712/bpic4641.jpg");
        //图片请求
        ImageRequest request = ImageRequestBuilder.newBuilderWithSource(uri)
                .setProgressiveRenderingEnabled(true)
                .build();
        //图片加载的控制
        DraweeController controller = Fresco.newDraweeControllerBuilder()
                .setOldController(simpleDraweeView.getController()) //复用,使用缓存
                .setImageRequest(request)
                .setControllerListener(controllerListener)
                .build();
        simpleDraweeView.setController(controller);
    }
}
