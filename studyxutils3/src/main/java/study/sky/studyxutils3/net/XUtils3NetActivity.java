package study.sky.studyxutils3.net;

import android.app.Activity;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import org.xutils.common.Callback;
import org.xutils.common.task.PriorityExecutor;
import org.xutils.http.RequestParams;
import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.io.File;

import study.sky.studyxutils3.R;

/**
 * Created by Administrator on 2017/12/19 0019.
 */
@ContentView(R.layout.activity_xutils_net)
public class XUtils3NetActivity extends Activity {

    @ViewInject(R.id.tv_result)
    private TextView textView;
    @ViewInject(R.id.progressbar)
    private ProgressBar progressBar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        x.view().inject(this);
    }

    @Event(value = {R.id.btn_get_post, R.id.btn_download_file})
    private void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_get_post:
                getAndPostNet();
                break;
            case R.id.btn_download_file:
                downloadFile();
                break;
//            case R.id.btn_upload_file:
//                break;
        }
    }

    private void downloadFile() {
        RequestParams params = new RequestParams("http://vf2.mtime.cn/Video/2016/09/15/mp4/160915092608935956_480.mp4");
        //设置保存数据
        params.setSaveFilePath(Environment.getExternalStorageDirectory().getAbsolutePath() + "/480.mp4");
        //设置是否可以立即取消下载
        params.setCancelFast(true);
        //设置是否自动根据头信息命名
        params.setAutoRename(false);
//        //自定义线程池,有效的值的范围[1,3]设置为3时,可能阻塞图片加载:下载加快
//        params.setExecutor(new PriorityExecutor(3, true));
        //设置断点续传
//        params.setAutoResume(true);

        x.http().get(params, new Callback.ProgressCallback<File>() {
            /**
             * 当下载成功的时候回调
             * @param file
             */
            @Override
            public void onSuccess(File file) {
                Log.e("TAG", "onSuccess==>" + file.toString());
                Toast.makeText(XUtils3NetActivity.this, "onSuccess==>" + file.toString(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                Log.e("TAG", "onError==>" + ex.getMessage());
            }

            @Override
            public void onCancelled(CancelledException cex) {
                Log.e("TAG", "onCancelled==>" + cex.getMessage());
            }

            @Override
            public void onFinished() {
                Log.e("TAG", "onFinished==");
            }

            @Override
            public void onWaiting() {
                Log.e("TAG", "onWaiting==");
            }

            @Override
            public void onStarted() {
                Log.e("TAG", "onStarted==");
            }

            /**
             *
             * @param total  总进度
             * @param current 当前进度
             * @param isDownloading
             */
            @Override
            public void onLoading(long total, long current, boolean isDownloading) {
                progressBar.setMax((int) total);
                progressBar.setProgress((int) current);
                Log.e("TAG", "onLoading==" + current + "/" + total + ",isDownloading" + isDownloading);
            }
        });
    }

    private void getAndPostNet() {
        //Get请求
        //post请求
        //设置请求链接
        RequestParams params = new RequestParams("http://v.juhe.cn/weather/index?cityname=%E8%A5%BF%E5%AE%89&dtype=&format=&key=065059c0265c98676f6755602f7c2c8e");
        //  x.http().get(params, new Callback.CommonCallback<String>() {
        x.http().post(params, new Callback.CommonCallback<String>() {
            /**
             * 成功的时候回调
             * @param result
             */
            @Override
            public void onSuccess(String result) {
                Log.e("TAG", "xUtils3联网请求成功==》" + result);
                //textView.setText("Get请求的结果:" + result);
                textView.setText("Post请求的结果:" + result);
            }

            /**
             * 错误的时候回调
             * @param ex
             * @param isOnCallback
             */
            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                Log.e("TAG", "xUtils3联网请求失败==》" + ex.getMessage());
                textView.setText("xUtils3联网请求失败==>" + ex.getMessage());
            }

            /**
             * 取消的时候回调
             * @param cex
             */
            @Override
            public void onCancelled(CancelledException cex) {
                Log.e("TAG", "xUtils3联网请求取消==》" + cex.getMessage());
            }

            /**
             * 完成的时候回调
             */
            @Override
            public void onFinished() {
                Log.e("TAG==》", "onFinished");
            }
        });
    }
}
