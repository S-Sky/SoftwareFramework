package study.sky.studyfresco;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
    }

    @OnClick({R.id.btn_fresco_progress, R.id.btn_fresco_cut, R.id.btn_circle, R.id.btn_gif})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_fresco_progress:
                startActivity(new Intent(this, FrescoProgressActivity.class));
                break;
            case R.id.btn_fresco_cut:
                startActivity(new Intent(this, FrescoCutActivity.class));
                break;
            case R.id.btn_circle:
                startActivity(new Intent(this, FrescoCircleActivity.class));
                break;
            case R.id.btn_gif:
                startActivity(new Intent(this, FrescoGifActivity.class));
                break;
        }
    }
}
