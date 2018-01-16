package study.sky.frame.rxjava.retrofitapi;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import study.sky.frame.rxjava.bean.DataBean;

/**
 * Created by Administrator on 2018/1/13 0013.
 * http://gank.io/api/data/福利/10/1
 */

public interface RetrofitApi {

    @GET("api/data/福利/10/1")
    Call<DataBean> getData();

    @GET("api/data/福利/{size}/1")
    Call<DataBean> getData1(@Path("size") int size);

}
