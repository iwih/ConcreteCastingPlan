package com.iwih.concretecastingplan;

import android.view.View;
import android.widget.CheckBox;
import android.widget.TextView;

/**
 * Created by iwih on 06/09/2017.
 */
public class MouldRowViewHolder {
    View mView;

    CheckBox selectionCheckBox;
    TextView mouldIdTextView;
    TextView mouldNameTextView;
    TextView mouldSizeTextView;

    public MouldRowViewHolder(View mView) {
        this.mView = mView;
        selectionCheckBox = (CheckBox) this.mView.findViewById(R.id.mould_row_checkbox);
        mouldIdTextView = (TextView) this.mView.findViewById(R.id.mould_id_for_row);
        mouldNameTextView = (TextView) this.mView.findViewById(R.id.mould_name_txt);
        mouldSizeTextView = (TextView) this.mView.findViewById(R.id.mould_size_txt);
    }
}
