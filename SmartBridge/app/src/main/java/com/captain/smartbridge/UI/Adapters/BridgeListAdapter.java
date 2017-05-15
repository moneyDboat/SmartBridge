package com.captain.smartbridge.UI.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.captain.smartbridge.R;
import com.captain.smartbridge.model.BridgeList;

import java.util.List;

/**
 * Created by fish on 17-5-15.
 */

public class BridgeListAdapter extends BaseAdapter {
    private static int counter = 0;
    List<BridgeList> mData = null;
    Context mContext = null;

    public BridgeListAdapter(Context context, List<BridgeList> data){
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
        View rootView = null;

        if (view == null) {
            LayoutInflater inflater = LayoutInflater.from(mContext);
            rootView = inflater.inflate(R.layout.list_bridge, viewGroup, false);
            holder = new ViewHolder();
            holder.bridgeName = (TextView) rootView.findViewById(R.id.list_bridge_name);
            holder.bridgeCode = (TextView) rootView.findViewById(R.id.list_bridge_code);
            holder.bridgeLocation = (TextView) rootView.findViewById(R.id.list_bridge_location);
            rootView.setTag(holder);
        } else {
            holder = (ViewHolder) rootView.getTag();
        }

        BridgeList item = mData.get(i);
        holder.bridgeName.setText(item.getName());
        holder.bridgeCode.setText(item.getCode());
        holder.bridgeLocation.setText(item.getLocation());
        return rootView;
    }

    private static class ViewHolder{
        public TextView bridgeName = null;
        public TextView bridgeCode = null;
        public TextView bridgeLocation = null;
    }
}
