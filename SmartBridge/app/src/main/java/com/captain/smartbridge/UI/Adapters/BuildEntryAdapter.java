package com.captain.smartbridge.UI.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.captain.smartbridge.R;

import java.util.List;

/**
 * Created by fish on 17-6-13.
 */

public class BuildEntryAdapter extends BaseAdapter{
    private static int counter = 0;
    List<String> mData = null;
    Context mContext = null;

    public BuildEntryAdapter(Context context, List<String> data){
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
            rootView = inflater.inflate(R.layout.list_build_entry, viewGroup, false);
            holder = new ViewHolder();
            holder.buildName = (TextView) rootView.findViewById(R.id.build_entry_name);
            rootView.setTag(holder);
        } else {
            holder = (ViewHolder) rootView.getTag();
        }

        String item = mData.get(i);
        holder.buildName.setText(item);
        return rootView;
    }

    private static class ViewHolder{
        public TextView buildName = null;
    }
}
