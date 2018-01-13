package study.sky.frame.retrofit.retrofitinterface;

import java.util.Map;

import io.reactivex.Observable;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;
import study.sky.frame.retrofit.bean.DataBean;
import study.sky.frame.retrofit.bean.DataParams;

/**
 * Created by Administrator on 2018/1/12 0012.
 */

public interface RetrofitApi {

    @GET("joke/content/list.php?pagesize=1&time=1418816972&key=d35856dce754ddc6b6c2f85d3403e6c8")
    Call<DataBean> getData();

    @GET("joke/content/list.php?time=1418816972&key=d35856dce754ddc6b6c2f85d3403e6c8")
    Call<DataBean> getData1(@Query("pagesize") int pagesize);

    @GET("joke/content/list.php?key=d35856dce754ddc6b6c2f85d3403e6c8")
    Call<DataBean> getData2(@Query("pagesize") int pagesize, @Query("time") String time);

    @GET("joke/content/list.php?key=d35856dce754ddc6b6c2f85d3403e6c8")
    Call<DataBean> getData3(@QueryMap Map<String, String> params);

    @POST("joke/content/list.php?key=d35856dce754ddc6b6c2f85d3403e6c8")
    Call<DataBean> postData(@Body DataParams params);

    @POST("joke/content/list.php?key=d35856dce754ddc6b6c2f85d3403e6c8")
    Observable<DataBean> postDataWithRxJava(@Body DataParams params);
}
