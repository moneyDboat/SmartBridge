package com.captain.smartbridge.UI.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.captain.smartbridge.R;
import com.captain.smartbridge.model.SimpleTexts;

import java.util.List;

/**
 * Created by fish on 17-5-15.
 */

public class TextsListAdapter extends BaseAdapter {

    private static int counter = 0;
    List<SimpleTexts> mData = null;
    Context mContext = null;

    public TextsListAdapter(Context context, List<SimpleTexts> data){
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
            rootView = inflater.inflate(R.layout.list_simple_text2, viewGroup, false);
            holder = new ViewHolder();
            holder.NameText = (TextView) rootView.findViewById(R.id.build_name);
            holder.kindText = (TextView) rootView.findViewById(R.id.build_kind);
            holder.numText = (TextView) rootView.findViewById(R.id.build_num);
            holder.categoryText = (TextView) rootView.findViewById(R.id.build_category);
            rootView.setTag(holder);
        } else {
            holder = (ViewHolder) rootView.getTag();
        }

        SimpleTexts item = mData.get(i);
        holder.NameText.setText(item.getName());
        holder.kindText.setText(item.getKind());
        holder.numText.setText(item.getNum());
        holder.categoryText.setText(item.getCategory());

        return rootView;
    }

    private static class ViewHolder{
        public TextView NameText = null;
        public TextView kindText = null;
        public TextView numText = null;
        public TextView categoryText = null;
    }
}
