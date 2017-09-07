package com.iwih.concretecastingplan;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.TextView;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private ListView mouldsListView = null;
    private TextView totalConcreteTextView = null;
    private TextView selectedMouldsCountTextView = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ArrayList<MouldType> list = new ArrayList<>();
        for (int i = 0; i < 30; i++) {
            MouldType mould = new MouldType(i, "MM" + i, 30 + i);
            list.add(mould);
        }

        mouldsListView = (ListView) findViewById(R.id.moulds_list_view);
        mouldsListView.setAdapter(new DataAdapter(this, list));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void RefreshConcreteQuantity() {
        if (mouldsListView == null) return;
        double totalConcreteQuantity = 0.0;
        int selectedMouldsCount = 0;

        DataAdapter dataAdapter = (DataAdapter) mouldsListView.getAdapter();

        for (int i = 0; i < dataAdapter.getCount(); i++) {
            MouldType row = dataAdapter.getItem(i);
            if (row.isCheckedOnRowView()) {
                totalConcreteQuantity += row.getMouldSize();
                selectedMouldsCount++;
            }
        }

        String totalConcreteQuantityStr = new DecimalFormat("#.##").format(totalConcreteQuantity);

        if (totalConcreteTextView == null)
            totalConcreteTextView = (TextView) findViewById(R.id.total_concrete_txtview);
        if (selectedMouldsCountTextView == null)
            selectedMouldsCountTextView = (TextView) findViewById(R.id.count_selected_moulds_txtview);

        totalConcreteTextView.setText(totalConcreteQuantityStr);
        selectedMouldsCountTextView.setText(String.valueOf(selectedMouldsCount));
    }
}
