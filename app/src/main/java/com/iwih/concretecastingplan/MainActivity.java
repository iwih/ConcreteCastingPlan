package com.iwih.concretecastingplan;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.iwih.concretecastingplan.data.DatabaseMouldsSQLite;
import com.iwih.concretecastingplan.data.Schema_MouldsTable;
import com.iwih.concretecastingplan.viewdata.DataAdapter;
import com.iwih.concretecastingplan.data.MouldType;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private static DataAdapter mouldsListDataAdapter;
    private static DatabaseMouldsSQLite mouldsDatabase;

    private ListView mouldsListView = null;
    private TextView totalConcreteTextView = null;
    private TextView selectedMouldsCountTextView = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        initializeDatabase();

        initializeDataAdapter();

        refreshConcreteQuantity();
    }

    private void initializeDataAdapter() {
        if (mouldsListDataAdapter == null) {
            ArrayList<MouldType> listMoulds = null;
            SQLiteDatabase mouldsDb = mouldsDatabase.getReadableDatabase();
            Cursor mouldsCursor = mouldsDb.query(Schema_MouldsTable.TABLE_NAME, null, null, null, null, null, null);

            int countMoulds = mouldsCursor.getCount();
            Log.v("ConreteCastingPlan", String.valueOf(countMoulds) + " mould(s) retrieved.");
            if (countMoulds > 0) {
                listMoulds = new ArrayList<>();
                for (int i = 0; i < countMoulds; i++) {
                    MouldType mould = new MouldType(
                            mouldsCursor.getLong(mouldsCursor.getColumnIndex(Schema_MouldsTable.ID_COLUMN)),
                            mouldsCursor.getString(mouldsCursor.getColumnIndex(Schema_MouldsTable.NAME_MOULD_COLUMN)),
                            mouldsCursor.getDouble(mouldsCursor.getColumnIndex(Schema_MouldsTable.SIZE_MOULD_COLUMN)));
                    listMoulds.add(mould);
                }
            }
            mouldsCursor.close();
            if (listMoulds != null)
                mouldsListDataAdapter = new DataAdapter(this, listMoulds);
        } else {
            mouldsListDataAdapter.setContext(this);
        }

        attachDataAdapterToListView();
    }

    private void attachDataAdapterToListView() {
        if (mouldsListDataAdapter != null)
            if (mouldsListDataAdapter.getCount() > 0) {
                mouldsListView = (ListView) findViewById(R.id.moulds_list_view);
                mouldsListView.setAdapter(mouldsListDataAdapter);
            }
    }

    private void initializeDatabase() {
        if (mouldsDatabase != null) return;
        mouldsDatabase = new DatabaseMouldsSQLite(getApplicationContext());
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
        switch (id) {
            case R.id.action_edit_moulds:

                break;
            case R.id.action_exit_app:
                finish();
                break;
        }
        if (id == R.id.action_edit_moulds) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void refreshConcreteQuantity() {
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

    public void unselectFAB(View view) {

    }

    public void editMouldsFAB(View view) {
        Toast.makeText(this, "Just checking the fab button", Toast.LENGTH_SHORT).show();
    }
}
