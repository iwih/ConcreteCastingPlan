package com.iwih.concretecastingplan;

import android.content.Context;
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
        LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = view;
        if (rowView == null)
            rowView = inflater.inflate(R.layout.mould_row, viewGroup, false);

        CheckBox checkBox = (CheckBox) rowView.findViewById(R.id.mould_row_checkbox);
        checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
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

        MouldType currentMould = mValues.get(i);

        TextView rowIdTextView = (TextView) rowView.findViewById(R.id.mould_id_for_row);
        rowIdTextView.setText(String.valueOf(i));

        TextView mouldNameTxtView = (TextView) rowView.findViewById(R.id.mould_name_txt);
        mouldNameTxtView.setText(currentMould.getMouldName());

        TextView mouldSizeTxtView = (TextView) rowView.findViewById(R.id.mould_size_txt);
        mouldSizeTxtView.setText(String.valueOf(currentMould.getMouldSize()));


        return rowView;
    }

    private void switchCheckBox(View view) {
        CheckBox checkBox = (CheckBox) view.findViewById(R.id.mould_row_checkbox);
        checkBox.setChecked(!checkBox.isChecked());
    }

    private void onRowCheckedChanged(View parentView, boolean checked) {
        //get the textview for the id of the row

        TextView rowIdTextView = (TextView) parentView.findViewById(R.id.mould_id_for_row);

        String rowIdString = rowIdTextView.getText().toString();
        int rowId = Integer.parseInt(rowIdString);

        // set the checked value for the corresponding row upto the user choice
        mValues.get(rowId).setCheckedOnRowView(checked);

        ((MainActivity) mContext).RefreshConcreteQuantity();
    }
}
