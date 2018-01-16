package study.sky.frame.rxjava.imageLoader;

import android.content.Context;
import android.util.Log;
import android.widget.ImageView;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

/**
 * Created by Administrator on 2018/1/16 0016.
 * 模拟Picasso源码，使用构造者模式创建RxImageLoader
 */

public class RxImageLoader {

    static RxImageLoader singleton;
    private String mUrl;
    private RequestCreator requestCreator;

    /**
     * private是为了防止创建该对象
     *
     * @param builder
     */
    private RxImageLoader(Builder builder) {
        requestCreator = new RequestCreator(builder.context);
    }

    /**
     * 单例模式 获取唯一实例
     *
     * @param context
     * @return
     */
    public static RxImageLoader with(Context context) {
        if (singleton == null) {
            synchronized (RxImageLoader.class) {
                if (singleton == null) {
                    singleton = new Builder(context).build();
                }
            }
        }
        return singleton;
    }

    public RxImageLoader load(String url) {
        this.mUrl = url;
        return singleton;
    }

    /**
     * first方法表示判断，如果IamgeBean中的bitmap为空，那么跳过此次连接，
     * 例如，requestCreator.getImageFromMemory(mUrl)获取的bitmap为空，
     * 那么直接跳过这次concat连接，进行requestCreator.getImageFromDisk(mUrl)操作，
     * 直到bitmap不为空则程序继续往下执行，断开concat的连接
     *
     * @param imageView
     */

    public void into(final ImageView imageView) {

        Observable.concat(requestCreator.getImageFromMemory(mUrl), requestCreator.getImageFromDisk(mUrl), requestCreator.getImageFromNetwork(mUrl))
                 .first(new ImageBean(null, mUrl)).toObservable()
                .subscribe(new Observer<ImageBean>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(ImageBean image) {
                        if (image.getBitmap() != null) {
                            imageView.setImageBitmap(image.getBitmap());
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
                    }

                    @Override
                    public void onComplete() {
                        Log.e("RxImageLoader", "onComplete");
                    }
                });

    }

    public static class Builder {

        private Context context;

        public Builder(Context context) {
            this.context = context;
        }

        public RxImageLoader build() {
            return new RxImageLoader(this);
        }
    }
}
