package com.iwih.concretecastingplan.data;

import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by iwih on 09/09/2017.
 */

public class DatabaseMouldsSQLite extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "moulds_db.db";
    private static final int VERSION_DATABASE = 1;
    private Context mContext;

    public DatabaseMouldsSQLite(Context context) {
        super(context, DATABASE_NAME, null, VERSION_DATABASE);
        mContext = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String creationQuery = String.format("CREATE TABLE %s (%s INTEGER PRIMARY KEY AUTOINCREMENT, %s TEXT NOT NULL, %s REAL NOT NULL);",
                Schema_MouldsTable.TABLE_NAME,
                Schema_MouldsTable.ID_COLUMN,
                Schema_MouldsTable.NAME_MOULD_COLUMN,
                Schema_MouldsTable.SIZE_MOULD_COLUMN);
        db.execSQL(creationQuery);
        try {
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public boolean dropTable(String tableName) {
        try {
            String queryDrop = String.format("DROP TABLE IF EXISTS %s", tableName);
            this.getWritableDatabase().execSQL(queryDrop);
            return true;
        } catch (SQLException exception) {
            exception.printStackTrace();
            return false;
        }
    }

    public boolean removeDatabase() {
        try {
            mContext.deleteDatabase(DATABASE_NAME);
            return true;
        } catch (SQLException exception) {
            exception.printStackTrace();
            return false;
        }
    }

}
