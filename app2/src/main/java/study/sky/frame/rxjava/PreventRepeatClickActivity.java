package study.sky.frame.rxjava;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;

import com.jakewharton.rxbinding2.view.RxView;

import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import study.sky.frame.R;

public class PreventRepeatClickActivity extends Activity {

    private Unbinder unbinder;

    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.btn_click)
    Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_prevent_repeat_click);
        unbinder = ButterKnife.bind(this);

        tvTitle.setText("防止按钮重复点击");

        //RxJava的做法
        RxView.clicks(button)
                //设置不能连续点击的时间间隔
                //ThrottleFirst 允许设置一个时间长度,之后它会发送固定时间长度内的第一个事件,而屏蔽其他时间,在间隔达到设置的时间后,可以再发送下一个事件
                .throttleFirst(5, TimeUnit.SECONDS)
                .subscribe(new Observer<Object>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(Object o) {
                        Log.e("PreventRepeatClick", "这里是点击事件" + System.currentTimeMillis());
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

/*    private long lastTime = 0;

    @OnClick(R.id.btn_click)
    public void onCklic(View view) {

        //传统操作
        long currTime = System.currentTimeMillis();
        if (currTime - lastTime > 500) {
            //进行操作
        } else {
            Toast.makeText(this, "不能重复点击", Toast.LENGTH_SHORT).show();
        }
        lastTime = currTime;
        Log.e("lastTime==",""+lastTime);

    }*/

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
    }
}
