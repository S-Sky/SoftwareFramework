package study.sky.frame.tablayout;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.widget.TextView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import study.sky.frame.R;
import study.sky.frame.tablayout.adapter.ViewPagerAdapter;
import study.sky.frame.tablayout.fragment.TestFragment;

/**
 * Created by Administrator on 2018/1/9 0009.
 * 使用TabLayout  implementation 'com.android.support:design:26.1.0'
 */

public class TabLayoutActivity extends FragmentActivity {


    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.view_pager)
    ViewPager viewPager;
    @BindView(R.id.tab_layout)
    TabLayout tabLayout;

    ArrayList<TestFragment> fragments;
    ViewPagerAdapter adapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tab_layout);
        ButterKnife.bind(this);

        initView();
    }

    private void initView() {
        tvTitle.setText("TabLayout");

        //初始化数据
        fragments = new ArrayList<>();
        for (int i = 0; i < 12; i++) {
            fragments.add(new TestFragment("标题" + i, "内容" + i));
        }

        adapter = new ViewPagerAdapter(getSupportFragmentManager(), fragments);
        viewPager.setAdapter(adapter);
        //关联ViewPager
        tabLayout.setupWithViewPager(viewPager);
        //设置为固定的
        //tabLayout.setTabMode(TabLayout.MODE_FIXED);
        //设置滚动的效果
        tabLayout.setTabMode(TabLayout.MODE_SCROLLABLE);
    }
}
