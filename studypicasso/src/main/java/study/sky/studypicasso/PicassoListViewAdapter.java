package study.sky.studypicasso;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2017/12/22 0022.
 */

public class PicassoListViewAdapter extends BaseAdapter {

    private Context context;
    private List<PicassoBean.ResultsBean> list;

    public PicassoListViewAdapter(Context context, List<PicassoBean.ResultsBean> list) {
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
        ViewHolder holder = null;
        if (convertView == null) {
            convertView = View.inflate(context, R.layout.listview_item_picasso, null);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        holder.textView.setText("item" + (position + 1));

        //加载图片
        Picasso.with(context)
                .load(list.get(position).getUrl())
                .placeholder(R.mipmap.ic_launcher) //占位,表示正在加载中显示的图片
                .error(R.mipmap.ic_launcher)
                .into(holder.imageView);

        return convertView;
    }

    class ViewHolder {

        @BindView(R.id.image_picasso_item)
        ImageView imageView;
        @BindView(R.id.tv_picasso_item)
        TextView textView;

        public ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
