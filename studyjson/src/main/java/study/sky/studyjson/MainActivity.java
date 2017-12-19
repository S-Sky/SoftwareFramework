package study.sky.studyjson;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import study.sky.studyjson.bean.JsonToJavaObject;

/**
 * JSON解析
 */

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private Button btn_json_to_object;
    private Button btn_json_to_list;
    private Button btn_object_to_json;
    private Button btn_list_to_json;
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
        btn_object_to_json.setOnClickListener(this);
        btn_list_to_json.setOnClickListener(this);
    }

    private void initView() {
        setContentView(R.layout.activity_main);
        btn_json_to_object = findViewById(R.id.btn_json_to_object);
        btn_json_to_list = findViewById(R.id.btn_json_to_list);
        btn_object_to_json = findViewById(R.id.btn_object_to_json);
        btn_list_to_json = findViewById(R.id.btn_list_to_json);
        text_orignal = findViewById(R.id.text_orignal);
        text_last = findViewById(R.id.text_last);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_json_to_object: //将json格式的字符串{}转换成Java对象
                //jsonToJavaObject(); //原生方法解析
                //gsonToJavaObject(); //使用Gson解析
                fastJsonToObject(); //FastJson解析
                break;
            case R.id.btn_json_to_list: //将json格式的字符串{}转换成Java对象的list
                //jsonToJavaList(); //原生方法解析
                //gsonToJavaList();//使用Gson解析
                fastJsonToObjectList(); //FastJson解析
                break;
            case R.id.btn_object_to_json: //将Java对象转换为json字符串{}
                //objectToJson();//使用Gson转换
                fastJsonObjectToJson();//FastJson转换
                break;
            case R.id.btn_list_to_json://将Java对象的List转换为json字符串{}
                //objectListToJson();//使用Gson转换
                fastJsonListToJson();//FastJson转换
                break;
        }
    }

    private void fastJsonListToJson() {

        List<JsonToJavaObject> list = new ArrayList<JsonToJavaObject>();
        JsonToJavaObject object = new JsonToJavaObject("胡小威", 20, true);
        JsonToJavaObject object1 = new JsonToJavaObject("胡大威", 21, false);
        list.add(object);
        list.add(object1);

        String jsonString = JSON.toJSONString(list);
        text_orignal.setText(list.toString());
        text_last.setText(jsonString);
    }

    private void fastJsonObjectToJson() {
        JsonToJavaObject object = new JsonToJavaObject("胡小威", 20, true);
        String jsonString = JSON.toJSONString(object);

        text_orignal.setText(object.toString());
        text_last.setText(jsonString);
    }

    private void fastJsonToObjectList() {
        String jsonData = "[{\"name\":\"胡小威\" , \"age\":20 , \"male\":true},{\"name\":\"赵小亮\" , \"age\":22 , \"male\":false}]";

        List<JsonToJavaObject> list = JSON.parseArray(jsonData, JsonToJavaObject.class);

        text_orignal.setText(jsonData);
        text_last.setText(list.toString());
    }

    private void fastJsonToObject() {
        String jsonData = "{\"name\":\"胡小威\" , \"age\":20 , \"male\":true}";

        JsonToJavaObject object = JSON.parseObject(jsonData, JsonToJavaObject.class);

        text_orignal.setText(jsonData);
        text_last.setText(object.toString());
    }

    private void objectListToJson() {
       /* List<JsonToJavaObject> list = new ArrayList<JsonToJavaObject>();
        JsonToJavaObject object = new JsonToJavaObject("胡小威", 20, true);
        JsonToJavaObject object1 = new JsonToJavaObject("胡大威", 21, false);
        list.add(object);
        list.add(object1);*/

        List<List<JsonToJavaObject>> lists = new ArrayList<>();
        List<JsonToJavaObject> list = new ArrayList<>();
        JsonToJavaObject object = new JsonToJavaObject("胡小威", 20, true);
        JsonToJavaObject object1 = new JsonToJavaObject("胡大威", 21, false);
        list.add(object);
        list.add(object1);

        List<JsonToJavaObject> list1 = new ArrayList<>();
        list1.add(object);
        list1.add(object1);

        lists.add(list);
        lists.add(list1);

        Gson gson = new Gson();
        String json = gson.toJson(lists);

        text_orignal.setText(lists.toString());
        text_last.setText(json);
    }

    private void objectToJson() {
        //获取或 创建Java对象
        JsonToJavaObject object = new JsonToJavaObject("胡小威", 20, true);
        //生成JSON对象
        Gson gson = new Gson();
        String json = gson.toJson(object);

        text_orignal.setText(object.toString());
        text_last.setText(json);
    }

    private void gsonToJavaList() {
        String jsonData = "[{\"name\":\"胡小威\" , \"age\":20 , \"male\":true},{\"name\":\"赵小亮\" , \"age\":22 , \"male\":false}]";
        text_orignal.setText(jsonData);
        Gson gson = new Gson();

        List<JsonToJavaObject> list = gson.fromJson(jsonData, new TypeToken<List<JsonToJavaObject>>() {
        }.getType());
        text_last.setText(list.toString());
    }

    private void gsonToJavaObject() {
        String jsonData = "{\"name\":\"胡小威\" , \"age\":20 , \"male\":true}";
        text_orignal.setText(jsonData);
        Gson gson = new Gson();
        JsonToJavaObject object = gson.fromJson(jsonData, JsonToJavaObject.class);

        text_last.setText(object.toString());
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
