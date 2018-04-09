package com.captain.smartbridge.UI.Adapters.tian;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.captain.smartbridge.R;
import com.captain.smartbridge.UI.Adapters.SensorDataAdapter;
import com.captain.smartbridge.model.other.MonData;

import java.util.List;

/**
 * Created by captain on 18-4-9.
 */

public class WarnListAdapter extends BaseAdapter {
    private static int counter = 0;
    List<MonData> mData = null;
    Context mContext = null;

    public WarnListAdapter(Context context, List<MonData> data){
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
        WarnListAdapter.ViewHolder holder = null;
        View rootView = view;

        if (view == null) {
            LayoutInflater inflater = LayoutInflater.from(mContext);
            rootView = inflater.inflate(R.layout.list_warning, viewGroup, false);
            holder = new WarnListAdapter.ViewHolder();
            holder.titleView = (TextView) rootView.findViewById(R.id.list_warning_title);
            holder.contextView = (TextView) rootView.findViewById(R.id.list_warning_context);
            rootView.setTag(holder);
        } else {
            holder = (WarnListAdapter.ViewHolder) rootView.getTag();
        }

        MonData item = mData.get(i);
        holder.titleView.setText(item.getTime());
        holder.contextView.setText(item.getValue());

        return rootView;
    }

    private static class ViewHolder{
        public TextView titleView = null;
        public TextView contextView = null;
    }


}
