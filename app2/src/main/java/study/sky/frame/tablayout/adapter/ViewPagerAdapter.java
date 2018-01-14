package study.sky.frame.tablayout.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;

import study.sky.frame.tablayout.fragment.TestFragment;

/**
 * Created by Administrator on 2018/1/9 0009.
 */

public class ViewPagerAdapter extends FragmentPagerAdapter {

    private final ArrayList<TestFragment> fragments;

    public ViewPagerAdapter(FragmentManager fm, ArrayList<TestFragment> fragments) {
        super(fm);
        this.fragments = fragments;
    }

    /**
     * 根据位置放回对应的fragment
     *
     * @param position
     * @return
     */
    @Override
    public Fragment getItem(int position) {
        return fragments.get(position);
    }

    @Override
    public int getCount() {
        return fragments.size();
    }

    /**
     * 得到页面的标题
     *
     * @param position
     * @return
     */
    @Override
    public CharSequence getPageTitle(int position) {
        return fragments.get(position).getTitle();
    }
}
