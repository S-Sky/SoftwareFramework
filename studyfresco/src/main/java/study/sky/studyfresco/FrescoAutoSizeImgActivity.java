package study.sky.studyfresco;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.LinearLayout;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.backends.pipeline.PipelineDraweeController;
import com.facebook.drawee.view.SimpleDraweeView;
import com.facebook.imagepipeline.request.ImageRequest;
import com.facebook.imagepipeline.request.ImageRequestBuilder;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Administrator on 2018/1/2 0002.
 */

public class FrescoAutoSizeImgActivity extends Activity {

    @BindView(R.id.linear_fresco)
    LinearLayout linearLayout;

    private SimpleDraweeView simpleDraweeView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fresco_autosizeimg);
        ButterKnife.bind(this);
        simpleDraweeView = new SimpleDraweeView(this);
        simpleDraweeView.setAspectRatio(1.0f); //设置宽高比
    }

    @OnClick(R.id.btn_fresco_load)
    public void onClick(View view) {
        Uri uri = Uri.parse("http://pic1.sc.chinaz.com/files/pic/pic9/201712/bpic4641.jpg");
        ImageRequest request = ImageRequestBuilder.newBuilderWithSource(uri)
                .build();
        PipelineDraweeController controler = (PipelineDraweeController) Fresco.newDraweeControllerBuilder()
                .setOldController(simpleDraweeView.getController())
                .setImageRequest(request)
                .build();
        simpleDraweeView.setController(controler);
        linearLayout.addView(simpleDraweeView);
    }
}
