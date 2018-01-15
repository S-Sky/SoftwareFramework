package study.sky.frame.rxjava;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;
import android.widget.TextView;

import com.jakewharton.rxbinding2.widget.RxTextView;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.functions.Predicate;
import io.reactivex.schedulers.Schedulers;
import study.sky.frame.R;

public class EditSearchActivity extends Activity {

    private Unbinder unbinder;
    @BindView(R.id.edit_search)
    EditText editSearch;
    @BindView(R.id.tv_title)
    TextView tvTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_search);
        unbinder = ButterKnife.bind(this);

        tvTitle.setText("关键词搜索");

        //RxJava的做法
        //对EditText的监听必须在UI线程中
        RxTextView.textChanges(this.editSearch)
                //使用debounce延迟200ms
                .debounce(200, TimeUnit.MILLISECONDS)
                .subscribeOn(AndroidSchedulers.mainThread())
                .filter(new Predicate<CharSequence>() { //过滤数据
                    @Override
                    public boolean test(CharSequence charSequence) throws Exception {
                        Log.e("EditSearchActivity-->", "filter：" + charSequence);
                        return charSequence.toString().trim().length() > 0;
                    }
                })
                //switchMap() 将Observable发射的数据集合变换为Observables集合,然后只发射这些Observables最近发射的数据
                .switchMap(new Function<CharSequence, ObservableSource<List<String>>>() {
                    @Override
                    public ObservableSource<List<String>> apply(CharSequence charSequence) throws Exception {
                        Log.e("EditSearchActivity-->", "switchMap：" + charSequence);
                        //do search from network
                        List<String> list = new ArrayList<>();
                        list.add("abc");
                        list.add("abd");
                        list.add("acb");

                        return Observable.just(list);
                    }
                })
                //数据转换  将字符串类型转换为集合类型
                //flatMap() 将Observable发射的数据集合变换为Observables,然后将这些Observable发射的数据平坦化的放进一个单独的Observable
/*                .flatMap(new Function<CharSequence, ObservableSource<List<String>>>() {
                    @Override
                    public ObservableSource<List<String>> apply(CharSequence charSequence) throws Exception {

                        Log.e("EditSearchActivity-->", "flatMap：" + charSequence);
                        //do search from network
                        List<String> list = new ArrayList<>();
                        list.add("abc");
                        list.add("abd");
                        list.add("acb");

                        return Observable.just(list);
                    }
                })*/
                .subscribeOn(Schedulers.io()) //网络请求不能再UI线程中
                .observeOn(AndroidSchedulers.mainThread()) //结果显示必须在UI线程中操作
                .subscribe(new Consumer<List<String>>() {
                    @Override
                    public void accept(List<String> strings) throws Exception {

                        //显示结果
                        Log.e("EditSearchActivity-->", "subscribe：" + strings);

                    }
                }, new Consumer<Throwable>() {
                    //出错的时候打印出出错信息
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        throwable.printStackTrace();
                    }
                });

        //传统做法
//        this.editSearch.addTextChangedListener(new TextWatcher() {
//            @Override
//            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
//
//            }
//
//            @Override
//            public void onTextChanged(CharSequence s, int start, int before, int count) {
//
//            }
//
//            @Override
//            public void afterTextChanged(Editable s) {
//                //进行数据搜索(从网络上进行请求)
//            }
//        });

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
    }
}
