package com.sky.SoftwareFramework.fragment;

import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;

import com.sky.SoftwareFramework.base.BaseFragment;

/**
 * Created by Administrator on 2017/12/14 0014.
 */

public class ThirdPartyFragment extends BaseFragment {
    private TextView textView;

    @Override
    protected View initView() {
        Log.i("ThirdPartyFragment", ".......");
        textView = new TextView(mContext);
        textView.setGravity(Gravity.CENTER);
        return textView;
    }

    @Override
    protected void initData() {
        super.initData();
        textView.setText("第三方");
    }
}
