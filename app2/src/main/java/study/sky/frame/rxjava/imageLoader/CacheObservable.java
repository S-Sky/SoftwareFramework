package study.sky.frame.rxjava.imageLoader;

import android.util.Log;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;

/**
 * Created by Administrator on 2018/1/16 0016.
 * 三级缓存的父类
 */

public abstract class CacheObservable {

    /**
     * 获取缓存数据
     *
     * @param url
     * @return
     */
    public Observable<ImageBean> getImage(final String url) {

        return Observable.create(new ObservableOnSubscribe<ImageBean>() {
            @Override
            public void subscribe(ObservableEmitter<ImageBean> e) throws Exception {
                //如果没有取消订阅
                if (!e.isDisposed()) {
                    ImageBean imageBean = getDataFromCache(url);
                    Log.e("url==",imageBean.getUrl());
                    e.onNext(imageBean);
                    e.onComplete();
                }
            }
        });
    }

    /**
     * 取出缓存数据
     *
     * @param url
     * @return
     */
    public abstract ImageBean getDataFromCache(String url);

    /**
     * 缓存数据
     *
     * @param imageBean
     */
    public abstract void putDataToCache(ImageBean imageBean);
}
