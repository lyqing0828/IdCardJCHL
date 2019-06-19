package com.idcard.hnd.idcardjchl.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.idcard.hnd.idcardjchl.R;
import com.idcard.hnd.idcardjchl.bean.Location;
import com.idcard.hnd.idcardjchl.bean.Region;

import java.util.List;

/**
 * Created by Administrator on 2019/6/17.
 */

public class RegionAdapter extends BaseAdapter {
    private Context mContext;
    private List<Region> data;

    //构造函数:要理解(这里构造方法的意义非常强大,你也可以传一个数据集合的参数,可以根据需要来传参数)
    public RegionAdapter(Context context, List<Region> data) {
        this.mContext = context;
        this.data = data;
    }
    public void setRegionData(List<Region> data){
        this.data.clear();
        this.data.addAll(data);
        notifyDataSetChanged();
    }

    //这里的getCount方法是程序在加载显示到ui上时就要先读取的，这里获得的值决定了listview显示多少行
    @Override
    public int getCount() {
        //在实际应用中，此处的返回值是由从数据库中查询出来的数据的总条数
        return data.size();
    }

    //根据ListView所在位置返回View
    @Override
    public Object getItem(int i) {
        return data.get(i);
    }

    //根据ListView位置得到数据源集合中的Id
    @Override
    public long getItemId(int i) {
        return i;
    }

    //重写adapter最重要的就是重写此方法，此方法也是决定listview界面的样式的
    @Override
    public View getView(int position, View convertView, ViewGroup viewGroup) {
        ViewHolder viewHolder = null;
        if (convertView == null) {
            viewHolder = new ViewHolder();
            convertView = LayoutInflater.from(mContext).inflate(
                    R.layout.region_layout, null);
            viewHolder.name_tv = (TextView) convertView
                    .findViewById(R.id.region_name);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.name_tv.setText(data.get(position).getName());
        return convertView;
    }

    public static class ViewHolder {
        public TextView name_tv;
    }
}
