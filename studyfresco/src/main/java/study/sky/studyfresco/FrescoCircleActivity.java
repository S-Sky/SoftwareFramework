package study.sky.studyfresco;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.facebook.drawee.generic.GenericDraweeHierarchy;
import com.facebook.drawee.generic.GenericDraweeHierarchyBuilder;
import com.facebook.drawee.generic.RoundingParams;
import com.facebook.drawee.view.SimpleDraweeView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Administrator on 2017/12/28 0028.
 */

public class FrescoCircleActivity extends Activity {

    @BindView(R.id.fresco_image)
    SimpleDraweeView simpleDraweeView;
    private Uri uri;
    private GenericDraweeHierarchyBuilder builder;
    private RoundingParams parms;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_circle);
        ButterKnife.bind(this);
        uri = Uri.parse("http://pic1.sc.chinaz.com/files/pic/pic9/201712/bpic4641.jpg");
        builder = new GenericDraweeHierarchyBuilder(getResources());
    }

    @OnClick({R.id.btn_fresco_circular, R.id.btn_fresco_circular_bead})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_fresco_circular:
                //设置为圆形
                parms = RoundingParams.asCircle();
                parms.setBorder(getResources().getColor(R.color.colorPrimary), 5);//边框
                setImage(parms);
                break;
            case R.id.btn_fresco_circular_bead:
                parms = RoundingParams.fromCornersRadius(50f);
                parms.setOverlayColor(getResources().getColor(R.color.colorAccent));//覆盖层
                setImage(parms);
                break;
        }
    }

    private void setImage(RoundingParams parms) {
        GenericDraweeHierarchy hierarchy = builder.setRoundingParams(parms).build();
        simpleDraweeView.setHierarchy(hierarchy);
        simpleDraweeView.setImageURI(uri);
    }
}

