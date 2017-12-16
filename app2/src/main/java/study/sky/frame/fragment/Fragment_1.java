package study.sky.frame.fragment;

import android.util.Log;
import android.view.View;
import android.widget.TextView;

import study.sky.frame.R;
import study.sky.frame.base.BaseFragment;

/**
 * Created by Administrator on 2017/12/16 0016.
 */

public class Fragment_1 extends BaseFragment {
    @Override
    protected int getResource() {
        Log.i("Fragment_1-->", "getResource");
        return R.layout.fragment_view;
    }

    @Override
    protected void init(View view) {
        Log.i("Fragment_1-->", "init");
        ((TextView) view.findViewById(R.id.tv)).setText("主页");
    }

    @Override
    protected void startDestroy() {
        Log.i("Fragment_1-->", "startDestroy");
    }
}
