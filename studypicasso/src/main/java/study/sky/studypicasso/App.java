package study.sky.studypicasso;

import android.app.Application;

import org.xutils.x;

/**
 * Created by Administrator on 2017/12/22 0022.
 */

public class App extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        //xUtils3初始化
        x.Ext.init(this);
        // 是否输出debug日志, 开启debug会影响性能.(默认设置为true)
        x.Ext.setDebug(BuildConfig.DEBUG);
    }
}
