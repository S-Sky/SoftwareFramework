package com.sky.studyglide;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.alibaba.fastjson.JSON;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2017/12/26 0026.
 */

public class GlideRecyclerViewActivity extends AppCompatActivity {

    @BindView(R.id.glide_recycler_view)
    RecyclerView recyclerView;
    private List<GlideBean.ResultsBean> list;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_glide_recycler_view);
        ButterKnife.bind(this);
        x.view().inject(this);
        getData();
    }

    private void getData() {
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        RequestParams params = new RequestParams("http://gank.io/api/data/福利/10/1");
        x.http().get(params, new Callback.CommonCallback<String>() {
            /**
             * 成功的时候回调
             * @param result
             */
            @Override
            public void onSuccess(String result) {
                Log.e("onSuccess==", result);
                GlideBean picassoBean = JSON.parseObject(result, GlideBean.class);
                list = picassoBean.getResults();
                new Handler().post(new Runnable() {
                    @Override
                    public void run() {
                        if (list != null && list.size() > 0) {
                            GlideRecyclerViewAdapter adapter = new GlideRecyclerViewAdapter(GlideRecyclerViewActivity.this, list);
                            recyclerView.setAdapter(adapter);
                        }
                    }
                });
            }

            /**
             * 错误的时候回调
             * @param ex
             * @param isOnCallback
             */
            @Override
            public void onError(Throwable ex, boolean isOnCallback) {

            }

            /**
             * 取消的时候回调
             * @param cex
             */
            @Override
            public void onCancelled(CancelledException cex) {
                Log.e("TAG", "xUtils3联网请求取消==》" + cex.getMessage());
            }

            /**
             * 完成的时候回调
             */
            @Override
            public void onFinished() {
                Log.e("TAG==》", "onFinished");
            }
        });
    }
}
