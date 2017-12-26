package study.sky.frame.activity;

import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import study.sky.frame.R;
import study.sky.frame.fragment.Fragment_1;
import study.sky.frame.fragment.Fragment_2;
import study.sky.frame.fragment.Fragment_3;
import study.sky.frame.fragment.Fragment_4;

/**
 * 框架二
 */
public class MainActivity extends AppCompatActivity {

    @BindView(R.id.img_home)
    ImageView mImgHome;
    @BindView(R.id.tv_home)
    TextView mTvHome;
    @BindView(R.id.img_house)
    ImageView mImgHouse;
    @BindView(R.id.tv_house)
    TextView mTvHouse;
    @BindView(R.id.img_buy)
    ImageView mImgBuy;
    @BindView(R.id.tv_buy)
    TextView mTvBuy;
    @BindView(R.id.img_mine)
    ImageView mImgMine;
    @BindView(R.id.tv_mine)
    TextView mTvMine;

    private Fragment_1 fragment_1;
    private Fragment_2 fragment_2;
    private Fragment_3 fragment_3;
    private Fragment_4 fragment_4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        init();
    }

    private void init() {
        showFragment(0);
        mImgHome.setBackgroundResource(R.drawable.ic_show_press);
        mTvHome.setTextColor(getResources().getColor(R.color.red));
    }

    @OnClick({R.id.parent_home, R.id.parent_house, R.id.parent_buy, R.id.parent_mine})
    public void onViewClicked(View view) {
        reSetBackground();
        switch (view.getId()) {
            case R.id.parent_home:
                showFragment(0);
                mImgHome.setBackgroundResource(R.drawable.ic_show_press);
                mTvHome.setTextColor(getResources().getColor(R.color.red));
                break;
            case R.id.parent_house:
                showFragment(1);
                mImgHouse.setBackgroundResource(R.drawable.ic_news_press);
                mTvHouse.setTextColor(getResources().getColor(R.color.red));
                break;
            case R.id.parent_buy:
                showFragment(2);
                mImgBuy.setBackgroundResource(R.drawable.ic_bottom_share_press);
                mTvBuy.setTextColor(getResources().getColor(R.color.red));
                break;
            case R.id.parent_mine:
                showFragment(3);
                mImgMine.setBackgroundResource(R.drawable.ic_me_press);
                mTvMine.setTextColor(getResources().getColor(R.color.red));
                break;
            default:
                break;
        }
    }

    //重置底部颜色
    private void reSetBackground() {
        mImgHome.setBackgroundResource(R.drawable.ic_show);
        mTvHome.setTextColor(getResources().getColor(R.color.color9));

        mImgHouse.setBackgroundResource(R.drawable.ic_news);
        mTvHouse.setTextColor(getResources().getColor(R.color.color9));

        mImgBuy.setBackgroundResource(R.drawable.ic_bottom_share);
        mTvBuy.setTextColor(getResources().getColor(R.color.color9));

        mImgMine.setBackgroundResource(R.drawable.ic_me);
        mTvMine.setTextColor(getResources().getColor(R.color.color9));
    }

    private void showFragment(int position) {
        //打开事务
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        hideFragment(ft);
        switch (position) {
            case 0:
                if (fragment_1 != null) {
                    ft.show(fragment_1);
                } else {
                    fragment_1 = new Fragment_1();
                    ft.add(R.id.main_fragment, fragment_1);
                }
                break;
            case 1:
                if (fragment_2 != null) {
                    ft.show(fragment_2);
                } else {
                    fragment_2 = new Fragment_2();
                    ft.add(R.id.main_fragment, fragment_2);
                }
                break;
            case 2:
                if (fragment_3 != null) {
                    ft.show(fragment_3);
                } else {
                    fragment_3 = new Fragment_3();
                    ft.add(R.id.main_fragment, fragment_3);
                }
                break;
            case 3:
                if (fragment_4 != null) {
                    ft.show(fragment_4);
                } else {
                    fragment_4 = new Fragment_4();
                    ft.add(R.id.main_fragment, fragment_4);
                }
                break;
        }
        ft.commit();
    }

    private void hideFragment(FragmentTransaction ft) {
        if (fragment_1 != null) {
            ft.hide(fragment_1);
        }
        if (fragment_2 != null) {
            ft.hide(fragment_2);
        }
        if (fragment_3 != null) {
            ft.hide(fragment_3);
        }
        if (fragment_4 != null) {
            ft.hide(fragment_4);
        }
    }

}
