package study.sky.frame.universalvideoview;

import android.app.Activity;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.universalvideoview.UniversalMediaController;
import com.universalvideoview.UniversalVideoView;

import butterknife.BindView;
import butterknife.ButterKnife;
import study.sky.frame.R;

/**
 * UniversalVideoView
 * 播放的视频格式和手机有关,调用了系统的API封装的
 */
public class UniversalVideoViewActivity extends Activity implements UniversalVideoView.VideoViewCallback {

    private static final String TAG = "UniversalVideoViewActivity";
    private static final String SEEK_POSITION_KEY = "SEEK_POSITION_KEY";
    private static final String VIDEO_URL = "http://pgccdn.v.baidu.com/1890622578_2514378657_20170523122443.mp4?authorization=bce-auth-v1%2Fc308a72e7b874edd9115e4614e1d62f6%2F2017-05-23T04%3A26%3A14Z%2F-1%2F%2F67f21c74e69c4d38e7f5a8d36dd9a62ef55b5e866227163216b53785d52860ba&responseCacheControl=max-age%3D8640000&responseExpires=Thu%2C+31+Aug+2017+12%3A26%3A14+GMT&xcode=9c16a6fd51f477f84bdcc17b45660fc8f7948e6b526ccb63&time=1515121899&_=1515038775084";

    @BindView(R.id.video_layout)
    FrameLayout mVideoLayout;
    @BindView(R.id.bottom_layout)
    LinearLayout mBottomLayout;
    @BindView(R.id.videoView)
    UniversalVideoView mVideoView;
    @BindView(R.id.media_controller)
    UniversalMediaController mMediaController;
    @BindView(R.id.start)
    Button mStart;
    @BindView(R.id.title_bar)
    LinearLayout title_bar;
    @BindView(R.id.tv_title)
    TextView tv_title;

    private int mSeekPosition;
    private int cachedHeight;
    private boolean isFullscreen;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activiry_universal_video_view);
        ButterKnife.bind(this);
        tv_title.setText("UniversalVideoView");
        mVideoView.setMediaController(mMediaController);
        //设置播放屏幕模式和设置播放地址
        setVideoAreaSize();
        //设置屏幕状态和播放状态的监听
        mVideoView.setVideoViewCallback(this);

        mStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mSeekPosition > 0) {
                    mVideoView.seekTo(mSeekPosition); //设置播放的进度
                }
                mVideoView.start();
                mMediaController.setTitle("MP4视频");
            }
        });
        //播放完成
        mVideoView.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                //Log.d(TAG, "onCompletion ");
            }
        });

    }

    @Override
    protected void onPause() {
        super.onPause();
        //Log.d(TAG, "onPause ");
        if (mVideoView != null && mVideoView.isPlaying()) {
            mSeekPosition = mVideoView.getCurrentPosition();//获得当前的播放位置
            //Log.d(TAG, "onPause mSeekPosition=" + mSeekPosition);
            mVideoView.pause();
        }
    }

    /**
     * 置视频区域大小
     */
    private void setVideoAreaSize() {
        mVideoLayout.post(new Runnable() {
            @Override
            public void run() {
                int width = mVideoLayout.getWidth();
                cachedHeight = (int) (width * 405f / 720f);
//                cachedHeight = (int) (width * 3f / 4f);
//                cachedHeight = (int) (width * 9f / 16f);
                ViewGroup.LayoutParams videoLayoutParams = mVideoLayout.getLayoutParams();
                videoLayoutParams.width = ViewGroup.LayoutParams.MATCH_PARENT;
                videoLayoutParams.height = cachedHeight;
                mVideoLayout.setLayoutParams(videoLayoutParams);
                mVideoView.setVideoPath(VIDEO_URL); //设置播放地址
                mVideoView.requestFocus();
            }
        });
    }


    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        //Log.d(TAG, "onSaveInstanceState Position=" + mVideoView.getCurrentPosition());
        outState.putInt(SEEK_POSITION_KEY, mSeekPosition);
    }

    @Override
    protected void onRestoreInstanceState(Bundle outState) {
        super.onRestoreInstanceState(outState);
        mSeekPosition = outState.getInt(SEEK_POSITION_KEY);
        //Log.d(TAG, "onRestoreInstanceState Position=" + mSeekPosition);
    }


    @Override
    public void onScaleChange(boolean isFullscreen) {
        this.isFullscreen = isFullscreen;
        if (isFullscreen) {
            ViewGroup.LayoutParams layoutParams = mVideoLayout.getLayoutParams();
            layoutParams.width = ViewGroup.LayoutParams.MATCH_PARENT;
            layoutParams.height = ViewGroup.LayoutParams.MATCH_PARENT;
            mVideoLayout.setLayoutParams(layoutParams);
            mBottomLayout.setVisibility(View.GONE);
            //全屏就隐藏标题栏
            title_bar.setVisibility(View.GONE);
        } else {
            ViewGroup.LayoutParams layoutParams = mVideoLayout.getLayoutParams();
            layoutParams.width = ViewGroup.LayoutParams.MATCH_PARENT;
            layoutParams.height = this.cachedHeight;
            mVideoLayout.setLayoutParams(layoutParams);
            mBottomLayout.setVisibility(View.VISIBLE);
            //竖直方向的时候显示标题栏
            title_bar.setVisibility(View.VISIBLE);
        }

        // switchTitleBar(!isFullscreen);
    }

   /* private void switchTitleBar(boolean show) {
        android.support.v7.app.ActionBar supportActionBar = getSupportActionBar();
        if (supportActionBar != null) {
            if (show) {
                supportActionBar.show();
            } else {
                supportActionBar.hide();
            }
        }
    }*/

    @Override
    public void onPause(MediaPlayer mediaPlayer) {
        //Log.d(TAG, "onPause UniversalVideoView callback");
    }

    @Override
    public void onStart(MediaPlayer mediaPlayer) {
        //Log.d(TAG, "onStart UniversalVideoView callback");
    }

    @Override
    public void onBufferingStart(MediaPlayer mediaPlayer) {
        //Log.d(TAG, "onBufferingStart UniversalVideoView callback");
    }

    @Override
    public void onBufferingEnd(MediaPlayer mediaPlayer) {
        //Log.d(TAG, "onBufferingEnd UniversalVideoView callback");
    }

    @Override
    public void onBackPressed() {
        if (this.isFullscreen) {
            mVideoView.setFullscreen(false);
        } else {
            super.onBackPressed();
        }
    }

}
