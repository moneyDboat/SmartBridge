package com.captain.smartbridge.UI.Adapters.other;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.captain.smartbridge.R;
import com.captain.smartbridge.UI.Adapters.tian.WarnListAdapter;
import com.captain.smartbridge.model.Plane;
import com.captain.smartbridge.model.other.MonData;

import java.util.List;

/**
 * Created by captain on 18-5-15.
 */

public class PlaneListAdapter extends BaseAdapter {
    private static int counter = 0;
    List<Plane> mData = null;
    Context mContext = null;

    public PlaneListAdapter(Context context, List<Plane> data) {
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
        PlaneListAdapter.ViewHolder holder = null;
        View rootView = view;

        if (view == null) {
            LayoutInflater inflater = LayoutInflater.from(mContext);
            rootView = inflater.inflate(R.layout.list_plane, viewGroup, false);
            holder = new PlaneListAdapter.ViewHolder();
            holder.titleView = (TextView) rootView.findViewById(R.id.list_plane_name);
            holder.contextView = (TextView) rootView.findViewById(R.id.list_plane_date);
            rootView.setTag(holder);
        } else {
            holder = (PlaneListAdapter.ViewHolder) rootView.getTag();
        }

        Plane item = mData.get(i);
        holder.titleView.setText(item.getName());
        holder.contextView.setText(item.getDate());

        return rootView;
    }

    private static class ViewHolder {
        public TextView titleView = null;
        public TextView contextView = null;
    }
}
