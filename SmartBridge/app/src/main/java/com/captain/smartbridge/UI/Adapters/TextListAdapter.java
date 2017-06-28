package com.captain.smartbridge.UI.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.captain.smartbridge.R;
import com.captain.smartbridge.model.SimpleText;

import java.util.List;

/**
 * Created by fish on 17-5-15.
 */

public class TextListAdapter extends BaseAdapter {

    private static int counter = 0;
    List<SimpleText> mData = null;
    Context mContext = null;

    public TextListAdapter(Context context, List<SimpleText> data){
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
            rootView = inflater.inflate(R.layout.list_simple_text, viewGroup, false);
            holder = new ViewHolder();
            holder.titleView = (TextView) rootView.findViewById(R.id.list_simple_title);
            holder.contextView = (TextView) rootView.findViewById(R.id.list_simple_context);
            rootView.setTag(holder);
        } else {
            holder = (ViewHolder) rootView.getTag();
        }

        SimpleText item = mData.get(i);
        holder.titleView.setText(item.getTitle());
        holder.contextView.setText(item.getContext());

        return rootView;
    }

    private static class ViewHolder{
        public TextView titleView = null;
        public TextView contextView = null;
    }
}
