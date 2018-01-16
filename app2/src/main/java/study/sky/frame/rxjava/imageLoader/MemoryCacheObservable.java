package study.sky.frame.rxjava.imageLoader;

import android.graphics.Bitmap;
import android.util.Log;
import android.util.LruCache;

/**
 * Created by Administrator on 2018/1/16 0016.
 * 内存缓存
 */

public class MemoryCacheObservable extends CacheObservable {

    private int maxMemory = (int) (Runtime.getRuntime().maxMemory() / 1024);
    private int cacheSize = maxMemory / 4;
    private LruCache<String, Bitmap> mLruCache = new LruCache<String, Bitmap>(cacheSize) {
        @Override
        protected int sizeOf(String key, Bitmap bitmap) {
            return bitmap.getRowBytes() * bitmap.getHeight() / 1024;
        }
    };

    @Override
    public ImageBean getDataFromCache(String url) {
        Log.e("getDataFromCache", "getDataFromMemoryCache");
        Bitmap bitmap = mLruCache.get(url);
        return new ImageBean(bitmap, url);
    }

    @Override
    public void putDataToCache(ImageBean imageBean) {
        mLruCache.put(imageBean.getUrl(), imageBean.getBitmap());
    }
}
