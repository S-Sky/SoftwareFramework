package com.sky.studyglide;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2017/12/26 0026.
 */

public class GlideRecyclerViewAdapter extends RecyclerView.Adapter<GlideRecyclerViewAdapter.ViewHolder> {


    private Context context;
    private List<GlideBean.ResultsBean> list;

    public GlideRecyclerViewAdapter(Context context, List<GlideBean.ResultsBean> list) {
        this.context = context;
        this.list = list;

    }

    @Override
    public GlideRecyclerViewAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = View.inflate(context, R.layout.glide_item_recycler_view, null);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(GlideRecyclerViewAdapter.ViewHolder holder, int position) {

        int width = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 200, context.getResources().getDisplayMetrics());
        int height = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 200F, context.getResources().getDisplayMetrics());

        Glide.with(context)
                .load(list.get(position).getUrl())
                .placeholder(R.mipmap.ic_launcher_round)//占位图
                .error(R.mipmap.ic_launcher) //出错的占位图
                .override(width, height) //图片显示的分辨率,像素值 可以转换为DP在设置
                .animate(R.anim.glide_anim) //加载图片的时候显示的动画
                .centerCrop() //图片显示样式
                .fitCenter()
                .into(holder.imageView);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.image_item)
        ImageView imageView;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
