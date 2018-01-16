package study.sky.frame.rxjava.imageLoader;

import android.graphics.Bitmap;

/**
 * Created by Administrator on 2018/1/16 0016.
 */

public class ImageBean {

    private String url;
    private Bitmap bitmap;

    public ImageBean(Bitmap bitmap, String url) {
        this.url = url;
        this.bitmap = bitmap;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Bitmap getBitmap() {
        return bitmap;
    }

    public void setBitmap(Bitmap bitmap) {
        this.bitmap = bitmap;
    }
}
