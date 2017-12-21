package study.sky.studyvolley;

import android.graphics.Bitmap;
import android.util.LruCache;

import com.android.volley.toolbox.ImageLoader;

/**
 * Created by Administrator on 2017/12/21 0021.
 */

public class BitmapCache implements ImageLoader.ImageCache {

    //用于缓存
    private LruCache<String, Bitmap> mCache;

    public BitmapCache() {
        int maxSize = 10 * 1024 * 1024; //缓存的大小10M
        mCache = new LruCache<String, Bitmap>(maxSize) {
            @Override
            protected int sizeOf(String key, Bitmap value) {
                //return super.sizeOf(key, value);
                return value.getRowBytes() * value.getHeight();
            }
        };
    }

    @Override
    public Bitmap getBitmap(String key) {
        return mCache.get(key);
    }

    @Override
    public void putBitmap(String key, Bitmap value) {
        mCache.put(key, value);
    }
}
