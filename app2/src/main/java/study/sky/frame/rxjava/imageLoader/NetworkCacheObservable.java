package study.sky.frame.rxjava.imageLoader;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;

/**
 * Created by Administrator on 2018/1/16 0016.
 * 网络缓存
 */

public class NetworkCacheObservable extends CacheObservable {

    @Override
    public ImageBean getDataFromCache(String url) {
        Log.e("getDataFromCache", "getDataFromNetworkCache");
        Bitmap bitmap = downloadImage(url);
        if (bitmap != null) {
            return new ImageBean(bitmap, url);
        }
        return null;
    }

    @Override
    public void putDataToCache(ImageBean imageBean) {

    }

    /**
     * 下载文件
     *
     * @param url
     * @return
     */
    public Bitmap downloadImage(String url) {
        Bitmap bitmap = null;
        InputStream inputStream = null;
        try {
            URL imageUrl = new URL(url);
            URLConnection urlConnection = (HttpURLConnection) imageUrl.openConnection();
            inputStream = urlConnection.getInputStream();
            bitmap = BitmapFactory.decodeStream(inputStream);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return bitmap;
    }
}
