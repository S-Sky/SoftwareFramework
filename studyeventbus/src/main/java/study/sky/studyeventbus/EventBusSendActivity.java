package study.sky.studyeventbus;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

/**
 * Created by Administrator on 2017/12/22 0022.
 */

public class EventBusSendActivity extends Activity implements View.OnClickListener {

    private TextView tv_show;
    private Button btn_send;
    private Button btn_sticky;

    private boolean isFirstFlag = true;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_bus_send);
        initView();
    }

    private void initView() {
        tv_show = findViewById(R.id.tv_show);
        btn_send = findViewById(R.id.btn_event_send_main);
        btn_sticky = findViewById(R.id.btn_event_sticky);

        btn_send.setOnClickListener(this);
        btn_sticky.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_event_send_main:
                //发送消息
                EventBus.getDefault().post(new MessageEventBus("study", "eventBus"));
                finish();
                break;
            case R.id.btn_event_sticky: //接受粘性事件
                if (isFirstFlag) {
                    isFirstFlag = false;
                    //如果是第一次  就注册
                    EventBus.getDefault().register(EventBusSendActivity.this);
                }
                break;
        }
    }

    //接受粘性事件
    @Subscribe(threadMode = ThreadMode.MAIN, sticky = true)
    public void stickyEvent(StickyEvent event) {
        tv_show.setText(event.getString());
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}
