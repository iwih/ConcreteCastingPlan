package com.iwih.concretecastingplan;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
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
        View rowView = view;
        MouldRowViewHolder viewHolder = null;

        if (rowView == null) {
            LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            rowView = inflater.inflate(R.layout.mould_row, viewGroup, false);
            viewHolder = new MouldRowViewHolder(rowView);
            rowView.setTag(viewHolder);
        } else {
            viewHolder = (MouldRowViewHolder) rowView.getTag();
            Log.v("ConcreteCastingPlan", "I've just used a recycled view! WOW!");
            viewHolder.selectionCheckBox.setOnCheckedChangeListener(null);
            rowView.setOnClickListener(null);
        }

        MouldType currentMould = mValues.get(i);

        viewHolder.selectionCheckBox.setChecked(currentMould.isCheckedOnRowView());

        viewHolder.mouldIdTextView.setText(String.valueOf(i));

        viewHolder.mouldNameTextView.setText(currentMould.getMouldName());

        viewHolder.mouldSizeTextView.setText(String.valueOf(currentMould.getMouldSize()));

        viewHolder.selectionCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                View parentView = (View) compoundButton.getParent();
                onRowCheckedChanged(parentView, b);
            }
        });

        rowView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //assign event listener to the clicks on the row view
                switchCheckBox(view);
            }
        });

        return rowView;
    }

    private void switchCheckBox(View view) {
        CheckBox checkBox = (CheckBox) view.findViewById(R.id.mould_row_checkbox);
        checkBox.setChecked(!checkBox.isChecked());
    }

    private void onRowCheckedChanged(View parentView, boolean checked) {
        //get the textview for the id of the row

        TextView rowIdTextView = ((MouldRowViewHolder) parentView.getTag()).mouldIdTextView;

        String rowIdString = rowIdTextView.getText().toString();
        int rowId = Integer.parseInt(rowIdString);

        // set the checked value for the corresponding row upto the user choice
        mValues.get(rowId).setCheckedOnRowView(checked);

        ((MainActivity) mContext).RefreshConcreteQuantity();
    }
}
