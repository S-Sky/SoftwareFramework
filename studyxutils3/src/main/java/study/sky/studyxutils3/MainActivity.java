package study.sky.studyxutils3;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.x;

import study.sky.studyxutils3.annotation.FragmentActivity;
import study.sky.studyxutils3.net.XUtils3NetActivity;

@ContentView(R.layout.activity_main)
public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_main);
        x.view().inject(this);
    }

    @Event(value = {R.id.btn_annotation, R.id.btn_net})
    private void getEvent(View view) {

        switch (view.getId()) {
            case R.id.btn_annotation:
                startActivity(new Intent(MainActivity.this, FragmentActivity.class));
                break;
            case R.id.btn_net:
                startActivity(new Intent(MainActivity.this, XUtils3NetActivity.class));
                break;
//            case R.id.btn_image:
//                Toast.makeText(this, "点击事件btn_image", Toast.LENGTH_SHORT).show();
//                break;
//            case R.id.btn_images_list:
//                Toast.makeText(this, "点击事件btn_images_list", Toast.LENGTH_SHORT).show();
//                break;
        }

    }

}
