package study.sky.frame.tablayout.fragment;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Created by Administrator on 2018/1/9 0009.
 */

@SuppressLint("ValidFragment")
public class TestFragment extends Fragment {

    private String title;
    private String content;
    Context mContext;
    TextView textView;

    /**
     * 得到标题
     *
     * @return
     */
    public String getTitle() {
        return title;
    }

    /**
     * 得到内容
     *
     * @return
     */
    public String getContent() {
        return content;
    }

    public TestFragment(String title, String content) {
        super();
        this.title = title;
        this.content = content;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = getActivity();
    }

    /**
     * 创建视图
     *
     * @param inflater
     * @param container
     * @param savedInstanceState
     * @return
     */
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        textView = new TextView(mContext);
        textView.setTextColor(Color.RED);
        textView.setTextSize(25);
        textView.setGravity(Gravity.CENTER);
        return textView;
    }

    /**
     * 绑定数据
     *
     * @param savedInstanceState
     */
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        //设置内容
        textView.setText(content);
    }
}
