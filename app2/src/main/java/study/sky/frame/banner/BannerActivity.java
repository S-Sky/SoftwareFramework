package study.sky.frame.banner;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.youth.banner.Banner;
import com.youth.banner.listener.OnBannerListener;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import study.sky.frame.App;
import study.sky.frame.R;
import study.sky.frame.banner.customView.SuperSwipeRefreshLayout;
import study.sky.frame.banner.loader.GlideImageLoader;

/**
 * Created by Administrator on 2018/1/6 0006.
 */

public class BannerActivity extends Activity implements SwipeRefreshLayout.OnRefreshListener, OnBannerListener, AdapterView.OnItemClickListener {

    private Unbinder ubBinder;
    static final int REFRESH_COMPLETE = 0X1112;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.swipe)
    SuperSwipeRefreshLayout swipe;
    @BindView(R.id.list)
    ListView list;
    Banner banner;

    private Handler mHandler = new Handler() {
        public void handleMessage(android.os.Message msg) {
            switch (msg.what) {
                case REFRESH_COMPLETE:
                    String[] urls = getResources().getStringArray(R.array.url4);
                    List list = Arrays.asList(urls);
                    List arrayList = new ArrayList(list);
                    banner.update(arrayList);
                    swipe.setRefreshing(false);
                    break;
            }
        }
    };

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_banner);
        ubBinder = ButterKnife.bind(this);

        initView();
    }

    private void initView() {
        tvTitle.setText("Banner");

        swipe.setOnRefreshListener(this);
        View header = LayoutInflater.from(this).inflate(R.layout.banner_header, null);
        banner = (Banner) header.findViewById(R.id.banner);
        banner.setLayoutParams(new AbsListView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, App.H / 4));
        list.addHeaderView(header);

        //String[] data = getResources().getStringArray(R.array.demo_list);
        String[] data = {};
        list.setAdapter(new SampleAdapter(this, data));
        list.setOnItemClickListener(this);


        String[] urls = getResources().getStringArray(R.array.url);
        List list = Arrays.asList(urls);
        List arrayList = new ArrayList(list);
        //简单使用 //加载数据
        banner.setImages(new ArrayList(Arrays.asList(getResources().getStringArray(R.array.url))))
//        banner.setImages(arrayList)
                .setImageLoader(new GlideImageLoader())
                .setOnBannerListener(this)
                .start();

    }

    @Override
    public void onRefresh() {
        //下拉刷新  两秒之后刷新轮播数据
        mHandler.sendEmptyMessageDelayed(REFRESH_COMPLETE, 2000);
    }

    //如果你需要考虑更好的体验，可以这么操作
    @Override
    protected void onStart() {
        super.onStart();
        //开始轮播
        banner.startAutoPlay();
    }

    @Override
    protected void onStop() {
        super.onStop();
        //结束轮播
        banner.stopAutoPlay();
    }

    @Override
    public void OnBannerClick(int position) {
        Toast.makeText(getApplicationContext(), "你点击了：" + position, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ubBinder.unbind();
    }

}
