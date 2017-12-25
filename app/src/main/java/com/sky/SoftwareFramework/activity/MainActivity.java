package com.sky.SoftwareFramework.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.widget.RadioGroup;

import com.sky.SoftwareFramework.R;
import com.sky.SoftwareFramework.base.BaseFragment;
import com.sky.SoftwareFramework.fragment.CommonFrameFragment;
import com.sky.SoftwareFramework.fragment.CustomFragment;
import com.sky.SoftwareFramework.fragment.OtherFragment;
import com.sky.SoftwareFramework.fragment.ThirdPartyFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 框架
 */

public class MainActivity extends FragmentActivity {

    @BindView(R.id.rg_main)
    RadioGroup mRadioGroup;

    private List<BaseFragment> mBaseFragment;
    /**
     * 选中的Fragment对应的位置
     */
    private int position;
    /**
     * 上次切换的Fragment
     */
    private Fragment mFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        //初始化Fragment
        initFragment();
        //设置RadioGroup的监听
        setListener();
    }

    private void setListener() {
        mRadioGroup.setOnCheckedChangeListener(new MyOnCheckedChangeListener());
        //设置默认选中值
        mRadioGroup.check(R.id.rb_common_frame); //这句放在这里才会导致MyOnCheckedChangeListener内部的执行
    }

    class MyOnCheckedChangeListener implements RadioGroup.OnCheckedChangeListener {
        @Override
        public void onCheckedChanged(RadioGroup group, int checkedId) {
            switch (checkedId) {
                case R.id.rb_common_frame:
                    position = 0;
                    break;
                case R.id.rb_thirdparty:
                    position = 1;
                    break;
                case R.id.rb_custom:
                    position = 2;
                    break;
                case R.id.rb_other:
                    position = 3;
                    break;
                default:
                    position = 0;
                    break;
            }
            //根据位置得到对应的Fragment
            BaseFragment toFragment = getFragment(position);
            //替换fragment
            switchFragment(mFragment, toFragment);
        }
    }

    /**
     *
     * @param from 之前显示的Fragment,马上就要被隐藏
     * @param to   马上要切换到的Fragment
     */
    private void switchFragment(Fragment from, Fragment to) {
        if (from != to) { //切换fragment
            mFragment = to;
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            if (!to.isAdded()) { //to没有被添加
                //from隐藏
                if (from != null) {
                    ft.hide(from);
                }
                //添加to
                if (to != null) {
                    ft.add(R.id.fl_content, to).commit();
                }
            } else { //to已经被添加
                //隐藏from
                if (from != null) {
                    ft.hide(from);
                }
                //显示to
                if (to != null) {
                    ft.show(to).commit();
                }
            }
        }
    }

    /**
     * 根据位置得到fragment
     *
     * @param position
     * @return
     */
    private BaseFragment getFragment(int position) {
        BaseFragment baseFragment = mBaseFragment.get(position);
        return baseFragment;
    }

    private void initFragment() {
        mBaseFragment = new ArrayList<BaseFragment>();
        mBaseFragment.add(new CommonFrameFragment());
        mBaseFragment.add(new ThirdPartyFragment());
        mBaseFragment.add(new CustomFragment());
        mBaseFragment.add(new OtherFragment());
    }
}
