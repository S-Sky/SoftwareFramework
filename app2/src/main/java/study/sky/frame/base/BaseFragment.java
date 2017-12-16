package study.sky.frame.base;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by Administrator on 2017/12/16 0016.
 */

public abstract class BaseFragment extends Fragment {

    protected Context context;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = getActivity();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = LayoutInflater.from(context).inflate(getResource(), container, false);
        init(view);
        return view;
    }

    protected abstract int getResource(); //初始化资源文件

    protected abstract void init(View view); //初始化组件

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initData();
    }

    //加载数据
    protected void initData() {
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        startDestroy();
        System.gc();
    }

    //销毁数据,释放内存
    protected abstract void startDestroy();

}
