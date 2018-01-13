package study.sky.frame.rxjava;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import study.sky.frame.R;
import study.sky.frame.rxjava.bean.DataBean;
import study.sky.frame.rxjava.retrofitapi.RetrofitApi;

public class SchedulerActivity extends AppCompatActivity {

    private Unbinder unbinder;
    @BindView(R.id.image)
    ImageView imageView;
    @BindView(R.id.tv_show)
    TextView textView;
    private RetrofitApi api;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scheduler);
        unbinder = ButterKnife.bind(this);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://gank.io/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        api = retrofit.create(RetrofitApi.class);

    }

    @OnClick(R.id.btn_get)
    public void onClick(View view) {
        Toast.makeText(this, "点击事件", Toast.LENGTH_SHORT).show();

        Observable.create(new ObservableOnSubscribe<DataBean>() {

            //主线程中执行
            @Override
            public void subscribe(ObservableEmitter<DataBean> e) throws Exception {

                DataBean dataBean = api.getData().execute().body();
                //请求网络
                // DataBean bean = new DataBean();
                e.onNext(dataBean);
            }
        }).subscribeOn(Schedulers.io())   //切换线程,开启一个线程池,在子线程中调用DataBean dataBean = api.getData().execute().body();
                .observeOn(AndroidSchedulers.mainThread()) //切换回主线程,
                .subscribe(new Observer<DataBean>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(DataBean dataBean) {

                        Log.e("SchedulerActivity", "onNext==" + dataBean.getResults().get(0).getUrl());
                        //必须在主线程中更新UI
                        textView.setText(dataBean.getResults().get(0).getWho());

                    }

                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
                    }

                    @Override
                    public void onComplete() {

                    }
                });

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
    }
}
