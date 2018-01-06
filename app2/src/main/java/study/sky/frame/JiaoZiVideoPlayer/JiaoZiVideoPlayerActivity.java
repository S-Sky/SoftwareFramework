package study.sky.frame.JiaoZiVideoPlayer;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import cn.jzvd.JZVideoPlayer;
import cn.jzvd.JZVideoPlayerStandard;
import study.sky.frame.JiaoZiVideoPlayer.CustomView.VideoPlayerStandard;
import study.sky.frame.R;

/**
 * Created by Administrator on 2018/1/5 0005.
 */

public class JiaoZiVideoPlayerActivity extends Activity {

    Unbinder mUnbinder;

    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.jz_video)
    VideoPlayerStandard jz_video;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_jiaozi_video_player);
        mUnbinder = ButterKnife.bind(this);

        initView();
    }

    private void initView() {
        tvTitle.setText("JiaoZiVideoPlayer");

        jz_video.setUp("http://jzvd.nathen.cn/342a5f7ef6124a4a8faf00e738b8bee4/cf6d9db0bd4d41f59d09ea0a81e918fd-5287d2089db37e62345123a1be272f8b.mp4",
                JZVideoPlayerStandard.SCREEN_WINDOW_NORMAL,
                "视频");
        //设置封面,不设置的话没有播放之前封面显示为黑色
        Picasso.with(this)
                .load("http://jzvd-pic.nathen.cn/jzvd-pic/1bb2ebbe-140d-4e2e-abd2-9e7e564f71ac.png")
                .into(jz_video.thumbImageView);
    }

    @OnClick(R.id.btn_tiny_window)
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_tiny_window:
                //小窗口播放
                jz_video.startWindowTiny();
                break;
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        JZVideoPlayer.releaseAllVideos(); //释放资源
    }

    @Override
    public void onBackPressed() {
        if (JZVideoPlayer.backPress()) {
            return;
        }
        super.onBackPressed();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mUnbinder.unbind();
    }
}
