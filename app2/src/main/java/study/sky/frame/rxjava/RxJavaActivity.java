package study.sky.frame.rxjava;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import study.sky.frame.R;

public class RxJavaActivity extends Activity {

    private Unbinder unbinder;
    @BindView(R.id.tv_show)
    TextView tvShow;
    @BindView(R.id.tv_title)
    TextView tvTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rx_java);
        unbinder = ButterKnife.bind(this);
        tvTitle.setText("RxJava");
    }

    @OnClick({R.id.btn_click, R.id.btn_scheduler, R.id.btn_rx_map})
    public void onClick(View view) {

        switch (view.getId()) {
            case R.id.btn_click:
                Observable<String> observable = getObservable();
                //        Observer<String> observer = getOberver();
                //        observable.subscribe(observer);
                //另一种方式
                observable.subscribe(new Consumer<String>() {
                    @Override
                    public void accept(String s) throws Exception {
                        Log.e("RxJavaActivity", "accept");
                        tvShow.append(s);
                        tvShow.append("\n");
                    }
                });
                break;
            case R.id.btn_scheduler:
                startActivity(new Intent(this, SchedulerActivity.class));
                break;
            case R.id.btn_rx_map:
                startActivity(new Intent(this, RxJavaMapActivity.class));
                break;
        }


    }

    /**
     * Observable 被观察者
     *
     * @return
     */
    public Observable<String> getObservable() {
        /*return Observable.create(new ObservableOnSubscribe<String>() {
            @Override
            public void subscribe(ObservableEmitter<String> e) throws Exception {
                e.onNext("发送数据");
                e.onNext("再次发送一个数据");

                e.onComplete(); //发送完成之后调用
            }
        });*/
        //Observable也可以这样创建
        Observable observable = Observable.just("发送数据", "再次发送数据");
        return observable;
    }

    /**
     * Observer  观察者
     */
    public Observer<String> getOberver() {
        return new Observer<String>() {
            @Override
            public void onSubscribe(Disposable d) {

                Log.e("RxJavaActivity", "onSubscribe");
            }

            @Override
            public void onNext(String s) {
                Log.e("RxJavaActivity", "onNext");
            }

            @Override
            public void onError(Throwable e) {
                Log.e("RxJavaActivity", "onError");
            }

            @Override
            public void onComplete() {
                Log.e("RxJavaActivity", "onComplete");
            }
        };
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
    }
}
