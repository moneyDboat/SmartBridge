package com.captain.smartbridge.UI.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.captain.smartbridge.Common.CommonUtils;
import com.captain.smartbridge.R;
import com.captain.smartbridge.model.Mission;

import java.util.List;

/**
 * Created by fish on 17-5-17.
 */

public class DeMissionListAdapter extends BaseAdapter {
    private static int counter = 0;
    List<Mission> mData = null;
    Context mContext = null;

    public DeMissionListAdapter(Context context, List<Mission> data){
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
            rootView = inflater.inflate(R.layout.list_misson_status, viewGroup, false);
            holder = new ViewHolder();
            holder.nameView = (TextView) rootView.findViewById(R.id.mission_stname);
            holder.codeView = (TextView) rootView.findViewById(R.id.mission_stcode);
            holder.asignView = (TextView) rootView.findViewById(R.id.mission_stasign);
            holder.detectView = (TextView) rootView.findViewById(R.id.mission_stdetect);
            holder.statusView = (TextView) rootView.findViewById(R.id.mission_status);
            rootView.setTag(holder);
        } else {
            holder = (ViewHolder) rootView.getTag();
        }

        Mission item = mData.get(i);
        holder.nameView.setText(item.getName());
        holder.codeView.setText(item.getCode());
        holder.asignView.setText(item.getAsign());
        holder.detectView.setText(item.getDetect());
        holder.statusView.setText(CommonUtils.getStatus(String.valueOf(item.getStatus())));

        return rootView;
    }

    private static class ViewHolder{
        public TextView nameView = null;
        public TextView codeView = null;
        public TextView asignView = null;
        public TextView detectView = null;
        public TextView statusView = null;
    }
}
