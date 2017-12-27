package study.sky.studyfresco;

import android.app.Activity;
import android.graphics.PointF;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.TextView;

import com.facebook.drawee.drawable.ScalingUtils;
import com.facebook.drawee.generic.GenericDraweeHierarchy;
import com.facebook.drawee.generic.GenericDraweeHierarchyBuilder;
import com.facebook.drawee.view.SimpleDraweeView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Administrator on 2017/12/27 0027.
 */

public class FrescoCutActivity extends Activity {

    @BindView(R.id.tv_fresco_explain)
    TextView textView;
    @BindView(R.id.fresco_image)
    SimpleDraweeView simpleDraweeView;

    GenericDraweeHierarchyBuilder builder;
    Uri uri;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cut);
        ButterKnife.bind(this);

        builder = new GenericDraweeHierarchyBuilder(getResources());
        uri = Uri.parse("http://pic1.sc.chinaz.com/files/pic/pic9/201712/bpic4641.jpg");
    }

    GenericDraweeHierarchy build;

    @OnClick({R.id.btn_fresco_cut_center, R.id.btn_fresco_cut_centercrop,
            R.id.btn_fresco_cut_focuscrop, R.id.btn_fresco_cut_centerinside,
            R.id.btn_fresco_cut_fitcenter, R.id.btn_fresco_cut_fitstart})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_fresco_cut_center: //居中无缩放
                textView.setText("居中无缩放");
                //设置样式
                build = builder.setActualImageScaleType(ScalingUtils.ScaleType.CENTER).build();
                //图片显示
                imageDisplay(build);
                break;
            case R.id.btn_fresco_cut_centercrop://保持宽高比缩小或缩放,使得两边都大于或等于显示边界。居中显示
                textView.setText("保持宽高比缩小或缩放,使得两边都大于或等于显示边界。居中显示");
                //设置样式
                build = builder.setActualImageScaleType(ScalingUtils.ScaleType.CENTER_CROP).build();
                //图片显示
                imageDisplay(build);
                break;
            case R.id.btn_fresco_cut_focuscrop: //同centerCrop,但局重点不是中心,而是指定的某个点,这里设置为图片的左上角
                textView.setText("同centerCrop,但局重点不是中心,而是指定的某个点,这里设置为图片的左上角");
                //设置样式
                PointF pointF = new PointF(0, 0);
                build = builder.setActualImageScaleType(ScalingUtils.ScaleType.FOCUS_CROP)
                        .setActualImageFocusPoint(pointF)
                        .build();
                //图片显示
                imageDisplay(build);
                break;
            case R.id.btn_fresco_cut_centerinside://使两边都在显示边界内,居中显示,如果图尺寸大于显示边界,则保持长宽比缩小图片
                textView.setText("使两边都在显示边界内,居中显示,如果图尺寸大于显示边界,则保持长宽比缩小图片");
                //设置样式
                build = builder.setActualImageScaleType(ScalingUtils.ScaleType.CENTER_INSIDE).build();
                //图片显示
                imageDisplay(build);
                break;
            case R.id.btn_fresco_cut_fitcenter://保持宽高比,缩小或放大,使得图片完全显示在显示边界内,居中显示
                textView.setText("保持宽高比,缩小或放大,使得图片完全显示在显示边界内,居中显示");
                //设置样式
                build = builder.setActualImageScaleType(ScalingUtils.ScaleType.FIT_CENTER).build();
                //图片显示
                imageDisplay(build);
                break;
            case R.id.btn_fresco_cut_fitstart: //保持宽高比,缩小或者放大,使得图片完全显示在显示边界内,不居中,和显示边界坐上对齐
                textView.setText("保持宽高比,缩小或者放大,使得图片完全显示在显示边界内,不居中,和显示边界坐上对齐");
                //设置样式
                build = builder.setActualImageScaleType(ScalingUtils.ScaleType.FIT_START).build();
                //图片显示
                imageDisplay(build);
                break;
        }
    }

    private void imageDisplay(GenericDraweeHierarchy build) {
        simpleDraweeView.setHierarchy(build);
        //加载图片
        simpleDraweeView.setImageURI(uri);
    }
}
