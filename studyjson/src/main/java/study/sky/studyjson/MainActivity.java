package study.sky.studyjson;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import study.sky.studyjson.bean.JsonToJavaObject;

/**
 * JSON解析
 * 1、将json格式的字符串{}转换成Java对象
 * 2、将json格式的字符串{}转换成Java对象的list
 * 3、复杂json数据解析
 * 4、特殊json数据接续
 */

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private Button btn_json_to_object;
    private Button btn_json_to_list;

    private TextView text_orignal;
    private TextView text_last;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();

        initListener();
    }

    private void initListener() {
        btn_json_to_object.setOnClickListener(this);
        btn_json_to_list.setOnClickListener(this);

    }

    private void initView() {
        setContentView(R.layout.activity_main);
        btn_json_to_object = findViewById(R.id.btn_json_to_object);
        btn_json_to_list = findViewById(R.id.btn_json_to_list);

        text_orignal = findViewById(R.id.text_orignal);
        text_last = findViewById(R.id.text_last);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_json_to_object:
                jsonToJavaObject();
                break;
            case R.id.btn_json_to_list:
                jsonToJavaList();
                break;
        }
    }

    private void jsonToJavaList() {
        String jsonData = "[{\"name\":\"胡小威\" , \"age\":20 , \"male\":true},{\"name\":\"赵小亮\" , \"age\":22 , \"male\":false}]";
        text_orignal.setText(jsonData);
        List<JsonToJavaObject> list = new ArrayList<>();
        try {
            JSONArray jsonArray = new JSONArray(jsonData);
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                if (jsonObject != null) {
                    String name = jsonObject.getString("name");
                    int age = jsonObject.optInt("age");
                    boolean male = jsonObject.optBoolean("male");
                    JsonToJavaObject object = new JsonToJavaObject(name, age, male);
                    list.add(object);
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        text_last.setText(list.toString());

    }

    private void jsonToJavaObject() {
        String jsonData = "{\"name\":\"胡小威\" , \"age\":20 , \"male\":true}";
        text_orignal.setText(jsonData);
        JsonToJavaObject jsonToJavaObject = null;
        //解析json
        try {
            JSONObject jsonObject = new JSONObject(jsonData);
            String name = jsonObject.getString("name");
            int age = jsonObject.optInt("age");
            boolean male = jsonObject.optBoolean("male");
            jsonToJavaObject = new JsonToJavaObject(name, age, male);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        text_last.setText(jsonToJavaObject.toString());
    }
}
