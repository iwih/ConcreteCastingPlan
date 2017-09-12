package com.iwih.concretecastingplan;

import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.iwih.concretecastingplan.Utilities.Logger;
import com.iwih.concretecastingplan.data.DatabaseMouldsSQLite;
import com.iwih.concretecastingplan.data.Schema_MouldsTable;
import com.iwih.concretecastingplan.viewdata.DataAdapter;
import com.iwih.concretecastingplan.data.MouldType;
import com.iwih.concretecastingplan.viewdata.MouldRowViewHolder;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private static DataAdapter listAdapterMoulds;
    private static DatabaseMouldsSQLite databaseMoulds;

    private ListView listViewMoulds = null;
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
        if (listAdapterMoulds == null) {
            ArrayList<MouldType> listMoulds = getMouldTypesFromDb();
            if (listMoulds != null)
                listAdapterMoulds = new DataAdapter(this, listMoulds);
        } else {
            listAdapterMoulds.setContext(this);
        }

        attachDataAdapterToListView();
    }

    private ArrayList<MouldType> getMouldTypesFromDb() {
        ArrayList<MouldType> listMoulds = null;
        try {
            SQLiteDatabase mouldsDb = databaseMoulds.getReadableDatabase();
            Cursor cursorMoulds = mouldsDb.query(Schema_MouldsTable.TABLE_NAME, null, null, null, null, null, null);

            int countMoulds = cursorMoulds.getCount();
            Logger.v(String.valueOf(countMoulds) + " mould(s) retrieved.");
            if (countMoulds > 0) {
                listMoulds = new ArrayList<>();
                while (cursorMoulds.moveToNext()) {
                    MouldType mould = new MouldType(
                            cursorMoulds.getLong(cursorMoulds.getColumnIndex(Schema_MouldsTable.ID_COLUMN)),
                            cursorMoulds.getString(cursorMoulds.getColumnIndex(Schema_MouldsTable.NAME_MOULD_COLUMN)),
                            cursorMoulds.getDouble(cursorMoulds.getColumnIndex(Schema_MouldsTable.SIZE_MOULD_COLUMN)));
                    listMoulds.add(mould);
                }
            }
            cursorMoulds.close();
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
        return listMoulds;
    }

    private void attachDataAdapterToListView() {
        if (listAdapterMoulds != null)
            if (listAdapterMoulds.getCount() > 0) {
                listViewMoulds = (ListView) findViewById(R.id.moulds_list_view);
                listViewMoulds.setAdapter(listAdapterMoulds);
            }
    }

    private void initializeDatabase() {
        if (databaseMoulds != null) return;
        databaseMoulds = new DatabaseMouldsSQLite(getApplicationContext());
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
                startEditMouldsActivity();
                break;
            case R.id.action_exit_app:
                finish();
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    private void startEditMouldsActivity() {
        Intent intent = new Intent(this, EditMouldsActivity.class);
        startActivity(intent);
    }

    public void refreshConcreteQuantity() {
        if (listViewMoulds == null || !isRefreshingConcreteQuantity) return;
        Logger.v("Refreshing concrete quantity display!");
        double totalConcreteQuantity = 0.0;
        int selectedMouldsCount = 0;

        DataAdapter dataAdapter = (DataAdapter) listViewMoulds.getAdapter();

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

    public void unselectFAB(View view) throws InterruptedException {
        final AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);

        alertDialog.setTitle("Confirm Opt");
        alertDialog.setMessage("Are you sure to un-select all the moulds?!");
        alertDialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //insertDumpMouldsIntoDb();
                unselectAllMoulds();
            }
        });
        alertDialog.setNegativeButton("Cancel", null);
        alertDialog.create().show();
    }

    private boolean isRefreshingConcreteQuantity = true;

    private void unselectAllMoulds() {
        int countMoulds = listViewMoulds.getCount();
        if (countMoulds <= 0) return;
        try {
            isRefreshingConcreteQuantity = false;

            for (int i = 0; i < countMoulds; i++) {
                MouldRowViewHolder currentViewMould = (MouldRowViewHolder)
                        listViewMoulds.getAdapter().getView(i, listViewMoulds.getChildAt(i), listViewMoulds).getTag();
                currentViewMould.selectionCheckBox.setChecked(false);
            }
        } catch (Exception exception) {
            exception.printStackTrace();
        } finally {
            isRefreshingConcreteQuantity = true;
            refreshConcreteQuantity();
        }
    }

    private void insertDumpMouldsIntoDb() {
        int countMoulds = 30;
        SQLiteDatabase mouldsSQLite = databaseMoulds.getReadableDatabase();
        for (int i = 0; i < countMoulds; i++) {
            MouldType mould = new MouldType(i, "MM" + i, countMoulds + i);
            ContentValues contentRowMould = new ContentValues();
            contentRowMould.put(Schema_MouldsTable.NAME_MOULD_COLUMN, mould.getMouldName());
            contentRowMould.put(Schema_MouldsTable.SIZE_MOULD_COLUMN, mould.getMouldSize());
            long insertedId = mouldsSQLite.insert(Schema_MouldsTable.TABLE_NAME, null, contentRowMould);
            Log.v("ConcreteCastingPlan", String.valueOf(insertedId) + " insertion result..");
        }
        mouldsSQLite.close();
    }
}
