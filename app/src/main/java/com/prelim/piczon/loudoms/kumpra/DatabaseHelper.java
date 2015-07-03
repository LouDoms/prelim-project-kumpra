package com.prelim.piczon.loudoms.kumpra;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;

import java.util.ArrayList;

/**
 * Created by LouDoms on 7/1/2015.
 */
public final class DatabaseHelper extends SQLiteOpenHelper {

    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "GroceryListApplication.db";
    private SQLiteDatabase db;

    private static final String CREATE_LIST_TABLE = "CREATE TABLE "
            + DatabaseEntry.LIST_TABLE_NAME + " ("
            + DatabaseEntry.COLUMN_NAME_ITEM_ID
            + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + DatabaseEntry.COLUMN_NAME_ITEM + " TEXT, "
            + DatabaseEntry.COLUMN_NAME_QUANTITY + " TEXT " + " )";

    public static abstract class DatabaseEntry implements BaseColumns {
        public static final String LIST_TABLE_NAME = "list_table";
        public static final String COLUMN_NAME_ITEM_ID = "item_id";
        public static final String COLUMN_NAME_ITEM = "item";
        public static final String COLUMN_NAME_QUANTITY = "quantity";
    }

    private static final String SQL_DELETE_LIST_TABLE = "DROP TABLE IF EXISTS "
            + DatabaseEntry.LIST_TABLE_NAME;

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_LIST_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(SQL_DELETE_LIST_TABLE);
        onCreate(db);
    }

    public long addItem(GroceryList groceryList) {

        db = getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(DatabaseEntry.COLUMN_NAME_ITEM, groceryList.getItem());
        values.put(DatabaseEntry.COLUMN_NAME_QUANTITY, groceryList.getQuantity());

        return db.insert(DatabaseEntry.LIST_TABLE_NAME, null, values);
    }

    public long deleteItem(String item_id) {

        db = getWritableDatabase();

        String selection = DatabaseEntry.COLUMN_NAME_ITEM_ID + "=?";

        String[] selectionArgs = { String.valueOf(item_id) };

        return db.delete(DatabaseEntry.LIST_TABLE_NAME, selection, selectionArgs);
    }

    public ArrayList<GroceryList> getList() {

        ArrayList<GroceryList> list = new ArrayList<GroceryList>();

        db = getReadableDatabase();

        String[] projection = { DatabaseEntry.COLUMN_NAME_ITEM_ID,
                DatabaseEntry.COLUMN_NAME_ITEM,
                DatabaseEntry.COLUMN_NAME_QUANTITY };

        String sortOrder = DatabaseEntry.COLUMN_NAME_ITEM + " ASC";

        Cursor c = db.query(DatabaseEntry.LIST_TABLE_NAME, projection, null,
                null, null, null, sortOrder);

        c.moveToFirst();

        while(!c.isAfterLast()) {
            long id = c.getLong(c.getColumnIndexOrThrow(DatabaseEntry.COLUMN_NAME_ITEM_ID));
            String item = c.getString(c.getColumnIndexOrThrow(DatabaseEntry.COLUMN_NAME_ITEM));
            String quantity = c.getString(c.getColumnIndexOrThrow(DatabaseEntry.COLUMN_NAME_QUANTITY));

            GroceryList groceryList = new GroceryList(id, item, quantity);
            list.add(groceryList);

            c.moveToNext();
        }

        return list;
    }
}
