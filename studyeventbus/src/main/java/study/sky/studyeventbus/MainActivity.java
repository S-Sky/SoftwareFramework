package study.sky.studyeventbus;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView tv_show;
    private Button btn_event;
    private Button btn_sticky;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
    }

    private void initView() {
        tv_show = findViewById(R.id.tv_show);
        btn_event = findViewById(R.id.btn_event);
        btn_sticky = findViewById(R.id.btn_sticky);

        btn_event.setOnClickListener(this);
        btn_sticky.setOnClickListener(this);

        //注册广播
        EventBus.getDefault().register(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_event:
                Intent sendIntent = new Intent(MainActivity.this, EventBusSendActivity.class);
                startActivity(sendIntent);
                break;
            case R.id.btn_sticky:
                //发送粘性事件
                EventBus.getDefault().postSticky(new StickyEvent("发送粘性事件"));
                Intent stickyIntent = new Intent(MainActivity.this, EventBusSendActivity.class);
                startActivity(stickyIntent);
                break;
        }
    }

    //接收消息(发送方发送消息,这里就可以接受到消息)
    @Subscribe(threadMode = ThreadMode.MAIN) //主线程中执行
    public void getMessage(MessageEventBus message) {
        //显示接收的消息
        tv_show.setText(message.toString());
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //解注册
        EventBus.getDefault().unregister(this);
    }
}
