package study.sky.studyokhttp;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.BitmapCallback;

import java.util.List;

import okhttp3.Call;

/**
 * Created by Administrator on 2017/12/18 0018.
 */

public class OKHttpListAdapter extends BaseAdapter {

    private List<DataBean.ItemData> list;
    private Context context;

    public OKHttpListAdapter(Context context, List<DataBean.ItemData> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = null;
        if (convertView == null) {
            viewHolder = new ViewHolder();
            convertView = View.inflate(context, R.layout.okhttp_list_item, null);
            viewHolder.imageView = convertView.findViewById(R.id.image);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        DataBean.ItemData data = list.get(position);

        //在列表中使用okhttp-utils请求图片   据说没有Glide好

        final ViewHolder finalViewHolder = viewHolder;
        OkHttpUtils
                .get()//
                .url(data.getUrl())//
                .tag(this)//
                .build()//
                .connTimeOut(20000) //连接超时
                .readTimeOut(20000) //读取超时
                .writeTimeOut(20000)
                .execute(new BitmapCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {

                    }

                    @Override
                    public void onResponse(Bitmap bitmap, int id) {
                        Log.e("TAG", "onResponse：complete");
                        finalViewHolder.imageView.setImageBitmap(bitmap);
                    }
                });

        return convertView;
    }

    static class ViewHolder {
        ImageView imageView;
    }

}
