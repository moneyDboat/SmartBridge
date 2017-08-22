package com.captain.smartbridge.UI.Adapters.other;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.captain.smartbridge.R;
import com.captain.smartbridge.model.other.EvaluteMess;

import java.util.List;

/**
 * Created by Captain on 17/8/21.
 */

public class EvMissAdapter extends BaseAdapter{
    private static int counter = 0;
    List<EvaluteMess> mData = null;
    Context mContext = null;

    public EvMissAdapter(Context context, List<EvaluteMess> data) {
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
            rootView = inflater.inflate(R.layout.list_evmiss, viewGroup, false);
            holder = new ViewHolder();
            holder.name = (TextView) rootView.findViewById(R.id.evmiss_name);
            holder.code = (TextView) rootView.findViewById(R.id.evmiss_code);
            holder.location = (TextView) rootView.findViewById(R.id.evmiss_location);
            rootView.setTag(holder);
        } else {
            holder = (ViewHolder) rootView.getTag();
        }

        EvaluteMess item = mData.get(i);
        holder.name.setText(item.getQlmc());
        holder.code.setText(item.getScore().substring(0, 6));
        holder.location.setText(item.getQlwz());
        return rootView;
    }

    private static class ViewHolder {
        public TextView name = null;
        public TextView code = null;
        public TextView location = null;
    }


}
