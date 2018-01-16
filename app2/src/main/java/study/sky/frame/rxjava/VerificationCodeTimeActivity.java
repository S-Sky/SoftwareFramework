package study.sky.frame.rxjava;

import android.app.Activity;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import study.sky.frame.R;

public class VerificationCodeTimeActivity extends Activity {

    private Unbinder unbinder;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.edit_phone)
    EditText editText;
    @BindView(R.id.btn_verification_code)
    Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verification_code_time);
        unbinder = ButterKnife.bind(this);

        tvTitle.setText("获取短信验证码倒计时");

    }

    int count = 10;

    @OnClick(R.id.btn_verification_code)
    public void onCklic(View view) {

        Observable.interval(0, 1, TimeUnit.SECONDS)
                .take(count+1) //设置倒计时的时长
                .observeOn(AndroidSchedulers.mainThread())
                .map(new Function<Long, Long>() {
                    @Override
                    public Long apply(Long aLong) throws Exception {
                        return count - aLong;
                    }
                })
                .doOnSubscribe(new Consumer<Disposable>() {
                    @Override
                    public void accept(Disposable disposable) throws Exception {
                        button.setEnabled(false);
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                            button.setBackgroundColor(getColor(R.color.colorPrimary));
                        }
                    }
                })
                .subscribe(new Observer<Long>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(Long aLong) {
                        button.setText(aLong + "秒");
                        Log.e("Observer==", "onNext:" + aLong);
                    }

                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
                    }

                    @Override
                    public void onComplete() {
                        Log.e("Observer==", "onComplete");
                        button.setEnabled(true);
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                            button.setBackgroundColor(getColor(R.color.color9));
                        }
                        button.setText("获取验证码");
                    }
                });


//        new CountDownTimer(1, 10) {
//            @Override
//            public void onTick(long millisUntilFinished) {
//                //倒计时的过程中
//            }
//
//            @Override
//            public void onFinish() {
//                //结束之后
//            }
//        };

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
    }
}
