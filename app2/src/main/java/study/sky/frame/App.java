package study.sky.frame;

import android.app.Application;
import android.content.Context;
import android.util.DisplayMetrics;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.zxy.recovery.core.Recovery;

import study.sky.frame.activity.MainActivity;

/**
 * Created by Administrator on 2018/1/6 0006.
 */

public class App extends Application {

    public static int H, W;
    public static App app;

    @Override
    public void onCreate() {
        super.onCreate();
        app = this;
        getScreen(this);
        Fresco.initialize(this);

        //让软件状态还原的框架
        Recovery.getInstance()
                .debug(true)
                .recoverInBackground(false)
                .recoverStack(true)
                .mainPage(MainActivity.class)
                .init(this);
    }

    public void getScreen(Context aty) {
        DisplayMetrics dm = aty.getResources().getDisplayMetrics();
        H = dm.heightPixels;
        W = dm.widthPixels;
    }
}
