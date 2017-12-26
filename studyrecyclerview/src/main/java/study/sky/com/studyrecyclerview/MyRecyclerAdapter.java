package study.sky.com.studyrecyclerview;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2017/12/25 0025.
 */

public class MyRecyclerAdapter extends RecyclerView.Adapter<MyRecyclerAdapter.MyViewHolder> {

    private Context context;
    private List<String> list;

    public MyRecyclerAdapter(Context context, List<String> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = View.inflate(context, R.layout.item_recycler_view, null);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        holder.textView.setText(list.get(position));
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public void addData(int position, String s) {
        list.add(position, s);
        notifyItemInserted(position);//插入数据
    }

    public void removeData(int position) {
        list.remove(position);
        notifyItemRemoved(position);
    }

    class MyViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.image_icon)
        ImageView imageView;
        @BindView(R.id.text)
        TextView textView;
        @BindView(R.id.item_layout)
        RelativeLayout item_layout;

        public MyViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (onItemClickListener != null) {
                        onItemClickListener.onItemClick(v, getLayoutPosition());
                    }
                }
            });

        }
    }

    /**
     * 点击RecyclerView某条的监听
     */
    public interface OnItemClickListener {
        /**
         * 当RecyclerView某个被点击的时候回调
         *
         * @param view     点击的item的视图
         * @param position 当前item在列表中的下标
         */
        public void onItemClick(View view, int position);
    }

    private OnItemClickListener onItemClickListener = null;

    /**
     * 设置RecyclerView的某一条的监听
     *
     * @param onItemClickListener
     */
    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }
}
