package com.captain.smartbridge.UI.Adapters.tian;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.captain.smartbridge.R;
import com.captain.smartbridge.model.MonBridge;

import java.util.List;

/**
 * Created by captain on 18-4-12.
 */

public class MonitorAdapter extends BaseAdapter {

    private static int counter = 0;
    List<MonBridge> mData = null;
    Context mContext = null;

    public MonitorAdapter(Context context, List<MonBridge> data) {
        mContext = context;
        mData = data;
        counter = 0;
    }


    @Override
    public int getCount() {
        return mData.size();
    }

    @Override
    public Object getItem(int i) {
        return mData.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder holder = null;
        View rootView = view;

        if (view == null) {
            LayoutInflater inflater = LayoutInflater.from(mContext);
            rootView = inflater.inflate(R.layout.list_monitor, viewGroup, false);
            holder = new ViewHolder();
            holder.name = (TextView) rootView.findViewById(R.id.momession_name);
            holder.code = (TextView) rootView.findViewById(R.id.momession_code);
            holder.location = (TextView) rootView.findViewById(R.id.momession_location);
            holder.time = (TextView) rootView.findViewById(R.id.momession_time);
            rootView.setTag(holder);
        } else {
            holder = (ViewHolder) rootView.getTag();
        }

        MonBridge item = mData.get(i);
        holder.name.setText(item.getQlmc());
        holder.code.setText(item.getJclxmc());
        holder.location.setText(item.getSf() + item.getSc() + item.getQx());
        holder.time.setText(item.getKsjcsj());
        return rootView;
    }

    private static class ViewHolder {
        public TextView name = null;
        public TextView code = null;
        public TextView location = null;
        public TextView time = null;
    }
}
