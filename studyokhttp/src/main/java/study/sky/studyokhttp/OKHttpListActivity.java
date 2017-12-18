package study.sky.studyokhttp;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Request;

/**
 * Created by Administrator on 2017/12/18 0018.
 */

public class OKHttpListActivity extends Activity {

    private static final String TAG = OKHttpListActivity.class.getSimpleName();

    private ListView listview;
    private TextView textView;
    private ProgressBar progressBar;
    private String url;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
        getDataFromNet();
    }

    private void getDataFromNet() {

        url = "http://gank.io/api/data/福利/10/1";

        //得到缓存的数据  (缓存的只是文本,图片没有缓存)
        String saveJson = CacheUtils.getString(this, url);
        if (!TextUtils.isEmpty(saveJson)) {
            parsedJson(saveJson);
        } else {
            OkHttpUtils.get()
                    .url(url)
                    .id(100)
                    .build()
                    .execute(new MyStringCallback());
        }
    }

    private void initView() {
        setContentView(R.layout.activity_okhttp);
        listview = findViewById(R.id.listview);
        textView = findViewById(R.id.tv_nodata);
        progressBar = findViewById(R.id.progressBar);
    }

    public class MyStringCallback extends StringCallback {
        @Override
        public void onBefore(Request request, int id) {
            setTitle("loading...");
        }

        @Override
        public void onAfter(int id) {
            setTitle("Sample-okHttp");
        }

        @Override
        public void onError(Call call, Exception e, int id) {
            e.printStackTrace();
            textView.setVisibility(View.VISIBLE);
        }

        @Override
        public void onResponse(String response, int id) {
            Log.e(TAG, "onResponse：complete");
            textView.setVisibility(View.GONE);

            switch (id) {
                case 100:
                    Toast.makeText(OKHttpListActivity.this, "http", Toast.LENGTH_SHORT).show();
                    break;
                case 101:
                    Toast.makeText(OKHttpListActivity.this, "https", Toast.LENGTH_SHORT).show();
                    break;
            }
            if (response != null) {
                //缓存数据
                CacheUtils.putString(OKHttpListActivity.this, url, response);
                processData(response);
            }
        }

        @Override
        public void inProgress(float progress, long total, int id) {
            Log.e(TAG, "inProgress:" + progress);
        }
    }

    /**
     * 解析和显示数据
     *
     * @param json
     */
    private void processData(String json) {

        //解析数据
        DataBean dataBean = parsedJson(json);
        List<DataBean.ItemData> results = dataBean.getResults();

        if (results != null && results.size() > 0) {
            //有数据,显示适配器
            textView.setVisibility(View.GONE);
            listview.setAdapter(new OKHttpListAdapter(OKHttpListActivity.this, results));
        } else {
            //没有数据
            textView.setVisibility(View.VISIBLE);
        }
        progressBar.setVisibility(View.GONE);
    }

    private DataBean parsedJson(String response) {
        DataBean dataBean = new DataBean();
        try {
            JSONObject jsonObject = new JSONObject(response);
            JSONArray jsonArray = jsonObject.optJSONArray("results");
            if (jsonArray != null && jsonArray.length() > 0) {
                List<DataBean.ItemData> results = new ArrayList<>();
                dataBean.setResults(results);
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject jsonObjectItem = (JSONObject) jsonArray.get(i);
                    if (jsonObjectItem != null) {
                        DataBean.ItemData itemData = new DataBean.ItemData();

                        String imageUrl = jsonObjectItem.optString("url");
                        itemData.setUrl(imageUrl);

                        results.add(itemData);
                    }
                }
            }

        } catch (Exception e) {

        }
        return dataBean;
    }

}
