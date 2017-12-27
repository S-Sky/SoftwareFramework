package study.sky.studyfresco;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.facebook.drawee.drawable.ProgressBarDrawable;
import com.facebook.drawee.generic.GenericDraweeHierarchy;
import com.facebook.drawee.generic.GenericDraweeHierarchyBuilder;
import com.facebook.drawee.view.SimpleDraweeView;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2017/12/27 0027.
 */

public class FrescoProgressActivity extends Activity {

    @BindView(R.id.fresco_image)
    SimpleDraweeView simpleDraweeView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_progress);
        ButterKnife.bind(this);
        initData();
    }

    private void initData() {
        //设置样式
        GenericDraweeHierarchyBuilder builder = new GenericDraweeHierarchyBuilder(getResources());
        GenericDraweeHierarchy build = builder.setProgressBarImage(new ProgressBarDrawable()).build();
        simpleDraweeView.setHierarchy(build);
        //加载图片
        simpleDraweeView.setImageURI(Uri.parse("http://pic1.sc.chinaz.com/files/pic/pic9/201712/bpic4641.jpg"));
    }
}
