package study.sky.studyxutils3.annotation;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

import org.xutils.view.annotation.ContentView;
import org.xutils.x;

import study.sky.studyxutils3.R;

/**
 * Created by Administrator on 2017/12/19 0019.
 */

@ContentView(R.layout.activity_xutils3_fragment)
public class FragmentActivity extends android.support.v4.app.FragmentActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        x.view().inject(this);

        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.replace(R.id.fl_content, new DemoFragment());
        transaction.commit();
    }
}
