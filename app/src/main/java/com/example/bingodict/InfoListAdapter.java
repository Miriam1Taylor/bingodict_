// InfoListAdapter.java
package com.example.bingodict;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

public class InfoListAdapter extends BaseAdapter {
    private Context context;
    private List<WordBean> mDatas;

    public InfoListAdapter(Context context, List<WordBean> mDatas) {
        this.context = context;
        this.mDatas = mDatas;
    }

    @Override
    public int getCount() {
        return mDatas.size();
    }

    @Override
    public Object getItem(int position) {
        return mDatas.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_infolist_lv, null);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        // Adding log statement for debugging
        Log.d("InfoListAdapter", "getView: position = " + position + ", mDatas size = " + mDatas.size());

        if (position < mDatas.size()) {
            WordBean wordBean = mDatas.get(position);
            holder.titleTv.setText(wordBean.getTitle());
            holder.transTv.setText(wordBean.getTran());
        } else {
            Log.e("InfoListAdapter", "getView: Invalid position: " + position);
        }

        return convertView;
    }

    static class ViewHolder {
        TextView titleTv;
        TextView transTv;

        ViewHolder(View view) {
            titleTv = view.findViewById(R.id.item_info_tv_title);
            transTv = view.findViewById(R.id.item_info_tv_translation);
        }
    }
}
