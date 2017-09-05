package com.iwih.concretecastingplan;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * Created by iwih on 05/09/2017.
 */

public class DataAdapter extends BaseAdapter {

    private Context mContext;
    private List<MouldType> mValues;

    public DataAdapter(Context mContext, List<MouldType> mValues) {
        this.mContext = mContext;
        this.mValues = mValues;
    }

    @Override
    public int getCount() {
        return mValues.size();
    }

    @Override
    public MouldType getItem(int i) {
        return mValues.get(i);
    }

    @Override
    public long getItemId(int i) {
        return mValues.get(i).get_id();
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = view;
        if (rowView == null)
            rowView = inflater.inflate(R.layout.mould_row, viewGroup, false);

        TextView mouldNameTxtView = (TextView) rowView.findViewById(R.id.mould_name_txt);
        mouldNameTxtView.setText(mValues.get(i).getMouldName());

        TextView mouldSizeTxtView = (TextView) rowView.findViewById(R.id.mould_size_txt);
        mouldSizeTxtView.setText(String.valueOf(mValues.get(i).getMouldSize()));

        return rowView;
    }
}
