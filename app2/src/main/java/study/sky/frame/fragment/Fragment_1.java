package study.sky.frame.fragment;

import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import study.sky.frame.R;
import study.sky.frame.base.BaseFragment;
import study.sky.frame.universalvideoview.UniversalVideoViewActivity;

/**
 * Created by Administrator on 2017/12/16 0016.
 */

public class Fragment_1 extends BaseFragment {

    @BindView(R.id.tv)
    TextView textView;
    @BindView(R.id.lv)
    ListView listView;

    List<String> datas;

    @Override
    protected int getResource() {
        Log.i("Fragment_1-->", "getResource");
        return R.layout.fragment_view;
    }

    @Override
    protected void init(View view) {
        ButterKnife.bind(this, view);
        datas = new ArrayList<>();
        textView.setVisibility(View.GONE);
    }

    @Override
    protected void initData() {
        datas.add("UniversalVideoView");
        datas.add("JiaoZiVideoPlayer");
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(context, android.R.layout.simple_list_item_1, datas);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (datas.get(position).equals("UniversalVideoView"))
                    startActivity(new Intent(context, UniversalVideoViewActivity.class));
            }
        });
    }

    @Override
    protected void startDestroy() {
        Log.i("Fragment_1-->", "startDestroy");
        datas = null;
    }
}
