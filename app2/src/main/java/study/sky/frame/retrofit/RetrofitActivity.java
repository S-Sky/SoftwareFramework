package study.sky.frame.retrofit;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import study.sky.frame.R;
import study.sky.frame.retrofit.bean.DataBean;
import study.sky.frame.retrofit.bean.DataParams;
import study.sky.frame.retrofit.retrofitinterface.RetrofitApi;

public class RetrofitActivity extends Activity {

    private Unbinder unbinder;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    private RetrofitApi api;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_retrofit);
        unbinder = ButterKnife.bind(this);

        tvTitle.setText("Retrofit");

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://v.juhe.cn/")
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create()) //retrofit与RxJava的结合使用
                .build();

        api = retrofit.create(RetrofitApi.class); //生成一个接口的动态代理
    }

    @OnClick({R.id.btn_retrofit, R.id.btn_retrofit_rxjava})
    public void onClick(View view) {

        switch (view.getId()) {
            case R.id.btn_retrofit:
                studyRetrofit();
                studyRetrofitPost();
                break;
            case R.id.btn_retrofit_rxjava:
                retrofitWithRxJava();
                break;
        }
    }

    /**
     * retrofit and RxJava
     */
    private void retrofitWithRxJava() {

        /*api.postDataWithRxJava(new DataParams(1, "1418816972"))
                .subscribe(new Sub)*/

    }

    /**
     * post
     */
    private void studyRetrofitPost() {
        api.postData(new DataParams(1, "1418816972")).enqueue(new Callback<DataBean>() {
            @Override
            public void onResponse(Call<DataBean> call, Response<DataBean> response) {

                if (response.isSuccessful()) {
                    List<DataBean.ResultBean.DataBeanTest> list = response.body().getResult().getData();
                    Log.e("list==", list.get(0).getContent());
                }
            }

            @Override
            public void onFailure(Call<DataBean> call, Throwable t) {

            }
        });
    }

    /**
     * get
     */
    private void studyRetrofit() {
        Toast.makeText(this, "click", Toast.LENGTH_SHORT).show();
        Map<String, String> map = new HashMap<String, String>();
        map.put("pagesize", "1");
        map.put("time", "1418816972");
        api.getData3(map).enqueue(new Callback<DataBean>() {
            @Override
            public void onResponse(Call<DataBean> call, Response<DataBean> response) {
                List<DataBean.ResultBean.DataBeanTest> list = response.body().getResult().getData();
                Log.e("list==", list.get(0).getContent());
            }

            @Override
            public void onFailure(Call<DataBean> call, Throwable t) {

            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
    }
}
