// vim: et sw=4 sts=4 tabstop=4
package com.issc.impl;

import com.issc.Bluebit;
import com.issc.R;
import com.issc.util.UuidMatcher;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.UUID;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.LayoutInflater;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class FunctionAdapter extends BaseAdapter {

    private final static int sLayoutRes = R.layout.row_detail;
    private final LayoutInflater mInflater;

    private ArrayList<UuidMatcher> mOptions;
    private ArrayList<UuidMatcher> mResults;
    private ArrayList<UUID> mUuids;

    public FunctionAdapter(Context context) {
        super();
        mOptions  = new ArrayList<UuidMatcher>();
        mResults  = new ArrayList<UuidMatcher>();
        mUuids    = new ArrayList<UUID>();
        mInflater = LayoutInflater.from(context);

        initOptions(mOptions);
    }

    public void addUuidInUiThread(UUID uuid) {
        if (mUuids.contains(uuid)) {
            return;
        }

        mUuids.add(uuid);
        updateDataSet();
    }

    private void updateDataSet() {
        mResults.clear();
        Iterator<UuidMatcher> it = mOptions.iterator();
        while (it.hasNext()) {
            UuidMatcher target = it.next();
            if (target.equals(mUuids)) {
                mResults.add(target);
            }
        }

        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return mResults.size();
    }

    @Override
    public Object getItem(int pos) {
        return mResults.get(pos);
    }

    @Override
    public long getItemId(int pos) {
        return pos;
    }

    @Override
    public View getView(int pos, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = mInflater.inflate(sLayoutRes, null);
        }

        TextView title = (TextView)convertView.findViewById(R.id.row_title);
        TextView desc = (TextView)convertView.findViewById(R.id.row_description);
        UuidMatcher handler = mResults.get(pos);
        title.setText(handler.getTitle());
        desc.setText(handler.getDesc());

        return convertView;
    }

    private void initOptions(ArrayList<UuidMatcher> options) {
        addLighting(options);
    }

    private void addLighting(ArrayList<UuidMatcher> options) {
        UuidMatcher matcher = new UuidMatcher();
        matcher.setTarget("com.issc.ui", "com.issc.ui.ActivityAIO");
        for (int i = 0; i < Bluebit.UUIDS_OF_LIGHTING.length; i++) {
            matcher.addRule(Bluebit.UUIDS_OF_LIGHTING[i]);
        }

        matcher.setInfo("Lighting", "Control the light");
        options.add(matcher);
    }
}