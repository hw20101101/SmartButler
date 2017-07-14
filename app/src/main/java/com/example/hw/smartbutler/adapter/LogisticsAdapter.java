package com.example.hw.smartbutler.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.hw.smartbutler.R;
import com.example.hw.smartbutler.entity.LogisticsEntity;

import java.util.List;

/**
 * Created by HW on 14/07/2017.
 * 物流查询适配器
 */

public class LogisticsAdapter extends BaseAdapter{

    private Context context;
    private List<LogisticsEntity> list;
    private LogisticsEntity entity;

    //布局加载器
    private LayoutInflater inflater;

    //构造方法
    public LogisticsAdapter(Context context, List<LogisticsEntity> list){
        this.context = context;
        this.list = list;

        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
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
    public View getView(int position, View view, ViewGroup viewGroup) {
        ViewHolder viewHolder = null;

        if (viewHolder == null){//第一次加载
            view = inflater.inflate(R.layout.activity_logistics_item, null);
            viewHolder = new ViewHolder();
            viewHolder.initView(view);
            //设置缓存
            view.setTag(viewHolder);

        } else {
            viewHolder = (ViewHolder) view.getTag();
        }

        //设置数据
        entity = list.get(position);
        viewHolder.setViewData(entity);
        return view;
    }

    class ViewHolder {

        private TextView tv_remark, tv_zone, tv_time;

        private void initView(View view){
            tv_remark = view.findViewById(R.id.tv_remark);
            tv_time = view.findViewById(R.id.tv_time);
        }

        private void setViewData(LogisticsEntity entity){
            tv_remark.setText(entity.getRemark());
            tv_time.setText(entity.getTime());
        }
    }
}
