package com.iwih.concretecastingplan;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;

public class EditMouldsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_moulds);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        setColorChangingListener();
    }

    private void setColorChangingListener() {
        Button addMouldBtn = (Button) findViewById(R.id.edit_moulds_add_new);
        Button removeMouldBtn = (Button) findViewById(R.id.edit_moulds_remove_selected);

        View.OnTouchListener onTouchListener = colorChangingListener();
        addMouldBtn.setOnTouchListener(onTouchListener);
        removeMouldBtn.setOnTouchListener(onTouchListener);
    }

    @NonNull
    private View.OnTouchListener colorChangingListener() {
        return new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        v.setBackgroundColor(getResources().getColor(R.color.button_down));
                        break;
                    case MotionEvent.ACTION_UP:
                        v.setBackgroundColor(getResources().getColor(R.color.button_normal));
                        break;
                }
                return false;
            }
        };
    }

}
