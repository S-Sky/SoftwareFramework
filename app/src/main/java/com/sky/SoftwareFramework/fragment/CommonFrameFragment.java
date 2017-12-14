package com.sky.SoftwareFramework.fragment;

import android.util.Log;
import android.view.View;
import android.widget.ListView;

import com.sky.SoftwareFramework.R;
import com.sky.SoftwareFramework.adapter.CommonFrameFragmentAdapter;
import com.sky.SoftwareFramework.base.BaseFragment;

/**
 * Created by Administrator on 2017/12/14 0014.
 */

public class CommonFrameFragment extends BaseFragment {

    ListView mListView;

    private String[] datas;

    @Override
    protected View initView() {
        Log.i("CommonFrameFragment", ".......");
        View view = View.inflate(mContext, R.layout.fragment_common_frame, null);
        mListView = view.findViewById(R.id.lv);
        return view;
    }

    @Override
    protected void initData() {
        super.initData();
        datas = new String[]{"OKHttp", "xUtils3", "Retrofit2", "Fresco", "Glide", "greenDao", "RxJava","volley"};
        mListView.setAdapter(new CommonFrameFragmentAdapter(mContext, datas));
    }
}
