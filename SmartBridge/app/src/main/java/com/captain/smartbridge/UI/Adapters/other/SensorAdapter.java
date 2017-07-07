package com.captain.smartbridge.UI.Adapters.other;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.captain.smartbridge.R;
import com.captain.smartbridge.model.other.Sensor;

import java.util.List;

/**
 * Created by Captain on 17/7/7.
 */

public class SensorAdapter extends BaseAdapter {
    private static int counter = 0;
    List<Sensor> mData = null;
    Context mContext = null;

    public SensorAdapter(Context context, List<Sensor> data) {
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
            rootView = inflater.inflate(R.layout.list_sensor, viewGroup, false);
            holder = new ViewHolder();
            holder.name = (TextView) rootView.findViewById(R.id.sensor_name);
            holder.code = (TextView) rootView.findViewById(R.id.sensor_code);
            holder.type = (TextView) rootView.findViewById(R.id.sensor_type);
            holder.material = (TextView) rootView.findViewById(R.id.sensor_material);
            holder.location = (TextView) rootView.findViewById(R.id.sensor_local);
            rootView.setTag(holder);
        } else {
            holder = (ViewHolder) rootView.getTag();
        }

        Sensor item = mData.get(i);
        holder.name.setText(item.getName());
        holder.code.setText(item.getCode());
        holder.type.setText(item.getType());
        holder.material.setText(item.getMaterial());
        holder.location.setText(item.getLocation());
        return rootView;
    }

    private static class ViewHolder {
        public TextView name = null;
        public TextView code = null;
        public TextView type = null;
        public TextView material = null;
        public TextView location = null;
    }
}
