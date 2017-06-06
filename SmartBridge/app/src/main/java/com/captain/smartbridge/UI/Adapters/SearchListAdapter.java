package com.captain.smartbridge.UI.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.captain.smartbridge.R;
import com.captain.smartbridge.model.SearchCodeRes;

import java.util.List;

/**
 * Created by fish on 17-6-6.
 */

public class SearchListAdapter extends BaseAdapter {
    private static int counter = 0;
    List<SearchCodeRes> mData = null;
    Context mContext = null;

    public SearchListAdapter(Context context, List<SearchCodeRes> data){
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
            rootView = inflater.inflate(R.layout.list_search, viewGroup, false);
            holder = new ViewHolder();
            holder.name = (TextView) rootView.findViewById(R.id.search_name);
            holder.location = (TextView) rootView.findViewById(R.id.search_location);
            rootView.setTag(holder);
        } else {
            holder = (ViewHolder) rootView.getTag();
        }

        SearchCodeRes item = mData.get(i);
        holder.name.setText(item.getQlmc());
        holder.location.setText(item.getSf()+item.getCs()+item.getQx());
        return rootView;
    }

    private static class ViewHolder{
        public TextView name = null;
        public TextView location = null;
    }
}
