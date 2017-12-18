package study.sky.studyokhttp;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by Administrator on 2017/12/18 0018.
 * 缓存工具类
 */

public class CacheUtils {

    /**
     * 保存数据
     *
     * @param context
     * @param key
     * @param values
     */
    public static void putString(Context context, String key, String values) {
        SharedPreferences sharedPreferences = context.getSharedPreferences("Sky", Context.MODE_PRIVATE);
        sharedPreferences.edit().putString(key, values).commit();
    }

    public static String getString(Context context, String key) {
        SharedPreferences sharedPreferences = context.getSharedPreferences("Sky", Context.MODE_PRIVATE);
        return sharedPreferences.getString(key, "");
    }
}
