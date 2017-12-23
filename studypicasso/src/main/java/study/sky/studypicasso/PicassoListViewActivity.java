package study.sky.studypicasso;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ListView;

import com.alibaba.fastjson.JSON;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2017/12/22 0022.
 */

public class PicassoListViewActivity extends AppCompatActivity {

    @BindView(R.id.lv_picasso)
    ListView listView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listview);
        ButterKnife.bind(this);
        x.view().inject(this);
        getData();
    }

    private List<PicassoBean.ResultsBean> list;

    private void getData() {
        RequestParams params = new RequestParams("http://gank.io/api/data/福利/10/1");
        x.http().get(params, new Callback.CommonCallback<String>() {
            /**
             * 成功的时候回调
             * @param result
             */
            @Override
            public void onSuccess(String result) {
                Log.e("onSuccess==", result);
                PicassoBean picassoBean = JSON.parseObject(result, PicassoBean.class);
                list = picassoBean.getResults();
                new Handler().post(new Runnable() {
                    @Override
                    public void run() {
                        if (list != null && list.size() > 0) {
                            PicassoListViewAdapter adapter = new PicassoListViewAdapter(PicassoListViewActivity.this, list);
                            listView.setAdapter(adapter);
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
