package study.sky.frame.rxjava;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import study.sky.frame.R;
import study.sky.frame.rxjava.bean.DataBean;
import study.sky.frame.rxjava.retrofitapi.RetrofitApi;

public class RxJavaMapActivity extends Activity {

    private Unbinder unbinder;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tv_show)
    TextView tvShow;
    private RetrofitApi api;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rx_java_map);
        unbinder = ButterKnife.bind(this);

        tvTitle.setText("操作符的学习");

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://gank.io/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        api = retrofit.create(RetrofitApi.class);

    }

    /**
     * 操作符的练习
     *
     * @param view
     */

    @OnClick({R.id.btn_map, R.id.btn_search_keyword, R.id.btn_repeat_click})
    public void onClick(View view) {

        switch (view.getId()) {
            /**
             * just
             * flatMap
             */
            case R.id.btn_map:
                btnMap();
                break;
            /**
             *  implementation 'com.jakewharton.rxbinding2:rxbinding:2.0.0'
             *  关键词搜索
             */
            case R.id.btn_search_keyword:
                startActivity(new Intent(this, EditSearchActivity.class));
                break;
            /**
             * 防止重复点击按钮
             */
            case R.id.btn_repeat_click:
                startActivity(new Intent(this, PreventRepeatClickActivity.class));
                break;
        }

    }

    private void btnMap() {
        Observable.just(1).flatMap(new Function<Integer, ObservableSource<DataBean>>() {
            @Override
            public ObservableSource<DataBean> apply(Integer integer) throws Exception {

                DataBean dataBean = api.getData1(integer).execute().body();

                return Observable.just(dataBean);
            }
        }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<DataBean>() {
                    @Override
                    public void accept(DataBean dataBean) throws Exception {
                        tvShow.setText(dataBean.getResults().get(0).getUrl());
                    }
                });

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
    }
}
