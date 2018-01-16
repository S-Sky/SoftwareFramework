package study.sky.frame.rxjava;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.jakewharton.rxbinding2.view.RxView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.functions.Consumer;
import study.sky.frame.R;

public class ImageCacheActivity extends Activity {

    private Unbinder unbinder;
    private Observable<String> memoryObservable;
    private Observable<String> diskObservable;
    private Observable<String> networkObservable;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.btn_cache_three)
    Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_cache);

        unbinder = ButterKnife.bind(this);
        tvTitle.setText("缓存");
        test1();

        RxView.clicks(button).subscribe(new Consumer<Object>() {
            @Override
            public void accept(Object o) throws Exception {
                Observable.concat(memoryObservable, diskObservable, networkObservable)

                        .subscribe(new Consumer<String>() {
                            @Override
                            public void accept(String s) throws Exception {
                                Log.e("RxView", s);
                            }
                        });
            }
        });
    }

    @OnClick(R.id.btn_cache_image)
    public void onClick(View view) {
        startActivity(new Intent(this, CustomImageCacheActivity.class));
    }

    private void test1() {
        memoryObservable = Observable.create(new ObservableOnSubscribe<String>() {
            @Override
            public void subscribe(ObservableEmitter<String> e) throws Exception {
                e.onNext("memory");
                e.onComplete();
            }
        });
        diskObservable = Observable.create(new ObservableOnSubscribe<String>() {
            @Override
            public void subscribe(ObservableEmitter<String> e) throws Exception {
                e.onNext("disk");
                e.onComplete();
            }
        });
        networkObservable = Observable.create(new ObservableOnSubscribe<String>() {
            @Override
            public void subscribe(ObservableEmitter<String> e) throws Exception {
                e.onNext("network");
                e.onComplete();
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
    }
}
