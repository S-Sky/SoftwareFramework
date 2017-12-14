package com.sky.SoftwareFramework.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

/**
 * Created by Administrator on 2017/12/14 0014.
 */

public class CommonFrameFragmentAdapter extends BaseAdapter {

    private Context mContext;
    private String[] datas;

    public CommonFrameFragmentAdapter(Context context, String[] datas) {
        this.mContext = context;
        this.datas = datas;
    }

    @Override
    public int getCount() {
        return datas.length;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        TextView textView = new TextView(mContext);
        textView.setTextColor(Color.BLACK);
        textView.setPadding(10, 10, 0, 10);
        textView.setTextSize(20);
        textView.setText(datas[position]);
        return textView;
    }
}
