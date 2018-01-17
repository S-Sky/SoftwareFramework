package study.sky.frame.rxjava.imageLoader;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import com.jakewharton.disklrucache.DiskLruCache;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileDescriptor;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by Administrator on 2018/1/16 0016.
 * 文件缓存
 * DiskLruCache的下载地址
 * android.googlesource.com/platform/libcore/+/jb-mr2-release/luni/src/main/java/libcore/io/DiskLruCache.java
 */

public class DiskCacheObservable extends CacheObservable {

    private Context mContext;
    private DiskLruCache mDiskLruCache;

    private final int maxSize = 10 * 1024 * 1024; //这里是10M

    public DiskCacheObservable(Context mContext) {
        this.mContext = mContext;
        initDiskLruCache();
    }

    @Override
    public ImageBean getDataFromCache(String url) {
        Log.e("getDataFromCache", "getDataFromDiskCache");
        Bitmap bitmap = getDataFromDiskLruCache(url);
        return new ImageBean(bitmap, url);
    }

    @Override
    public void putDataToCache(final ImageBean imageBean) {
        //由于网络读取需要在子线程中执行
        Observable.concat(new ObservableSource<ObservableSource<?>>() {
            @Override
            public void subscribe(Observer<? super ObservableSource<?>> observer) {
                putDataToDiskLruCache(imageBean);
            }
        }).subscribeOn(Schedulers.io()).subscribe();
    }

    /**
     * 初始换DiskLruCache
     */

    public void initDiskLruCache() {
        //获取缓存的目录
        File cacheDir = DiskCacheUtils.getDiskCacheDir(mContext, "our_cache");
        if (!cacheDir.exists()) {
            cacheDir.mkdirs();
        }
        int versionCode = DiskCacheUtils.getAppVersion(mContext);
        try {
            //这里需要注意参数二：缓存版本号，只要不同版本号，缓存都会被清除，重新使用新的
            mDiskLruCache = DiskLruCache.open(cacheDir, versionCode, 1, maxSize);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 获取文件缓存
     *
     * @param url
     * @return
     */
    private Bitmap getDataFromDiskLruCache(String url) {
        Bitmap bitmap = null;
        FileDescriptor fileDescriptor = null;
        FileInputStream fileInputStream = null;
        try {
            //生成图片URL对应的key
            final String key = DiskCacheUtils.getMD5String(url);
            //查找key对应的缓存
            DiskLruCache.Snapshot snapshot = mDiskLruCache.get(key);
            if (snapshot != null) {
                fileInputStream = (FileInputStream) snapshot.getInputStream(0);
                fileDescriptor = fileInputStream.getFD();
            }
            //将缓存数据解析成Bitmap对象
            if (fileDescriptor != null) {
                bitmap = BitmapFactory.decodeStream(fileInputStream);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (fileInputStream != null) {
                try {
                    fileInputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return bitmap;
    }

    /**
     * 缓存文件数据
     *
     * @param imageBean
     */
    private void putDataToDiskLruCache(ImageBean imageBean) {
        try {
            //获取将要缓存的图片的对应唯一key值
            String key = DiskCacheUtils.getMD5String(imageBean.getUrl());
            //获取DiskLrcCache的Editor
            DiskLruCache.Editor editor = mDiskLruCache.edit(key);
            if (editor != null) {
                //从Editor中获取OutputStream
                OutputStream outputStream = editor.newOutputStream(0);
                //下载网络图片并且保存至DiskLruCache图片缓存中
                boolean isSuccess = downloadUrlToStream(imageBean.getUrl(), outputStream);
                if (isSuccess) {
                    editor.commit();
                } else {
                    editor.abort();
                }
                mDiskLruCache.flush();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 下载文件
     *
     * @param urlString
     * @param outputStream
     * @return
     */
    private boolean downloadUrlToStream(String urlString, OutputStream outputStream) {
        HttpURLConnection urlConnection = null;
        BufferedOutputStream out = null;
        BufferedInputStream in = null;
        try {
            final URL url = new URL(urlString);
            urlConnection = (HttpURLConnection) url.openConnection();
            in = new BufferedInputStream(urlConnection.getInputStream(), 8 * 1024);
            out = new BufferedOutputStream(outputStream, 8 * 1024);
            int b;
            while ((b = in.read()) != -1) {
                out.write(b);
            }
            return true;
        } catch (final IOException e) {
            e.printStackTrace();
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
            try {
                if (out != null) {
                    out.close();
                }
                if (in != null) {
                    in.close();
                }
            } catch (final IOException e) {
                e.printStackTrace();
            }
        }
        return false;
    }

}
